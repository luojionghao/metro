<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>系统管理/用户管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/smf_user.css'/>">
	<!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<input type="hidden" id="deptId" name="deptId">
	<input type="hidden" id="deptName" name="deptName">
	<div class="user_control clearfix">
		<div class="left_content">
			<div class="org_title">组织机构</div>
			<ul class="org_list">
				<#if dlist??>
				<#list dlist as d>
					<li <#if d_index==0> class="org_active" </#if> deptid="${(d.id)}" deptname="${(d.deptName)}">					
					<a href="<@s.url'/user/to/right'/>?deptId=${d.id}" target="iframe_content" class="monitor_center">
					${(d.deptName)}
					</a>
					</li>
				</#list>
				</#if>
			</ul>
			<div class="org_bottom">
				<div class="org_bottom_content">
					<ol class="org_btn clearfix ver_center">
						<li class="add_org_btn">添加</li>
						<li class="change_name_btn">修改</li>
						<li class="del_org_btn">删除</li>
					</ol>
				</div>
			</div>
		</div>
		<div class="right_content">
		    <iframe class="iframe_content" name="iframe_content" scrolling="no" src="" frameborder="0" width="100%">
            </iframe>
		</div>
	</div>
	<!-- 删除弹窗 -->
	<div class="cover del_cover">
		<div class="pop middle">
			<div class="pop_title">您是否确定删除该机构？</div>
			<div class="pop_btn clearfix">
				<a href="javascript:void(0);" class="cancel_btn">取消</a>
				<a href="javascript:void(0);" class="sure_btn">确定</a>
			</div>
		</div>
	</div>
    <!-- 添加，修改弹窗 -->
	<div class="cover add_user_cover">
		<div class="modal-content middle">
	      	<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          	<span aria-hidden="true">×</span>
		        </button>
		        <span class="modal-title">机构信息</span>
	      	</div>
		    <div class="modal-body">
				<div class="org_name_block">
					<span>机构名称：</span>
					<input type="text" class="org_name_input" id="dept" name="dept">
		        </div>
		    </div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
	        	<button type="button" class="btn btn-primary save_btn">保存</button>
	      	</div>
    	</div>
	</div>

<script type="text/javascript">
	var operate='';
		$(document).ready(function(){
			$(".monitor_center")[0].click();
			$("#deptId").val($(".org_active").attr("deptId"));
			$("#deptName").val($(".org_active").attr("deptName"));
			// 左侧组织机构
			$(".org_list li").on("click",function(){
				$("#deptId").val($(this).attr("deptId"));
				$("#deptName").val($(this).attr("deptName"));
				$(this).addClass("org_active").siblings("li").removeClass("org_active");
			})
			
			$(".modal-content button").on("click",function(){
				$(".cover").hide();
			})			
			
			/*添加机构*/
			$(".add_org_btn").on("click",function(){
				operate='add';
				$("#dept").val("");
				$(".add_user_cover").show();
			})
			
			/*修改机构*/
			$(".change_name_btn").on("click",function(){
				if($("#deptId").val()!=null&&$("#deptId").val().length>0&&$("#deptName").val()!=null&&$("#deptName").val().length>0){	
					operate='edit';
					$("#dept").val($("#deptName").val());
					$(".add_user_cover").show();
				}else{
					alert('请选择机构');
				}								
			})
			
			/*删除机构*/
			$(".del_org_btn").on("click",function(){
				if($("#deptId").val()!=null&&$("#deptId").val().length>0){	
					operate='del';
					$(".del_cover").show();
				}else{
					alert('请选择机构');
				}
				//取消
				$(".cancel_btn").on("click",function(){
					$(".del_cover").hide();
				})
				
				//确认
				$(".sure_btn").on("click",function(){					
					saveDeptInfo();
				})
			})
			
			$(".save_btn").on("click",function(){
				saveDeptInfo();				
			})

		})
		
function saveDeptInfo(){
	$.ajax({
		type:'POST',
		url:"<@s.url'/user/save/dept/info'/>",
		data:{
			"operate":operate,
			"deptId":$("#deptId").val(),
			"deptName":$("#dept").val()
		},
		success:function(data){
			window.location.reload();
		}
	});
	
}
</script>
</body>
</html>