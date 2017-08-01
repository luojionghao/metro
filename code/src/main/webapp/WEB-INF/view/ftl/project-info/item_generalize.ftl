<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目概况管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url '/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <!-- 图标1 -->
    <link rel="stylesheet" href="<@s.url '/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url '/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url '/plugins/bower/datepicker/datepicker3.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.css'/>">
	<link rel="stylesheet" href="<@s.url '/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/bower/jstree/dist/themes/default/style.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url '/plugins/css/item_generalize.css?v=2'/>">
	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url '/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url '/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url '/plugins/bower/colorpicker/bootstrap-colorpicker.min.js'/>"></script>
    <script src="<@s.url '/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
    <!-- 树状图 -->
    <script src="<@s.url '/plugins/bower/jstree/dist/jstree.min.js'/>"></script>
    <!-- <script src="<@s.url '/plugins/js/item_generalize.js'/>"></script> -->
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="com_content clearfix">
		<div class="com_left_content">
			<div id="tree"></div>
			<!-- 新建线路 -->
            <div class="new_way">
                <img src="<@s.url '/plugins/images/new_way_icon.png'/>">
                <span>新建线路</span>
            </div>
            <!-- 新建区间 -->
            <div class="new_section com_section">
                <ul class="clearfix">
                    <li class="new_section_btn">
                        <img src="<@s.url '/plugins/images/new_way_icon.png'/>">
                        <span>新建区间</span>
                    </li>
                    <li onclick="deleteLine();">
                        <img src="<@s.url '/plugins/images/del.png'/>">
                        <span>删除工程</span>
                    </li>
                </ul>
            </div>
            <!-- 删除区间 -->
            <div class="del_section com_section">
                <ul class="clearfix">
                    <li class="ren_section_btn">
                        <img src="<@s.url '/plugins/images/new_way_icon.png'/>">
                        <span>编辑区间</span>
                    </li>
                    <li onclick="deleteInterval();">
                        <img src="<@s.url '/plugins/images/del.png'/>">
                        <span>删除区间</span>
                    </li>
                </ul>
            </div>
		</div>
		<div class="com_right_content">
			<iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="no" src="" frameborder="0" width="100%">
            </iframe>
		</div>
	</div>
	
	
	    <!-- 新建线路弹框 -->
	<form id="lineform" name="lineform" method="post" action="" onsubmit="return false;">
	<input type="hidden" id="cityId" name="cityId" value="1"/> <!-- 默认广州为1，也可以动态加载树的第一个id -->
    <input type="hidden" id="lineColor" name="lineColor" value="#22B038"/> <!-- 取选择器的默认值 -->
    <div class="cover new_way_cover">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="lineclose();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">新建线路</span>
            </div>
            <div class="modal-body">
                <ul class="project_info">
                    <!-- <li>
                        <span>工程名称：</span>
                        <input type="text" name="lineName" id="lineName">
                    </li>
                    <li>
                        <span>工程标号：</span>
                        <input type="text" name="lineMark" id="lineMark">
                    </li> -->
                    <li>
                        <span>线路名称：</span>
                        <input type="text" name="lineName" id="lineName">
                    </li>
                    <li>
                        <span>线路编号：</span>
                        <input type="number" name="lineNo" id="lineNo">
                    </li>
                    <li>
                        <span>标记颜色：</span>
                        <div class="my-colorpicker" id="lineColorPicker">
                            <div class="input-group-addon">
                                <i style="background-color: rgb(34, 176, 56);"></i>
                            </div>
                        </div>
                    </li>
                    <li>
                        <span>状态：</span>
                        <select class="state_select" id="lineStatus" name="lineStatus">
                            <option value="1" selected="selected">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </li>
                    <li>
                        <span>地图标记坐标-X：</span>
                        <input type="text" name="mapX" id="mapX" class="coord_val">
                        <span>地图标记坐标-Y：</span>
                        <input type="text" name="mapY" id="mapY" class="coord_val">
                    </li>
                    <li class="clearfix">
                        <span class="left_text">备注：</span>
                        <textarea class="remark_textarea" name="remark" id="remark"></textarea>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="linecancel();">取消</button>
                <button type="button" class="btn btn-primary" onclick="linesave();">保存</button>
            </div>
        </div>
    </div>
    </form>
    <!-- 新建区间弹框 -->
    <form id="intervalform" name="intervalform" method="post" action="" onsubmit="return false;">
    <div class="cover new_section_cover">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="intervalclose();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">区间信息</span>
            </div>
            <div class="modal-body">
                <ul>
                    <li>
                      <span>区间名称：</span>
                      <input type="hidden" id="lineId" name="lineId"/>
                      <input type="text" id="intervalName" name="intervalName" class="section_name_input">
                    </li>
                    <li class="pro_mark">
                       <span>工程标号：</span>
                       <input type="number" id="intervalMark" name="intervalMark" class="section_name_input">
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="intervalcancel();">取消</button>
                <button type="button" class="btn btn-primary" onclick="intervalsave();">保存</button>
            </div>
        </div>
    </div>
    </form>
        <!-- 区间重命名 -->
    <input type="hidden" id="intervalId"/> 
    <form id="intervalUpdateForm" name="intervalUpdateForm" method="post" action="" onsubmit="return false;">
    <div class="cover ren_section_cover">
    </div>
    </form>
    <script>
    
    
    //线路保存
    function linesave(){
    	var lineNo = $("#lineNo").val();
    	if(lineNo == null || lineNo == ""){
    		alert("请输入线路编号");
    		return;
    	}
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${request.contextPath}/project-info/basic/lineinfo/save",
    		data:$("#lineform").serialize(),
    		success:function(json){
    			if(json.code == 1){
    				$(".new_way_cover").hide();
    				$("#lineform")[0].reset();
    				$("#tree").jstree(true).refresh(); //刷新
    				//alert(json.result);
    			}else{
    				alert(json.result);
    			}
    		},
            error: function (data, status, e){  
                alert("保存失败");
            }
    	});
    }
  //线路区间保存
    function intervalsave(){
	  var intervalMark = $("#intervalMark").val();
	  if(intervalMark == null || intervalMark==""){
		  alert("请输入工程标号");
		  return;
	  }
	   	$.ajax({
	   		type:"POST",
	   		dataType:"json",
	   		url:"${request.contextPath}/project-info/basic/intervalinfo/save",
	   		data:$("#intervalform").serialize(),
	   		success:function(json){
	   			if(json.code == 1){
	   				$(".new_section_cover").hide();
	   				$("#intervalform")[0].reset();
	   				$("#tree").jstree(true).refresh(); //刷新
	   				//alert(json.result);
	   			}else{
	   				alert(json.result);
	   			}
	   		},
	           error: function (data, status, e){  
	               alert("保存失败");
	           }
	   	});
    }
    //线路区间更新命名
    function intervalNameUpdate(){
    	$.ajax({
    		type:"POST",
    		dataType:"json",
    		url:"${request.contextPath}/project-info/basic/intervalinfo/update",
    		data:$("#intervalUpdateForm").serialize(),
    		success:function(json){
    			if(json.code == 1){
    				$(".ren_section_cover").hide();
    				$("#intervalUpdateForm")[0].reset();
    				$("#tree").jstree(true).refresh(); //刷新
    				//alert(json.result);
    			}else{
    				alert(json.result);
    			}
    		},
            error: function (data, status, e){  
                alert("保存失败");
            }
    	});
    }
    //线路保存取消
    function linecancel(){
    	$(".new_way_cover").hide();
    	$("#lineform")[0].reset();
    }
    //线路保存弹框关闭
    function lineclose(){
    	$(".new_way_cover").hide();
    	$("#lineform")[0].reset();
    }
    
    //区间保存取消
    function intervalcancel(){
    	$(".new_section_cover").hide();
    	$("#intervalform")[0].reset();
    }
    //区间保存弹框关闭
    function intervalclose(){
    	$(".new_section_cover").hide();
    	$("#intervalform")[0].reset();
    }
    //区间重命名取消
    function intervalUpdateCancel(){
    	$(".ren_section_cover").hide();
    	$("#intervalUpdateForm")[0].reset();
    }
    //区间重命名弹框关闭
    function intervalUpdateClose(){
    	$(".ren_section_cover").hide();
    	$("#intervalUpdateForm")[0].reset();
    }

    //删除工程（线路）
    function deleteLine(){
    	var lineId = $("#lineId").val();
    	var r=confirm("确认删除吗？");
    	if (r==true)
    	{
    		$.ajax({
        		type:"POST",
        		dataType:"json",
        		url:"${request.contextPath}/project-info/basic/lineinfo/delete",
        		data:{"lineId":lineId},
        		success:function(json){
        			if(json.code == 1){
        				$("#tree").jstree(true).refresh(); //刷新
        				   //$("#tree").on('loaded.jstree',function(){
        				       // $("#tree").jstree('select_node',"city");
        				    //});
        				//alert(json.result);
        			}else{
        				alert(json.result);
        			}
        		},
                error: function (data, status, e){  
                    alert("删除失败");
                }
        	});
    	}
    }
    
    //删除区间
    function deleteInterval(){
    	var intervalId = $("#intervalId").val();
    	var r=confirm("确认删除吗？");
    	if (r==true)
    	{
    		$.ajax({
        		type:"POST",
        		dataType:"json",
        		url:"${request.contextPath}/project-info/basic/intervalinfo/delete",
        		data:{"intervalId":intervalId},
        		success:function(json){
        			if(json.code == 1){
        				$("#tree").jstree(true).refresh(); //刷新
        				   //$("#tree").on('loaded.jstree',function(){
        				       // $("#tree").jstree('select_node',"city");
        				    //});
        				//alert(json.result);
        			}else{
        				alert(json.result);
        			}
        		},
                error: function (data, status, e){  
                    alert("删除失败");
                }
        	});
    	}
    }
    
	//加载树
	 $("#tree").jstree({
      "check_callback":true,
      "core":{
    	  "data":{
    		  "url":"${request.contextPath}/project-info/basic/tree-data/get",
    	      "dataType":"json",
    	  }
      }
    });
    //$("#tree").jstree(true).refresh();
    
    // 默认选中城市 首次加载监听
    $("#tree").on('loaded.jstree',function(){
        $("#tree").jstree('select_node',"city");
    });
    // 刷新监听
    $("#tree").on('refresh.jstree',function(){
    	$("#tree").jstree(true).deselect_all();
        $("#tree").jstree('select_node',"city");
    });
 // 市
	$(".new_way").hide();
    //新建线路
	$(".new_way").on("click",function(){
		$(".new_way_cover").show();
	})
	/* $(".modal-content button").on("click",function(){
		$(".new_way_cover").hide();
		$(".new_section_cover").hide();
	})*/
	
	$(".new_section").hide();
	//新建区间
	$(".new_section_btn").on("click",function(){
		$(".new_section_cover").show();
	}) 
	// 区间
	$(".del_section").hide();
    
	// 重命名
	$(".ren_section_btn").on("click",function(){
		/* var jstree_clicked=$(".jstree-clicked").text();
		$("#updateIntervalName").val(jstree_clicked); */
		//请求编辑的数据
    	$.ajax({
    		type:"get",
    		url:"${request.contextPath}/project-info/basic/intervalinfo/to-edit",
    		data:{"intervalId":$("#intervalId").val()},
    		success:function(data){
				 $(".ren_section_cover").html(data);
			     $(".ren_section_cover").show();
    		},
            error: function (data, status, e){
            	//$(".add_cart_cover").hide();
                alert("加载失败");
            }
    	});
		
	})
    
 // 选中事件回应
    $("#tree").on("select_node.jstree",function(e,data){
    	$("#iframe_content").attr("src",data.node.original.url);
        if(data.node.original.type === 'city'){
            // 城市类型节点被选中
            $(".new_way").show();
            $(".new_section").hide();
            $(".del_section").hide();
        }
        if(data.node.original.type === 'line'){
            // 线路类型节点被选中
            //alert(data.node.original.id.split("_")[1]);
            $("#lineId").val(data.node.original.id.split("_")[1]);
            $(".new_section").show();
            $(".new_way").hide();
            $(".del_section").hide();
        }
        if(data.node.original.type === 'area'){
            // 区间类型节点被选中
            $("#intervalId").val(data.node.original.id.split("_")[1]);
            $(".del_section").show();
            $(".new_way").hide();
            $(".new_section").hide();
        }
    });
    
   /*  var lineColorPicker = $(".line-colorpicker").colorpicker();
    lineColorPicker.toHex();
     */
    $('#lineColorPicker').colorpicker();
    //颜色拾取颜色改变时事件.
    $('#lineColorPicker').colorpicker().on('changeColor', function(ev){
        $("#lineColor").val(ev.color.toHex());
    });
    
    $(".date_input").datepicker();
        function resizeContent(){
            var bottom = window.innerHeight;
            $('.com_content').css('height',function(){
                var topOffset = $(this).position().top;
                return bottom-topOffset;
            });
            $('#iframe_content').css('height',function(){
                var topOffset = $("#iframe_content")[0].offsetTop;
                return bottom-topOffset;
            })
        }
        resizeContent();
        $(window).on('resize',resizeContent);
    </script>
</body>
</html>
