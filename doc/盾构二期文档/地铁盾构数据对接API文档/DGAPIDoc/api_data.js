define({ "api": [
  {
    "type": "post",
    "url": "/api/values/allmileage",
    "title": "获取所有环数对应的里程数",
    "version": "2.0.0",
    "name": "PostAllMileage",
    "group": "BaseAPI",
    "description": "<p>获取所有环数对应的里程数 from [MetroInfo].[dbo].[{identify}]</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>线路标识</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>环号对应里程数json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/allmileage"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values",
    "title": "标签当前值查询",
    "version": "2.0.0",
    "name": "PostData",
    "group": "BaseAPI",
    "description": "<p>输入标签数组（标签数组无数量限制），返回每个标签的当前值</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>包含标签名与对应值的json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/exportdatabydate",
    "title": "按日期导出数据（秒级，极容易超时）",
    "version": "2.0.0",
    "name": "PostExportDatabyDate",
    "group": "BaseAPI",
    "description": "<p>按单个日期导出单个标签数据（间隔1s）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "datetime",
            "description": "<p>日期</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>标签及对应值json格式数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n               \"1487174460\":0,\n               \"1487174520\":0,\n               \"1487174580\":0\n            }\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/exportdatabydate"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/exportdatabydatemin",
    "title": "按日期导出数据(分级)",
    "version": "2.0.0",
    "name": "PostExportDatabyDateMin",
    "group": "BaseAPI",
    "description": "<p>按单个日期导出单个tag数据（间隔1m）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "datetime",
            "description": "<p>日期</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example:{\"request\":\"GZ21_16L.J0001.F_CV\",\"datetime\":\"1487232558\"}",
          "content": "{\n  \"parameters\":\"H66eXNn6R7OtLTuRHFDhJNpT0Q/oeJ1yp7s7hlSh0Q+ycsey/sfbndFlcb3aeij1qxBvLZIAyusM0kzg1urhkR2lNhGLqllnamhffkSLBZw3d6XVsvgPCzPaZQGgcDEcMlmkIVUlsDypQJ6/E0awaTGklxzJOXKK6rIpBsanVU6CxG4JddGieufi+akJ2BZGSu3PuTthKiy5KIX4wiH+jw==\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>标签及对应值json格式数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200OK  \n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n               \"1487174460\":0,\n               \"1487174520\":0,\n               \"1487174580\":0\n            }\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/exportdatabydatemin"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/exportdatabyring",
    "title": "按环号导出数据(秒级，极容易超时)",
    "version": "2.0.0",
    "name": "PostExportDatabyRing",
    "group": "BaseAPI",
    "description": "<p>按单个环号导出单个tag数据（间隔1s）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ring",
            "description": "<p>环号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>每秒对应值json格式数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200 OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n               \"1484753756\":40445.51,\n               \"1484753816\":40445.51,\n               \"1484753876\":40445.513\n            }\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/exportdatabyring"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/exportdatabyringmin",
    "title": "按环号导出数据(分级)",
    "version": "2.0.0",
    "name": "PostExportDatabyRingMin",
    "group": "BaseAPI",
    "description": "<p>按单个环号导出单个tag数据（间隔1m）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ring",
            "description": "<p>环号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example{\"request\":\"GZ21_16L.J0001.F_CV\",\"ring\":\"1134\"}",
          "content": "{\n  \"parameters\":\"IHMkG9xHXtPDhzBt2X+yracqTjGY4RmzNZ5XIOsjETKEgImaG6p8vLDPdFBukeTlzc4m7YBBaIOGbJSf7PteFg+AY5SMNQ7FGes24jRCNeG/k0ZRQcqEJ3PweEUpFSChxETrsjDk7mb5UdCfTjBN1lMRXRQ8jSFVCOGm3dke5YYhQ56qPQ+Awg==\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>每秒对应值json格式数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200 OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n               \"1484753756\":40445.51,\n               \"1484753816\":40445.51,\n               \"1484753876\":40445.513\n            }\n  }",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/exportdatabyringmin"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/onlinecalc",
    "title": "统计在线个数",
    "version": "2.0.0",
    "name": "PostOnlineCalc",
    "group": "BaseAPI",
    "description": "<p>统计在线个数(一般用于A0001测在线状态,支持多个标签同时查询)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>被统计的标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>在线数量与不在线数量的json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/onlinecalc"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/ringquality",
    "title": "环号质量",
    "version": "2.0.0",
    "name": "PostRingQuality",
    "group": "BaseAPI",
    "description": "<p>输入标签数组，获得环号及对应值（标签包含A0001时，对应值取整并获取质量字段值；标签包含A0002时，对应值取整）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>包含标签名与对应值的json格式数组(标签包含A0001时有会多出一个质量字段)</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "BaseAPI",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/ringquality"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/ringcalc",
    "title": "导向",
    "version": "2.0.0",
    "name": "PostRingCalc",
    "group": "Orientation",
    "description": "<p>输入环号数组 &amp; 标签数组，获取每个环号间标签对应值的互减结果</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ringarr",
            "description": "<p>环号数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>环号与水平垂直互减结果的json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Orientation",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/ringcalc"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/fivetagvaluebyringv2",
    "title": "数据分析-统计环号查询数据",
    "version": "2.0.0",
    "name": "PostFiveTagValuebyRingV2",
    "group": "Reports",
    "description": "<p>通过环号数组获取标签数组对应数据（间隔8min）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "value",
            "description": "<p>The value include ringarr,request,fixcount,identify</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ringarr",
            "description": "<p>环号数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fixcount",
            "description": "<p>固定组编号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>线路标识</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>每个时间点对应的数组（标签&amp;对应值）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n       {\n           \"1487033382\":\n           {\n               \"GZ8_9L.A0001.F_CV\":23,\n               \"GZ8_9L.C1511.F_CV\":74,\n               \"GZ8_9L.C0703.F_CV\":9,\n               \"GZ8_9L.J0024.F_CV\":-1,\n               \"GZ8_9L.J0026.F_CV\":0\n           }\n       },\n       {\n           \"1487033862\":\n           {\n               \"GZ8_9L.C1511.F_CV\":0,\n               \"GZ8_9L.C0703.F_CV\":0,\n               \"GZ8_9L.J0024.F_CV\":0,\n               \"GZ8_9L.J0026.F_CV\":0,\n               \"GZ8_9L.A0001.F_CV\":23\n           }\n       }\n}",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/fivetagvaluebyringv2"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/fivetagvaluebytsv2",
    "title": "数据分析-统计时间查询数据",
    "version": "2.0.0",
    "name": "PostFiveTagValuebyTsV2",
    "group": "Reports",
    "description": "<p>通过获取标签数组对应数据（间隔8min）</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "starttime",
            "description": "<p>起始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "endtime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fixcount",
            "description": "<p>固定组编号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>线路标识</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>每个时间点对应的数组（标签&amp;对应值）</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n       {\n           \"1487033382\":\n           {\n               \"GZ8_9L.A0001.F_CV\":23,\n               \"GZ8_9L.C1511.F_CV\":74,\n               \"GZ8_9L.C0703.F_CV\":9,\n               \"GZ8_9L.J0024.F_CV\":-1,\n               \"GZ8_9L.J0026.F_CV\":0\n           }\n       },\n       {\n           \"1487033862\":\n           {\n               \"GZ8_9L.C1511.F_CV\":0,\n               \"GZ8_9L.C0703.F_CV\":0,\n               \"GZ8_9L.J0024.F_CV\":0,\n               \"GZ8_9L.J0026.F_CV\":0,\n               \"GZ8_9L.A0001.F_CV\":23\n           }\n       }\n}",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/fivetagvaluebytsv2"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/maxofring",
    "title": "材料消耗",
    "version": "2.0.0",
    "name": "PostMaxOfRing",
    "group": "Reports",
    "description": "<p>输入环号数组 &amp; 标签数组，获取每个环号之间的标签及其对应的最大值</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ringarr",
            "description": "<p>环号数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>环号与标签json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/maxofring"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/ringsdiffbydate",
    "title": "汇总统计-推进环数",
    "version": "2.0.0",
    "name": "PostRingsDiffByDate",
    "group": "Reports",
    "description": "<p>用两个时间戳获取推进环数的间隔</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "starttime",
            "description": "<p>起始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "endtime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "field",
            "description": "<p>标签名</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>推进环数的间隔</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/ringsdiffbydate"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/summarydatabyts",
    "title": "汇总统计-数据分析",
    "version": "2.0.0",
    "name": "PostSummaryDatabyTs",
    "group": "Reports",
    "description": "<p>用两个时间戳获取掘进时间K0001、拼装时间K0002、停机时间K0003、注浆量D0023、盾尾油脂量G0001(时间间隔为1min)</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "starttime",
            "description": "<p>起始时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "endtime",
            "description": "<p>结束时间</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "timerequest",
            "description": "<p>时间标签</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "fieldrequest",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "encodeResult",
            "description": "<p>The encodeResult include (ts &amp; K0001+K0002+K0003+D0023+G0001)'s Array</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/summarydatabyts"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/timecalcbyring",
    "title": "汇总统计-推进环数",
    "version": "2.0.0",
    "name": "PostTimeCalcbyRing",
    "group": "Reports",
    "description": "<p>获取每个环的0/3/4状态所占时间</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "ringarr",
            "description": "<p>环号数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>每个环的0/3/4状态所占时间json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Reports",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/timecalcbyring"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/planecoordinates",
    "title": "获取平面坐标",
    "version": "2.0.0",
    "name": "PostPlaneCoordinates",
    "group": "SVG",
    "description": "<p>获取平面坐标 from [MetroInfo].[dbo].[{identify}]</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>线路标识</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>X,Y,水平,垂直json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "SVG",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/planecoordinates"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/todayRing",
    "title": "今日施工的环数",
    "version": "2.0.0",
    "name": "PostTodayRing",
    "group": "SVG",
    "description": "<p>今日施工的环数</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>标签</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Request-Example",
          "content": "{\n     \"identify\":\"GZ21_16\"\n}",
          "type": "json"
        }
      ]
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>今日施工json格式数组</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response",
          "content": "HTTP/1.1 200 OK\n原始数据：\n{\n   \"code\":200,\n   \"message\":\"message to hint\",\n   \"result\":{\n       \"L\":{\n           \"ring\":1135,\n            \"count\":5\n       }\n   }\n }",
          "type": "json"
        }
      ]
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "SVG",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/todayRing"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/profilecoordinates",
    "title": "获取廓面坐标",
    "version": "2.0.0",
    "name": "PostprofileCoordinates",
    "group": "SVG",
    "description": "<p>获取廓面坐标from [MetroInfo].[dbo].[{identify}]</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "identify",
            "description": "<p>线路标识</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>X,Z,水平,垂直json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "SVG",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/profilecoordinates"
      }
    ]
  },
  {
    "type": "post",
    "url": "/api/values/mileage2ts",
    "title": "区间-监测数据",
    "version": "2.0.0",
    "name": "PostMileage2Ts",
    "group": "Sense_Data",
    "description": "<p>输入标签数组 &amp; 里程，获得前30米后50米时间戳</p>",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "request",
            "description": "<p>标签数组</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "mileage",
            "description": "<p>当前里程数</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "200": [
          {
            "group": "200",
            "type": "String",
            "optional": false,
            "field": "response",
            "description": "<p>前30米后50米的时间戳json格式数组</p>"
          }
        ]
      }
    },
    "filename": "Controllers/ValuesController.cs",
    "groupTitle": "Sense_Data",
    "sampleRequest": [
      {
        "url": "http://ts.gzdtjl.com:8250/api/values/mileage2ts"
      }
    ]
  }
] });
