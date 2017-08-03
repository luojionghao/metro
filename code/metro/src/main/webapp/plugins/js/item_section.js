$(document).ready(function(){
	/**
	function autoFrameSize(){
        $("#iframe_content").css('height',function(){
            var screenHeight = window.innerHeight;
            var offset = $("#iframe_content")[0].offsetTop;
            return screenHeight-offset;
        });
    }
    $(window).resize(autoFrameSize);
    autoFrameSize();
	*/
	// 通过json直接配置树状图
	$('#tree').jstree({
	    "check_callback":true,
	    'core':{
	        'data':[
	        {
	            "text":"广州市",
	            "state":{"opened":true,"disabled":true},
	            "icon":"city",
	            "children":[
	            {
	                "text":"二号线",
                    "state":{"opened":true,"disabled":true},
	                "icon":"line",
	                "children":[
	                {
                        "id":"area0",
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                }
	                ]
	            },
	            {
	                "text":"二号线",
	                "state":{"opened":true,"disabled":true},
	                "icon":"line",
	                "children":[
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "url":"item_section_right.html",
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
});

// 树形图加载加成事件，一些依赖树状图加载完成后才执行的操作写在这里面
$("#tree").on('loaded.jstree',function(){
    $("#tree").jstree('select_node',"area0");
});

// // 2秒后发起请求
// setTimeout(update,2000);

})
