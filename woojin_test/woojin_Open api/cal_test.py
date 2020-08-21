import requests
import xmltodict
import json

key = "k9SIHsYmigNv2OONMs4CHstwWrnYUTa2rlIxPlvRLxoTRXJL7KmrH2%2B126GkdZLo5T1Jmf8BNbb8sFxV7QGqRw%3D%3D"

url = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRouteSt?ServiceKey={}&busRouteId=100100221&startOrd=1&endOrd=13".format(key)

content = requests.get(url).content
dict = xmltodict.parse(content)
jsonstring = json.dumps(dict['ServiceResult']['msgBody']['itemList'],ensure_ascii=False)
jsonobj = json.loads(jsonstring)
#print(jsonstring)

for item in jsonobj:
    print(item['plainNo'])