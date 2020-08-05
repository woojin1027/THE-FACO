from __future__ import print_function
import numpy as np
import cv2 as cv
import datetime

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
                        

def main(cap):
    import sys
    from glob import glob
    import itertools as it

    hog = cv.HOGDescriptor()
    hog.setSVMDetector( cv.HOGDescriptor_getDefaultPeopleDetector() )

    while cap.isOpened():
        ret,img = cap.read()
        if not ret:
            print("영상이 끝났습니다. 종료 중 ...")
            break
        frame = img
        rows, cols = frame.shape[:2]
        rotation_matrix = cv.getRotationMatrix2D((cols/2, rows/2), -90 , 1)
        image_rotation = cv.warpAffine(frame, rotation_matrix, (cols, rows))
        img = np.array(image_rotation)
        img1 = img.copy()
        fps = cap.get(cv.CAP_PROP_FPS) # 또는 cap.get(5)
        
        if cap.get(1)%30 == 0:
            #30프레임에 한번씩 사람인 객체를 검사한다.
            print("초당 프레임 수: %d" %(fps))
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
            
            print("줄을 서고있는 인원:",len(line))
            print("파악된 인원:",'%d (%d)' % (len(found_filtered), len(found)),"명")

            nowtime = datetime.datetime.now()
            print(nowtime)
            #현재 시간 출력
        img = cv.resize(img,(900,500))
        cv.imshow('frame', img)
        
        ch = cv.waitKey(30)& 0xff
        if ch == 27:
            print('Done')
            break


if __name__ == '__main__':
    cap = cv.VideoCapture('132.mp4')
    main(cap)
    cv.destroyAllWindows()