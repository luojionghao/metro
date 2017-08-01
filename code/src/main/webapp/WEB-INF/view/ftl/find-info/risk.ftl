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
    <link rel="stylesheet" href="<@s.url'/plugins/bower/select2/dist/css/select2.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/progress.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/info_monitor.css'/>">

    <link rel="stylesheet" href="<@s.url '/plugins/bower/jstree/dist/themes/default/style.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/item_generalize.css'/>">

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!''}">
<input type="hidden" id="riskPdfUrl" name="riskPdfUrl" value="${(riskPdfUrl)!''}">
<section class="content" style="background: white; padding:0px">
        <div class="main">
          	<div class="tab-pane" id="tab_6">
                    <div class="tab-pane active" id="tab_7">
                        <div class="top_btn">
                            <a href="javascript:openUploadBox();">上传</a>
                            <a href="javascript:download();">下载</a>
                            <a href="javascript:deleteFile();">删除</a>
                        </div>
                        <iframe id="iframe_content5" name="iframe_content" class="iframe-content" scrolling="auto" <#if riskPdfUrl?? && riskPdfUrl != "">src="${riskPdfUrl+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if> frameborder="0"  width="100%">
                        </iframe>
                    </div>
                <!-- 上传文件弹框 -->
                <div id="uploadBox" class="cover new_cover">
                    <div class="modal-content middle">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                    aria-label="Close"  onclick="cancelUploadBox();">
                                <span aria-hidden="true">×</span>
                            </button>
                            <span class="modal-title">上传文件</span>
                        </div>
                        <div class="modal-body">
                            <form id="form1" name="form1" action="<@s.url '/monitor/info/risk-pdf/upload'/>" method="post" enctype="multipart/form-data" >
                                <label class="btn btn-default btn-file">
                                    选择文件<input type="file" name="file" id="pdf" accept="application/pdf" style="display:none;"/>
                                </label>
                                <span id="upFileName"></span>
                                <input type="hidden" name="iId" id="iId" value="${(intervalId)!0}"/>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left"
                                    data-dismiss="modal" onclick="cancelUploadBox();">取消</button>
                            <button type="button" class="btn btn-primary save_btn2"
                                    onclick="fsubmit();">上传</button>
                        </div>
                    </div>
                </div>

          	</div>
        </div>
</section>
    <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/echarts/dist/echarts.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/progressbar.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
    <script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/jquery-dateFormat/dist/jquery-dateFormat.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/select2/dist/js/select2.min.js'/>"></script>
  	<script type="text/javascript">
        // 取消上传框
        function cancelUploadBox(){
            $('#uploadBox').hide();
        }
        // 打开上传框
        function openUploadBox(){
            $('#uploadBox').show();
        }
        // 选择文件后，显示文件路径
        registerUpfileName(["pdf"],["upFileName"]);
        // 开始上传
        function fsubmit(){
            var pdf = $("#pdf").val();
            if(pdf == null || pdf==""){
                alert("请先选择文件");
                return;
            }
            var formData = new FormData($( "#form1" )[0]);
            $.ajax({
                url: '/monitor/info/risk-pdf/upload',
                type: 'POST',
                data: formData,
                dataType: "json",
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (returndata) {
                    console.log("success");
                    console.log(returndata);
                    document.getElementById('iframe_content5').src = returndata.riskPdfUrl+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90';
                    document.getElementById('riskPdfUrl').val = returndata.riskPdfUrl;
                    cancelUploadBox();
                },
                error: function (returndata) {
                    console.log("error");
                    console.log(returndata);
                }
            });

        }


        //删除文件
        function deleteFile(){
            var pdfUrl = $('#riskPdfUrl').val;
            if(pdfUrl == null || pdfUrl == ""){
                alert("还未上传文件");
                return;
            }
            $.ajax({
                type: "post",
                dataType:"json",
                url: "/monitor/info/risk-pdf/delete",
                data: {"intervalId":"${intervalId!''}"},
                success: function(json){
                    if(json.code == 1){
                        alert("删除成功");
                        document.getElementById('iframe_content5').src = '';
                    }else{
                        alert(json.result);
                    }
                },
                error: function (data, status, e){
                    //alert(e);
                    alert("删除失败");
                }
            });

        }
        function freset(obj){
            obj.reset();
        }
        function download(obj){
            if(obj == null || obj == ""){
                alert("还未上传文件");
                return;
            }
            var filename = obj.substring(obj.lastIndexOf("/")+1);
            location.href = "${request.contextPath}/common/file-download?filename="+filename;
            //下载
            /* $.ajax({
                type: "get",
                dataType:"json",
                url: "${request.contextPath}/common/file-download",
     		   data: {"filename":filename},
     		   success: function(json){
	   			   if(json.code == 1){
	   				   alert("下载成功");
	   			   }else{
	   				   alert(json.result);
	   			   }
   		       },
	   		  error: function (data, status, e){
	   			    alert(e);
	                alert("下载失败");
	            }
     	    }); */

        }
        function autoFrameSize(){
            $("#iframe_content5").css('height',function(){
                var screenHeight = window.innerHeight;
                var offset = $("#iframe_content5")[0].offsetTop;
                return screenHeight-offset;
            });
        }
        $(window).resize(autoFrameSize);
        autoFrameSize();
	</script>
</body>
</html>
