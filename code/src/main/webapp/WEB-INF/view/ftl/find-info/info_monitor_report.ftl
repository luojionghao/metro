<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>报表系统</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/jstree/dist/themes/default/style.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">

	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <!-- 树状图 -->
    <script src="<@s.url'/plugins/bower/jstree/dist/jstree.min.js'/>"></script>
<script type="text/javascript">
$(document).ready(function(){
    
	//加载树
	$("#tree").jstree({
		 "check_callback":true,
		 "core":{
			  "data":{
				  "url":"${request.contextPath}/monitor/report/tree-data/get",
			      "dataType":"json",
			  }
		 }
	});
	
	// 选中事件回应
	$("#tree").on("select_node.jstree",function(e,data){
		$("#frame_content").attr("src",data.node.original.url);
	});

	$("#tree").on('loaded.jstree',function(){
		var node1 = $("#tree").jstree(true).get_selected(true)[0];
   	 	$("#frame_content").attr("src",node1.original.url);
	});
	
	});
</script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="com_content clearfix">
		<div class="com_left_content">
			<div id="tree"></div>
		</div>
		<div class="com_right_content">
			<iframe id="frame_content" name="frame_content" class="frame-content" scrolling="no" src="" frameborder="0">
		</div>
	</div>
</body>
</html>
