$(document).ready(function(){
	
	$(".wrong_hint").hide();
	$(".load_box").hide();
	function mistake(str){
		// TODO
		$(".worng_txt").text(str);
		// 显示提示语
		$(".wrong_hint").show();
	}
	$(".loginBtn").on("click",function(){
		// 错误提示
		var username=$("#username").val();
		var password=$("#password").val();
		if(username=="" || password==""){
			mistake("账号或密码不正确，如有疑问，请联系系统管理员");
			return false;
		}

		$(".login_main").hide();
		$(".wrong_hint").hide();
		$(".load_box").show();

		function runIt(){
		// 登陆中...变亮
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
			// 登陆中...变暗
			$(".load_text").animate({
				opacity:0.6,
			});
		},500);
	}
	runIt();
	// 页面跳转
	setTimeout(function(){
		location.href="index.html"
	},5000);
	})
})