<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>修改密码</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/change_password.css'/>">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<div class="change_pw_wrap">
		<div class="change_pw_content">
			<form class="change_form">
				<div class="form_group">
					<label>当前用户：</label><span>${(name)!''}</span>
				</div>
				<div class="form_group">
					<label>原密码：</label><input type="password" id="oldpass">
				</div>
				<div class="form_group">
					<label>新密码：</label><input type="password" id="newpass">
				</div>
				<div class="form_group">
					<label>重复密码：</label><input type="password" id="rnewpass">
				</div>
			</form>
			<div class="sure_btn_block clearfix">
				<a href="javascript:void(0);" class="sure_btn ver_center" onclick="save();">
					确定
				</a>
			</div>
		</div>
	</div>
<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
 <script>
 	$("#oldpass").blur(function(){
	  	
	}); 
 	
    function save(){
    	var oldpass = $("#oldpass").val();
    	var newpass = $("#newpass").val();
    	var rnewpass = $("#rnewpass").val();
    	if(oldpass==null||oldpass==''){
    		alert("请输入原密码");
    		return false;
    	}
    	if(newpass!=null&&newpass.length>0){
    		if(rnewpass==newpass){
    			$.ajax({
    				type:'POST',
    				url:"<@s.url'/login/save/edit/pass/info'/>",
    				dataType:"json",
    				data:{
    					"oldpass":oldpass,
    					"newpass":newpass
    				},
    				success:function(data){	
    					if(data.code==1){
    						alert("修改成功");
    					}else if(data.code==2){
    						alert("旧密码错误");
    					}else{
    						alert("修改失败");
    					}
    						
    				}
    			});
    		}else{
        		alert("两次输入的新密码不一致");
        		return false;
    		}
    	}else{
    		alert("请输入新密码");
    		return false;
    	}
   	}
     
 </script>
</body>
</html>