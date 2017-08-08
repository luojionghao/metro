$(document).ready(function(){
	// 通过json直接配置树状图
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
	            "url":"../page/info_monitor_city.html",
	            "children":[
	            {
	                "text":"二号线",
	                "state":{"opened":true},
	                "icon":"line",
	                "type":"line",
	            	"url":"../page/info_monitor_line.html",
	                "children":[
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
	                    }]
	                },
	                {
	                    "text":"公园前区间",
	                    "state":{"opened":true},
	                    "icon":"area",
	                    "type":"area",
	                    "url":"../page/info_monitor_area.html",
	                    "children":[{
	                        "text":"左线",
	                        "icon":"left",
	                        "type":"left",
	                        "url":"info_monitor_LRline.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right",
	                        "type":"right",
	                        "url":"info_monitor_LRline.html",
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

// 树形图加载加成事件，一些依赖树状图加载完成后才执行的操作写在这里面
$("#tree").on('loaded.jstree',function(){
    $("#tree").jstree('select_node',"city");
});

// // 2秒后发起请求
// setTimeout(update,2000);

})