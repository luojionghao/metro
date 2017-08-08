$('.shield-select').select2();
$(".date-input").datepicker({language:"zh-CN"});

// 获取日期选择或者环号选择,日期选择返回0，环号选择返回1
function getRadioSelect(){
  return $('input:radio[name="shield-option"]:checked').val();
}

// 获取起始时间
function getBeginTime(){
  return $('#beginTime').val();
}


// 获取起始环号
function getStartRing(){
  return $('#startRing').val();
}

function getShieldType(){
  return $('#shield-select').val();
}

function getLeftOrRight(){
    return $('#leftOrRight-select').val();
}

// 导出按钮点击
$('#shield-export-btn').on('click',function(){
  var type="ring";
  var beginTime = getBeginTime();
  var beginRing = getStartRing();
  if(getRadioSelect()==0){
    // 选择日期
    type="date";
  }else{
    // 选择环号
    type="ring";
  }
  var shieldType = getShieldType();
  var leftOrRight = getLeftOrRight();
  exportShieldToExcel(leftOrRight,beginRing,beginTime,type,shieldType);
});
