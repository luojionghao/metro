<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目概况管理-区间</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url '/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <!-- 图标1 -->
    <link rel="stylesheet" href="<@s.url '/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url '/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/datepicker/datepicker3.css'/>">
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
    <script src="<@s.url '/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
    <script src="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.js'/>"></script>
    <script src="<@s.url '/plugins/js/base.js'/>"></script>

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
 			<li class="active">
          		<a href="#tab_1" data-toggle="tab" aria-expanded="true" id="tab1">左线工程信息</a>
          	</li>
            <li class="">
                <a href="#tab_2" data-toggle="tab" aria-expanded="false" id="tab2">右线工程信息</a>
            </li>
          	<li class="">
          		<a href="#tab_3" data-toggle="tab" aria-expanded="false" id="tab3">工程文档</a>
          	</li>
        </ul>
        
      
        <div class="tab-content">
         
            <div class="tab-pane active" id="tab_1">
            <form id="intervalform1" name="intervalform1" method="post" action="" onsubmit="return false;">
            <input type="hidden" id="intervalId1" name="intervalId" value="${interval.id?c!''}"/> 
            <input type="hidden" id="leftOrRight1" name="leftOrRight" value="l"/> 
            <#if interval.intervalLrList ?? && interval.intervalLrList?size gt 0 >
                <#assign l = interval.intervalLrList[0]>
                <input type="hidden" id="intervalColor1" name="intervalColor" value="${l.intervalColor!''}"/>
                <input type="hidden" id="saveOrUpdate1" name="saveOrUpdate" value="1"/>
                <input type="hidden" id="updateId1" name="updateId" value="${l.id?c!''}"/>
            <#else>
                <input type="hidden" id="saveOrUpdate1" name="saveOrUpdate" value="2"/> 
            </#if>
            
            	<ul class="project_info">
            		<li>
                        <span>显示状态：</span>
                        <select class="state_select" id="status" name="status">
                        <#if l?? && l.status == 0>
                            <option value="1">启用</option>
                            <option value="0" selected="selected">停用</option>
                        <#else>
                            <option value="1" selected="selected">启用</option>
                            <option value="0">停用</option>
                        </#if>
                        </select>
                    </li>
            		<li>
                        <span>工程状态：</span>
                        <select class="state_select" id="buildStatus" name="buildStatus">
                         <#if l?? && l.buildStatus == 0>
                            <option value="0" selected="selected">未启动</option>
                         <#else>
                            <option value="0">未启动</option>
                         </#if>
                         <#if l?? && l.buildStatus == 1>
                            <option value="1" selected="selected">已启动</option>
                         <#else>
                            <option value="1">已启动</option>
                         </#if>   
                         <#if l?? && l.buildStatus == 2>
                            <option value="2" selected="selected">已贯通</option>
                         <#else>
                            <option value="2">已贯通</option>
                         </#if> 
                            
                            
                        </select>
                    </li>
            		<li class="clearfix">
            			<span class="left_text">施工日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="buildDate" value="${(l.buildDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker1">
                        </div>
            		</li>
            		<li class="clearfix">
            			<span class="left_text">贯通日期：</span>
            			<div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="throughDate" value="${(l.through_date?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker2">
                        </div>
            		</li>
            		<li>
                        <span>总环数：</span>
                        <input type="text" value="${(l.ringNum)!''}" id="ringNum" name="ringNum">
                    </li>
                    <li>
                        <span>线路颜色：</span>
                        <div class="my-colorpicker" id="colorpicker1">
                            <div class="input-group-addon">
                                <i style="background-color: ${(l.intervalColor)!''};"></i>
                            </div>
                        </div>
                    </li>
                    <li class="clearfix">
                        <span class="left_text">百度坐标集合：</span>
                        <textarea class="remark_textarea" id="mapXy" name="mapXy">${(l.mapXy?html)!''}</textarea>
                        <span style="color: red;">坐标录入格式：[{"x":经度坐标,"y":纬度坐标},{"x":经度坐标,"y":纬度坐标}]</span>
                       <!--  <input type="text" value="${(l.mapXy?html)!''}" id="mapXy" name="mapXy"> -->
                    </li>
                    <li>
                        <span>盾构机编号：</span>
                        <input type="text" value="${(l.machineNo)!''}" id="machineNo" name="machineNo">
                    </li>
                    <li>
                        <span>盾构机厂家：</span>
                        <input type="text" value="${(l.machineCompany)!''}" id="machineCompany" name="machineCompany">
                    </li>
                    <li>
                        <span>盾构机类型：</span>
                        <select class="state_select" id="machineType_left" name="machineType">
                            <option <#if l.machineType ??><#if l.machineType == "3">selected="selected"</#if></#if> value="3">泥水盾构</option>
                            <option <#if l.machineType ??><#if l.machineType == "1">selected="selected"</#if></#if> value="1">土压平衡式盾构机</option> <!-- 检查一下数据库土压的id是多少？ -->
                        </select>
                    </li>
                    <li class="clearfix">
                        <span class="left_text">盾构机出厂日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="machineProductDate" value="${(l.machineProductDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker1">
                        </div>
                    </li>
                    <li>
                        <span>盾构器承包商：</span>
                        <input type="text" value="${(l.machineContractor)!''}" id="machineContractor" name="machineContractor">
                    </li>
                    <li class="clearfix">
                        <span class="left_text">盾构机审查日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="machineReviewDate" value="${(l.machineReviewDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker2">
                        </div>
                    </li>
                    <li id="li_knif_left">
                        <span id="knif_left">刀盘背景：</span>
                        <select class="state_select" id="cutterPhotoId" name="cutterPhotoId">
                        <option value="">--请选择--</option>
                        <#if cutterPhotos ?? && cutterPhotos?size gt 0>
                          <#list cutterPhotos as cutter>
                            <#if l?? && l.cutterPhotoId?? && l.cutterPhotoId == cutter.id>
                                 <option selected="selected" value="${cutter.id?c}">${(cutter.photoName)!''}</option>
                            <#else>
                                 <option value="${cutter.id?c}">${(cutter.photoName)!''}</option>
                            </#if>
                          </#list>
                        </#if>
                        </select>
                    </li>
                    <li id="li_slurry_left">
                        <span id="slurry_left">泥水背景：</span>
                        <select class="state_select" id="slurryPhotoId" name="slurryPhotoId">
                            <option value="">--请选择--</option>
                        <#if slurryPhotos ?? && slurryPhotos?size gt 0>
                            <#list slurryPhotos as slurry>
                                <#if l?? && l.slurryPhotoId?? && l.slurryPhotoId == slurry.id>
                                    <option selected="selected" value="${slurry.id?c}">${(slurry.photoName)!''}</option>
                                <#else>
                                    <option value="${slurry.id?c}">${(slurry.photoName)!''}</option>
                                </#if>
                            </#list>
                        </#if>
                        </select>
                    </li>
                    <li id="li_screw_left">
                        <span id="screw_left">螺旋背景：</span>
                         <select class="state_select" id="screwPhotoId" name="screwPhotoId">
                         <option value="">--请选择--</option>
                         <#if screwPhotos ?? && screwPhotos?size gt 0>
                          <#list screwPhotos as screw>
                            <#if l?? && l.screwPhotoId?? && l.screwPhotoId == screw.id>
                                 <option selected="selected" value="${screw.id?c}">${(screw.photoName)!''}</option>
                            <#else>
                                 <option value="${screw.id?c}">${(screw.photoName)!''}</option>
                            </#if>
                          </#list>
                        </#if>
                        </select>
                    </li>
            		<li class="clearfix">
            			<span class="left_text">备注：</span>
            			<textarea class="remark_textarea" id="remark" name="remark">${(l.remark)!''}</textarea>
            		</li>
            	</ul>
            	<div class="bottom_btn">
            		<a href="javascript:intervalSaveOrUpdate(document.intervalform1);" class="save_btn">保存</a>
            	</div>
              </form>
          	</div>
 
            <div class="tab-pane" id="tab_2">
            <form id="intervalform2" name="intervalform2" method="post" action="" onsubmit="return false;">
            <input type="hidden" id="intervalId2" name="intervalId" value="${interval.id?c!''}"/> 
            <input type="hidden" id="leftOrRight2" name="leftOrRight" value="r"/> 
            <#if interval.intervalLrList ?? && interval.intervalLrList?size gt 1 >
                <#assign r = interval.intervalLrList[1]>
                <input type="hidden" id="saveOrUpdate2" name="saveOrUpdate" value="1"/>
                <input type="hidden" id="updateId2" name="updateId" value="${r.id?c!''}"/>
                <input type="hidden" id="intervalColor2" name="intervalColor" value="${r.intervalColor!''}"/>
            <#else>
                <input type="hidden" id="saveOrUpdate2" name="saveOrUpdate" value="2"/> 
                <input type="hidden" id="intervalColor2" name="intervalColor"/>
            </#if>
              	<ul class="project_info">
            		<li>
                        <span>显示状态：</span>
                        <select class="state_select" id="status" name="status">
                        <#if r?? && r.status == 0>
                            <option value="1">启用</option>
                            <option value="0" selected="selected">停用</option>
                        <#else>
                            <option value="1" selected="selected">启用</option>
                            <option value="0">停用</option>
                        </#if>
                        </select>
                    </li>
            		<li>
                        <span>工程状态：</span>
                        <select class="state_select" id="buildStatus" name="buildStatus">
                         <#if r?? && r.buildStatus == 0>
                            <option value="0" selected="selected">未启动</option>
                         <#else>
                            <option value="0">未启动</option>
                         </#if>
                         <#if r?? && r.buildStatus == 1>
                            <option value="1" selected="selected">已启动</option>
                         <#else>
                            <option value="1">已启动</option>
                         </#if>   
                         <#if r?? && r.buildStatus == 2>
                            <option value="2" selected="selected">已贯通</option>
                         <#else>
                            <option value="2">已贯通</option>
                         </#if> 
                            
                            
                        </select>
                    </li>
            		<li class="clearfix">
            			<span class="left_text">施工日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="buildDate" value="${(r.buildDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker1">
                        </div>
            		</li>
            		<li class="clearfix">
            			<span class="left_text">贯通日期：</span>
            			<div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="throughDate" value="${(r.through_date?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker2">
                        </div>
            		</li>
            		<li>
                        <span>总环数：</span>
                        <input type="text" value="${(r.ringNum)!''}" id="ringNum" name="ringNum">
                    </li>
                    <li>
                        <span>线路颜色：</span>
                        <div class="my-colorpicker" id="colorpicker2">
                            <div class="input-group-addon">
                                <i style="background-color: ${(r.intervalColor)!''};"></i>
                            </div>
                        </div>
                    </li>
                    <li class="clearfix">
                         <span class="left_text">百度坐标集合：</span>
                         <textarea class="remark_textarea" id="mapXy" name="mapXy">${(r.mapXy?html)!''}</textarea>
                        <span style="color: red;">坐标录入格式：[{"x":经度坐标,"y":纬度坐标},{"x":经度坐标,"y":纬度坐标}]</span>
                        <!-- <input type="text" value="${(r.mapXy?html)!''}" id="mapXy" name="mapXy"> -->
                    </li>
                    <li>
                        <span>盾构机编号：</span>
                        <input type="text" value="${(r.machineNo)!''}" id="machineNo" name="machineNo">
                    </li>
                    <li>
                        <span>盾构机厂家：</span>
                        <input type="text" value="${(r.machineCompany)!''}" id="machineCompany" name="machineCompany">
                    </li>
                    <li>
                        <span>盾构机类型：</span>
                        <select class="state_select" id="machineType_right" name="machineType">
	                        <option <#if r.machineType ??><#if r.machineType == "3">selected="selected"</#if></#if> value="3">泥水盾构</option>
	                        <option <#if r.machineType ??><#if r.machineType == "1">selected="selected"</#if></#if> value="1">土压平衡式盾构机</option> <!-- 检查一下数据库土压的id是多少？ -->
                        </select>
                    </li>
                    <li class="clearfix">
                        <span class="left_text">盾构机出厂日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="machineProductDate" value="${(r.machineProductDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker1">
                        </div>
                    </li>
                    <li>
                        <span>盾构器承包商：</span>
                        <input type="text" value="${(r.machineContractor)!''}" id="machineContractor" name="machineContractor">
                    </li>
                    <li class="clearfix">
                        <span class="left_text">盾构机审查日期：</span>
                        <div class="input-group date">
                            <div class="input-group-addon">
                                <i class="fa fa-calendar"></i>
                            </div>
                            <input type="text" name="machineReviewDate" value="${(r.machineReviewDate?string('dd/MM/yyyy'))!''}" class="form-control pull-right date_input" id="datepicker2">
                        </div>
                    </li>
                    <li id="li_knif_right">
                        <span id="knif_right">刀盘背景：</span>
                       <select class="state_select" id="cutterPhotoId" name="cutterPhotoId">
                        <option value="">--请选择--</option>
                        <#if cutterPhotos ?? && cutterPhotos?size gt 0>
                          <#list cutterPhotos as cutter>
                            <#if r?? && r.cutterPhotoId?? && r.cutterPhotoId == cutter.id>
                                 <option selected="selected" value="${cutter.id?c}">${(cutter.photoName)!''}</option>
                            <#else>
                                 <option value="${cutter.id?c}">${(cutter.photoName)!''}</option>
                            </#if>
                          </#list>
                        </#if>
                        </select>
                    </li>

                    <li id="li_slurry_right">
                        <span id="slurry_right">泥水背景：</span>
                        <select class="state_select" id="slurryPhotoId" name="slurryPhotoId">
                            <option value="">--请选择--</option>
                        <#if slurryPhotos ?? && slurryPhotos?size gt 0>
                            <#list slurryPhotos as slurry>
                                <#if r?? && r.slurryPhotoId?? && r.slurryPhotoId == slurry.id>
                                    <option selected="selected" value="${slurry.id?c}">${(slurry.photoName)!''}</option>
                                <#else>
                                    <option value="${slurry.id?c}">${(slurry.photoName)!''}</option>
                                </#if>
                            </#list>
                        </#if>
                        </select>
                    </li>

                    <li id="li_screw_right">
                        <span id="screw_right">螺旋背景：</span>
                       <select class="state_select" id="screwPhotoId" name="screwPhotoId">
                       <option value="">--请选择--</option>
                        <#if screwPhotos ?? && screwPhotos?size gt 0>
                          <#list screwPhotos as screw>
                            <#if r?? && r.screwPhotoId?? && r.screwPhotoId == screw.id>
                                 <option selected="selected" value="${screw.id?c!''}">${(screw.photoName)!''}</option>
                            <#else>
                                 <option value="${screw.id?c!''}">${(screw.photoName)!''}</option>
                            </#if>
                          </#list>
                        </#if>
                        </select>
                    </li>
            		<li class="clearfix">
            			<span class="left_text">备注：</span>
            			<textarea class="remark_textarea" id="remark" name="remark">${(r.remark)!''}</textarea>
            		</li>
            	</ul>
            	<div class="bottom_btn">
            		<a href="javascript:intervalSaveOrUpdate(document.intervalform2);" class="save_btn">保存</a>
            	</div>
                </form>
            </div>
          	<div class="tab-pane" id="tab_3">
          		<div class="top_btn">
                    <a href="javascript:openUploadBox();">上传</a>
                    <a href="javascript:download('${(interval.projectPdfUrl)!}');">下载</a>
                    <a href="javascript:deleteFile();">删除</a>
                </div>
                <iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="auto" <#if interval?? && interval.projectPdfUrl?? && interval.projectPdfUrl != ""> src="${(interval.projectPdfUrl)+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if> frameborder="0" width="100%">
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
		<form id="fileform" name="fileform" action="<@s.url '/project-info/basic/interval-pdf/upload'/>" method="post" enctype="multipart/form-data" >
	    <label class="btn btn-default btn-file">
    	   选择文件 <input id="pdf" name="file" type="file" accept="application/pdf" style="display:none;"/> 
    	</label>
    	<span id="upFileName"></span>
    	<input type="hidden" name="intervalId" id="intervalId" value="${interval.id?c!''}"/>
        </form>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default pull-left"
			data-dismiss="modal" onclick="cancelUploadBox();">取消</button>
		<button type="button" class="btn btn-primary save_btn2"
			onclick="fsubmit(document.fileform);">上传</button>
	</div>
</div>
</div>
  	
  	
<script type="text/javascript">
    var active = "${active!''}";
    if(active == "3"){
      $('#tab3').tab('show');
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
    
      //线路区间保存或更新
    function intervalSaveOrUpdate(thisform){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${request.contextPath}/project-info/basic/interval-lrinfo/save-or-update",
    		data:$(thisform).serialize(),
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
        	var pdfUrl = "${(interval.projectPdfUrl)!''}";
        	if(pdfUrl == null || pdfUrl == ""){
        		alert("还未上传文件");
        		return;
        	}
        	$.ajax({
      		   type: "post",
      		   dataType:"json",
      		   url: "${request.contextPath}/project-info/basic/interval-pdf/delete",
      		   data: {"intervalId":"${(interval.id?c)!''}"},
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
    
    
        $(".date_input").datepicker();
        $(".my-colorpicker").colorpicker();

	    //颜色拾取颜色改变时事件.
	    $("#colorpicker1").colorpicker().on('changeColor', function(ev){
	        $("#intervalColor1").val(ev.color.toHex());
	    });
	   
	    $("#colorpicker2").colorpicker().on('changeColor', function(ev){
	        $("#intervalColor2").val(ev.color.toHex());
	    });
        
        function autoFrameSize(){
            $("#iframe_content").css('height',function(){
                var screenHeight = window.innerHeight;
                var offset = $("#iframe_content")[0].offsetTop;
                return screenHeight-offset;
            });
            
            $(".tab-content").css('height',function(){
                var screenHeight = window.innerHeight;
                var offset = $(".tab-content")[0].offsetTop;
                return screenHeight-offset;
            });
        }
        $(window).resize(autoFrameSize);
        autoFrameSize();

   if($("#machineType_left").children('option:selected').val() == 3){
        $("#li_slurry_left").show();
        $("#li_screw_left").hide();
    }else if($("#machineType_left").children('option:selected').val() == 1){
       $("#li_screw_left").show();
       $("#li_slurry_left").hide();
   }

    if($("#machineType_right").children('option:selected').val() == 3){
        $("#li_slurry_right").show();
        $("#li_screw_right").hide();
    }else if($("#machineType_right").children('option:selected').val() == 1){
        $("#li_screw_right").show();
        $("#li_slurry_right").hide();
    }
        
        /****************** 泥水与土压盾构选择响应 *******************/
        
        function machineTypeChange(name){
        	var select_name = "#machineType_"+name;
        	var span_name = "#knif_"+name;
        	console.log(select_name);
        	console.log(span_name);
        	$(select_name).change(function(){
        		
        		var selected = $(this).children('option:selected').val();
        		console.log('in');
        		if(selected == 3){
        		    $("#li_slurry_"+name).show();
                    $("#li_screw_"+name).hide();
                }else if(selected == 1){
                    $("#li_screw_"+name).show();
                    $("#li_slurry_"+name).hide();
                }

        	});
        }
        machineTypeChange('left');
        machineTypeChange('right');
    </script>
</body>
</html>
