#sin((x-6)/4)+19
#based on 24h
#minute = 1/60
import math
import datetime
from datetime import timedelta
import requests
import time

startDate = "2017-03-24 12:00:00"
averagetemp = 21
hoursToGenerate = 24
url = "http://192.168.0.18:4567/sensordata/dummy"
nodeName = "CHIBB-Node-3"

def post_data(data):
    headers = {"Content-Type": "application/json"}
    r = requests.post(url, json=data, headers=headers, timeout=10)
    print(data)
    print(r.text)

def generateFrames():
    generateData("frame", 1/60)

def generateHours():
    generateData("hour", 1)

def generateQuarter():
    generateData("quarter", 1/4)

def generateData(timeframe, increaseBy):
    current_time = 0
    date = datetime.datetime.strptime(startDate, "%Y-%m-%d %H:%M:%S")
    while current_time < hoursToGenerate:
        timestamp = (date + timedelta(hours=current_time)).isoformat()
        response = {}
        response["value"] = get_temp(round(current_time, 2))
        response["timestamp"] = str(timestamp)
        response["timeframe"] = timeframe
        response["nodeName"] = nodeName
        post_data(response)
        current_time = current_time + increaseBy

def get_temp(current_time):
    return round(math.sin((current_time-6)/4)+averagetemp, 2)

if __name__ == "__main__":
    generateFrames()
    generateQuarter()
    generateHours()
