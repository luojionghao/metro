// 显示管片姿态新建弹框
function new_shield_profile(){
  $("#new_shield_profile").show();
}

// 关闭管片姿态新建弹框
function shield_profile_dismiss(){
  $("#new_shield_profile").hide();
  $("#alter_shield_profile").hide();
}

// 显示修改管片姿态弹框
function alter_shield_profile(elm) {
  var table = $('#roleTable_shield_profile').DataTable();
  var parentTR = $(elm).parents('tr');
  var data = table.row(parentTR).data();
  $('#intervalSaId').val(data.id);
  $('#alter_shield_profile_ringNum').val(data.ringNum);
  $('#alter_shield_profile_horizontalDev').val(data.horizontalDev);
  $('#alter_shield_profile_verticalDev').val(data.verticalDev);
  $('#alter_shield_profile_leftRight').val(data.leftOrRight);
  $('#alter_shield_profile').show();
}

// 显示删除管片姿态弹框
function del_shield_profile(elm){
  var table = $('#roleTable_shield_profile').DataTable();
  var parentTR = $(elm).parents('tr');
  var data = table.row(parentTR).data();
  $("#del_shield_profile_btn").data("rowId",data.id);
  $("#del_shield_profile_modal").modal("show")
}

// 删除管片姿态数据
function del_shield_profile_save(contextPath,elm) {
  var rowId = $(elm).data("rowId");
  $.ajax({
      type: "post",
      dataType:"json",
      url: contextPath+"/project-info/interval/sainfo/delete",
      data: {"id":rowId},
      success: function(json){
          $("#del_shield_profile_modal").modal("hide")
          alert("删除成功");
          $('#pagination_shield_profile').pagination('remote');
      },
      error: function (data, status, e){
          $("#del_shield_profile_modal").modal("hide")
          alert("删除出错");
      }
  });
}

// 保存管片姿态新建数据
function shield_profile_new_save(contextPath){
  var url = contextPath+"/project-info/interval/sainfo/save";
    post_profile_form_data(url,"#new_shield_profile_form","#new_shield_profile");
}

// 保存管片姿态修改数据
function shield_profile_alter_save(contextPath){
  var url = contextPath + "/project-info/interval/sainfo/update";
    post_profile_form_data(url,"#alter_shield_profile_form","#alter_shield_profile");
}

// 管片姿态excel数据上传
function shield_profile_file_upload(contextPath,intervalId){
    $("#risk_file_upload").data("successCallBack",function(data,status){
        alert("上传成功");
        $('#pagination_shield_profile').pagination('remote');
    });
    $("#risk_file_upload").data("failedCallBack",function(data, status, e){
        alert("上传失败");
    });
    $("#risk_file_upload").data("api",contextPath+"/project-info/interval/sainfo/import");
    $("#risk_file_upload").data("args",{'intervalId':intervalId});
    $("#risk_file_upload").trigger('click');
}

// 导出管片姿态Excel
function shield_profile_file_export(contextPath,intervalId){
    $.ajax({
        type: "post",
        dataType:"json",
        url: contextPath+"/project-info/interval/sainfo/export",
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
function post_profile_form_data(url,formId,modalId){
    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        data: $(formId).serialize(),
        success: function(result) {
            // 提交成功
            if(result.code == "1"){
                alert('保存成功！');
                $('#pagination_shield_profile').pagination('remote');
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
