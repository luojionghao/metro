// 风险组段pdf文件上传
function riskFileUpload(contextPath,intervalId){
    $("#risk_file_upload").data("successCallBack",function(data,status){
        alert("上传成功");
        $('.risk_frame').attr("src",data.riskPdfUrl+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90');
        $('#risk_del_btn').attr("pdfUrl",data.riskPdfUrl);
    });
    $("#risk_file_upload").data("failedCallBack",function(data, status, e){
        alert("上传失败");
    });
    $("#risk_file_upload").data("api",contextPath+"/monitor/info/risk-pdf/upload");
    $("#risk_file_upload").data("args",{'iId':intervalId});
    $("#risk_file_upload").trigger('click');
}

function risk_file_submit(elm){
    console.log(elm);
    var api = $(elm).data("api");
    var args = $(elm).data("args");
    var formData = new FormData();
    var successCallBack = $(elm).data("successCallBack");
    var failedCallBack = $(elm).data("failedCallBack");
    formData.append('file',$(elm).get(0).files[0]);
    for(var k in args){
        formData.append(k,args[k]);
    }
    $.ajax({
        url: api,
        type: 'POST',
        data: formData,
        dataType: "json",
        async: false,
        cache: false,
        contentType: false,
        processData: false,
        success: successCallBack,
        error: failedCallBack
    });
}

// 风险阻断pdf下载
function riskFileDownload(contextPath,intervalId){
    var filePath = $("#risk_del_btn").attr("pdfUrl");
    var filename = filePath.substring(filePath.lastIndexOf("/")+1);
    location.href = "/common/file-download?filename="+filename;
}

// 风险阻断pdf删除
function riskFileDelete(contextPath,intervalId){
    $.ajax({
        type: "post",
        dataType:"json",
        url: contextPath+"/monitor/info/risk-pdf/delete",
        data: {"intervalId":intervalId},
        success: function(json){
            if(json.code == 1){
                alert("删除成功");
                $('.risk_frame').attr("src","");
            }else{
                alert(json.result);
            }
        },
        error: function (data, status, e){
            alert("删除失败");
        }
    });
}
