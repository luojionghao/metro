// create Table with config
function zookeTable(id,config){
    var def = {
        "paging":false, // 默认关闭表格自带的分页（这个分页不好用）
        //"sScrollY":1,   // 默认滚动高度为1，下面通过监听window窗口重新设置表格高度
        "scrollY":'auto',
        "scrollCollapse":true,
        "lengthChange":false, 
        "searching":false,    // 关闭表格搜索功能,如有必要可以开启
        "ordering":true,      // 排序,采用默认规则
        "info":false,         // 不显示表格数量分页信息
        "autoWidth":true, // 自动宽度
        "oLanguage": {
        	"sEmptyTable":" "
        },
        "headerCallback":function(thead){
        	$(thead).css({"overflow":"visible"});
        }
    };
    var table = $(id).DataTable($.extend({},def,config));
    $(window).on("resize",function(){
    	table.columns.adjust().draw();
    })
    return table;
}

function zookePagination(id,config){
    var def = {
        pageBtnCount:9,
        showFirstLastBtn:true,
        firstBtnText:"首页",
        lastBtnText:"末页",
        loadFirstPage:true,
        showInfo:true,
        infoFormat:"显示{start}-{end}条，共计{total}条",
        noInfoText:"没有查找到合适的数据",
        showJump:true,
        jumpBtnText:"跳转",
        showPageSize:true,
        debug:true,
        remote:{
            pageParams:function(data){
                return {
                    pageIndex: data.pageIndex,
                    pageSize:data.pageSize
                };
            }
        },
        totalName:'total'
    };
    var pagination = $(id).pagination($.extend({},def,config));
    // 右对齐分页信息显示
    $(".m-pagination-info").addClass('pull-right');
    // 重新计算表格高度

    return pagination;
}


// 使table的高度跟随窗口变化，永远保持在分页上方，必要时滚动
function resizeTable(){
    $('.dataTables_scrollBody').css('height',function(){
        var top = $('.dataTables_scrollBody').offset().top;
        var bottom = $('.box-footer').offset().top;
        return bottom-top;
    });
}


