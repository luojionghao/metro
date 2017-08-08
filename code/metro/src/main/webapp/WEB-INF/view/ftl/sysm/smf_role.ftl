<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>系统管理/角色管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/smf_role.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
<!-- jQuery 2.2.0 -->
<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
<script src="<@s.url'/plugins/js/table.js'/>"></script>
<script src="<@s.url'/plugins/bower/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/AdminLTE/dist/js/app.min.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
        <div class="box">
            <div class="box-header">
                <button class="add_btn">新增</button>
            </div>
            <div class="box-body">
                <div class="row">
                    <div class="col-sm-12">
                    <!-- 表格模块 -->
                    <table id="roleTable" class="table table-bordered table-hover dataTable">
                        <thead>
                            <tr>
                            <th>角色代号</th>
                            <th>角色名称</th>
                            <th>状态</th>
                            <th>描述</th>
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
	<!-- 删除弹窗 -->
	<div class="cover del_cover">
		<div class="pop middle">
			<div class="pop_title">您是否确定删除该列表？</div>
			<div class="pop_btn clearfix">
				<a href="javascript:void(0);" class="cancel_btn">取消</a>
				<a href="javascript:void(0);" class="sure_btn">确定</a>
			</div>
		</div>
	</div>
    <!-- 新增弹窗 -->
    <input type="hidden" id="nowRoleId" name="nowRoleId">
	<div class="cover edit_cover">

	</div>
</body>	
<script>
var operate = 0;//0新增 1修改
// 初始化表格
var table = zookeTable('#roleTable',{
    columns:[           // 表明每一column的数据，跟json数据对应
        {"data":"roleCode"},
        {"data":"roleName"},
        {"data":"isUsed"},
        {"data":"roleDescribe"},
        {"data":"roleCode"}
    ],
    columnDefs:[
        {
           render:function(data,row){
               if (data === 1){
                   return "启用";
               }else{
                   return "禁用"
               }
           },
           targets:-3

        },
        {
            render:function(data,type,row){
            	//alert(data);
                if (data === 'R001'){
                    return "";
                }else{
                    return "<a href=\"###\" class=\"edit_btn\" roleid="+row.id+">编辑</a><!----><a href=\"###\" class=\"del_btn\" roleid="+row.id+">删除</a>"
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
	// 删除按钮
	$(".del_btn").on("click",function(){
		var currentData = table.row($(this).parents("tr")).data(); // 获取到当前要删除的Data
		$("#nowRoleId").val($(this).attr("roleid"));
		$(".del_cover").show();
		$(".cancel_btn").on("click",function(){
			$(".del_cover").hide();			
		})
		$(".sure_btn").on("click",function(){
			$.ajax({
				type:'POST',
				url:"<@s.url'/role/del/role'/>",
				data:{
					"roleId":$("#nowRoleId").val()
				},
				success:function(data){
					$(".del_cover").hide();
					window.location.reload();
				}
			});			
            // 执行删除逻辑
		})
	})

	$(".modal-content button").on("click",function(){
		$(".edit_cover").hide();
	})
	
	$(".edit_btn").on("click",function(){
		$("#nowRoleId").val($(this).attr("roleid"));
		operate = 1;
		toEdit();	
		$("#nowRoleId").val('');
		$(".edit_cover").show();
		
	});
})

var pagination = zookePagination('#pagination',{
    pageSize:10,
    remote:{
        url:"<@s.url'/role/find/roles'/>",
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


// 新增按钮点击事件
$(".add_btn").on("click",function(){
	operate = 0;
	toEdit();
	$(".edit_cover").show();
});

function toEdit(){
	$.ajax({
		type:'POST',
		url:"<@s.url'/role/to/edit'/>",
		data:{
			"operate":operate,
			"roleId":$("#nowRoleId").val()
		},
		success:function(data){
			$(".edit_cover").html(data);
			$(":radio").click(function(){
				$("#isUsed").val($(this).val());
			});
		}
	});
}
function saveRoleInfo(){
	$(".save_btn").attr("disabled","true");
	var chk_value = []; 
	$('.menus:checked').each(function(){ 
		chk_value.push($(this).val()); 
	});
	$.ajax({
		type:'POST',
		url:"<@s.url'/role/save/role/info'/>",
		data:{
			"operate":operate,
			"roleId":$("#roleId").val(),
			"roleCode":$("#roleCode").val(),
			"roleName":$("#roleName").val(),
			"isUsed":$("#isUsed").val(),
			"roleDescribe":$("#roleDescribe").val(),
			"menuIds":chk_value.toString()
		},
		success:function(data){
			cancel();
			window.location.reload();
		}
	});

}

function cancel(){
	$(".edit_cover").hide();
}
</script>
</html>
