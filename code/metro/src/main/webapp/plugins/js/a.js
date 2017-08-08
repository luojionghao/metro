$(document).ready(function(){
	// 通过json直接配置树状图
	$('#tree').jstree({
	    "check_callback":true,
	    'core':{
	        'data':[
	        {
	            "text":"广州市",
	            "state":{"opened":true},
	            "icon":"city",
	            "type":"city",
	            "url":"../page/info_monitor_city.html",
	            "children":[
	            {
	                "text":"二号线",
	                "state":{"opened":true},
	                "icon":"line",
	                "children":[
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left"
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
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
	$("#frame_content").attr("src",data.node.original.url);
});


// // 2秒后发起请求
// setTimeout(update,2000);

})