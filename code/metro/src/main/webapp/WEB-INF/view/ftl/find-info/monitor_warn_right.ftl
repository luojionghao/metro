<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>监测预警/线</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datepicker/datepicker3.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/monitor_warn.css'/>">

    <!-- jQuery 2.2.0 -->
	<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
	<script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.zh-CN.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/AdminLTE/dist/js/app.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
	<script src="<@s.url'/plugins/js/table.js'/>"></script>

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!''}">
	<input type="hidden" id="leftOrRight" name="leftOrRight" value="${(leftOrRight)!''}">
    <div class="box">
        <div class="box-header">
            <div class="wran_head clearfix">
				<div class="wran_head_left">
					<ol class="title_list clearfix">
						<li>预警日期：</li>
						<li>
							<div class="input-group date">
		                        <div class="input-group-addon">
		                            <i class="fa fa-calendar"></i>
		                        </div>
		                        <input type="text" value="${(beginTime)!''}" class="pull-right date_input" id="beginTime">
		                    </div>
						</li>
						<li class="to">至</li>
						<li>
							<div class="input-group date">
		                        <div class="input-group-addon">
		                            <i class="fa fa-calendar"></i>
		                        </div>
		                        <input type="text" value="${(endTime)!''}" class="pull-right date_input" id="endTime">
		                    </div>
						</li>
					</ol>
				</div>
				<div class="wran_head_right">
					预警参数：
					<select class="warn_parameter_select" id="warn_param">
						<option value="-1">全部</option>
						<#if wps??>
							<#list wps as o>
							  <option value="${(o.param)!''}">${(o.paramDic.dicMean)!''}</option>
							</#list>
						</#if>
					</select>
					<button class="query_btn">查询</button>
				</div>
			</div>
        </div>
        <div class="box-body">
            <div class="row">
                <div class="col-sm-12">
                <!-- 表格模块 -->
                <table id="roleTable" class="table table-bordered table-hover dataTable">
                    <thead>
                        <tr>
	                        <th>预警时间</th>
	                        <th>参数名称</th>
	                        <th>参数值</th>
	                        <th>红色预警上限</th>
	                        <th>橙色预警上限</th>
	                        <th>橙色预警下限</th>
	                        <th>红色预警下限</th>
	                        <th>环号</th>
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
</body>

<script>
// 日期初始化
$(".date_input").datepicker({language:"zh-CN"});

// 初始化表格
var table = zookeTable('#roleTable',{
    "columns":[           // 表明每一column的数据，跟json数据对应
        {"data":"warningTime"},
        {"data":"dic.dicMean"},
        {"data":"numValue"},
        {"data":"redWarningMax"},
        {"data":"orangeWarningMax"},
        {"data":"orangeWarningMin"},
        {"data":"redWarningMin"},
        {"data":"ringNum"}
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
        url:"<@s.url'/monitor/warn/find/warns'/>",
        pageParams:function(data){
            return {
                pageNum: data.pageIndex+1,
                pageSize:data.pageSize,
                leftOrRight:$("#leftOrRight").val(),
                intervalId:$("#intervalId").val(),
                beginTime:$("#beginTime").val(),
                endTime:$("#endTime").val(),
                warnParam:$("#warn_param option:selected").val()
            };
        },
        success:function(result){
            // 刷新数据，result.data为获取到的表格数据
            $(".query_btn").removeAttr('disabled');
			$(".query_btn").text('查询');
            table.clear();
            table.rows.add(result.list);
            table.draw();
        },
        totalName:'page.totalRow'
    }
});

$(".query_btn").on("click",function(){
	$(this).attr('disabled', 'disabled');
	$(this).text('查询中...');
	$("#page").pagination('setPageIndex',1);
	$("#pagination").pagination('remote');
});

</script>
</html>
