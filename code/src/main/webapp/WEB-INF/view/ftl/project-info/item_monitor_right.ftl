<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>监测预警设置</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <!--<link rel="stylesheet" href="<@s.url'/plugins/css/tree.css'/>" media="all">-->
    <!--<link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">-->
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/item_monitor.css?V=3'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/select2/dist/css/select2.min.css'/>">


    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/AdminLTE/dist/js/app.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/bower/select2/dist/js/select2.full.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/vue.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
<div class="dict_content">
    <div class="box">

        <ul class="box-header clearfix">
            <li><button type="button" class="head_btn new_btn" >新增</button></li>
            <li><button type="button" class="head_btn" onclick="exportExcel();">导出</button></li>
            <li><button type="button" class="head_btn" onclick="javascript:openUploadBox();">导入</button></li>
        </ul>

        <div class="box-body">
            <div class="row no-pad">
                <div class="col-sm-12">
                    <!-- 表格模块 -->
                    <table id="roleTable" class="table table-bordered table-hover dataTable">
                        <thead>
                        <tr>
                            <th>参数</th>
                            <th>预警类型</th>
                            <th>开始环号</th>
                            <th>结束环号</th>
                            <th>红色预警上限</th>
                            <th>橙色预警上限</th>
                            <th>橙色预警下限</th>
                            <th>红色预警下限</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                    <!-- table end -->
                </div>
            </div>
        </div>
        <div class="box-footer">
            <div class="row">
                <div class="col-sm-12">
                    <!-- 分页模块 -->
                    <div id="pagination"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 新增弹框 -->
<form id="newform" name="newform" method="post" action="" onsubmit="return false;">
    <input type="hidden" name="intervalId" value="${intervalId!''}"/>
    <input type="hidden" name="leftOrRight" value="${leftOrRight!''}"/>
    <div class="cover new_cover">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="newclose();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">预警信息</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span class="arg_tag">参数：</span>
                        <select id="arg_select_new" class="para_select" name="param">
                        <#if dics ?? && dics?size gt 0>
                            <#list dics as dic>
                                <option value="${dic.dicName!''}">${dic.dicMean!''}</option>
                            </#list>
                        </#if>
                        </select>
                    </li>
                    <li>
                      <div id="warnTypeCheck">
                            <span class="arg_tag">预警类型：</span>
                            <input id="warn-advance" class="warn-type-check" value="1" v-model="warnCheck" type="checkbox" >
                            <label for="warn-advance">推进预警</label>
                            <input id="warn-assembling" class="warn-type-check" value="2" v-model="warnCheck" type="checkbox" >
                            <label for="warn-assembling">拼装预警</label>
                            <input id="warn-down" class="warn-type-check" value="4" v-model="warnCheck" type="checkbox" >
                            <label for="warn-down">停机预警</label>
                        </div>
                    </li>
                    <li>
                        <span class="arg_tag">开始环号：</span>
                        <input type="number" name="startRingNum" class="start_num">
                    </li>
                    <li>
                        <span class="arg_tag">结束环号：</span>
                        <input type="number" name="endRingNum" class="end_num">
                    </li>
                    <li>
                        <span class="arg_tag">红色预警上限：</span>
                        <input type="number" step="0.01" name="redWarningMax" class="red_upper">
                    </li>
                    <li>
                        <span class="arg_tag">橙色预警上限：</span>
                        <input type="number" step="0.01" name="orangeWarningMax" class="orange_upper">
                    </li>
                    <li>
                        <span class="arg_tag">橙色预警下限：</span>
                        <input type="number" step="0.01" name="orangeWarningMin" class="orange_floor">
                    </li>
                    <li>
                        <span class="arg_tag">红色预警下限：</span>
                        <input type="number" step="0.01" name="redWarningMin" class="red_floor">
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="newcancel();">取消</button>
                <button type="button" class="btn btn-primary save_btn" onclick="newsave();">保存</button>
            </div>
        </div>
    </div>
</form>
<!-- 修改弹框 -->
<form id="updateform" name="updateform" method="post" action="" onsubmit="return false;">
    <div class="cover change_cover">
    </div>
</form>
<!-- 删除弹窗 -->
<div class="cover del_cover">
    <div class="pop middle">
        <div class="pop_title">您是否确定删除该预警设置？</div>
        <div class="pop_btn clearfix">
            <a href="javascript:void(0);" class="cancel_btn">取消</a>
            <a href="javascript:void(0);" class="sure_btn">确定</a>
        </div>
    </div>
</div>

<!-- 上传文件弹框 -->
<div id="uploadBox" class="cover">
    <div class="modal-content middle">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close"  onclick="cancelUploadBox();">
                <span aria-hidden="true">×</span>
            </button>
            <span class="modal-title">上传文件</span>
        </div>
        <div class="modal-body">
            <form id="form1" name="form1" action="<@s.url '/project-info/monitor/lrinfo/import'/>" method="post" enctype="multipart/form-data" >
                <label class="btn btn-default btn-file">
                    选择文件<input id="excel" class="input_file" type="file" name="file" accept="application/vnd.ms-excel" style="display:none;"/>
                </label>
                <span id="upFileName"></span>
                <input type="hidden" name="intervalId" value="${intervalId!''}"/>
                <input type="hidden" name="leftOrRight" value="${leftOrRight!''}"/>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left"
                    data-dismiss="modal" onclick="cancelUploadBox();">取消</button>
            <button type="button" class="btn btn-primary save_btn2"
                    onclick="fsubmit(document.form1);">上传</button>
        </div>
    </div>
</div>

<script type="text/javascript">
    var warnChecks = new Vue({
        el:'#warnTypeCheck',
        data:{
            warnCheck:[1]
        },
        methods:{
            getWarnCheck:function(){
                return this.warnCheck.reduce(function(acc,val){
                    return acc + parseInt(val);
                },0);
            }
        },
        watch:{
            warnCheck:function(newValue,oldValue){
                if(this.warnCheck.length == 0){
                    this.warnCheck = oldValue;
                };
            }
        }
    });

    var editWarnChecks = null;

    // 打开上传框
    function openUploadBox(){
        $('#uploadBox').show();
    }

    // 取消上传框
    function cancelUploadBox(){
        $('#uploadBox').hide();
    }
    // 选择文件后，显示文件路径
    registerUpfileName(["excel"],["upFileName"]);

    // 参数选择器初始化
    $('#arg_select_new').select2();
    // 导入文件
    function fsubmit(obj){
        var excel = $("#excel").val();
        if(excel == null || excel==""){
            alert("请先选择文件");
            return;
        }
        obj.submit();
    }


    //导出excel数据
    function exportExcel(){
        $.ajax({
            type: "post",
            dataType:"json",
            url: "${request.contextPath}/project-info/monitor/lrinfo/export",
            data: {"intervalId":"${intervalId!''}","leftOrRight":"${leftOrRight!''}"},
            success: function(json){
                if(json.code == 1){
                    var filename = json.result;
                    location.href = "${request.contextPath}/common/file-download?filename="+filename;
                }else{
                    alert(json.result);
                }
            },
            error: function (data, status, e){
                //alert(e);
                alert("导出失败");
            }
        });
    }

    function newclose(){
        $(".new_cover").hide();
    }
    function newcancel(){
        $(".new_cover").hide();
    }

    function getNewSaveData(){
        return $("#newform").serialize()+"&category="+warnChecks.getWarnCheck();
    }

    //预警阀值信息保存
    function newsave(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/monitor/lrinfo/save",
            data:getNewSaveData(),
            success:function(json){
                if(json.code == 1){
                    $(".new_cover").hide();
                    $("#newform")[0].reset();
                    $("#pagination").pagination('remote');
                    //$("#tree").jstree(true).refresh(); //刷新
                    //alert(json.result);
                }else{
                    alert(json.result);
                }
            },
            error: function (data, status, e){
                alert("保存失败");
            }
        });
    }
    function updateclose(){
        $(".change_cover").hide();
    }
    function updatecancel(){
        $(".change_cover").hide();
    }
    function updatesave(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/monitor/lrinfo/update",
            data:$("#updateform").serialize(),
            success:function(json){
                if(json.code == 1){
                    $(".change_cover").hide();
                    $("#updateform")[0].reset();
                    $("#pagination").pagination('remote');
                    //$("#tree").jstree(true).refresh(); //刷新
                    //alert(json.result);
                }else{
                    alert(json.result);
                }
            },
            error: function (data, status, e){
                alert("保存失败");
            }
        });
    }

    $(".new_btn").on("click",function(){
        $(".new_cover").show();
    })

    /*  $(".save_btn").on("click",function(){
         var para=$(".para_select option").text();
         var start_num=$(".start_num").val();
         var end_num=$(".end_num").val();
         var red_upper=$(".red_upper").val();
         var orange_upper=$(".orange_upper").val();
         var red_floor=$(".red_floor").val();
         var orange_floor=$(".orange_floor").val();
         var $tr=$("<tr></tr>");
         var td='<td>'+para+'</td>'+'<td>'+start_num+'</td>'+'<td>'+end_num+'</td>'+
         '<td>'+red_upper+'bar'+'</td>'+'<td>'+orange_upper+'bar'+'</td>'+
         '<td>'+red_floor+'bar'+'</td>'+'<td>'+orange_floor+'bar'+'</td>'+
         '<td>'+'<a href="###" class="edit_btn">'+'修改'+'</a>'
         +'<a href="###" class="del_btn">'+'删除'+'</a>'+'</td>';
         $tr.html(td);
         $(".dict_table tbody").append($tr);
     }) */
    // 初始化表格
    var table = zookeTable('#roleTable',{
        "columns":[           // 表明每一column的数据，跟json数据对应
            {"data":"paramDic.dicMean"},
            {"data":"category"},
            {"data":"startRingNum"},
            {"data":"endRingNum"},
            {"data":"redWarningMax"},
            {"data":"orangeWarningMax"},
            {"data":"orangeWarningMin"},
            {"data":"redWarningMin"},
            {"data":"operation"}
        ],
        "columnDefs":[{
            "render":function(data,type,row){
                // return "<a href=\'${request.contextPath}/project-info/monitor/lrinfo/to-edit?id="+row.id.toString()+"\' class=\'edit_btn\'>修改</a><a href=\'###\' class=\'del_btn\'>删除</a>";
                return "<a href=\'javascript:void(0);\'"+" warnid="+row.id.toString()+" class=\'edit_btn\'>修改</a>"+"<a href=\'javascript:void(0);\'"+" warnid="+row.id.toString()+" class=\'del_btn\'>删除</a>";
            },
            "targets":-1
        },{
            "render":function(data,type,row){
                var categoryStr = "";
                if(data == 1 || data == 3 || data == 5 || data == 7){
                    categoryStr +="推进预警 ";
                }
                if(data == 2 || data == 3 || data == 6 || data == 7){
                    categoryStr +="拼装预警 ";
                }
                if(data == 4 || data == 5 || data == 6 || data == 7){
                    categoryStr +="停机预警 ";
                }
                return categoryStr;
            },
            "targets":-8
        }]
    });

    /**
     * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
     */
    table.on('draw.dt',function(){
        // 修改按钮
        $(".edit_btn").on("click",function(){
            //请求编辑的数据
            $.ajax({
                type:"get",
                url:"${request.contextPath}/project-info/monitor/lrinfo/to-edit",
                data:{"id":$(this).attr("warnid")},
                success:function(data){
                    //$(".edit_cover").html('');
                    //console.log(data);
                    $(".change_cover").html(data);
                    $(".change_cover").show();
                    $('#arg_select_edit').select2();

                    var categoryVal = parseInt($('#categoryVal').val());
                    var initData = [];
                    if(categoryVal & 1){
                        initData.push(1);
                    }
                    if(categoryVal & 2){
                        initData.push(2);
                    }
                    if(categoryVal & 4){
                        initData.push(4);
                    }
                    editWarnChecks = new Vue({
                        el: "#editWarnTypeCheck",
                        data: {
                            warnCheck:initData
                        },
                        methods:{
                            getWarnCheck:function(){
                                return this.warnCheck.reduce(function(acc,val){
                                    return acc + parseInt(val);
                                },0);
                            }
                        },
                        watch:{
                            warnCheck:function(newValue,oldValue){
                                if(this.warnCheck.length == 0){
                                    this.warnCheck = oldValue;
                                }
                                $('#categoryVal').val(this.getWarnCheck());
                            }
                        }
                    });
                },
                error: function (data, status, e){
                    //$(".add_cart_cover").hide();
                    alert("加载失败");
                }
            });

        })
        // 删除按钮
        $(".del_btn").on("click",function(){
            $(".del_cover").show();
            $(".cancel_btn").on("click",function(){
                $(".del_cover").hide();
            });
            var warnid = $(this).attr("warnid");
            $(".sure_btn").on("click",function(){
                $(".del_cover").hide();
                $.ajax({
                    type:"POST",
                    dataType:"json",
                    url:"${request.contextPath}/project-info/monitor/lrinfo/delete",
                    data:{"id":warnid},
                    success:function(json){
                        if(json.code == 1){
                            $("#pagination").pagination('remote');
                            //$("#tree").jstree(true).refresh(); //刷新
                            //alert(json.result);
                        }else{
                            alert(json.result);
                        }
                    },
                    error: function (data, status, e){
                        alert("删除失败");
                    }
                });
            })

            /* 	var current=$(this).parent("td").parent("tr");
                $(".del_cover").show();
                $(".cancel_btn").on("click",function(){
                    $(".del_cover").hide();
                })
                $(".sure_btn").on("click",function(){
                    $(".del_cover").hide();
                    current.remove();
                }) */
        })
    })

    var pagination = zookePagination('#pagination',{
        pageSize:10,
        remote:{
            url:"<@s.url'/project-info/monitor/lrinfo/find'/>",
            pageParams:function(data){
                return {
                    intervalId : "${intervalId!''}",
                    leftOrRight : "${leftOrRight!''}",
                    pageNum : data.pageIndex+1,
                    pageSize : data.pageSize
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
</script>
</body>
</html>
