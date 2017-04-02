define({ "api": [
  {
    "type": "get",
    "url": "/formatDB/:timeframe/:newtimeframe",
    "title": "Start formatting of db values",
    "name": "FormatDb",
    "group": "Data",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "timeframe",
            "description": "<p>from timeframe.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "newtimeframe",
            "description": "<p>to timeframe</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/api/FormatDataBaseUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "get",
    "url": "/sensor/:id/:from/:to/:type",
    "title": "Request Sensor data in time span",
    "name": "GetSensorData",
    "group": "Data",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Sensor ID.</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "from",
            "description": "<p>Start of time span.</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "to",
            "description": "<p>End of time span</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "type",
            "description": "<p>Frame type.</p>"
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/api/GetSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensordata",
    "title": "Post Sensor data",
    "name": "PostData",
    "group": "Data",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"statuscode\":202,\"data\":[],\"message\":\"Data added\",\"status\":\"success\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/api/PostSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensordata/dummy",
    "title": "Post dummy sensor data",
    "name": "PostDummyData",
    "group": "Data",
    "description": "<p>Post to this route does not add a timestamp unlike a post to /sensordata</p>",
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\"statuscode\":202,\"data\":[],\"message\":\"Data added\",\"status\":\"success\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/api/PostSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensor",
    "title": "Add a sensor",
    "name": "AddSensor",
    "group": "Sensor",
    "version": "0.0.0",
    "filename": "src/main/java/api/AddSensorUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "get",
    "url": "/sensors",
    "title": "Request All Sensors",
    "name": "GetAllSensors",
    "group": "Sensor",
    "version": "0.0.0",
    "filename": "src/main/java/api/GetAllSensorsUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "get",
    "url": "/sensor/:id",
    "title": "Request Sensor information",
    "name": "GetSensor",
    "group": "Sensor",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>Sensor ID.</p>"
          }
        ]
      }
    },
    "success": {
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n    {\"statuscode\":200,\"data\":{\"nodeName\":\"Test-Node\",\"type\":\"Temperature\",\"nodeType\":\"Data Collection Node\",\"sensorId\":\"CHIBB-Node-Test\"},\"message\":\"\",\"status\":\"success\"}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "src/main/java/api/GetSensorURL.java",
    "groupTitle": "Sensor"
  }
] });
