define({ "api": [
  {
    "type": "get",
    "url": "/formatDB/:timeframe/:newtimeframe",
    "title": "Start formatting of db values",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
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
            "description": "<p>from time frame</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "newtimeframe",
            "description": "<p>to time frame</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "src/main/java/api/data/FormatDataBaseUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "get",
    "url": "/sensor/:id/:from/:to/:type",
    "title": "Request Sensor data in time span for given type(s)",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
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
            "field": "date",
            "description": "<p>type e.g. Temperature OR Temperature,Pressure</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "src/main/java/api/data/GetSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "get",
    "url": "/sensor/:id/:from/:to",
    "title": "Request Sensor data in time span",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "GetSensorDataAll",
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
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "src/main/java/api/data/GetSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensordata",
    "title": "Post Sensor data",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
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
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/PostSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensordata/dummy",
    "title": "Post dummy sensor data",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
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
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/PostSensorDataUrl.java",
    "groupTitle": "Data"
  },
  {
    "type": "post",
    "url": "/sensor",
    "title": "Add a sensor",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "AddSensor",
    "group": "Sensor",
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/AddSensorUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "delete",
    "url": "/sensor",
    "title": "Archives a sensor",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "ArchiveSensor",
    "group": "Sensor",
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/DeleteSensorUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "get",
    "url": "/sensors",
    "title": "Request All Sensors",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "GetAllSensors",
    "group": "Sensor",
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/AddSensorUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "get",
    "url": "/house/:id/:floor",
    "title": "Request Sensors from specific floor in house",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "GetHouseSensors",
    "group": "Sensor",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "id",
            "description": "<p>House ID.</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "floor",
            "description": "<p>Floor (optional).</p>"
          }
        ]
      }
    },
    "version": "1.0.0",
    "filename": "src/main/java/api/house/GetSensorsHouseUrl.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "get",
    "url": "/sensor/:id",
    "title": "Request Sensor information",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "GetSensor",
    "group": "Sensor",
    "examples": [
      {
        "title": "Example url:",
        "content": "https://api.visualidata.nl/sensor/CHIBB-Test-Node",
        "type": "js"
      }
    ],
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
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/GetSensorURL.java",
    "groupTitle": "Sensor"
  },
  {
    "type": "post",
    "url": "/sensor/update",
    "title": "Update a sensor",
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>Authorization key.</p>"
          }
        ]
      }
    },
    "name": "UpdateSensor",
    "group": "Sensor",
    "version": "1.0.0",
    "filename": "src/main/java/api/sensor/DeleteSensorUrl.java",
    "groupTitle": "Sensor"
  }
] });
