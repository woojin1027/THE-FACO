
import cv2 as cv
import numpy as np

img = np.zeros((500,500,3))
img = cv.line(img,(10,10),(490,490),(255,0,0),4)
cv.imshow("test",img)
cv.waitKey(0)
cv.destoryAllwindows()