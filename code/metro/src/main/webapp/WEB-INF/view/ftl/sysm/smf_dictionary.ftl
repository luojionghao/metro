<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>系统管理/字典管理</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
 	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/smf_dictionary.css'/>">
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
    <div class="box">
        <div class="box-header">
            <button class="add_btn">添加</button>
        </div>
        <div class="box-body">
            <table id="dic_table" class="dict_table table table-bordered table-hover">
                <thead>
                    <tr>
                        <td>背景图名称</td>
                        <td>类型</td>
                        <td>管理</td>
                    </tr>
                </thead>
            </table>
        </div>
        <div class="box-footer">
            <div class="row">
                <div class="col-sm-12">
                    <div id="dic_page"></div>
                </div>
            </div>
        </div>
	</div>
	<!-- 删除弹窗 -->
	<div class="cover del_cover">
		<div class="pop middle">
			<div class="pop_title">您是否确定删除该背景图？</div>
			<div class="pop_btn clearfix">
				<a href="javascript:void(0);" class="cancel_btn">取消</a>
				<a href="javascript:void(0);" class="sure_btn">确定</a>
			</div>
		</div>
	</div>
	<form id="form1" name="form1" action="<@s.url '/dic/save/photo/info'/>" method="post" enctype="multipart/form-data" >
	<!-- 添加弹窗 -->
	<div class="cover add_cover">

    </div>
    </form>
	<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
	<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
    <script src="<@s.url'/plugins/js/ajaxfileupload.js'/>" ></script>
    <script src="<@s.url'/plugins/js/base.js'/>" ></script>
    <script>
    
    	var operate = 0;
    	
        var table = zookeTable('#dic_table',{
            columns:[
                {"data":"photoName"},
                {"data":"photoType"},
                {"data":"dictionary_operation"}
            ],

            columnDefs:[{
                "render":function(data,type,row){
                     return '<a href="###" class="ren_btn" onclick="edit_btn('+row.id+');">修改</a><a href="'+row.photoUrl+'" class="upload_btn" photoid="'+row.id+'">查看图片</a><a href="###" class="del_btn" photoid="'+row.id+'">删除</a>';
                },
                "targets":-1
            },
            {
                "render":function(data,type,row){
                	 if(row.photoType==1){
                		 return '刀盘背景 ';
                	 }else if(row.photoType==2){
                		 return '螺旋背景';
                	 }else{
                         return '泥水背景';
                     }
                     
                },
                "targets":-2
            }
            ]
        });

        /**
          * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
          */
        table.on('draw.dt',function(){
            // 删除按钮
            $(".del_btn").on("click",function(){
            	var photoId = $(this).attr("photoid");
                var current=$(this).parent("td").parent("tr");
                $(".del_cover").show();
                $(".cancel_btn").on("click",function(){
                    $(".del_cover").hide();
                });
                $(".sure_btn").on("click",function(){ 
                	$(".del_cover").hide();
                	if(photoId!=null){
                    	$.ajax({
                    		type:'POST',
                    		url:"<@s.url'/dic/del/photo/info'/>",
                    		data:{
                    			"photoId":photoId
                    		},
                    		success:function(data){
                    			window.location.reload(); 	 
                    		}
                    	});
                	}
                });
            })
        })
      
        
        var pagenation = zookePagination('#dic_page',{
            pageSize:10,
            remote:{
                url:"<@s.url'/dic/find/photos'/>",
                pageParams:function(data){
                    return {
                        pageNum: data.pageIndex+1,
                        pageSize:data.pageSize
                    };
                },
                success:function(result){
                    // 刷新数据，result.data为获取到的表格数据
                    table.clear();
                    table.rows.add(result.list);
                    table.draw();
                },
                totalName:'page.totalRow'
            }
        });
        

        function toEdit(photoId){
        	$.ajax({
        		type:'POST',
        		url:"<@s.url'/dic/to/edit'/>",
        		data:{
        			"operate":operate,
        			"photoId":photoId
        		},
        		success:function(data){
        			$(".add_cover").html(data);
        			$(".add_cover").show();
        			$('#photoImg').on("change",function(){
        				//$('#photoImg').replaceWith('<input type="file" class="select_btn" id="photoImg" name="photoImg">');   
        				$("#fileUpName").text($(this).val());     				
        				$("#photoUrl").val("");
        			});
        			 
        		}
        	});
        }
        
	    // 添加按钮
	    $(".add_btn").on("click",function(){
	    	operate = 0;
	    	toEdit(0);	    	
	    })
	    
	    function edit_btn(photoId){
	    	operate = 1;
	    	toEdit(photoId);	
	    }
	    
	    function cancel(){
	    	$(".add_cover").hide();
	    }
	    
        function fsubmit(obj){
			var pname = $("#photoName").val();
			var pimg = $("#photoImg").val();
			var photoUrl = $("#photoUrl").val();
			var operate = $("#operate").val();
        	if(pname == null || pname==""){
        		alert("请填写图片名称");
        		return;
        	}else{ 
        		var b = 0;
        		if(operate==0){
        			b=1;
                	$.ajax({
                		type:'POST',
                		url:"<@s.url'/dic/check/photo/name'/>",
                		async: false,
                		data:{
                			"pname":pname
                		},
                		success:function(data){
                			 if(data.code==1){
                				 alert("名称已经存在");
                				 b=2;
                			 }	 
                		}
                	});                	
        		}
        		if(b<2){
                   	if(pimg == null || pimg==""){
                        if(operate==0){
                       		alert("请选择图片");
                       		return;
                    	}else{
        					if(photoUrl==null || photoUrl==""){
        	               		alert("请选择图片");
        	               		return;
        					}
                    	}
                   	}        	
                	obj.submit();
        		}
        	}
        	

        }
        
       
    </script>
</body>
</html>
