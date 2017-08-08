<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>导向数据管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/item_guide.css?V=2'/>">
	
	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
	<script src="<@s.url'/plugins/js/table.js'/>"></script>
	<script src="<@s.url'/plugins/js/base.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="dict_content">
	    <div class="box">
	       
             <ul class="box-header clearfix">
    			<li><button type="button" class="head_btn" style="margin-left:0px;" onclick="downloadTemplateFile('导向数据模板.xls');">下载模板文件</button></li>
    			<li><button type="button" class="head_btn" onclick="exportExcel();">导出</button></li>
    			<li><button type="button" class="head_btn" onclick="javascript:openUploadBox();">上传</button></li>
            </ul>
           
            <div class="box-body">
                <div class="row">
                    <div class="col-sm-12">
                    <!-- 表格模块 -->
                    <table id="roleTable" class="table table-bordered table-hover dataTable">
                        <thead>
                            <tr>
	                            <th>里程</th>
	                            <th>X坐标</th>
	                            <th>Y坐标</th>
	                            <th>Z坐标</th>
	                            <th>操作</th>
                            </tr>
                        </thead>
                    </table>
                    <!-- table end -->
                    </div>
                </div>
            </div>
            <div class="box-footer">
                <div class="row">
                    <div class="col-sm-12">
                        <!-- 分页模块 -->
                        <div id="pagination"></div>
                    </div>
                </div>
            </div>
        </div>
	</div>
	<!-- 删除弹窗 -->
	<div class="cover del_cover">
		<div class="pop middle">
			<div class="pop_title">您是否确定删除该导向数据？</div>
			<div class="pop_btn clearfix">
				<a href="javascript:void(0);" class="cancel_btn">取消</a>
				<a href="javascript:void(0);" class="sure_btn">确定</a>
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
		<form id="form1" name="form1" action="<@s.url '/project-info/guidedata/lrinfo/import'/>" method="post" enctype="multipart/form-data" >
  	    <label class="btn btn-default btn-file">
    	   选择文件<input id="excel" class="input_file" type="file" name="file" accept="application/vnd.ms-excel" style="display:none;"/>
    	</label>
    	<span id="fileUpName"></span>
    	<input type="hidden" name="intervalId" value="${intervalId!''}"/>
	   	<input type="hidden" name="leftOrRight" value="${leftOrRight!''}"/>
	    <input type="hidden" name="desc" value="${desc!''}"/>
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
	// 打开上传框
	function openUploadBox(){
		$('#uploadBox').show();
	}
   
	// 取消上传框
	function cancelUploadBox(){
		$('#uploadBox').hide();
	}
	
	// 选择文件后，显示文件路径
   	registerUpfileName(["excel"],["fileUpName"]);

	//上传
	 function fsubmit(obj){
	     	var excel = $("#excel").val();
	     	if(excel == null || excel==""){
	     		alert("请先选择文件");
	     		return;
	     	}
	     	obj.submit();
	     }
		
	    function downloadTemplateFile(filename){
	    	location.href = "${request.contextPath}/common/file-download?filename="+filename;
	    }
		
	    //导出excel数据
	    function exportExcel(){
	    	$.ajax({
	      		   type: "post",
	      		   dataType:"json",
	      		   url: "${request.contextPath}/project-info/guidedata/lrinfo/export",
	      		   data: {"intervalId":"${intervalId!''}","leftOrRight":"${leftOrRight!''}","desc":"${desc!''}"},
	      		   success: function(json){
	 	   			   if(json.code == 1){
	 	   	        	var filename = json.result;
	 	             	location.href = "${request.contextPath}/common/file-download?filename="+filename;
	 	   			   }else{
	 	   				   alert(json.result);
	 	   			   }
	    		   },
	 	   		  error: function (data, status, e){
	 	   			    //alert(e);
	 	                alert("导出失败");
	 	            }
	      	    });
	    }
		
	// 初始化表格
	var table = zookeTable('#roleTable',{
	    "columns":[           // 表明每一column的数据，跟json数据对应
	        {"data":"mileage"},
	        {"data":"mapX"},
	        {"data":"mapY"},
	        {"data":"mapZ"},
	        {"data":"operation"}
	    ],
	    "columnDefs":[{
	        "render":function(data,type,row){
	            return "<a href=\'javascript:void(0);\'"+" guideid="+row.id.toString()+" class=\'del_btn\'>删除</a>";
	        },
	        "targets":-1
	    }]
	});

		/**
		  * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
		  */
		table.on('draw.dt',function(){
			 // 删除按钮
			$(".del_btn").on("click",function(){
				$(".del_cover").show();
				$(".cancel_btn").on("click",function(){
					$(".del_cover").hide();
				});
				var guideid = $(this).attr("guideid");
				$(".sure_btn").on("click",function(){
					$(".del_cover").hide();
					$.ajax({
		        		type:"POST",
		        		dataType:"json",
		        		url:"${request.contextPath}/project-info/guidedata/lrinfo/delete",
		        		data:{"id":guideid},
		        		success:function(json){
		        			if(json.code == 1){
		        				$("#pagination").pagination('remote');
		        				//$("#tree").jstree(true).refresh(); //刷新
		        				//alert(json.result);
		        			}else{
		        				alert(json.result);
		        			}
		        		},
		                error: function (data, status, e){  
		                    alert("删除失败");
		                }
		        	});
				})
		});
     });

		var pagination = zookePagination('#pagination',{
			 pageSize:10,
	            remote:{
	                url:"<@s.url'/project-info/guidedata/lrinfo/find'/>",
	                pageParams:function(data){
	                    return {
	                    	intervalId : "${intervalId!''}",
	                    	leftOrRight : "${leftOrRight!''}",
	                        pageNum : data.pageIndex+1,
	                        pageSize : data.pageSize
	                    };
	                },
	                success:function(result){
	                    // 刷新数据，result.data为获取到的表格数据
	                    table.clear();
	                    table.rows.add(result.list);
	                    table.draw();
	                },
	                totalName:'page.totalRow'
	            }
		});
	</script>
</body>
</html>
