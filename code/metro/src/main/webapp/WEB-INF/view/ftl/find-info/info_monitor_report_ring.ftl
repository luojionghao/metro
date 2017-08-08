<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>盾构信息监控/左右线</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/report.css'/>" media="all">
</head>
<body class="report-body">
	<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!'0'}">
    <input type="hidden" id="lineId" name="lineId" value="${(lineId)!'0'}">
    <input type="hidden" id="cityName" name="cityName" value="GZ">
	<div class="nav-tabs-custom report-tabs">
    <ul class="nav nav-tabs report-nav-tabs">
      <li class="">
        <a id="tab1" href="#ring_report" data-toggle="tab" aria-expanded="true">环报表</a>
      </li>
      <li class="">
        <a id="tab5" href="#day_report" data-toggle="tab" aria-expanded="false">日报表</a>
      </li>
    </ul>
    <div class="tab-content report-tab-content">
      <div class="tab-pane" id="ring_report">
        <div class="exportBar">
          环号:
          <input id="ringNum" class="widge" type="number">
          线路:
          <select id="lrLine" class="form-control widge" name="">
            <option value="L">左线</option>
            <option value="R">右线</option>
          </select>
          <button id="exportBtn" class="btn btn-info btn-sm widge">导出</button>
        </div>
      </div>
      <div class="tab-pane" id="day_report">
        日报表
      </div>
    </div>
  </div>

  <!-- 环报表下载弹框 -->
  <div id="modal-range-download" class="modal fade">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button class="close" type="button" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">x</span>
          </button>
          <h4 class="modal-title">导出</h4>
        </div>
        <div class="modal-body range-download-dialog">
          <span class="range-download-name"></span>
          <button id="rangeDownload" class="btn btn-default btn-sm" type="button">下载</button>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" type="button" data-dismiss="modal">退出</button>
        </div>
      </div>
    </div>
  </div>
  <!-- jQuery 2.2.0 -->
  <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
  <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
	<script src="<@s.url'/plugins/js/base.js'/>"></script>
  <script>
      var ax_request = axios.create({
          baseURL:'${request.contextPath}/monitor/report',
          timeout:60000
      });
    $('#tab1').tab('show');

    $("#exportBtn").on("click",function(){
        ax_request.get('/ring/export',{
            params:{
                cityName:$("#cityName").val(),
                intervalId:$("#intervalId").val(),
                leftOrRight:$("#lrLine").val(),
                lineId:$("#lineId").val(),
                ring:$("#ringNum").val()
            }
        }).then(function(res){
            console.log(res);
          if(res.data.code == 1){
            var filePath = res.data.result;
            var downlink = filePath.substring(filePath.lastIndexOf("/")+1);
            $('.range-download-name').text(downlink);
            $('#rangeDownload').removeAttr('disabled');
            $('#rangeDownload').data('downlink',filePath);
          }else{
            $('.range-download-name').text('文件不存在');
            $('#rangeDownload').prop('disabled',true);
            $('#rangeDownload').data('downlink','');
          }
          $('#modal-range-download').modal('show');
        }).catch(function(err){
            alert("查询超时");
            $("#query_btn1").removeAttr('disabled');
            $("#query_btn1").text('查询');
            loadingDismiss();
        });
    });

    $('#rangeDownload').on('click',function(){
      var fileName = $(this).data('downlink');
      location.href = "${request.contextPath}/common/file-download/report?filename="+fileName;
      $('#modal-range-download').modal('hide');
    });

  </script>
</body>
</html>
