$(document).ready(function(){
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
	// 新增按钮
	$(".add_btn").on("click",function(){
		$(".edit_cover").show();
	})
	$(".modal-content button").on("click",function(){
		$(".edit_cover").hide();
	})

})