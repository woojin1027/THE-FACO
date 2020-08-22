# -*- coding: utf-8 -*-
 
import subprocess
 
def sendAutostartMail():
    import smtplib
    import string
    USER = 'thefaco18@gmail.com'              
    PASS = 'dnwls7952'              
    TO = 'vvv8178@gmail.com'    
    SUBJECT = '[RP_first] IP adress Mail' 
    ip = subprocess.check_output("hostname -I", shell = True)
    TEXT = 'this is Autostart mail. \n RPi2s IP adress is \n %s' %ip
    print (TEXT)
    FROM = USER
    HOST = 'smtp.gmail.com:587'      
    BODY = '\r\n'.join((
        'From: %s' %FROM,
        'To: %s' %TO,
        'Subject: %s' %SUBJECT ,
        '\r\n',
        TEXT,
        ))
     
    server = smtplib.SMTP(HOST)
    server.starttls()
    server.login(USER, PASS)
    server.sendmail(FROM, TO, BODY)
    server.quit()
     
    print ('%s send mail' %TO)
   
if __name__ == "__main__": 
    sendAutostartMail()