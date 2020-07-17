import cv2
import numpy as np

face_cascade = cv2.CascadeClassifier('haarcascade\\haarcascade_frontalface_default.xml')
body_cascade = cv2.CascadeClassifier('haarcascade\\haarcascade_fullbody.xml')
cap = cv2.VideoCapture('123.mp4') 

while(cap.isOpened()):
    #ret,frame = cap.read()

    ret, image = cap.read()
    frame = image
    rows, cols = frame.shape[:2]
    rotation_matrix = cv2.getRotationMatrix2D((cols/2, rows/2), -90 , 1)
    image_rotation = cv2.warpAffine(frame, rotation_matrix, (cols, rows))
    image = image_rotation
    frame = np.array(image)
    '''
    img_frame = np.array(image)
    img_frame  = cv2.cvtColor(img_frame, cv2.COLOR_RGB2BGR)
    frame = img_frame
    '''
    frame = cv2.resize(frame,(900,500))
    fps = cap.get(cv2.CAP_PROP_FPS)
    print(fps)
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