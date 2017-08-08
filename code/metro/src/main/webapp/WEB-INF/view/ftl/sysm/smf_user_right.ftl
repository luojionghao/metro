<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>系统管理/用户管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/smf_user.css'/>">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<input type="hidden" id="deptId" name="deptId" value="${(deptId)}">
    <div class="box">
        <div class="user-header">
            <div class="input-group input-group-sm btn-head search-bar">
              <input id="searchInput" class="form-control search-input" type="text"></input>
              <div class="input-group-btn btn-group">
                <button id="searchBtn" class="btn btn-info btn-flat search-btn" type="button">搜索</button>
              </div>
            </div>
            <button id="showAll" class="btn btn-info btn-head btn-single btn-xs">显示全部</button>
            <button id="add_btn" class="btn btn-info btn-head btn-single btn-xs">添加用户</button>
        </div>
        <div class="box-body">
        	<div class="row no-pad">
	            <table id="user_table" class="dict_table table table-bordered table-hover">
	                <thead>
	                    <tr>
	                        <th>在线</th>
	                        <th>账号</th>
	                        <th>姓名</th>
	                        <th>创建时间</th>
	                        <th>登陆时间</th>
	                        <th>IP</th>
	                        <th>操作</th>
	                    </tr>
	                </thead>
	            </table>
            </div>
        </div>
        <div class="box-footer">
            <div class="row">
                <div class="col-sm-12">
                    <div id="user_page"></div>
                </div>
            </div>
        </div>
    </div>
	<!-- 添加用户弹窗,编辑弹窗 -->
	<input type="hidden" id="nowUserId" name="nowUserId">
	<div class="cover edit_cover">

	</div>
	<!-- 删除弹窗 -->
	<div class="cover del_cover">
		<div class="pop middle">
			<div class="pop_title">您是否确定删除该用户？</div>
			<div class="pop_btn clearfix">
				<a href="javascript:void(0);" class="cancel_btn">取消</a>
				<a href="javascript:void(0);" class="sure_btn">确定</a>
			</div>
		</div>
	</div>

    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
	<script src="<@s.url'/plugins/js/smf_user.js'/>"></script>
<script>
var operate = 0;//0新增 1修改
var table = zookeTable('#user_table',{
    columns:[
        {"data":"onlineStatus"},
        {"data":"username"},
        {"data":"name"},
        {"data":"createTime"},
        {"data":"loginTime"},
        {"data":"loginIp"},
        {"data":"id"},
    ],
    columnDefs:[
        {
            "render":function(data,type,row){
                if(row.onlineStatus==1){
                    return '<i class="dot"></i>';
                }else{
                    return '<i class="dont_dot"></i>';
                }
            },
            "targets":0
        },
        {
            "render":function(data,type,row){
            	var htm = '<a href="###" class="edit_btn" userid="'+row.id+'">编辑</a>'
            			 +'<a href="###" class="del_btn" userid="'+row.id+'">删除</a>'
            			 +'<a href="###" class="reset_btn" userid="'+row.id+'">重置密码</a>';
                return htm;
            },
            "targets":-1
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
		$("#nowUserId").val($(this).attr("userid"));
		$(".del_cover").show();
		$(".cancel_btn").on("click",function(){
			$(".del_cover").hide();
		})
		$(".sure_btn").on("click",function(){
			$.ajax({
				type:'POST',
				url:"<@s.url'/user/del/user'/>",
				data:{
					"userId":$("#nowUserId").val(),
					"deptId":$("#deptId").val()
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

	//编辑按钮
	$(".edit_btn").on("click",function(){
		$("#nowUserId").val($(this).attr("userid"));
		operate = 1;
		toEdit();
		$("#nowUserId").val('');
		$(".edit_cover").show();

	});

	//重置密码按钮
	$(".reset_btn").on("click",function(){
		$("#nowUserId").val($(this).attr("userid"));
		$.ajax({
			type:'POST',
			url:"<@s.url'/user/reset/user/pass'/>",
			data:{
				"userId":$("#nowUserId").val()
			},
			success:function(data){
				alert('密码已重置为：'+data.password);
			}
		});
	});
})

//新增按钮
$("#add_btn").on("click",function(){
	operate = 0;
	toEdit();
	$("#nowUserId").val('');
	$(".edit_cover").show();

});
var pagination = zookePagination('#user_page',{
    pageSize:10,
    remote:{
        url:"<@s.url'/user/find/users'/>",
        pageParams:function(data){
            return {
                pageNum: data.pageIndex+1,
                pageSize:data.pageSize,
                deptId:$("#deptId").val()
            };
        },
        success:function(result){
            // 刷新数据，result.data为获取到的表格数据
            table.clear();
            console.log(result.list);
            table.rows.add(result.list);
            table.draw();
        },
        totalName:'page.totalRow'
    }
});

// 显示全部
$('#showAll').on('click',function(){
  window.location.reload();
});

// 搜索按钮
$('#searchBtn').on('click',function(){
  var searchText = $('#searchInput').val();
  console.log(searchText);
  $.ajax({
    type:'GET',
    url:"${request.contextPath!}"+"/user/find/name",
    data:{
      pageNum: 0,
      pageSize: 10,
      name: searchText
    },
    success:function(res){
      table.clear();
      table.rows.add(res.list);
      table.draw();
    }
  });
})



function toEdit(){
	$.ajax({
		type:'POST',
		url:"<@s.url'/user/to/edit'/>",
		data:{
			"operate":operate,
			"userId":$("#nowUserId").val(),
			"deptId":$("#deptId").val()
		},
		success:function(data){
			$(".edit_cover").html(data);
		}
	});
}

function saveUserInfo(){
	$(".save_btn").attr("disabled","true");
	var chk_value = [];
	$('.depts:checked').each(function(){
		chk_value.push($(this).val());
	});
	$.ajax({
		type:'POST',
		url:"<@s.url'/user/save/user/info'/>",
		data:{
			"operate":operate,
			"roleId":$("#roleId").val(),
			"userId":$("#userId").val(),
			"password":$("#u_pass").val(),
			"username":$("#u_name").val(),
			"name":$("#name").val(),
			"deptIds":chk_value.toString(),
			"oldDeptIds":$("#oldDeptIds").val()
		},
		success:function(data){
			if(data.code==1){
				alert("该账号已经存在");
				$(".save_btn").removeAttr("disabled");
			}else{
				cancel();
				window.location.reload();
			}

		}
	});

}

function cancel(){
	$(".edit_cover").hide();
}
</script>
</body>
</html>
