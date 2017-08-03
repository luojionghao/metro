<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>盾构信息监控/线</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap-select.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/info_monitor.css'/>">

	 <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
   	<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap-select.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
	<script src="<@s.url'/plugins/js/table.js'/>"></script>
	<script src="<@s.url'/plugins/js/base.js'/>"></script>

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
 			<li class="active">
          		<a href="#tab_1" data-toggle="tab" aria-expanded="true">工程介绍</a>
          	</li>
          	<li class="">
          		<a href="#tab_2" data-toggle="tab" aria-expanded="false">数据监测</a>
          	</li>
        </ul>
        <div class="tab-content">
          	<div class="tab-pane active" id="tab_1">
          	
                <iframe id="iframe_content" name="iframe_content" class="iframe-content" scrolling="auto" <#if lineUrl?? && lineUrl != "">src="${lineUrl+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if> frameborder="0" width="100%" height="100%">
                </iframe>
          	</div>
          	<div class="tab-pane" id="tab_2">
            	<div class="state_wrap">
					<div class="box">
						<input type="hidden" id="lineId" name="lineId" value="${(lineId)!''}">
			            <div class="box-header table_title">
			                <span>工程状态：</span>
							<select class="selectpicker" id="buildStatus" name="buildStatus">
								<option value="-1">全部</option>
								<option value="0">未施工</option>
								<option value="1">正在施工</option>
								<option value="2">已贯通</option>
							</select>
							<span>总计：${(totalm)!'0'}台</span>
							<span>正在施工：${(total1)!'0'}台</span>
							<span>未施工：<em class="abnormal_num">${(total0)!'0'}</em>台</span>
                            <span>正常通信：${(totalSuccess)!'0'}台</span>
                            <span>通信异常：<em class="abnormal_num">${(totalFail)!'0'}</em>台</span>
							<span>已贯通：${(total2)!'0'}台</span>
			            </div>
			            <div class="box-body">
			                <div class="row no-pad">
			                    <div class="col-sm-12">
			                    <!-- 表格模块 -->
			                    <table id="monitorLineTable" class="table table-bordered table-hover dataTable">
			                        <thead>
			                            <tr>
				                            <th>区间</th>
				                            <th>线路</th>
				                            <th>工作状态</th>
                                            <th>通信状态</th>
				                            <th>当前环</th>
				                            <th>总环数</th>
				                            <th>总推力</th>
			                            	<th>平均土压</th>
			                            	<th>刀盘扭矩</th>
			                            	<th>推进速度</th>
			                            	<th>刀盘转速</th>
			                            	<th>注浆总量</th>
			                            	<th>工程状态</th>
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
          	</div>
        </div>
  	</div>

  	<script>
  		// 下拉选择
  		$("#buildStatus").selectpicker({
  			width:"100px"
  		});
  	
  		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
  			if(e.target.innerText === '数据监测'){
                // 防止重复创建
                if ($("#pagination").pagination()){
                    return;
                }; 
            	// 初始化表格
            	var table = zookeTable('#monitorLineTable',{
            	    columns:[           // 表明每一column的数据，跟json数据对应
            	        {"data":"intervalName"},
            	        {"data":"leftOrRight"},
            	        {"data":"A0002"},
                        {"data":"communiStatus"},
            	        {"data":"A0001"},
            	        {"data":"totalRingNum"},
            	        {"data":"B0006"},
            	        {"data":"A0013"},
            	        {"data":"B0004"},
            	        {"data":"B0001"},
            	        {"data":"B0002"},
            	        {"data":"D0015"},
            	        {"data":"buildStatus"}
            	    ],
            	    columnDefs:[
                        {
                            render:function(data,row){
                                if (data === 'l'){
                                    return "左线";
                                }else{
                                    return "右线"
                                }
                            },
                            targets:-12

                        },
                        {
                            render:function(data,row){
                                console.log("停机");
								console.log(data);
                                if (data>2 && data<=3){
                                    return "推进";
                                }else if(data>3 && data<=4){
                                    return "拼装";
                                }else{
                                    return "停机"
                                }
                            },
                            targets:-11

                        },
                        {
                            render:function(data,row){
                                if (data === 0){
                                    return "通信异常";
                                }else if(data === 1){
                                    return "正常通信";
                                }
                            },
                            targets:-10

                        },
                        {
                            render:function(data,row){
                                if (data === 0){
                                    return "未施工";
                                }else if(data === 1){
                                    return "正在施工"
                                }else{
                                    return "已贯通"
                                }
                            },
                            targets:-1

                        }
            	    ]
            	});

            	/**
            	  * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
            	  */
            	table.on('draw.dt',function(){
            		
            	})

            	var pagination = zookePagination('#pagination',{
            	    pageSize:10,
            	    remote:{
            	        url:"<@s.url'/monitor/info/find/line/datas'/>",
            	        pageParams:function(data){
            	            return {
            	                pageNum: data.pageIndex+1,
            	                pageSize:data.pageSize,
            	                lineId:$("#lineId").val(),
            	                buildStatus:$("#buildStatus").val()
            	            };
            	        },
            	        success:function(result){
            	            // 刷新数据，result.data为获取到的表格数据
            	            table.clear();
            	            table.rows.add(result.list);
            	            table.draw();
            	           	loadingDismiss();
            	        },
            	        totalName:'page.totalRow'
            	    }
            	});
				loadingShow("${request.contextPath!}");
            	
            	$("#buildStatus").on("change",function(){
            		$("#page").pagination('setPageIndex',1);
            		$("#pagination").pagination('remote');
            		loadingShow("${request.contextPath!}");
            	});

            }
  			
		})

	</script>
</body>
</html>