import cv2
import numpy as np
import time
import test19 as vehicles

class ped():
    def lineDD(self,frame):
        src = cv2.resize(frame,(900,500))
        dst = src.copy()
        gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
        canny = cv2.Canny(gray, 5000, 1500, apertureSize = 5, L2gradient = True)

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
            if d < c:
                d = c
                d1 = abs(a1)
                d2 = abs(b1)
                Radian = np.arctan(d2 / d1)
        
        """
        lines = cv2.HoughLinesP(canny, 0.8, np.pi / 180, 90, minLineLength = 10, maxLineGap = 100)

        for i in lines:
            cv2.line(dst, (i[0][0], i[0][1]), (i[0][2], i[0][3]), (0, 0, 255), 2)
        """
        #print(Radian/(np.pi / 180.))
        return(Radian/(np.pi / 180.))
        #cv2.imshow("dst", dst)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    
    def main(self):
        cap=cv2.VideoCapture("test.mp4")
        fgbg=cv2.createBackgroundSubtractorMOG2(detectShadows=False,history=200,varThreshold = 90)

        kernalOp = np.ones((3,3),np.uint8)
        kernalOp2 = np.ones((5,5),np.uint8)
        kernalCl = np.ones((11,11),np.uint8)
        font = cv2.FONT_HERSHEY_SIMPLEX
        cars = []
        max_p_age = 5
        pid = 1
        cnt_up=0
        cnt_down=0
        woo = 0
        print("people counting and classification")
        line_up=70
        line_down=320
        up_limit=50
        down_limit=340

        ret,frame1=cap.read(0)
        frame1=cv2.resize(frame1,(900,500))
        arp = self.lineDD(frame1)

        while(cap.isOpened()):
            ret,frame=cap.read()
            frame=cv2.resize(frame,(900,500))
            rows, cols = frame.shape[:2]
            rotation_matrix = cv2.getRotationMatrix2D((cols/2, rows/2), 90 - arp , 1)
            image_rotation = cv2.warpAffine(frame, rotation_matrix, (cols, rows))
            frame = image_rotation

            for i in cars:
                i.age_one()
            fgmask=fgbg.apply(frame)

            if ret==True:
                ret,imBin=cv2.threshold(fgmask,200,255,cv2.THRESH_BINARY)
                mask = cv2.morphologyEx(imBin, cv2.MORPH_OPEN, kernalOp)
                mask = cv2.morphologyEx(mask, cv2.MORPH_CLOSE, kernalCl)

                (countours0,hierarchy)=cv2.findContours(mask,cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_NONE)
                for cnt in countours0:
                    area=cv2.contourArea(cnt)
                    #print(area)
                    if area>300:

                        m=cv2.moments(cnt)
                        cx=int(m['m10']/m['m00'])
                        cy=int(m['m01']/m['m00'])
                        x,y,w,h=cv2.boundingRect(cnt)

                        new=True
                        if cx in range(10,890):
                            if cy in range(up_limit,down_limit):
                                for i in cars:
                                    
                                    if abs(x - i.getX()) <= w and abs(y - i.getY()) <= h:
                                        new = False
                                        i.updateCoords(cx, cy)

                                        if i.going_UP(line_down,line_up)==True:
                                            cnt_up+=1

                                        elif i.going_DOWN(line_down,line_up)==True:
                                            cnt_down+=1

                                        break
                                    if i.getState()=='1':
                                        if i.getDir()=='down'and i.getY()>down_limit:
                                            i.setDone()
                                        elif i.getDir()=='up'and i.getY()<up_limit:
                                            i.setDone()
                                    if i.timedOut():
                                        index=cars.index(i)
                                        cars.pop(index)
                                        del i

                            if new==True:
                                p=vehicles.Car(pid,cx,cy,max_p_age)
                                cars.append(p)
                                pid+1
                        cv2.circle(frame, (cx, cy), 2, (0, 0, 255), -1)


                        img=cv2.rectangle(frame,(x,y),(x+w,y+h),(0,255,0),2)

                """
                for i in cars:
                    cv2.putText(frame, str(i.getId()), (i.getX(), i.getY()), font, 0.3, (255,255,0), 1, cv2.LINE_AA)
                    if line_up+30 <= i.getY() <= line_down-20:
                        a = (h + (.74*w)- 100)

                    if a >= 0:
                        cv2.putText(frame, "k", (i.getX(), i.getY()), font, 1, (0,0,255), 2, cv2.LINE_AA)
                    else:
                        cv2.putText(frame, "p", (i.getX(), i.getY()), font, 1, (0,0,255), 2, cv2.LINE_AA)
                    woo += 1
                """
                str_up='UP: '+str(cnt_up)
                str_down='DOWN: '+str(cnt_down)
                frame=cv2.line(frame,(0,line_up),(900,line_up),(0,0,255),3,8)
                frame=cv2.line(frame,(0,up_limit),(900,up_limit),(0,255,255),1,8)

                frame=cv2.line(frame,(0,down_limit),(900,down_limit),(255,255,0),1,8)
                frame = cv2.line(frame, (0, line_down), (900, line_down), (255, 0,0), 3, 8)
                #frame = cv2.line(frame, (315, 0), (900, 410), (255, 0,0), 3, 8)
                #frame = cv2.line(frame, (420, 0), (350, 500), (255, 0,0), 3, 8)


                cv2.putText(frame, str_up, (10, 40), font, 0.5, (0, 0, 255), 1, cv2.LINE_AA)
                cv2.putText(frame, str_down, (10, 90), font, 0.5, (255, 0, 0), 1, cv2.LINE_AA)
                cv2.imshow('Frame',frame)
                time.sleep(0.01)
                print("밑으로 내려오는 차는 : ",str(cnt_down)," 대 입니다." )
                
                key = cv2.waitKey(1)
                if key == 27:
                    break
            else:
                break
        cap.release()
        cv2.destroyAllWindows()

if True:
    ped().main()
    cv2.waitKey(0)
    cv2.destroyAllWindows()
   