var contextPath = "http://"+window.location.host;
var intervalId = "";
var table = null;
var newApi = "/project-info/interval/riskinfo/save";
var showApi = "/project-info/interval/riskinfo/find";
var updateApi = "/project-info/interval/riskinfo/update";
var deleteApi = "/project-info/interval/riskinfo/delete";
var fileNameRegx = /(\w*)\.(jpg|jpeg|png|pdf|gif)$/i

/**
 * 显示表格
 * tableName : 表格id名称后缀
 * columns : [{"data":"monitorDate"},
 {"data":"originFileName"},
 {"data":"createTime"},
 {"data":"operation"}]

 * columnDefs: "columnDefs":[{
          "render":function(data,type,row){
             return "<a href=\'javascript:void(0);\'"+" onclick=\'mdredelete("+row.id.toString()+")\' class=\'edit_btn\'>删除</a>"
             +"<a href=\'javascript:void(0);\'"+" onclick=\'mdredownload(\""+row.fileName+"\")"+"\' class=\'del_btn\'>下载</a>";
          },
          "targets":-1
        }]
 * requestUrl 请求url
 * intervalId 区间id
 */
function showTable(tableName,columns,columnDefs,pageSize,_contextPath,_showApi,_intervalId){
    var pagination_plug_name = '#pagination_'+tableName;
    var roleTable_plug_name = '#roleTable_'+ tableName;
    contextPath = _contextPath;
    intervalId = _intervalId;

    // 防止重复加载表格
    if ($(pagination_plug_name).pagination()){
        updateTableHeight(tableName);
        table.order([0,'desc']).draw();
        return;
    };


    var table = zookeTable(roleTable_plug_name,{
        "columns":columns,
        "columnDefs":columnDefs
    });

    var pagination = zookePagination(pagination_plug_name,{
        pageSize:pageSize,
        remote:{
            url:contextPath+_showApi,
            pageParams:function(data){
                return {
                    intervalId : intervalId,
                    pageNum : data.pageIndex+1,
                    pageSize : data.pageSize
                };
            },
            success:function(result){
                table.clear();
                table.rows.add(result.list);
                table.draw();
                updateTableHeight(tableName);
            },
            totalName:'page.totalRow'
        }
    });
}

/**
 * 自动计算并更新表格高度
 */
function updateTableHeight(tableName){
    var table_scroll_body_name = '.'+tableName + ' .dataTables_scrollBody';
    var table_scroll_boxFooter_name = '.'+tableName + '-box-footer'
    // 更新表格高度
    $(table_scroll_body_name).css('height',function(){
        var top = $(table_scroll_body_name).offset().top;
        var bottom = $(table_scroll_boxFooter_name).offset().top;
        return bottom-top;
    });

}

/**************************** helper *******************************/
// 获取文件名
function getFileName(url) {
    if(!url || url == "") return "";
    var result = url.substring(url.lastIndexOf('/')+1).split('_')[1];
    return result;
}

// 获取表格行数据
function getRowData(elm){
    var parentTR = $(elm).parents('tr');
    var data = table.row(parentTR).data();
    delete data.createTime;
    delete data.updateTime;
    return table.row(parentTR).data();
}

// ajax request
function request(url,data,modal){
    $.ajax({
        url: contextPath+url,
        type: 'POST',
        dataType: 'json',
        data: data,
        success: function(result) {
            // 提交成功
            if(result.code == "1"){
                alert('保存成功！');
                $('#pagination_dangerous').pagination('remote');
                $("#dangerous_new_form").find("input[type=text], textarea").val("");;
            }else{
                alert('网络不给力，请稍后再试!');
            }
            modal.modal('hide');
        }
    });
}

// 设置 Radio 的值
function setRadio(name,value){
    $("input[type=radio][name="+name+"]").prop("checked",false);
    $("input[type=radio][name="+name+"][value="+value+"]").prop("checked",true);
}

// 获取 Radio 的值
function getRadio(name){
    return $("input[type=radio][name="+name+"]:checked").val();
}

/*************************** call back *****************************/

// 新建风险位置信息
function dangerous_new_submit(form){
    request(newApi,$(form).serializeArray(),$('#dangerous_new_modal'));
    return false;
}

// 修改文本信息弹框
function dangerous_alter_text(elm) {
    // 初始化文本框
    var rowData = getRowData(elm);
    $('#dangerous_text').val(rowData.textMsg);
    $('#dangerous_alter_text_btn').data("rowData",rowData);
    $('#dangerous_alter_text_modal').modal('show');
}

// 提交文本信息修改
function dangerous_alter_text_submit(elm){
    var rowData = $(elm).data("rowData");
    rowData.textMsg = $('#dangerous_text').val();
    request(updateApi,{
        "id":rowData.id,
        "intervalId":rowData.intervalId,
        "textMsg":rowData.textMsg
    },$('#dangerous_alter_text_modal'));
}

// 修改图片弹框
function dangerous_alter_images(elm){
    var rowData = getRowData(elm);
    $('#dangerous_alter_image_btn').data("rowData",rowData);
    $('#dangerous_image').val(getFileName(rowData.riskImgUrl));
    $('#dangerous_alter_image_modal').modal('show');
}


// 提交图片修改
function dangerous_alter_images_submit(elm){
    var rowData = $(elm).data("rowData");
    request(updateApi,{
        "id":rowData.id,
        "intervalId":rowData.intervalId,
        "riskImgUrl":$("#dangerous_image").data("data-url")
    },$("#dangerous_alter_image_modal"));
    return false;
}

// 修改文档弹框
function dangerous_alter_docs(elm){
    var rowData = getRowData(elm);
    $('#dangerous_doc1').val(getFileName(rowData.riskPdf1Url));
    $('#dangerous_doc2').val(getFileName(rowData.riskPdf2Url));
    $('#dangerous_doc3').val(getFileName(rowData.riskPdf3Url));
    $('#dangerous_alter_docs_btn').data("rowData",rowData);
    $('#dangerous_alter_docs_modal').modal('show');
}

// 提交文档信息修改
function dangerous_alter_docs_submit(elm){
    var rowData = $(elm).data("rowData");
    request(updateApi,{
        "id":rowData.id,
        "intervalId":rowData.intervalId,
        "riskPdf1Url":$("#dangerous_doc1").data("data-url"),
        "riskPdf2Url":$("#dangerous_doc2").data("data-url"),
        "riskPdf3Url":$("#dangerous_doc3").data("data-url")
    },$("#dangerous_alter_docs_modal"));
    return false;
}

// 修改其它信息弹框
function dangerous_alter_info(elm){
    var rowData = getRowData(elm);
    $('#dangerous_alter_code').val(rowData.positionNo);
    setRadio("dangerous_lr",rowData.leftOrRight);
    setRadio("dangerous_svg",rowData.gType);
    $("#dangerous_alter_info_btn").data("rowData",rowData);
    $('#dangerous_alter_info_modal').modal('show');
}

// 提交修改其他信息
function dangerous_alter_info_submit(elm){
    var rowData = $(elm).data("rowData");
    rowData.positionNo = $("#dangerous_alter_code").val();
    rowData.leftOrRight = getRadio("dangerous_lr");
    rowData.gType = getRadio("dangerous_svg");
    request(updateApi,rowData,$('#dangerous_alter_info_modal'));
    return false;
}

// 删除位置信息弹框
function dangerous_delete_info(elm){
    var rowData = getRowData(elm);
    $('#dangerous_delete_info_p').text("确认删除"+rowData.positionNo+"位置标识信息?");
    $('#dangerous_delete_info_btn').data('rowData',rowData);
    $('#dangerous_delete_info_modal').modal('show');
    return false;
}

// 提交位置信息删除
function dangerous_delete_info_submit(elm){
    var rowData = $(elm).data("rowData");
    request(deleteApi,{"id":rowData.id},$("#dangerous_delete_info_modal"));
    return false;
}

// 触发文件选框
function trigger_upload(callBackId,callBackModal){
    $("#dangerous_file_upload").data("callBackSuccess",function(data,status){
        var fileName = getFileName(data.result);
        $(callBackId).val(fileName);
        console.log(callBackId);
        $(callBackId).data("data-url",data.result?data.result:"");
    });
    $("#dangerous_file_upload").data("callBackFailed",function(data, status, e){
        $(callBackModal).modal('hide');
    });
    $("#dangerous_file_upload").data("argStr","fileId=risk");
    $("#dangerous_file_upload").data("api",contextPath+"/project-info/interval/riskinfo/file-upload");
    $("#dangerous_file_upload").trigger('click');
}

// 文件上传
function dangerous_file_submit(elm){
    var successCallBack = $(elm).data("callBackSuccess");
    var failedCallBack = $(elm).data("callBackFailed");
    var argStr = $(elm).data("argStr");
    var api = $(elm).data("api");
    $.ajaxFileUpload({
        url:api+'?'+argStr,
        secureuri: false,
        fileElementId: 'dangerous_file_upload',
        dataType:'json',
        success: successCallBack,
        error:failedCallBack
    });
}

function trigger_clearDoc(elmId){
    $(elmId).val("");
    $(elmId).data("data-url","");
}
