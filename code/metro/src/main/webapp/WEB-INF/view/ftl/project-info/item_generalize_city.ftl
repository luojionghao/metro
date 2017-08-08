<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目概况管理/市</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url '/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url '/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url '/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/jstree/dist/themes/default/style.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/css/item_generalize.css'/>">
	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url '/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url '/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
   	<script src="<@s.url '/plugins/js/base.js'/>"></script>

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#tab_1" data-toggle="tab" aria-expanded="true">工程信息</a>
            </li>
        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="tab_1">
                <div class="top_btn">
                    <a href="javascript:openUploadBox();">上传</a>
                    <a href="javascript:download('${projectPdfUrl!}');">下载</a>
                    <a href="javascript:deleteFile();">删除</a>
                </div>
                <iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="auto" <#if projectPdfUrl?? && projectPdfUrl != "">src="${projectPdfUrl+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if>  frameborder="0" width="100%">
                </iframe>
            </div>
        </div>
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
	    <form id="form1" name="form1" action="<@s.url '/project-info/basic/city-pdf/upload'/>" method="post" enctype="multipart/form-data" >
	    <label class="btn btn-default btn-file">
    	   选择文件<input type="file" name="file" id="pdf" accept="application/pdf" style="display:none;"/>
    	</label>
    	<span id="upFileName"></span>
        <input type="hidden" name="cityId" id="cityId" value="${cityId!''}"/>
        </form>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default pull-left"
			data-dismiss="modal" onclick="cancelUploadBox();">取消</button>
		<button type="button" class="btn btn-primary save_btn2"
			onclick="fsubmit(document.form1);">上传</button>
	</div>
</div>
</div>
		
    
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
        function fsubmit(obj){
        	var pdf = $("#pdf").val();
        	if(pdf == null || pdf==""){
        		alert("请先选择文件");
        		return;
        	}
        	obj.submit();
        }
        
        
        //删除文件
        function deleteFile(){
        	var pdfUrl = "${projectPdfUrl!''}";
        	if(pdfUrl == null || pdfUrl == ""){
        		alert("还未上传文件");
        		return;
        	}
        	$.ajax({
      		   type: "post",
      		   dataType:"json",
      		   url: "${request.contextPath}/project-info/basic/city-pdf/delete",
      		   data: {"cityId":"${cityId!''}"},
      		   success: function(json){
 	   			   if(json.code == 1){
 	   			       alert("删除成功");
 	   			       location.href="${request.contextPath}/project-info/basic/cityinfo?cityId="+${cityId!''};
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
            $("#iframe_content").css('height',function(){
                var screenHeight = window.innerHeight;
                var offset = $("#iframe_content")[0].offsetTop;
                return screenHeight-offset;
            });
        }
        $(window).resize(autoFrameSize);
        autoFrameSize();
    </script>
</body>
</html>
