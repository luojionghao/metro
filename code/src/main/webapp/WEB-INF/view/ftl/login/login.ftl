<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>登录</title>
	<link rel="shortcut icon" type="image/x-icon" href="<@s.url'/plugins/images/favicon.png'/>" />
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/css/login.css'/>">
	<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<script type="text/javascript">
      if(top != self) window.parent.location.href = "<@s.url'/login/index'/>";  //"index";
    </script>
</head>
<body>
	<input type="hidden" id="login_out" value="${(login_out)!0}">
	<input type="hidden" id="login_msg" value="${(login_msg)!''}">
	<div class="loginBg">
		<!--登录错误提示-->
        <div class="wrong_hint">
            <i class="red_hint"></i>
            <span class="worng_txt"></span>
        </div>
		<!-- 登录 -->
		<form id="loginForm">
		<div class="login_main middle">
			<ul class="login_list">
				<li>
					<img src="<@s.url'/plugins/images/login_logo.png'/>" class="login_logo">
					<span>广州地铁盾构施工监控信息管理系统</span>
				</li>
				<li>
					<div class="login_input">
						<label class="login_icon"><i class="name_icon"></i></label>
						<input type="text" id="username" name="username">
					</div>
				</li>
				<li>
					<div class="login_input">
						<label class="login_icon"><i class="pass_icon"></i></label>
						<input type="password" id="password" name="password">
					</div>
				</li>
				<li>
					<div class="login_input clearfix">
						<input type="text" id="vcode" name="vcode" class="code_input">
						<div class="code_right clearfix">
							<img id="imgObj"  alt="" src="<@s.url'/login/get/vcode/img'/>" class="code_num">
							<a href="javascript:void(0);" class="change_btn" onclick="changeImg()">换一张</a>
						</div>
					</div>
				</li>
				<li><Input type="submit" class="loginBtn" value="登录" /></li>
			</ul>
		</div>
		</form>
		<!-- 登录loading... -->
		<div class="load_box">
			<div class="middle">
				<div class="load_round"></div>
			</div>
			<span class="load_text middle">登录中...</span>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
 function changeImg(){ 
    var imgSrc = $("#imgObj");     
    var src = imgSrc.attr("src");
    imgSrc.attr("src","")
    imgSrc.attr("src",chgUrl(src));     
}     
//时间戳     
//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳     
function chgUrl(url){     
    var timestamp = (new Date()).valueOf();     
    urlurl = url.substring(0,17);     
    if((url.indexOf("&")>=0)){     
        urlurl = url + "×tamp=" + timestamp;     
    }else{     
        urlurl = url + "?timestamp=" + timestamp;     
    }     
    return urlurl;     
} 

$(document).ready(function(){
	/*
	if(parseInt($("#login_out").val())==1){
		alert($("#login_msg").val());
	}*/
	
	$(".wrong_hint").hide();
	$(".load_box").hide();
	function mistake(str){
		// TODO
		$(".worng_txt").text(str);
		// 显示提示语
		$(".wrong_hint").show();
	}
	
	function runIt(){
		// 登录中...变亮
		$(".load_text").css({
			opacity:0.85,
		});
		setTimeout(function(){
			// 外边的圆圈
			$(".load_round").css({
				"width":"80px",
				"height":"80px",
				"borderRadius":"40px",
				"border":"2px solid white",
				"opacity":"1",
			}).animate({
				width:400,
				height:400,
				borderRadius:200,
				opacity:0,
			},1000,runIt);
			// 登录中...变暗
			$(".load_text").animate({
				opacity:0.6,
			});
		},500);
	}
	
	$("#loginForm").submit(function(){
		// 错误提示
		var username=$("#username").val();
		var password=$("#password").val();
		if(username=="" || password==""){
			mistake("账号或密码不正确，如有疑问，请联系系统管理员");
			return false;
		}else{
			$.ajax({
				type:'POST',
				url:"<@s.url'/login/check/login'/>",
				dataType:"json",
				data:{
					"username":$("#username").val(),
					"password":$("#password").val(),
					"vcode":$("#vcode").val()
				},
				success:function(data){
					if(data.code==1){
						$(".login_main").hide();
						$(".wrong_hint").hide();
						$(".load_box").show();
						runIt();
						setTimeout(function(){
							window.location.href="<@s.url'/login/to-main'/>";
						},2000);				 
					}else if(data.code==3){
						mistake(data.result);
					}else if(data.code==4){
						mistake(data.result);
					}else if(data.code==0){
						mistake(data.result);
					}else{
						mistake(data.result);
					}
				}
			});
			return false;
		}

		$(".login_main").hide();
		$(".wrong_hint").hide();
		$(".load_box").show();

		function runIt(){
		// 登录中...变亮
		$(".load_text").css({
			opacity:0.85,
		});
		setTimeout(function(){
			// 外边的圆圈
			$(".load_round").css({
				"width":"80px",
				"height":"80px",
				"borderRadius":"40px",
				"border":"2px solid white",
				"opacity":"1",
			}).animate({
				width:400,
				height:400,
				borderRadius:200,
				opacity:0,
			},1000,runIt);
			// 登录中...变暗
			$(".load_text").animate({
				opacity:0.6,
			});
		},500);
	}
	//runIt();
	// 页面跳转

	})
	
})
</script>  