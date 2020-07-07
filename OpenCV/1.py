import cv2 
import imutils 
import time

# Initializing the HOG person 
# detector 
hog = cv2.HOGDescriptor() 
hog.setSVMDetector(cv2.HOGDescriptor_getDefaultPeopleDetector()) 
   
cap = cv2.VideoCapture('123.mp4') 

count = 0

while cap.isOpened(): 
    # Reading the video stream 
    ret, image = cap.read()
    frame = image
    rows, cols = frame.shape[:2]
    rotation_matrix = cv2.getRotationMatrix2D((cols/2, rows/2), 0 , 1)
    image_rotation = cv2.warpAffine(frame, rotation_matrix, (cols, rows))
    image = image_rotation
    print(cv2.CAP_PROP_FRAME_COUNT)
    print(cv2.CAP_PROP_POS_MSEC)
    if ret: 
        image = imutils.resize(image,  
                               width=min(700, image.shape[1])) 
        # Detecting all the regions  
        # in the Image that has a  
        # pedestrians inside it 
        (regions, _) = hog.detectMultiScale(image, 
                                            winStride=(2, 2), 
                                            padding=(4, 4), 
                                            scale=1.05) 
   
        # Drawing the regions in the  
        # Image 
        for (x, y, w, h) in regions: 
            cv2.rectangle(image, (x, y), 
                          (x + w, y + h),  
                          (0, 0, 255), 2) 
   
        # Showing the output Image
        #time.sleep(3)
        cv2.imshow("Image", image) 
        if cv2.waitKey(27) & 0xFF == ord('q'): 
            break
    else: 
        break
cap.release() 
cv2.destroyAllWindows()