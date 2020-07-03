import numpy as np
import cv2

cap=cv2.VideoCapture("123.mp4")
ret,frame=cap.read()
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
    else:
        pass


"""
lines = cv2.HoughLinesP(canny, 0.8, np.pi / 180, 90, minLineLength = 10, maxLineGap = 100)

for i in lines:
    cv2.line(dst, (i[0][0], i[0][1]), (i[0][2], i[0][3]), (0, 0, 255), 2)
"""
print(Radian/(np.pi / 180.))
cv2.imshow("dst", dst)
cv2.waitKey(0)
cv2.destroyAllWindows()