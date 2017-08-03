<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>盾构信息监控/区间</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/progress.css'/>">
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
<input type="hidden" id="lRingNum" value="${(lRingNum)!0}">
<input type="hidden" id="rRingNum" value="${(rRingNum)!0}">
<input type="hidden" id="lCountRing" value="${(lCountRing)!0}">
<input type="hidden" id="rCountRing" value="${(rCountRing)!0}">
<input type="hidden" id="lvl" value="${(lr_l.ringNum)!0}">
<input type="hidden" id="rvr" value="${(lr_r.ringNum)!0}">
<input type="hidden" id="sectionSvgUrl" value="${(pp.sectionSvgUrl)!""}">
<input type="hidden" id="lLinekey" value="${(lLinekey)!""}">
<input type="hidden" id="rLinekey" value="${(rLinekey)!""}">
<input type="hidden" id="goNumAll" value="${(goNumAll)!0}">
<section class="content" style="background: white; padding:0px">

    <div class="main">
        <button id="rangbtn" type="button" style="width: 82px" class="btn btn-primary" onclick="clickRange()">测  距</button>
        <button id="coordbtn" type="button" style="width: 82px" class="btn btn-primary" onclick="clickCoordinate()">查看坐标</button>
        <button id="leftbtn" type="button" style="width: 82px" class="btn btn-primary" onclick="clickLeft()">左线</button>
        <button id="hidecoord" type="button" style="width: 82px" class="btn btn-primary" onclick="hidecoord()">隐藏线路</button>
        <div class="map-ping">
            <div class="map-content">
                <svg id="svg" width="100%" xmlns="http://www.w3.org/2000/svg" height="100%" style="min-height: 696px;" version="1.1"></svg>
            </div>
            <div  class="box-body" id="riskModal"  style="width: 450px; height: 320px;background: white; z-index: 1000; position: absolute; bottom: 28px; border: 1px solid;display:none">
                <div class="row">
                    <div class="col-xs-7" style="padding-right: 2px; width: 180px;">
                        <h4 style="font-weight: bold; margin-top: 1px;">风险位置标识</h4>
                        <textarea class="riskTextarea" id="riskTextMsg" disabled="disabled"></textarea>
                        <h6 style="font-size: small" id="riskPdf1Url"></h6>
                        <h6 style="font-size: small" id="riskPdf2Url"></h6>
                        <h6 style="font-size: small" id="riskPdf3Url"></h6>
                    </div>
                    <div class="col-xs-5"  style="padding-left: 20px;">
                        <div style="width: 245px;height: 245px;background: grey;" id="risImgUrl"></div>
                        <button class="btn btn-primary" style="margin-top: 10px; margin-left: 192px;" onclick="closeRisk()">关闭</button>
                    </div>
                </div>
            </div>
        </div>

    </div>


  <#--<div class="main">-->
    <#--<div class="info-box bg-ablue" >-->
      <#--<div class="progress-abule" id="containerL"></div>-->
      <#--<div class="info-box-content ablue">-->
        <#--<span class="info-box-number">左线</span>-->
        <#--<div class="progress" style="height: 5px;background: #382924;">-->
          <#--<div class="progress-bar" style="width: ${(lvlz)!0}%"></div>-->
        <#--</div>-->
        <#--<span class="info-box-number">-->
          <#--${lRingNum!'0'}/${(lr_l.ringNum)!'0'}环-->
          <#--<a class="progress-a">${(lSearchTime?string('yyyy-MM-dd HH:mm:ss'))!'无'}</a>-->
        <#--</span>-->
      <#--</div>-->
    <#--</div>-->
    <#--<div class="info-box bg-agreen">-->
      <#--<div class="progress-agreen" id="containerR"></div>-->
      <#--<div class="info-box-content agreen">-->
        <#--<span class="info-box-number">右线</span>-->
        <#--<div class="progress" style="height: 5px;background: #382924;">-->
          <#--<div class="progress-bar" style="width: ${(rvrz)!0}%"></div>-->
        <#--</div>-->
        <#--<span class="info-box-number">-->
          <#--${(rRingNum)!'0'}/${(lr_r.ringNum)!'0'}环-->
          <#--<a class="progress-a">${(rSearchTime?string('yyyy-MM-dd HH:mm:ss'))!'无'}</a>-->
        <#--</span>-->
      <#--</div>-->
    <#--</div>-->
    <#--<div class="map-ping">-->
      <#--<span class="map-span">平面图</span>-->
      <#--<div class="map-content">-->
        <#--<img id="pingImg" class="map-img" src="${(pp.ppSvgUrl)!''}"></div>-->
        <#--<!-- <img id="pingImg" class="map-img" src="<@s.url'/plugins/images/pm.png'/>"></div> &ndash;&gt;-->
    <#--</div>-->
    <#--<div class="map-pou">-->
      <#--<span class="map-span" >剖面图</span>-->
      <#--<div class="map-content">-->
        <#--<!-- <img id="pouImg" class="map-img" src="<@s.url'/plugins/images/po.png'/>"></div> &ndash;&gt;-->
        <#--<img id="pouImg" class="map-img" src="${(pp.sectionSvgUrl)!''}"></div>-->
        <#--</div>-->
      <#--</div>-->
    </section>
  <!-- </div> -->
    <script>
        loadingShow("${request.contextPath!}");
        var rpArray = [];

        var lLinedata;
        var rLinedata;
        var lCoordinates;
        var rCoordinates;

        function goNum(goNum) {
            moveToNum(goNum);
        }

        function getLinedata(){
            var lLinekey = $("#lLinekey").val();
            if(lLinekey == null || lLinekey==""){
                return;
            }
            $.ajax({
                type:"GET",
                dataType:"json",
                url:"${request.contextPath}/monitor/info/find/line/monitor/risk?intervalId=${intervalId!""}",
                async: false,
                success:function(json){
                    rpArray = json;
                },
                error: function (data, status, e){
                    lLinedata= null;
                }
            });

            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${request.contextPath}/monitor/info/find/line/monitor/coordinates?line=" + lLinekey,
                async: false,
                success: function (json) {
                    if (json.code == 1) {
                        lCoordinates = json.result;
                    } else {
                        lCoordinates = null;
                    }
                },
                error: function (data, status, e) {
                    lCoordinates = null;
                }
            });

            $.ajax({
                type: "GET",
                dataType: "json",
                url: "${request.contextPath}/monitor/info/find/line/monitor/coordinates?line=" + rLinekey,
                async: false,
                success: function (json) {
                    if (json.code == 1) {
                        rCoordinates = json.result;
                    } else {
                        rCoordinates = null;
                    }
                },
                error: function (data, status, e) {
                    rCoordinates = null;
                }
            });

            $.ajax({
                type:"GET",
                dataType:"json",
                url:"${request.contextPath}/monitor/info/find/line/monitor/datas?line="+lLinekey,
                async: false,
                success:function(json){
                    if(json.code == 1){
                        lLinedata = json.result;
                    }else{
                        lLinedata= null;
                    }
                },
                error: function (data, status, e){
                    lLinedata= null;
                }
            });
            $.ajax({
                type:"GET",
                dataType:"json",
                url:"${request.contextPath}/monitor/info/find/line/monitor/datas?line="+rLinekey,
                async: false,
                success:function(json){
                    if(json.code == 1){
                        rLinedata = json.result;
                    }else{
                        rLinedata= null;
                    }
                },
                error: function (data, status, e){
                    rLinedata= null;
                }
            });
        }
    </script>
</body>
    <script src="<@s.url'/plugins/js/jquery-2.2.3.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/snap.svg.js'/>"></script>
    <script src="<@s.url'/plugins/js/snap.svg.zpd.js'/>"></script>
    <script src="<@s.url'/plugins/js/snapL.js'/>"></script>
</html>  