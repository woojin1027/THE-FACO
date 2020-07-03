import cv2
import numpy as np
import time
import pyautogui

class ped():
    def lineDD(self,frame,wh,hi):
        src = cv2.resize(frame,(wh,hi))
        dst = src.copy()
        gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
        canny = cv2.Canny(gray, 5000, 1500, apertureSize = 5, L2gradient = True)
        e1 = cv2.getTickCount()#연산처리시간
        lines = cv2.HoughLines(canny, 0.8, np.pi / 180, 150, srn = 100, stn = 200, min_theta = 0, max_theta = np.pi)
        d = 0;d1 = 0;d2 = 0
        for i in lines:
            rho, theta = i[0][0], i[0][1]
            a, b = np.cos(theta), np.sin(theta)
            x0, y0 = a*rho, b*rho

            scale = src.shape[0] + src.shape[1]

            x1 = int(x0 + scale * -b)
            y1 = int(y0 + scale * a)
            x2 = int(x0 - scale * -b)
            y2 = int(y0 - scale * a)
            cv2.line(dst, (x1, y1), (x2, y2), (0, 0, 255), 2)
            
            a1 = (x2 - x1)
            b1 = (y2 - y1)
            c = a1*a1 + b1*b1
            if a1 > 0:
                if d < c:
                    d = c
                    d1 = abs(a1)
                    d2 = abs(b1)
                    Radian = np.arctan(d2 / d1)
        #print(Radian/(np.pi / 180.))
        e2 =cv2.getTickCount()#연산처리시간
        print("연산처리 시간은 : ",(e2 - e1)/cv2.getTickCount())#연산처리시간
        return(Radian/(np.pi / 180.))
        #cv2.imshow("dst", dst)
       
    cv2.waitKey(0)
    cv2.destroyAllWindows()

    def main(self):
            fgbg=cv2.createBackgroundSubtractorMOG2(detectShadows=False,history=200,varThreshold = 90)
            face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')
            body_cascade = cv2.CascadeClassifier('haarcascade_fullbody.xml')

            wh = 750; hi = 990
            cap = pyautogui.screenshot(region=(1, 1, wh, hi))
            frame1 = np.array(cap)
            frame1  = cv2.cvtColor(frame1, cv2.COLOR_RGB2BGR)
            arp = self.lineDD(frame1,wh,hi)

            while 1:
                cap = pyautogui.screenshot(region=(1, 1, wh, hi))
                img_frame = np.array(cap)
                img_frame  = cv2.cvtColor(img_frame, cv2.COLOR_RGB2BGR)
                frame = img_frame
                
                rows, cols = frame.shape[:2]
                rotation_matrix = cv2.getRotationMatrix2D((cols/2, rows/2), 90 - arp , 1)
                image_rotation = cv2.warpAffine(frame, rotation_matrix, (cols, rows))
                frame = image_rotation
                gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                
                body = body_cascade.detectMultiScale(gray, 1.3, 5)

                for (x,y,w,h) in body:
                    cv2.rectangle(frame,(x,y),(x+w,y+h),(255,0,0),2)
                    roi_gray = gray[y:y+h, x:x+w]
                    roi_color = frame[y:y+h, x:x+w]
                    faces = face_cascade.detectMultiScale(roi_gray)
                    for (ex,ey,ew,eh) in body:
                        cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)
                cv2.imshow('frame',frame)

                k = cv2.waitKey(30) & 0xff
                if k == 27:
                    break

if True:
    ped().main()
    cv2.waitKey(27)
    cv2.destroyAllWindows()