// 显示盾尾间隙新建弹框
function new_shield_tail(){
  $("#new_shield_tail").show();
}

// 关闭盾尾间隙新建弹框
function shield_tail_dismiss(){
  $("#new_shield_tail").hide();
  $("#alter_shield_tail").hide();
}

// 显示修改盾尾间隙弹框
function alter_shield_tail(elm) {
  var table = $('#roleTable_shield_tail').DataTable();
  var parentTR = $(elm).parents('tr');
  var data = table.row(parentTR).data();
  $('#intervalStId').val(data.id);
  $('#alter_shield_tail_ringNum').val(data.ringNum);
  $('#alter_shield_tail_stUp').val(data.stUp);
  $('#alter_shield_tail_stDown').val(data.stDown);
  $('#alter_shield_tail_stLeft').val(data.stLeft);
  $('#alter_shield_tail_stRight').val(data.stRight);
  $('#alter_shield_tail_leftRight').val(data.leftOrRight);
  $('#alter_shield_tail').show();
}

// 显示删除盾尾间隙弹框
function del_shield_tail(elm){
  var table = $('#roleTable_shield_tail').DataTable();
  var parentTR = $(elm).parents('tr');
  var data = table.row(parentTR).data();
  $("#del_shield_tail_btn").data("rowId",data.id);
  $("#del_shield_tail_modal").modal("show")
}

// 删除盾尾间隙数据
function del_shield_tail_save(contextPath,elm) {
  var rowId = $(elm).data("rowId");
  $.ajax({
      type: "post",
      dataType:"json",
      url: contextPath+"/project-info/interval/stinfo/delete",
      data: {"id":rowId},
      success: function(json){
          $("#del_shield_tail_modal").modal("hide")
          $("#pagination_shield_tail").pagination('remote');
          alert("删除成功");
      },
      error: function (data, status, e){
          $("#del_shield_tail_modal").modal("hide")
          alert("删除出错");
      }
  });
}

// 保存盾尾间隙新建数据
function shield_tail_new_save(contextPath){
  var url = contextPath+"/project-info/interval/stinfo/save";
    post_shield_tail_form_data(url,"#new_shield_tail_form","#new_shield_tail");
}

function shield_tail_alter_save(contextPath){
  var url = contextPath + "/project-info/interval/stinfo/update";
    post_shield_tail_form_data(url,"#alter_shield_tail_form","#alter_shield_tail");
}

// 盾尾间隙excel数据上传
function shield_tail_file_upload(contextPath,intervalId){
    $("#risk_file_upload").data("successCallBack",function(data,status){
        alert("上传成功");
        $('#pagination_shield_tail').pagination('remote');

    });
    $("#risk_file_upload").data("failedCallBack",function(data, status, e){
        alert("上传失败");
    });
    $("#risk_file_upload").data("api",contextPath+"/project-info/interval/stinfo/import");
    $("#risk_file_upload").data("args",{'intervalId':intervalId});
    $("#risk_file_upload").trigger('click');
}

// 导出盾尾间隙Excel
function shield_tail_file_export(contextPath,intervalId){
    $.ajax({
        type: "post",
        dataType:"json",
        url: contextPath+"/project-info/interval/stinfo/export",
        data: {"intervalId":intervalId},
        success: function(json){
            if(json.code == 1){
                var filename = json.result;
                location.href = contextPath+"/common/file-download?filename="+filename;
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

// ajax上传form表单
function post_shield_tail_form_data(url,formId,modalId){
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: $(formId).serialize(),
        success: function(result) {
            // 提交成功
            if(result.code == "1"){
                $("#pagination_shield_tail").pagination('remote');
                $("#new_shield_tail").find("input[type=text], textarea").val("");
                alert('保存成功！');
            }else{
                alert('网络不给力，请稍后再试!');
            }
            $(modalId).hide();
        },
        error:function(){
          $(modalId).hide();
        }
    });
}
