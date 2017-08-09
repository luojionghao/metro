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
	<link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datepicker/datepicker3.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/monitor_warn.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/info_monitor.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/slurry.css'/>">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!'0'}">
	<input type="hidden" id="leftOrRight" name="leftOrRight" value="${(leftOrRight)!'l'}">
    <input type="hidden" id="machineType" name="machineType" value="${(machineType)!'l'}">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
 			<li class="">
                <a id="tab1" href="#tab_1" data-toggle="tab" aria-expanded="true">刀盘</a>
          	</li>
          	<li class="">
			<#if machineType ??>
				<#if  machineType == "1">
                    <a id="tab2" href="#tab_2" data-toggle="tab" aria-expanded="false">螺旋</a>
				<#else>
                    <a id="tab6" href="#tab_6" data-toggle="tab" aria-expanded="true">泥水</a>
				</#if>
			<#else>
                <a id="tab2" href="#tab_2" data-toggle="tab" aria-expanded="false">螺旋</a>
			</#if>

          	</li>
          	<li class="">
          		<a id="tab3" href="#tab_3" data-toggle="tab" aria-expanded="false">导向</a>
          	</li>
          	<li class="">
          		<a id="tab4" href="#tab_4" data-toggle="tab" aria-expanded="false">综合</a>
          	</li>
          	<li class="">
          		<a id="tab5" href="#tab_5" data-toggle="tab" aria-expanded="false">统计报表</a>
          	</li>
        </ul>
        <div class="tab-content">

            <div class="tab-pane" id="tab_1">
            </div>
           <#if machineType ??>
			<#if machineType == "1">
                <div class="tab-pane" id="tab_2">
                </div>
			<#else>
                <div class="tab-pane" id="tab_6">
                </div>
			</#if>
		  <#else>
              <div class="tab-pane" id="tab_2">
              </div>
		  </#if>

          	<div class="tab-pane" id="tab_3">
          	</div>
          	<div class="tab-pane" id="tab_4">
            	<div class="box">
		            <div class="box-body">
		                <div class="row no-pad">
		                    <div class="col-sm-12">
		                    <!-- 表格模块 -->
		                    <table id="monitorIntervalDicTable" class="table table-bordered table-hover dataTable">
		                        <thead>
		                        	<tr>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        		<td>1</td>
		                        	</tr>
		                        </thead>
		                    </table>
		                    <!-- table end -->
		                    </div>
		                </div>
		            </div>
		            <div class="box-footer" style="padding:0px">
		                <div class="row">
		                    <div class="col-sm-12" style="display: none;">
		                        <!-- 分页模块 -->
		                        <div id="pagination"></div>
		                    </div>
		                </div>
		            </div>
		        </div>
          	</div>
          	<div class="tab-pane" id="tab_5" style="overflow:hidden;">
	            <iframe id="frameContent5" name="frameContent" class="frame-content" scrolling="no" src="" frameborder="0">
	            </iframe>
          	</div>
        </div>
  	</div>
  	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
	<script src="<@s.url'/plugins/js/table.js'/>"></script>
    <script src="<@s.url'/plugins/bower/echarts/dist/echarts.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/EaselJS/lib/easeljs-0.8.2.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/prosture.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
	<script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/css-element-queries/src/ResizeSensor.js'/>"></script>
	<script src="<@s.url'/plugins/bower/css-element-queries/src/ElementQueries.js'/>"></script>
	<script src="<@s.url'/plugins/js/slurry.js'/>"></script>
	
  	<script>
	    // 请求器
	    var ax_request = axios.create({
	        baseURL:'${request.contextPath}/monitor/info',
	        timeout:1000
	    });
	    
        // 点击综合tab
	    $('#tab4').on('shown.bs.tab', function (e) {
            // 防止重复创建
            if ($("#pagination").pagination()){
            	$("#pagination").pagination('remote');
            	return;
            };
            
            // 初始化表格
            var table = zookeTable('#monitorIntervalDicTable',{
            	scrollY:"90vh",
        	    columns:[           // 表明每一column的数据，跟json数据对应
        	            	        {"data":"key1"},
        	            	        {"data":"value1"},
        	            	        {"data":"key2"},
        	            	        {"data":"value2"},
        	            	        {"data":"key3"},
        	            	        {"data":"value3"},
        	            	        {"data":"key4"},
        	            	        {"data":"value4"}
        	            	    ],
                "ordering":false,
                "autoWidth":true,
                "headerCallback":function(thead, data, start, end, display){
                    $(thead).css({"display":"none"});
                },
                "columnDefs":[
                    {"className":'col-dark-bg',"targets":[0,2,4,6]}
                ],
            });
            var pagination = zookePagination('#pagination',{
                pageSize:300,
                remote:{
                    url:"${request.contextPath}/monitor/info/find/all/dic/datas",
        	        pageParams:function(data){
        	            return {
        	                pageNum: data.pageIndex+1,
        	                pageSize:data.pageSize,
        	                intervalId:$("#intervalId").val(),
        	                leftOrRight:$("#leftOrRight").val()        	                
        	            };
        	        },
                    success:function(result){
                        // 刷新数据，result.data为获取到的表格数据
        	            table.clear();
        	            table.rows.add(result.list);
        	            table.draw();
        	            $(this).remove();
        	            loadingDismiss();
                    },
                    totalName:'page.totalRow'
                }, 
            });
            loadingShow("${request.contextPath!}");
		});
	   	function gttime(){
	   		var randomnumber=Math.floor(Math.random()*100000)
	   		return randomnumber;
	   	}
	   	
	   	var t1 = 0;
	    
	 	// 请求器
	    var ax_request = axios.create({
	        baseURL:'${request.contextPath}/monitor',
	        timeout:60000
	    });
	    
	    // 获取当前数据
	    function getCurrentData(){
	    	return ax_request.get('/info/find/lr/monitor/datas/now?intervalId='+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val());
	    }
	    
	    // 获取当前警告
//	    function getCurrentWarning(){
//	    	return ax_request.get('/warn/find/warns/all');
//	    }
	 
	    function getdata(){
	    	//$('.now_time').html("获取数据中.......");
	    	axios.all([getCurrentData()])
	    	.then(axios.spread(function(datas){
	    		if(datas!=null){     
	        		//数据填充
					var df = new DataFetcher(datas.data);
					df.fill($('.fill'));
	        	}
	        	
	        	// 根据警告内容变换字体颜色
	        	if(!datas)return;
	        	var warning_elms = datas.data.res.reduce(function(acc,elm){
	        		acc[elm.paramName] = (elm.warningLevel == 4 || elm.warningLevel == 1)?'warn_red':'warn_orange';
	        		return acc;
	        	},{});
	        	// addClass
	        	for(var key in warning_elms){
	        		$('div[tag="'+ key +'"]').addClass(warning_elms[key]);
	        	};
	        	
	        	
	        	console.log(warning_elms);
	    	}));
	    }
	    
	  	//第一次
	  	var t = null;

	  	// 泥水
	  	$('#tab6').on('shown.bs.tab',function(e){
	    	loadingShow("${request.contextPath!}");
	    	clearInterval(t);
	    		$.ajax({
					type:"POST",
		            url:"${request.contextPath}/monitor/info/to/lr/slurry",
					data:{
							"intervalId":$("#intervalId").val(),
			  				"leftOrRight":$("#leftOrRight").val()
			  		},
				   	success:function(data){
				   		$("#tab_6").html(data);
				   		loadingDismiss();
				   		initSlurry();
					  	//定时获取数据
						   t = setInterval(function(){
						    	getdata();
						    },8000);
						//alert(data);
	              	}
		    	});
	    });

        // 刀盘
	    $('#tab1').on('shown.bs.tab',function(e){
	    	loadingShow("${request.contextPath!}");
	    	clearInterval(t);
	    		$.ajax({
					type:"POST",
		            url:"${request.contextPath}/monitor/info/to/lr/knife",
					data:{
							"intervalId":$("#intervalId").val(),
			  				"leftOrRight":$("#leftOrRight").val()
			  		},
				   	success:function(data){
				   		$("#tab_1").html(data);
				   		loadingDismiss();
					  	//定时获取数据
						   t = setInterval(function(){
						    	getdata();
						    },8000);
						//alert(data);
	              	}
		    	});
	    });

        
        // 螺旋
	    $('#tab2').on('shown.bs.tab', function (e) {
    		//var htm ='<iframe id="frameContent2" name="frameContent" class="frame-content" src="" frameborder="0"></iframe> ';
    		//$("#tab_2").html(htm);
	    	//$('#frameContent2').attr('src',"${request.contextPath}/monitor/info/to/lr/sprial?intervalId="+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val()+"&ts="+gttime());
			loadingShow("${request.contextPath!}");
	    	clearInterval(t);
	    	$.ajax({
				type:"POST",
	            url:"${request.contextPath}/monitor/info/to/lr/sprial",
				data:{
						"intervalId":$("#intervalId").val(),
		  				"leftOrRight":$("#leftOrRight").val()
		  		},
			   	success:function(data){
			   		$("#tab_2").html(data);
			   		loadingDismiss();
				  	//定时获取数据
					   t = setInterval(function(){
					    	getdata();
					    },8000);
					//alert(data);
              	}
	    	});	
	    });
        
        // 导向
	    $('#tab3').on('shown.bs.tab', function (e) {
    		//var htm ='<iframe id="frameContent3" name="frameContent" class="frame-content" src="" frameborder="0"></iframe> ';
    		//$("#tab_3").html(htm);
		    //$('#frameContent3').attr('src',"${request.contextPath}/monitor/info/to/lr/guide?intervalId="+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val()+"&ts="+gttime());
	    
			loadingShow("${request.contextPath!}");
	    	clearInterval(t);
	    	$.ajax({
				type:"POST",
	            url:"${request.contextPath}/monitor/info/to/lr/guide",
				data:{
						"intervalId":$("#intervalId").val(),
		  				"leftOrRight":$("#leftOrRight").val()
		  		},
			   	success:function(data){
			   		$("#tab_3").html(data);
			   		getdata1();			   		
			   		loadingDismiss();
				  	//定时获取数据
					   t = setInterval(function(){
						   //$('.now_time').html("获取数据中.......");
							getdata2();
							getdata1();
					    },8000);
					//alert(data);
              	}
	    	});	

	    
	    });
        
        // 数据统计
	    $('#tab5').on('shown.bs.tab', function (e) {
	    	$('#frameContent5').attr('src',"${request.contextPath}/monitor/info/to/lr/static/index?intervalId="+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val()+"&ts="+gttime());
        });
        
        // 选中刀盘
        $("#tab1").tab('show');
        
	</script>
</body>
</html>
