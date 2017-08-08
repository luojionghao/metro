<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>地铁盾构信息管理系统</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">

    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
    <div class="box">
        <div class="box-header"></div>
        <div class="box-body">
            <div class="row">
                <div class="col-sm-12">
                <table id="record_table" class="table table-bordered table-hover dataTable">
                    <thead>
                        <tr>
                            <th>访问时间</th>
                            <th>IP</th>
                            <th>用户</th>
                            <th>模块</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                </table>
                </div>
            </div>
        </div>
        <div class="box-footer">
            <div class="row">
            <div class="col-sm-12">
                <div id="record_page"></div>
            </div>
            </div>
        </div>
    </div>
	<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
    <script>
        var table = zookeTable('#record_table',{
            columns:[
                {"data":"visitTime"},
                {"data":"loginIp"},
                {"data":"username"},
                {"data":"visitMenu"},
                {"data":"operation"}
            ]
        });

        var pagination = zookePagination('#record_page',{
            pageSize:10,
            remote:{
                url:"<@s.url'/opreate/find/opreates'/>",
                pageParams:function(data){
                    return {
                        pageNum: data.pageIndex+1,
                        pageSize:data.pageSize
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
