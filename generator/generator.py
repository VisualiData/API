#sin((x-6)/4)+19
#based on 24h
#minute = 1/60
import math
import datetime
from datetime import timedelta
import requests
import time
import random

startDate = "2017-05-27 00:00:00"
averagetemp = 20
hoursToGenerate = 72
url = "http://localhost:4567/sensordata/dummy?authkey=dev"
nodeName = "CHIBB-Test-02"
sensorType = "Temperature"

def post_data(data):
    headers = {"Content-Type": "application/json"}
    r = requests.post(url, json=data, headers=headers, timeout=10)
    print(data)
    print(r.text)

def generate_frames():
    generate_data("frame", 1/60)

def generate_hours():
    generate_data("hour", 1)

def generate_quarter():
    generate_data("quarter", 1/4)

def generate_data(timeframe, increaseby):
    current_time = 0
    date = datetime.datetime.strptime(startDate, "%Y-%m-%d %H:%M:%S")
    while current_time < hoursToGenerate:
        timestamp = (date + timedelta(hours=current_time)).timestamp() * 1000
        response = {}
        values = []
        value = {}
        value["value"] = get_temp(round(current_time, 2))
        value["timestamp"] = int(timestamp)
        value["timeframe"] = timeframe
        response["sensor_id"] = nodeName
        value["type"] = sensorType
        values.append(value)
        response["values"] = values
        # print(response)
        post_data(response)
        current_time = current_time + increaseby

def get_temp(current_time):
    num = random.sample(range(6),1)
    return round(math.sin((current_time-6)/4)+averagetemp + (num[0] / 10), 2)

if __name__ == "__main__":
    generate_frames()
    generate_quarter()
    generate_hours()
