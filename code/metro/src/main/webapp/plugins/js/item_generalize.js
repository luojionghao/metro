$(document).ready(function(){
	// 市
	$(".new_way").hide();
	$(".new_way").on("click",function(){
		$(".new_way_cover").show();
	})

    // 按钮关闭事件
    $('button.dismiss').on('click',function btnClose(){
        $(".new_way_cover").hide();
        $(".new_section_cover").hide();
        $(".ren_section_cover").hide();
    });
	// 线
	$(".new_section").hide();
	$(".new_section_btn").on("click",function(){
		$(".new_section_cover").show();
	})
	// 区间
	$(".del_section").hide();
	
	// 重命名
	$(".ren_section_btn").on("click",function(){
		$(".ren_section_cover").show();
		var jstree_clicked=$(".jstree-clicked").text();
		$(".ren_section_cover .section_name_input").val(jstree_clicked);
	})

	// 通过json直接配置树状图
    // 一般通过配置id字段来唯一表示节点，id非常重要，通过程序操作节点一般通过传id实现
	$('#tree').jstree({
	    "check_callback":true,
	    'core':{
	        'data':[
	        {
	            "text":"广州市",
                "id":"city",
	            "state":{"opened":true},
	            "icon":"city",
	            "type":"city",
	            "url":"item_generalize_city.html",
	            "children":[
	            {
	                "text":"二号线",
	                "state":{"opened":true},
	                "icon":"line",
	                "type":"line",
	            	"url":"item_generalize_line.html",
	                "children":[
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                }
	                ]
	            },
	            {
	                "text":"三号线",
	                "state":{"opened":true},
	                "icon":"line",
	                "type":"line",
	            	"url":"item_generalize_line.html",
	                "children":[
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                },
	                {
	                    "text":"广州塔区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"item_generalize_area.html",
	                }
	                ]
	            }]
	        }]
	    }
	});

	/*通过访问API接口更新tree
   	接口：localhost:10220/tree/treeAll（关于接口的修改，请参考根目录关于gulp-mock-server的使用)
  	*/
// function update(){
//     $("#tree").jstree(true).settings.core.data = {
//             "url":"http://127.0.0.1:10220/tree/treeAll",
//             "dataType":"json",
//     };
//     $("#tree").jstree(true).refresh();
// }

// 选中事件回应
$("#tree").on("select_node.jstree",function(e,data){
	$("#iframe_content").attr("src",data.node.original.url);
    if(data.node.original.type === 'city'){
        // 城市类型节点被选中
        $(".new_way").show();
        $(".new_section").hide();
        $(".del_section").hide();
    }
    if(data.node.original.type === 'line'){
        // 线路类型节点被选中
        $(".new_section").show();
        $(".new_way").hide();
        $(".del_section").hide();
    }
    if(data.node.original.type === 'area'){
        // 区间类型节点被选中
        $(".del_section").show();
        $(".new_way").hide();
        $(".new_section").hide();
    }
});


// 树形图加载加成事件，一些依赖树状图加载完成后才执行的操作写在这里面
$("#tree").on('loaded.jstree',function(){
    $("#tree").jstree('select_node',"city");
});

// // 2秒后发起请求
// setTimeout(update,2000);

});
