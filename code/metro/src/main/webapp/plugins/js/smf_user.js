$(document).ready(function(){
	// 左侧组织机构
	$(".org_list li").on("click",function(){
		$(this).addClass("org_active").siblings("li").removeClass("org_active");
	})
	// 删除按钮
	$(".del_btn").on("click",function(){
		var current=$(this).parent("td").parent("tr");
		$(".del_cover").show();
		$(".cancel_btn").on("click",function(){
			$(".del_cover").hide();
		})
		$(".sure_btn").on("click",function(){
			$(".del_cover").hide();
			current.remove();
		})
	})
	$(".edit_btn").on("click",function(){
		$(".edit_cover").show();
	})
	$(".modal-content button").on("click",function(){
		$(".cover").hide();
	})
	$(".add_btn").on("click",function(){
		$(".edit_cover").show();
	})
	$(".add_org_btn").on("click",function(){
		$(".add_user_cover").show();
		$(".org_name_input").val("");
	})
	$(".change_name_btn").on("click",function(){
		$(".add_user_cover").show();
		var org_name=$(".org_active").text();
		$(".org_name_input").val(org_name);
	})
	$(".reset_btn").on("click",function(){
		$(".reset_password_cover").show();
	})

	/**
	function autoFrameSize(){
        $(".user_control").css('height',function(){
            var screenHeight = window.innerHeight;
            var offset = $(".user_control")[0].offsetTop;
            return screenHeight-offset;
        });
        $(".iframe_content").css('height',function(){
            var screenHeight = window.innerHeight;
            var offset = $(".iframe_content")[0].offsetTop;
            return screenHeight-offset;
        });
    }
    $(window).resize(autoFrameSize);
    autoFrameSize();
    */

})