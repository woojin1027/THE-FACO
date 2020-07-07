import cv2
import numpy as np
import time
import firmwaretest2 as vehicles

class ped():
    def main(self):
        #cv2.namedWindow("frame");
        #cv2.moveWindow("frame", 750,0);
        fgbg=cv2.createBackgroundSubtractorMOG2(detectShadows=False,history=200,varThreshold = 90)
        kernalOp = np.ones((3,3),np.uint8)
        kernalOp2 = np.ones((5,5),np.uint8)
        kernalCl = np.ones((11,11),np.uint8)
        font = cv2.FONT_HERSHEY_SIMPLEX
        ppl = []
        max_p_age = 5
        pid = 1
        cnt_up=0
        cnt_down=0
        woo = 0

        print("people counting and classification")

        line_right=570    
        line_left=170

        right_limit=590
        left_limit=150
        jin = 1

        wh = 700; hi = 950
        cap = pyautogui.screenshot(region= (50, 50, wh, hi))
        frame1 = np.array(cap)
        frame1  = cv2.cvtColor(frame1, cv2.COLOR_RGB2BGR)
        arp = self.lineDD(frame1,wh,hi)

        #while(cap.isOpened()):
        while True:
            cap = pyautogui.screenshot(region=(10, 10, wh, hi))
            img_frame = np.array(cap)
            img_frame  = cv2.cvtColor(img_frame, cv2.COLOR_RGB2BGR)
            #ret,frame=frame.read()
            #frame=cv2.resize(frame,(900,500))\
            frame = img_frame
            rows, cols = frame.shape[:2]
            rotation_matrix = cv2.getRotationMatrix2D((cols/2, rows/2), 90 - arp , 0.9)
            image_rotation = cv2.warpAffine(frame, rotation_matrix, (cols, rows))
            frame = image_rotation

            for i in ppl:
                i.age_one()
            fgmask=fgbg.apply(frame)

            if jin==True:
                ret,imBin=cv2.threshold(fgmask,200,255,cv2.THRESH_BINARY)
                '''
                 cv2.threshold(src, thresh, maxval, type)
                 src – input image로 single-channel 이미지.(grayscale 이미지)
                 thresh – 임계값
                 maxval – 임계값을 넘었을 때 적용할 value
                 type – thresholding type
                '''
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
                        if cy in range(10,980):
                            if cx in range(right_limit,left_limit):
                                for i in ppl:
                                    if abs(x - i.getX()) <= w and abs(y - i.getY()) <= h:
                                        new = False
                                        i.updateCoords(cx, cy)

                                        if i.going_UP(line_left,line_right)==True:
                                            cnt_up+=1

                                        elif i.going_DOWN(line_right,line_left)==True:
                                            cnt_down+=1

                                        break
                                    if i.getState()=='1':
                                        if i.getDir()=='left'and i.getY()>left_limit:
                                            i.setDone()
                                        elif i.getDir()=='right'and i.getY()<right_limit:
                                            i.setDone()
                                    if i.timedOut():
                                        index = ppl.index(i)
                                        ppl.pop(index)
                                        del i

                            if new==True:
                                p=vehicles.Car(pid,cx,cy,max_p_age)
                                ppl.append(p)
                                pid+1
                        cv2.circle(frame, (cx, cy), 2, (0, 0, 255), -1)
                        img=cv2.rectangle(frame,(x,y),(x+w,y+h),(0,255,0),3)

                str_up='right: '+str(cnt_up)
                str_down='left: '+str(cnt_down)

                frame = cv2.line(frame,(line_right,0),(line_right,990),(0,0,255),3,8)
                frame = cv2.line(frame,(right_limit,0),(right_limit,990),(0,255,255),2,8)
                frame = cv2.line(frame,(line_left,0),(line_left,990),(255,255,0),3,8)
                frame = cv2.line(frame, (left_limit,0), (left_limit,990), (255, 0,0), 2, 8)

                cv2.putText(frame, str_up, (10, 40), font, 0.5, (0, 0, 255), 1, cv2.LINE_AA)
                cv2.putText(frame, str_down, (10, 90), font, 0.5, (255, 0, 0), 1, cv2.LINE_AA)
                cv2.imshow('Frame',frame)
                #time.sleep(1)
                print("오른쪽으로 가는 인원은 : ",str(cnt_down)," 명 입니다." )

                key = cv2.waitKey(1)
                if key == 27:
                    break
            else:
                break
        cv2.destroyAllWindows()

if True:
    ped().main()
    cv2.waitKey(0)
    cv2.destroyAllWindows()