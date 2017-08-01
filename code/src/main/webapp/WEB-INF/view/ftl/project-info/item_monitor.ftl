<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>监测预警设置</title>
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
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="com_content clearfix">
		<div class="com_left_content">
			<div id="tree"></div>
		</div>
		<div class="com_right_content">
			<iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="auto" src="" frameborder="0" width="100%">
            </iframe>
		</div>
	</div>
	
	<script type="text/javascript">
	
	function autoFrameSize(){
        $("#iframe_content").css('height',function(){
            var screenHeight = window.innerHeight;
            var offset = $("#iframe_content")[0].offsetTop;
            return screenHeight-offset;
        });
    }
    $(window).resize(autoFrameSize);
    autoFrameSize();
     //加载树
	 $("#tree").jstree({
     "check_callback":true,
     "core":{
   	  "data":{
   		  "url":"${request.contextPath}/project-info/monitor/tree-data/get",
   	      "dataType":"json",
   	   }
      }
     });
     
    $("#tree").on('loaded.jstree',function(){
    	 var node1 = $("#tree").jstree(true).get_selected(true)[0];
    	 $("#iframe_content").attr("src",node1.original.url);
        $("#tree").jstree('select_node','l_1'); 
    }); 
    // 选中事件回应
    $("#tree").on("select_node.jstree",function(e,data){
    	$("#iframe_content").attr("src",data.node.original.url);
    });


	</script>
</body>
</html>