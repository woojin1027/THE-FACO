from __future__ import print_function
import numpy as np
import cv2 as cv
import datetime
from time import sleep
from random import randint

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
                        

def main(cap,Name):
    import sys
    from glob import glob
    import itertools as it

    checkT = 0
    w = 640
    h = 480
    hog = cv.HOGDescriptor()
    hog.setSVMDetector( cv.HOGDescriptor_getDefaultPeopleDetector() )

    #a = cap.get(cv.CAP_PROP_FRAME_WIDTH)
    #b = cap.get(cv.CAP_PROP_FRAME_HEIGHT)
    #print(a,b)

    #재생할 파일의 프레임 얻기
    fps = cap.get(cv.CAP_PROP_FPS) # 또는 cap.get(5)
    
    while cap.isOpened():

        nowtime = datetime.datetime.now()
        ret,img = cap.read()
        if not ret:
            print("영상이 끝났습니다. 종료 중 ...")
            break
        frame = img
        rows, cols = frame.shape[:2]
        rotation_matrix = cv.getRotationMatrix2D((cols/2, rows/2), -90 , 1)
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
            
            """print("초당 프레임 수: %d" %(fps))
            print("줄을 서고있는 인원:",len(line))
            print("파악된 인원:",'%d (%d)' % (len(found_filtered), len(found)),"명")
            print(nowtime)"""
            #현재 시간 출력
            
            if len(line) >= 1:
                
                Detect_Number = randint(1,25)
                
                paylodmsg0="{"
                paylodmsg1 = "\"StationId\": \""
                paylodmsg2 = "\", \"Detect_Number(People)\":"
                paylodmsg3 = ", \"station_Name\": \""
                paylodmsg4 = ", \"Experiment_Time\": \""
                paylodmsg5 ="\"}"
                paylodmsg = "{} {} {} {} {} {} {} {}".format(paylodmsg0, paylodmsg1, Name, paylodmsg2, Detect_Number, paylodmsg4, nowtime, paylodmsg5)
                print(paylodmsg)
            else:
                print("waiting for connection...")   

        img = cv.resize(img,(w,h))
        cv.imshow('frame', img)
        

        ch = cv.waitKey(30)& 0xff
        if ch == 27:
            print('Done')
            break
    

if __name__ == '__main__':
    cap = cv.VideoCapture('133.mp4')
    Name = 'Sindorim'
    sleep(5)
    main(cap,Name)
    cv.destroyAllWindows()