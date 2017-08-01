<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>系统管理/数据权限管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/smf_data.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">


</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
    <div class="box">
        <div class="box-header">
            用户
        </div>
        <div class="box-body">
            <div class="row">
            <div class="col-sm-12">
                <table id="dataTable" class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>账号</th>
                            <th>姓名</th>
                            <th>状态</th>
                            <th>创建时间</th>
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
                    <div id="dataPage"></div>
                </div>
            </div>
        </div>
    </div>
	<!-- 编辑弹窗 -->
	<div class="cover edit_cover">
	</div>
	
    <input type="hidden" id="nowUserId" name="nowUserId"/>

    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/jquery-slimscroll/jquery.slimscroll.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/AdminLTE/dist/js/app.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
	<script>

        // 初始化表格
        var table = zookeTable('#dataTable',{
            columns:[           // 表明每一column的数据，跟json数据对应
                                {"data":"username"},
                                {"data":"name"},
                                {"data":"isUsed"},
                                {"data":"createTime"},
                                {"data":"roleId"}
            ],
            columnDefs:[
               {
                  render:function(data,row){
                  
                      if (data === 1){
                          return "启用";
                      }else{
                          return "禁用";
                      }
                  },
                  targets:-3
               },{
            	   render:function(data,type,row){
                       if (data === 1){
                           return "";
                       }else{
                      		return "<span class=\'edit_btn\' userid=" + row.id.toString() + ">编辑</span>";
                       }
                   },
            	   targets:-1
               }
          	]
        });

        var pagination = zookePagination('#dataPage',{
            pageSize:10,
            remote:{
                url:"<@s.url'/data/right/find/users'/>",
                pageParams:function(data){
                    return {
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


        /**
          * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
          */
        table.on('draw.dt',function(){
            // 编辑按钮
            $(".edit_btn").on("click",function(){
            	$("#nowUserId").val($(this).attr("userid"));
            	//请求编辑的用户数据
            	$.ajax({
            		type:"get",
            		url:"${request.contextPath}/data/right/get/user/data-right",
            		data:{"userId":$("#nowUserId").val()},
            		success:function(data){
        				 $(".edit_cover").html(data);
        				 $(".edit_cover").show();
            		},
                    error: function (data, status, e){
                        alert("加载失败");
                    }
            	});
            });
        })
        
        //编辑保存
        function save(){
        	$(".edit_cover").hide();
        	var dataRightArr = new Array();
        	$("input[type='checkbox']:checked").each(function(){
        		dataRightArr.push($(this).val());
        	});
        	var dataRight = dataRightArr.join(",");
        	if(dataRight == null || dataRight == ''){
        		alert("请勾选权限项");
        		return;
        	}
        	var nowUserId = $("#nowUserId").val();
        	$.ajax({
     		   type: "POST",
     		   dataType:"json",
     		   url: "${request.contextPath}/data/right/save/user/data-right",
     		   data: {"userId":nowUserId,"dataRight":dataRight},
     		   success: function(json){
     				if(json.code == 1){
     					alert("保存成功");
     				}else{
     					alert(json.result);
     				}
     		   },
     		  error: function (data, status, e){
                  alert("加载失败");
              }
     		});
        }
        //编辑取消
        function cancel(){
        	$(".edit_cover").hide();
        }
        //编辑关闭对话框
        function closeCover(){
        	$(".edit_cover").hide();
        }

    </script>
</body>
</html>
