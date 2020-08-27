from __future__ import print_function
import numpy as np
import cv2 as cv
import datetime
import paho.mqtt.client as paho
import os
import socket
import ssl
import random
import string
import json
from time import sleep
from random import randint
 
connflag = False
 
def on_connect(client, userdata, flags, rc):                # func for making connection
    global connflag
    print ("Connected to AWS")
    connflag = True
    print("Connection returned result: " + str(rc) )
 
def on_message(client, userdata, msg):                      # Func for Sending msg
    print(msg.topic+" "+str(msg.payload))
    
def get_Station_Name(length):
    letters = string.ascii_lowercase
    result_str = ''.join(random.choice(letters) for i in range(length))
    print("Random string of length", length, "is:", result_str)
    return result_str
    
def getMAC(interface='eth0'):
  # Return the MAC address of the specified interface
  try:
    str = open('/sys/class/net/%s/address' %interface).read()
  except:
    str = "00:00:00:00:00:00"
  return str[0:17]
def getEthName():
  # Get name of the Ethernet interface
  try:
    for root,dirs,files in os.walk('/sys/class/net'):
      for dir in dirs:
        if dir[:3]=='enx' or dir[:3]=='eth':
          interface=dir
  except:
    interface="None"
  return interface

def inside(r, q):
    rx, ry, rw, rh = r
    qx, qy, qw, qh = q
    return rx > qx and ry > qy and rx + rw < qx + qw and ry + rh < qy + qh


def draw_detections(img, rects, thickness = 1):
    readc = []
    readc2 = []
    for x, y, w, h in rects:
        # the HOG detector returns slightly larger rectangles than the real objects.
        # so we slightly shrink the rectangles to get a nicer output.
        pad_w, pad_h = int(0.15*w), int(0.05*h)
        cv.rectangle(img, (x+pad_w, y+pad_h), (x+w-pad_w, y+h-pad_h), (0, 255, 0), thickness)
        w2 = int(w/2)
        h2 = int(h/2)
        cv.circle(img,(x+w2,y+h2),5,(255,255,255),3)
           
        readc.append((str(x+w2),str(y+h2)))
    #print(readc[0][0])
    #print(len(readc))
    #print(readc)
    for i in range(len(readc)):
        for j in range(len(readc)):
            a = abs(int(readc[i][0]) - int(readc[j][0]))
            b = abs(int(readc[i][1]) - int(readc[j][1]))
            if i != j:
                if a < 200:
                    if b < 100:
                        readc2.append(( int(readc[i][0]),int(readc[i][1]) ))
                        readc2.append(( int(readc[j][0]),int(readc[j][1]) ))
                        readc2 = list(set(readc2))
                        cv.line(img,(int(readc[i][0]),int(readc[i][1])),(int(readc[j][0]),int(readc[j][1])),(0,50,55),5)
                        if len(readc2) >= 15:
                            print("줄을 서고있는 인원이 15명 이상입니다.")
                            return(readc2)                
    return(readc2)
                        
def nameSelect(Name):
    busid = [206000040,206000087,206000082,2060000299,206000613,206000725]
    busname = ['ori','mikoom','bundangkoo_office','seohyun','imecheon','jungja']
    order = [5,7,10,5,6,9]
    busnumber = [8100,81004102,8100,4102,4102,81004102]
    if Name in busname:
        num = busname.index(Name)
    
        #print(busname.index(Name))
        #print(busid[num])
        return(busid[num],busname[num],order[num],busnumber[num])

    else:
        print("bus name incollect")

def main(cap,Name):
    import sys
    from glob import glob
    import itertools as it

    checkT = 0
    w = 640
    h = 480
    hog = cv.HOGDescriptor()
    hog.setSVMDetector( cv.HOGDescriptor_getDefaultPeopleDetector() )

    # bus station id, name select
    id,name,stationorder,busnumber = nameSelect(Name)
    
    
    while cap.isOpened():

        nowtime = datetime.datetime.now()
        ret,img = cap.read()
        if not ret:
            print("영상이 끝났습니다. 종료 중 ...")
            break
        frame = img
        rows, cols = frame.shape[:2]
        rotation_matrix = cv.getRotationMatrix2D((cols/2, rows/2), 90 , 1)
        image_rotation = cv.warpAffine(frame, rotation_matrix, (cols, rows))
        img = np.array(image_rotation)

        # 현재시간에서 과거에 측정한 시간을 뺐을때, 3초 이상이면, 새로 측정을 진행
        nowT =  int(nowtime.strftime('%S'))
        #print("현재 초 : ",nowT)
        resultT = abs(nowT - checkT)

        if resultT > 1:
            checkT = nowT
            
            found, _w = hog.detectMultiScale(img, winStride=(8,8), padding=(32,32), scale=1.05)
            found_filtered = []
            for ri, r in enumerate(found):
                for qi, q in enumerate(found):
                    if ri != qi and inside(r, q):
                        break
                else:
                    found_filtered.append(r)
            line = draw_detections(img, found)
            draw_detections(img, found_filtered, 3)
            if line == None:
                line = []
            fps = cap.get(cv.CAP_PROP_FPS) # 또는 cap.get(5)
            #print("초당 프레임 수: %d" %(fps))
            #print("줄을 서고있는 인원:",len(line))
            #print("파악된 인원:",'%d (%d)' % (len(found_filtered), len(found)),"명")
            #print(nowtime) 
            #현재 시간 출력


            #def on_log(client, userdata, level, buf):
            #    print(msg.topic+" "+str(msg.payload))
            mqttc = paho.Client()                                       # mqttc object
            mqttc.on_connect = on_connect                               # assign on_connect func
            mqttc.on_message = on_message                               # assign on_message func
            #mqttc.on_log = on_log

            #### Change following parameters #### 
            awshost = "a2x0wkr22clewa-ats.iot.ap-northeast-2.amazonaws.com"      # Endpoint
            awsport = 8883                                              # Port no.   
            clientId = "detect_test_Client"                                     # Thing_Name
            thingName = "detect_test_Client"                                    # Thing_Name
            caPath = "/home/pi/Downloads/AmazonRootCA1.pem"                                      # Root_CA_Certificate_Name
            certPath = "/home/pi/Downloads/aa34877af5-certificate.pem.crt"                            # <Thing_Name>.cert.pem
            keyPath = "/home/pi/Downloads/aa34877af5-private.pem.key"                          # <Thing_Name>.private.key
            
            mqttc.tls_set(caPath, certfile=certPath, keyfile=keyPath, cert_reqs=ssl.CERT_REQUIRED, tls_version=ssl.PROTOCOL_TLSv1_2, ciphers=None)  # pass parameters
            
            mqttc.connect(awshost, awsport, keepalive=60)               # connect to aws server
            
            mqttc.loop_start()                                          # Start the loop

            if connflag == True:
                ethName = getEthName()
                ethMAC = getMAC(ethName)
                macIdStr = ethMAC
                Detect_Number = randint(1,25)
                Station_Name= get_Station_Name(8)
                paylodmsg0="{"
                paylodmsg1 = "\"StationId\": "
                paylodmsg2 = "\", \"Detect_Number(People)\":"
                paylodmsg3 = ", \"station_Name\": \""
                paylodmsg4 = ", \"Experiment_Time\":\""
                paylodmsg5 =", \"Staorder\": "
                paylodmsg6 ="\", \"BusNumber\": "
                paylodmsg7 ="}"
                paylodmsg = "{} {} {} {} {} {} {} {} {} {} {} {} {} {}".format(paylodmsg0, paylodmsg1,id, paylodmsg3, name, paylodmsg2, Detect_Number,paylodmsg5 , stationorder, paylodmsg4,nowtime, paylodmsg6, busnumber ,paylodmsg7)
                paylodmsg = json.dumps(paylodmsg) 
                paylodmsg_json = json.loads(paylodmsg)       
                mqttc.publish("Detect_People", paylodmsg_json , qos=1)        # topic: temperature # Publishing Temperature values
                print("msg sent: Detect_People" ) # Print sent temperature msg on console
                print(paylodmsg_json)
            else:
                print("waiting for connection...")   
           

        img = cv.resize(img,(w,h))
        cv.imshow('frame', img)
            
        ch = cv.waitKey(30)& 0xff
        if ch == 27:
            print('Done')
            break   

if __name__ == '__main__':
    cap = cv.VideoCapture(0)
    Name = 'mikoom'
    sleep(5)
    main(cap,Name)
    print("BYE BYE")
    cv.destroyAllWindows()