<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目概况管理/线</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport'/>">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url '/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url '/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.css'/>">
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
    <script src="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.js'/>"></script>
    <script src="<@s.url '/plugins/js/base.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a href="#tab_1" data-toggle="tab" aria-expanded="true" id="tab1">工程信息</a>
            </li>
            <li class="">
                <a href="#tab_2" data-toggle="tab" aria-expanded="false" id="tab2">工程文档</a>
            </li>
        </ul>
        
        <form id="lineform" style="height:100%" name="lineform" method="post" action="" onsubmit="return false;">
        <input type="hidden" id="lineColor" name="lineColor" value="${line.lineColor!''}"/>
        <input type="hidden" id="cityId" name="cityId" value="1"/> 
        <input type="hidden" id="id" name="id" value="${line.id?c!''}"/> 
        
        <div class="tab-content">
        <div class="tab-pane active" id="tab_1">
            
                <ul class="project_info">
                    <li>
                        <span>线路名称：</span>
                        <input type="text" name="lineName" id="lineName" value="${line.lineName!''}">
                    </li>
                    <li>
                        <span>线路编号：</span>
                        <input type="text" name="lineNo" id="lineNo" value="${line.lineNo!''}">
                    </li>
                    <li>
                        <span>标记颜色：</span>
                        <div class="my-colorpicker">
                            <div class="input-group-addon">
                                <i style="background-color: ${line.lineColor!''};"></i>
                            </div>
                        </div>
                    </li>
                    <li>
                        <span>显示状态：</span>
                        <select class="state_select" id="lineStatus" name="lineStatus">
                            <#if line.lineStatus == 1>
                               <option value="1" selected="selected">启用</option>
                               <option value="0">禁用</option>
                            <#else>
                               <option value="1">启用</option>
                               <option value="0" selected="selected">禁用</option>
                            </#if>
                        </select>
                    </li>
                    <li>
                        <span>地图标记坐标-X：</span>
                        <input type="text" name="mapX" id="mapX" value="${(line.mapX?string('0.000000'))!''}" class="coord_val">
                        <span>地图标记坐标-Y：</span>
                        <input type="text" name="mapY" id="mapY" value="${(line.mapY?string('0.000000'))!''}" class="coord_val">
                    </li>
                    <li class="clearfix">
                        <span class="left_text">备注：</span>
                        <textarea class="remark_textarea" name="remark" id="remark">${line.remark!''}</textarea>
                    </li>
                </ul>
                <div class="bottom_btn">
                    <a href="javascript:void(0);" class="save_btn" onclick="lineupdate();">保存</a>
                </div>
            </div>
         </form>
   
            <div class="tab-pane" id="tab_2">
                
                <div class="top_btn">
                    <a href="javascript:openUploadBox()">上传</a>
                    <a href="javascript:download('${(line.projectPdfUrl)!}');">下载</a>
                    <a href="javascript:deleteFile();">删除</a>
                </div>
                <iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="auto" <#if line?? && line.projectPdfUrl?? && line.projectPdfUrl != ""> src="${(line.projectPdfUrl)+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if> frameborder="0" width="100%">
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
			<form id="form1" name="form1" action="<@s.url '/project-info/basic/line-pdf/upload'/>" method="post" enctype="multipart/form-data" >
	  	    <label class="btn btn-default btn-file">
	    	   选择文件<input type="file" name="file" id="pdf" accept="application/pdf" style="display:none;"/>
	    	</label>
	    	<span id="upFileName"></span>
	    	<input type="hidden" name="lineId" id="lineId" value="${line.id!''}"/>
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
    
    var active = "${active!''}";
    if(active == "2"){
      $('#tab2').tab('show');
    }
    
    $('#tab_2').on('shown.bs.tab', function (e) {
    	$("#iframe_content").attr("src","<#if line?? && line.projectPdfUrl?? && line.projectPdfUrl != "">${(line.projectPdfUrl)+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}</#if>");
    });
    
    
     //线路更新保存
    function lineupdate(){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${request.contextPath}/project-info/basic/lineinfo/update",
    		data:$("#lineform").serialize(),
    		success:function(json){
    			if(json.code == 1){
    				alert("保存成功");
    			}else{
    				alert(json.result);
    			}
    		},
            error: function (data, status, e){  
                alert("保存失败");
            }
    	});
    }
    
    // 打开上传框
	function openUploadBox(){
		$('#uploadBox').show();
	}
   
	// 取消上传框
	function cancelUploadBox(){
		$('#uploadBox').hide();
	}
	// 选择文件后，显示文件路径
    registerUpfileName(["pdf"],["upFileName"]);
    
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
        	var pdfUrl = "${(line.projectPdfUrl)!''}";
        	if(pdfUrl == null || pdfUrl == ""){
        		alert("还未上传文件");
        		return;
        	}
        	$.ajax({
      		   type: "post",
      		   dataType:"json",
      		   url: "${request.contextPath}/project-info/basic/line-pdf/delete",
      		   data: {"lineId":"${(line.id?c)!''}"},
      		   success: function(json){
 	   			   if(json.code == 1){
 	   			       alert("删除成功");
 	   			       $("#iframe_content")[0].src = "";
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
         function download(obj){
        	if(obj == null || obj == ""){
        		alert("还未上传文件");
        		return;
        	}
        	var filename = obj.substring(obj.lastIndexOf("/")+1);
        	location.href = "${request.contextPath}/common/file-download?filename="+filename;
        }
    
    
        $(".my-colorpicker").colorpicker();
	    //颜色拾取颜色改变时事件.
	    $('.my-colorpicker').colorpicker().on('changeColor', function(ev){
	        $("#lineColor").val(ev.color.toHex());
	    });
	    
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
