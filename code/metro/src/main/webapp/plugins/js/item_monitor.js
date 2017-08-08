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
	            "state":{ "opened":true, "disabled":true},
	            "icon":"city",
	            "children":[
	            {
	                "text":"二号线",
                    "state":{ "opened":true, "disabled":true},
	                "icon":"line",
	                "children":[
	                {
	                    "text":"公园前区间",
                        "state":{ "opened":true, "disabled":true},
	                    "icon":"area",
	                    "children":[{
                            "id":'left0',
	                        "text":"左线",
	                        "icon":"left",
	                        "url":"item_monitor_right.html",
	                    },{
	                        "text":"右线",
	                        "icon":"right"
	                    }]
	                },
	                {
	                    "text":"公园前区间",
                        "state":{ "opened":true, "disabled":true},
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
                        "state":{ "opened":true, "disabled":true},
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
                        "state":{ "opened":true, "disabled":true},
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
                        "state":{ "opened":true, "disabled":true},
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
                        "state":{ "opened":true, "disabled":true},
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
                        "state":{ "opened":true, "disabled":true},
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
	$("#iframe_content").attr("src",data.node.original.url);
});

$("#tree").on('loaded.jstree',function(){
    $("#tree").jstree('select_node',"left0");
});

// // 2秒后发起请求
// setTimeout(update,2000);

})
