import cv2
import time
import numpy

CAM_ID = 0
cam = cv2.VideoCapture(CAM_ID) #카메라 생성
if cam.isOpened() == False: #카메라 생성 확인
    print('(%d) 카메라 영상이 실행되지 않습니다.' % (CAM_ID))
    exit()

#윈도우 생성 및 사이즈 변경
cv2.namedWindow('CAM_Window')
prevTime = 0 #이전 시간을 저장할 변수

while(True):

    #카메라에서 이미지 얻기
    ret, frame = cam.read()
    #현재 시간 가져오기 (초단위로 가져옴)
    curTime = time.time()
    sec = curTime - prevTime
    prevTime = curTime

    # 프레임 계산 한바퀴 돌아온 시간을 1초로 나누면 된다.
    # 1 / time per frame
    fps = 1/(sec)

    # 디버그 메시지로 확인해보기
    print ("Time {0} " . format(sec))
    print ("FPS {0} " . format(fps))
    print (cam.get(1))

    
    str = "FPS : %0.1f" % fps
    cv2.putText(frame, str, (0, 100), cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0))
    

    cv2.imshow('CAM_Window', frame)


    #10ms 동안 키입력 대기
    if cv2.waitKey(5) >= 0:
       break;

cam.release()
cv2.destroyWindow('CAM_Window')