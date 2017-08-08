<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>地铁线网图</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/tube_map.css'/>">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
<div id="map_block"></div>

<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=gT3XsPy9tGLkQseu8T3LnoO6hMH3cxR2" type="text/javascript"></script>
<script src="<@s.url'/plugins/js/InfoBox.js'/>"></script>
<script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/jquery-slimscroll/jquery.slimscroll.js'/>"></script>
<script src="<@s.url'/plugins/bower/jquery-dateFormat/dist/jquery-dateFormat.min.js'/>"></script>
<script>
    // 切换左右线信息显示
    function toggleBtn(uniqId){
        $('#leftContent'+uniqId).toggleClass('show hide');
        $('#rightContent'+uniqId).toggleClass('show hide');
    }
    $(document).ready(function(){
// 地图数据
        var mapData = [];
        var ax_request = axios.create({
            baseURL:'${request.contextPath}/tube/map',
            timeout:60000
        });

        var current_info_window = null;
// ===================================== 地图尺寸调整与初始化 ======================================
// 初始化地图
        var map = new BMap.Map('map_block');
        map.centerAndZoom(new BMap.Point(113.270218,23.135574),15);
        map.enableScrollWheelZoom();
        map.setMinZoom(12);
        map.setMapStyle({
            styleJson:[
                {
                    "featureType": "road",
                    "elementType": "all",
                    "stylers": {"lightness": 20}
                },
                {
                    "featureType": "highway",
                    "elementType": "geometry",
                    "stylers": {"color": "#f49935"}
                },
                {
                    "featureType": "railway",
                    "elementType": "all",
                    "stylers": {"visibility": "off"}
                },
                {
                    "featureType": "local",
                    "elementType": "labels",
                    "stylers": {"visibility": "off"}
                },
                {
                    "featureType": "subway",
                    "elementType": "all",
                    "stylers": {"visibility": "off"}
                },
                {
                    "featureType": "poi",
                    "elementType": "all",
                    "stylers": {"visibility": "off"}
                }
            ]});

// ====================================== 线标签绘制 ==============================================
        /**
         * 地图Tag
         */
        function Tag(point,text,color){
            this._point = point;
            this._text = text;
            this._color = color;
            this._clz = 'tag';
        }
        Tag.prototype = new BMap.Overlay();
// 初始化
        Tag.prototype.initialize = function(map){
            this._map = map;
            var block = document.createElement('div');
            var span = document.createElement('span');
            block.appendChild(span);
            span.appendChild(document.createTextNode(this._text));
            block.className = this._clz;
            span.className = this._clz;
            span.style.backgroundColor = this._color;

            map.getPanes().labelPane.appendChild(block);
            this._div = block;
            return block;
        }
// 绘制
        Tag.prototype.draw = function(e){
            // 纠正位置偏移

            var position = this._map.pointToOverlayPixel(this._point);
            this._div.style.left = position.x + 'px';
            this._div.style.top = position.y + 'px';
        }

// 根据数据,画标签
        function drawTags(map,data){
            data.forEach(function(ele){
                drawTag(map,ele.mapX,ele.mapY,ele.lineName,ele.lineColor);
            });
        }

// 画一个标签
        function drawTag(map,p_x,p_y,tagName,tagColor){
            var point = new BMap.Point(p_x,p_y);
            var tag = new Tag(point,tagName,tagColor);
            map.addOverlay(tag);
        }

// ============================== 数据处理 =========================================================

//组装区间左线或者右线信息
        function getAllLines(data){
            var result = [];
            data.forEach(function(line){
                line.intervalList.forEach(function(interval){
                    interval.intervalLrList.forEach(function(lrLine){
                        var lineNode = {};
                        var lineName = line.lineName+'-'+interval.intervalName;
                        var leftData = interval.intervalLrList.filter(function(lrLine){
                            return lrLine.leftOrRight === 'l';
                        })[0];
                        var rightData = interval.intervalLrList.filter(function(lrLine){
                            return lrLine.leftOrRight === 'r';
                        })[0];
                        lineNode.points = JSON.parse(lrLine.mapXy);
                        lineNode.color = lrLine.intervalColor;
                        lineNode.reqId = interval.id;
                        lineNode.data = {
                            isLeft:(lrLine.leftOrRight === 'l'),
                            uniqId:lrLine.leftOrRight+lrLine.id+interval.id,
                            left:{
                                name:lineName+'-左线',
                                ringNum:leftData?leftData.ringNum:"",
                                buildStatus:leftData?leftData.buildStatus:""
                            },
                            right:{
                                name:lineName+'-右线',
                                ringNum:rightData?rightData.ringNum:"",
                                buildStatus:rightData?rightData.buildStatus:""
                            }
                        };
                        result.push(lineNode);
                    });
                });
            });
            return result;
        }


// 生成地图需要的坐标点集
        function createPolyPoints(points){
            return points.map(function(point){
                return new BMap.Point(point.x,point.y);
            });
        }

// ==================================== 界面拼装 ==================================================
// 拼装信息窗外框，如果没有数据，默认返回loading.....
        function createContent(data){
            var sContent =  '<div class="content">';
            if(data){
                sContent += createMessageContent(data);
            }else{
                sContent += '<div class="loading"></div>';
            }
            sContent += '</div>'+
                    '<div class="tiddle"></div>';
            return sContent;
        }

// 组装窗口信息
        function createMessageContent(data){
            var sContent =  '<div class="leftRight">'+
                    '<button id="switchBtn'+data.uniqId+'" class="switchBtn btn btn-info btn-sm" onclick="toggleBtn(\''+data.uniqId+'\')">切换</button>'+
                    '<div id="leftContent'+data.uniqId+'" class="leftContent '+(data.isLeft?'show':'hide')+'">'+
                    createNodeContent(data.left)+
                    '</div>'+
                    '<div id="rightContent'+data.uniqId+'" class=" rightContent '+(data.isLeft?'hide':'show')+'">'+
                    createNodeContent(data.right)+
                    '</div>'+
                    '</div>'
            return sContent;
        }

// 组装节点结构
        function createNodeContent(data){
            var sContent = '<span class="leftTitle"><b>'+data.name+'</b></span>';

            if (data.hasOwnProperty('currentRing') || data.hasOwnProperty('ringNum')){
                sContent += '<span class="current">当前环：'+
                        (data.currentRing?data.currentRing:0)+'/'+(data.ringNum?data.ringNum:0)+'</span>';
            }
            // 数据时间拼装
            if (data.hasOwnProperty('dataTime')){
                sContent += '<span class="data-time">数据时间：'+data.dataTime+'</span>';
            }else{
                var dateString = $.format.date(new Date(),'yyyy-MM-dd hh:mm:ss');
                sContent += '<span class="date-time">数据时间：'+dateString+'</span>';
            }
            // 推进状态拼装
            if(data.hasOwnProperty('advanceStatus')&& data.advanceStatus != null){
                sContent += '<span class="advanceStatus">推进状态：';
                switch (data.advanceStatus){
                    case 0:
                        sContent += "停机";
                        break;
                    case 3:
                        sContent += "推进";
                        break;
                    case 4:
                        sContent += "拼装";
                        break;
                }
                sContent += '</span>';
            }
            // 工程状态拼装
            if(data.hasOwnProperty('buildStatus') && data.buildStatus != null){
                sContent += '<span class="projectStatus">工程状态：';
                switch (data.buildStatus){
                    case 0:
                        sContent += "未施工";
                        break;
                    case 1:
                        sContent += "正在施工";
                        break;
                    case 2:
                        sContent += "已贯通";
                        break;
                }
                sContent += '</span>';
            }

            if(data.hasOwnProperty('communiStatus') && data.communiStatus != null){
                sContent += '<span class="communiStatus">通信状态：';
                switch (data.communiStatus){
                    case 0:
                        sContent += "通信异常";
                        break;
                    case 1:
                        sContent += "正常通信";
                        break;
                }
                sContent += '</span>';
            }

            // 异常预警拼装
            if(data.hasOwnProperty('alarm')&&data.alarm != null){
                var hasRed = data.alarm.indexOf("红色") !== -1;
                sContent += '<span class="alarm '+(hasRed?"alarmRed":"alarmOrange")+'"><i style="color:black;">异常预警：</i>'+data.alarm+'</span>';
            }

            return sContent;
        }

// =================================== 画线和信息框 ================================================
// 根据数据，画线条
        function drawLines(map,data){
            var lines = getAllLines(data);

            lines.forEach(function(line){
                if(!line.points){return;}
                // 画线
                var points = createPolyPoints(line.points);
                var polyline = new BMap.Polyline(points,{
                    strokeColor:line.color,
                    strokeWidth:4.0,
                    strokeOpacity:0.5
                });

                // 绘制信息窗口
                var sContent = createContent();
                var infoBox = new BMapLib.InfoBox(map,sContent,{
                    boxStyle:{
                        width:"350px",
                        height:"260px",
                        border:"5px solid #3C8DBC",
                        backgroundColor:"white",
                        borderRadius:"8px",
                        position:"relative"
                    },
                    closeIconUrl:"${request.contextPath}/plugins/images/map_close.gif",
                    closeIconMargin:"10px 10px",
                    enableAutoPan:true,
                    offset:new BMap.Size(0,15)
                });

                // 用户线路hover时查询用的请求id
                polyline.infoId = line.reqId;
                polyline.data = line.data;
                polyline.infoBox = infoBox;
                polyline.addEventListener('mouseover',function(e){
                    // 关闭旧窗口
                    if(current_info_window){
                        current_info_window.close();
                    }
                    // 取一条线最中间的点作为窗口弹起的基点
                    var point = polyline.getPath()[Math.floor(polyline.getPath().length/2)];
                    polyline.infoBox.open(point);
                    current_info_window = polyline.infoBox;
                    // 发起请求
                    ax_request.get('/find/line/interval/datas?intervalId='+polyline.infoId).then(function(res){
                        // 合并新结果
                        //var resData = JSON.parse(res.data);
                        $.extend(true,polyline.data,res.data.data);
                        // 展示新结果
                        updateInfoBox(polyline.infoBox,polyline.data);
                    })
                            .catch(function(err){
                                // 网络出错，展示旧结果
                                updateInfoBox(polyline.infoBox,polyline.data);
                            });
                });
                // 地图上添加路线
                map.addOverlay(polyline);
            });
        }

// 展示infoBox
        function updateInfoBox(infoBox,data){
            if(infoBox._isOpen){
                var content = createContent(data);
                infoBox.setContent(content);
            }
        }

// ================================== 数据请求 =====================================================

// 请求地图线路数据
        ax_request.post('/find/line/datas')
                .then(function(res){
                    // 移动到第一个线标的地点
                    var firstLine = res.data[0];
                    map.centerAndZoom(new BMap.Point(firstLine.mapX,firstLine.mapY),15);
                    mapData = res.data;
                    drawTags(map,mapData);
                    drawLines(map,mapData);
                })
                .catch(function(err){
                    if(err.code === 'ECONNABORTED'){
                        // 请求超时
                    }
                });
    });

</script>
</body>
</html>
