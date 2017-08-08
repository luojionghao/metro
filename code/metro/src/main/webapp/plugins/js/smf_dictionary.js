$(document).ready(function(){
	// 添加按钮
	$(".add_btn").on("click",function(){
		$(".add_cover").show();
	})
	$(".modal-content button").on("click",function(){
		$(".add_cover").hide();
	})
	$(".save_btn").on("click",function(){
		var name_input=$(".name_input").val();
		var $tr=$("<tr></tr>");
		var td='<td>'+name_input+'</td>'
		+'<td>'+'<a href="javascript:void(0);" class="ren_btn">'+'重命名'+'</a>'
		+'<a href="javascript:void(0);" class="upload_btn">'+'上传'+'</a>'+'</td>';
		$tr.html(td);
		$(".dict_table tbody").append($tr);
		$(".name_input").val("");
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
	
})