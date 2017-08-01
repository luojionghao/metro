<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>区间数据管理</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <!-- 图标1 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/select2/dist/css/select2.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datepicker/datepicker3.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/item_section.css?v=6'/>">
    <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
    <script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.zh-CN.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/ajaxfileupload.js'/>"></script>
    <script src="<@s.url'/plugins/js/table.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
<input type="hidden" id="desc" name="desc" value="${(desc)!""}">
<div class="nav-tabs-custom">
    <ul class="nav nav-tabs">
        <li class="active">
            <a id="tab1" href="#tab_1" data-toggle="tab" aria-expanded="true">工程进度维护</a>
        </li>
        <li class="">
            <a id="tab2" href="#tab_2" data-toggle="tab" aria-expanded="false">沉降点管理</a>
        </li>
        <li class="">
            <a id="tab3" href="#tab_3" data-toggle="tab" aria-expanded="false">沉降点监测数据管理</a>
        </li>
        <li class="">
            <a id="tab4" href="#tab_4" data-toggle="tab" aria-expanded="false">风险位置管理</a>
        </li>
        <li class="">
            <a href="#tab_5" class="session-tab" data-toggle="tab" aria-expanded="false">风险分析组段划分</a>
        </li>
        <li class="">
            <a href="#tab_6" class="session-tab" data-toggle="tab" aria-expanded="false">盾构数据管理</a>
        </li>
        <li class="">
            <a href="#tab_7" class="session-tab" data-toggle="tab" aria-expanded="false">盾尾间隙数据管理</a>
        </li>
        <li class="">
            <a href="#tab_8" class="session-tab" data-toggle="tab" aria-expanded="false">管片姿态数据管理</a>
        </li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="tab_1">
            <form id="intervalform1" name="intervalform1" method="post" action="" onsubmit="return false;">
                <input type="hidden" name="intervalId" value="${intervalId!''}"/>
                <input type="hidden" name="id" value="${(pp.id?c)!''}"/>
                <div class="progress_content">
                    <ul class="progress_list">
                        <li class="clearfix">
                            <span>平面图：</span>
                            <a href="javascript:void(0);" class="upload_btn" onclick="openUploadBox('ppsvgBox');">
                                上传
                            </a>
                            <input id="ppSvgUrl" name="ppSvgUrl" type="hidden"/>
                            &nbsp;当前文件：
                            <label id="currentPpSvg" style="font-weight:normal;">
                            ${(pp.ppSvgUrl?substring(pp.ppSvgUrl?last_index_of('/')+1)?split('_')[1])!''}
                            </label>
                        </li>
                        <li class="clearfix">
                            <span>剖面图：</span>
                            <a href="javascript:void(0);" class="upload_btn" onclick="openUploadBox('sectionsvgBox');">
                                上传
                            </a>
                            <input id="sectionSvgUrl" name="sectionSvgUrl" type="hidden"/>
                            &nbsp;当前文件：
                            <label id="currentSectionSvg" style="font-weight:normal;">
                            ${(pp.sectionSvgUrl?substring(pp.sectionSvgUrl?last_index_of('/')+1)?split('_')[1])!''}
                            </label>
                        </li>
                    </ul>
                    <div class="save_btn_block clearfix">
                        <a href="javascript:ppSaveOrUpdate(document.intervalform1);" class="save_btn">保存</a>
                    </div>
                </div>
            </form>
        </div>
        <div class="tab-pane" id="tab_2">
            <div class="dict_content">
                <div class="box">
                    <div class="box-header">
                        <ol class="title_list clearfix">
                            <li><button class="new_btn">新增</button></li>
                            <li><button type="button" class="head_btn" onclick="downloadTemplateFile('沉降点数据模板.xls');">下载模板文件</button></li>
                            <li><button type="button" class="head_btn" onclick="exportExcel();">导出</button></li>
                            <li><button type="button" class="head_btn" onclick="javascript:openUploadBox1();">上传</button></li>
                        <ol/>
                    </div>
                    <div class="box-body">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 表格模块 -->
                                <table id="roleTable1" class="table table-bordered table-hover dataTable">
                                    <thead>
                                    <tr>
                                        <th>沉降点名称</th>
                                        <th>线路</th>
                                        <th>初始里程</th>
                                        <th>大地坐标X</th>
                                        <th>大地坐标Y</th>
                                        <th>累计沉降正值</th>
                                        <th>累计沉降负值</th>
                                        <th>沉降速率预警值</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                                <!-- table end -->
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 分页模块 -->
                                <div id="pagination1"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab_3">
            <div class="dict_content">
                <div class="box">
                    <div class="box-header">
                        <ol class="title_list clearfix">
                            <li>
                                <button class="head_btn" type="button" style="margin-left:0px;" onclick="openUploadBox('mdreBox');">上传</button>
                            </li>
                            <li>
                                <button class="head_btn" onclick="downloadTemplateFile('沉降检测表-广州.xls');">下载模板文件</button>
                            </li>
                        </ol>
                    </div>
                    <div class="box-body">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 表格模块 -->
                                <table id="roleTable2" class="table table-bordered table-hover dataTable">
                                    <thead>
                                    <tr>
                                        <th>监测日期</th>
                                        <th>文件名</th>
                                        <th>上传时间</th>
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
                                <div id="pagination2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab_4">
            <div class="dict_content">
                <div class="box">
                    <div class="box-header">
                        <ol class="title_list clearfix">
                            <li>
                                <button class="head_btn" data-toggle="modal" type="button" style="margin-left:0px;" data-target="#dangerous_new_modal">新增</button>
                            </li>
                        </ol>
                    </div>
                    <div class="box-body">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 表格模块 -->
                                <table id="roleTable_dangerous" class="table table-bordered table-hover dataTable">
                                    <thead>
                                    <tr>
                                        <th>位置标识码</th>
                                        <th>左/右线</th>
                                        <th>工程图</th>
                                        <th>文本信息</th>
                                        <th>图片</th>
                                        <th>文档</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                                <!-- table end -->
                            </div>
                        </div>
                    </div>
                    <div class="dangerous-box-footer">
                        <div class="row">
                            <div class="col-sm-12">
                                <!-- 分页模块 -->
                                <div id="pagination_dangerous"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane risk-pane" id="tab_5">
                      <div class="risk_btn_bar">
                          <a class="risk_btn" href="javascript:riskFileUpload('${request.contextPath}','${intervalId!''}')">上传</a>
                          <a id="risk_del_btn" class="risk_btn" pdfUrl="${riskPdfUrl!''}" href="javascript:riskFileDownload('${request.contextPath}','${intervalId!''}');">下载</a>                          <a class="risk_btn" href="javascript:riskFileDelete('${request.contextPath}','${intervalId!''}');">删除</a>
                      </div>
                      <div class="risk_frame_area">
                          <iframe class="risk_frame" scrolling="auto" src="${riskPdfUrl!''}" frameborder="0" width="100%">                        </iframe>
                      </div>
                    </div>
        <div class="tab-pane shield-data-pane" id="tab_6">
            <div class="shield-data-row">
                <label class="shield-label">
                    <input type="radio" name="shield-option" value="0" checked>
                    选择日期:
                </label>
                <i class="fa fa-calendar warn-date-icon"></i>
                <input type="text" value="${(beginTime)!'7/16/2016'}" class="date-input" id="beginTime">
            </div>
            <div class="shield-data-row">
                <label class="shield-label">
                    <input type="radio" name="shield-option" value="1">
                    选择环号:
                </label>
                <input type="number" value="0" class="" id="startRing">
            </div>
            <div class="shield-data-row input-group">
                <label class="shield-label" for="leftOrRight-select">选择左右线：</label>
                <select id="leftOrRight-select" class="form-control" name="">
                        <option value="l">左线</option>
                        <option value="r">右线</option>
                </select>
            </div>
            <div class="shield-data-row input-group">
                <label class="shield-label" for="shield-select">选择盾构数据名：</label>
                <select id="shield-select" class="form-control" name="">
                <#if dics ?? && dics?size gt 0>
                    <#list dics as dic>
                        <option value="${dic.dicName!''}">${dic.dicMean!''}</option>
                    </#list>
                </#if>
                </select>
            </div>
            <div class="shield-export">
                <div class="col-xs-2 pull-right">
                    <button id="shield-export-btn" class="btn btn-block btn-primary" type="button">导出</button>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab_7">
            <div class="dict_content">
                <div class="box">
                    <div class="box-header">
                        <ol class="title_list clearfix">
                            <li><button type="button" class="head_btn" onclick="new_shield_tail();">新增</button></li>
                            <li><button type="button" class="head_btn" onclick="downloadTemplateFile('盾尾间隙数据模板.xls');">下载模板文件</button></li>
                            <li><button type="button" class="head_btn" onclick="shield_tail_file_export('${request.contextPath}',${intervalId!''});">导出</button></li>
                            <li><button type="button" class="head_btn" onclick="shield_tail_file_upload('${request.contextPath}',${intervalId!''});">上传</button></li>
                            <ol/>
                    </div>
                    <div class="box-body">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 表格模块 -->
                                <table id="roleTable_shield_tail" class="table table-bordered table-hover dataTable">
                                    <thead>
                                    <tr>
                                        <th>环号</th>
                                        <th>线路</th>
                                        <th>日期</th>
                                        <th>盾尾间隙上</th>
                                        <th>盾尾间隙下</th>
                                        <th>盾尾间隙左</th>
                                        <th>盾尾间隙右</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                                <!-- table end -->
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 分页模块 -->
                                <div id="pagination_shield_tail"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="tab_8">
            <div class="dict_content">
                <div class="box">
                    <div class="box-header">
                        <ol class="title_list clearfix">
                            <li><button type="button" class="head_btn" onclick="new_shield_profile();">新增</button></li>
                            <li><button type="button" class="head_btn" onclick="downloadTemplateFile('管片姿态数据模板.xls');">下载模板文件</button></li>
                            <li><button type="button" class="head_btn" onclick="shield_profile_file_export('${request.contextPath}',${intervalId!''});">导出</button></li>
                            <li><button type="button" class="head_btn" onclick="shield_profile_file_upload('${request.contextPath}',${intervalId!''});">上传</button></li>
                            <ol/>
                    </div>
                    <div class="box-body">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 表格模块 -->
                                <table id="roleTable_shield_profile" class="table table-bordered table-hover dataTable">
                                    <thead>
                                    <tr>
                                        <th>环号</th>
                                        <th>线路</th>
                                        <th>日期</th>
                                        <th>水平偏差</th>
                                        <th>垂直偏差</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                </table>
                                <!-- table end -->
                            </div>
                        </div>
                    </div>
                    <div class="box-footer">
                        <div class="row no-pad">
                            <div class="col-sm-12">
                                <!-- 分页模块 -->
                                <div id="pagination_shield_profile"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 上传剖面图文件弹框 -->
<div id="sectionsvgBox" class="cover new_cover">
    <div class="modal-content middle">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"  onclick="cancelUploadBox('sectionsvgBox');">
                <span aria-hidden="true">×</span>
            </button>
            <span class="modal-title">上传文件</span>
        </div>
        <div class="modal-body">
            <label class="btn btn-default btn-file">
                选择文件
                <input type="file" id="sectionsvg" class="upload_file" name="sectionsvg" style="display:none;"/>
            </label>
            <span id="sectionsvgUpName"></span>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="cancelUploadBox('sectionsvgBox');">
                取消
            </button>
            <button type="button" class="btn btn-primary save_btn2" onclick="ajaxFileUpload('sectionsvg');">
                上传
            </button>
        </div>
    </div>
</div>

<!-- 上传平面文件弹框 -->
<div id="ppsvgBox" class="cover new_cover">
    <div class="modal-content middle">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"  onclick="cancelUploadBox('ppsvgBox');">
                <span aria-hidden="true">×</span>
            </button>
            <span class="modal-title">上传文件</span>
        </div>
        <div class="modal-body">
            <label class="btn btn-default btn-file">
                选择文件
                <input type="file" id="ppsvg" class="upload_file" name="ppsvg" value="tt" style="display:none;"/>
            </label>
            <span id="ppsvgUpName"></span>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left"
                    data-dismiss="modal" onclick="cancelUploadBox('ppsvgBox');">取消</button>
            <button type="button" class="btn btn-primary save_btn2"
                    onclick="ajaxFileUpload('ppsvg');">上传</button>
        </div>
    </div>
</div>

<!-- 修改弹框 -->
<form id="spupdateform" name="spupdateform" method="post" action="" onsubmit="return false;">
    <div class="cover change_cover">
    </div>
</form>

<!-- 新增沉降点弹框 -->
<div id="settelmentBox" class="cover new_cover">
    <form id="spform" name="spform" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="intervalId" value="${intervalId!''}">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="spclose();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">沉降点信息</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span>沉降点名称：</span>
                        <input type="text" name="spName" class="settling_name">
                    </li>
                    <li>
                        <span>初始里程：</span>
                        <input type="text" name="originMileage">
                    </li>
                    <li>
                        <span>大地坐标X：</span>
                        <input type="text" name="mapX" class="x_axis">
                    </li>
                    <li>
                        <span>大地坐标Y：</span>
                        <input type="text" name="mapY" class="y_axis">
                    </li>
                    <li>
                        <span>累计沉降正值：</span>
                        <input type="text" name="spSumAdd" class="upright">
                    </li>
                    <li>
                        <span>累计沉降负值：</span>
                        <input type="text" name="spSumSub" class="negative">
                    </li>
                    <li>
                        <span>沉降速率预警值：</span>
                        <input type="text" name="spSpeedWarningVal" class="wran_value">
                    </li>
                    <li>
                        <span>线路：</span>
                        <select class="state_select" name="leftOrRight">
                            <option value="l">左线</option>
                            <option value="r">右线</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="spcancel();">取消</button>
                <button type="button" class="btn btn-primary save_btn2" onclick="spsave();">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- 沉降点删除弹窗 -->
<div class="cover del_cover" id="spcover">
    <div class="pop middle">
        <div class="pop_title">您是否确定删除该沉降点？</div>
        <div class="pop_btn clearfix">
            <a href="javascript:void(0);" class="cancel_btn" id="spcancel">取消</a>
            <a href="javascript:void(0);" class="sure_btn" id="spsure">确定</a>
        </div>
    </div>
</div>

<!-- 沉降点监测数据记录删除弹窗 -->
<div class="cover del_cover" id="mdrecover">
    <div class="pop middle">
        <div class="pop_title">您是否确定删除该记录？</div>
        <div class="pop_btn clearfix">
            <a href="javascript:void(0);" class="cancel_btn" id="mdrecancel">取消</a>
            <a href="javascript:void(0);" class="sure_btn" id="mdresure">确定</a>
        </div>
    </div>
</div>

<!-- 上传沉降点监测文件弹框 -->
<div id="mdreBox" class="cover new_cover">
    <div class="modal-content middle">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close"  onclick="cancelUploadBox('mdreBox');">
                <span aria-hidden="true">×</span>
            </button>
            <span class="modal-title">上传文件</span>
        </div>
        <div class="modal-body">
            <label class="btn btn-default btn-file">
                选择文件
                <input type="file" class="upload_file" name="mdreFile" id="mdreFile" accept="application/vnd.ms-excel" style="display:none;"/>
            </label>
            <span id="mdreFileName"></span>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left"
                    data-dismiss="modal" onclick="cancelUploadBox('mdreBox');">取消</button>
            <button type="button" class="btn btn-primary save_btn2"
                    onclick="mdreUpload('mdreFile');">上传</button>
        </div>
    </div>
</div>

<!-- 风险阻断新建弹框 -->
<div class="modal fade" id="dangerous_new_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form id="dangerous_new_form" onsubmit='return dangerous_new_submit(this)'>
                <input type="hidden" name="intervalId" value="${intervalId!''}">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">新增</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="dangerous_code">位置标识码</label>
                        <input id="dangerous_code" name="positionNo" class="form-control" type="text" placeholder="填写位置标识码">
                    </div>
                    <div class="form-group">
                        <label>左/右线</label>
                        <div class="radio">
                            <label>
                                <input type="radio" name="leftOrRight" value="l" checked>
                                左线
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="leftOrRight" value="r" >
                                右线
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>工程图</label>
                        <div class="radio">
                            <label>
                                <input type="radio" name="gType" value="1" checked>
                                平面
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input type="radio" name="gType" value="2" >
                                剖面
                            </label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                    <button type="submit" class="btn btn-primary">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- 风险阻断修改弹框-->
<div class="modal fade" id="dangerous_alter_info_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="dangerous_code">位置标识码</label>
                    <input id="dangerous_alter_code" name="dangerous_code" class="form-control" type="text" placeholder="填写位置标识码">
                </div>
                <div class="form-group">
                    <label>左/右线</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="dangerous_lr" value="l" checked>
                            左线
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="dangerous_lr" value="r" >
                            右线
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label>工程图</label>
                    <div class="radio">
                        <label>
                            <input type="radio" name="dangerous_svg" value="1" checked>
                            平面
                        </label>
                    </div>
                    <div class="radio">
                        <label>
                            <input type="radio" name="dangerous_svg" value="2" >
                            剖面
                        </label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="dangerous_alter_info_btn" onclick="javascript:return dangerous_alter_info_submit(this)" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 风险阻断修改文本弹框-->
<div class="modal fade" id="dangerous_alter_text_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">修改</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="dangerous_code">文本信息</label>
                    <textarea class="form-control" rows="5" id="dangerous_text" name="dangerous_text" placeholder=""></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="dangerous_alter_text_btn" onclick="javascript:return dangerous_alter_text_submit(this)" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 风险阻断修改图片弹框-->
<div class="modal fade" id="dangerous_alter_image_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <a onclick="javascript:return trigger_upload('#dangerous_image')">上传图片</a>
                    <input id="dangerous_image" class="form-control" disabled="" name="dangerous_image" type="text">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="dangerous_alter_image_btn" onclick="javascript:return dangerous_alter_images_submit(this)" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 风险阻断修改文档弹框-->
<div class="modal fade" id="dangerous_alter_docs_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label for="dangerous_doc1">文档1</label>
                    <a onclick="trigger_upload('#dangerous_doc1')">上传</a>
                    <a onclick="trigger_clearDoc('#dangerous_doc1')">删除</a>
                    <input id="dangerous_doc1" name="dangerous_doc1" class="form-control" type="text">
                </div>
                <div class="form-group">
                    <label for="dangerous_doc2">文档2</label>
                    <a onclick="trigger_upload('#dangerous_doc2')">上传</a>
                    <a onclick="trigger_clearDoc('#dangerous_doc2')">删除</a>
                    <input id="dangerous_doc2" name="dangerous_doc2" class="form-control" type="text">
                </div>
                <div class="form-group">
                    <label for="dangerous_doc3">文档3</label>
                    <a onclick="trigger_upload('#dangerous_doc3')">上传</a>
                    <a onclick="trigger_clearDoc('#dangerous_doc3')">删除</a>
                    <input id="dangerous_doc3" name="dangerous_doc3" class="form-control" type="text">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="dangerous_alter_docs_btn" onclick="javascript:return dangerous_alter_docs_submit(this)" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<!-- 风险阻断删除确认弹框-->
<div class="modal fade" id="dangerous_delete_info_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p id="dangerous_delete_info_p">确认删除此位置标识信息?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="dangerous_delete_info_btn" onclick="javascript:return dangerous_delete_info_submit(this)" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- 新增盾尾间隙数据弹框 -->
<div id="new_shield_tail" class="cover new_cover">
    <form id="new_shield_tail_form" name="spform" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="intervalId" value="${intervalId!''}">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="shield_tail_dismiss();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">盾尾间隙数据</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span>环号：</span>
                        <input type="text" name="ringNum" class="settling_name">
                    </li>
                    <li>
                        <span>盾尾间隙上：</span>
                        <input type="text" name="stUp" class="x_axis">
                    </li>
                    <li>
                        <span>盾尾间隙下：</span>
                        <input type="text" name="stDown" class="y_axis">
                    </li>
                    <li>
                        <span>盾尾间隙左：</span>
                        <input type="text" name="stLeft" class="upright">
                    </li>
                    <li>
                        <span>盾尾间隙右：</span>
                        <input type="text" name="stRight" class="negative">
                    </li>
                    <li>
                        <span>线路：</span>
                        <select class="form-control shieldSelect" name="leftOrRight">
                                <option value="l">左线</option>
                                <option value="r">右线</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="shield_tail_dismiss()">取消</button>
                <button type="button" class="btn btn-primary save_btn2" onclick="shield_tail_new_save('${request.contextPath}')">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- 修改盾尾间隙数据弹框 -->
<div id="alter_shield_tail" class="cover new_cover">
    <form id="alter_shield_tail_form" name="spform" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="intervalId" value="${intervalId!''}">
        <input type="hidden" name="id" id="intervalStId">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="shield_tail_dismiss();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">盾尾间隙数据</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span>环号：</span>
                        <input id="alter_shield_tail_ringNum" type="text" name="ringNum" class="settling_name">
                    </li>
                    <li>
                        <span>盾尾间隙上：</span>
                        <input id="alter_shield_tail_stUp" type="text" name="stUp" class="x_axis">
                    </li>
                    <li>
                        <span>盾尾间隙下：</span>
                        <input id="alter_shield_tail_stDown" type="text" name="stDown" class="y_axis">
                    </li>
                    <li>
                        <span>盾尾间隙左：</span>
                        <input id="alter_shield_tail_stLeft" type="text" name="stLeft" class="upright">
                    </li>
                    <li>
                        <span>盾尾间隙右：</span>
                        <input id="alter_shield_tail_stRight" type="text" name="stRight" class="negative">
                    </li>
                    <li>
                        <span>线路：</span>
                        <select id="alter_shield_tail_leftRight" class="form-control shieldSelect" name="leftOrRight">
                                <option value="l">左线</option>
                                <option value="r">右线</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="shield_tail_dismiss()">取消</button>
                <button type="button" class="btn btn-primary save_btn2" onclick="shield_tail_alter_save('${request.contextPath}')">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- 删除盾尾间隙数据弹框 -->
<div class="modal fade" id="del_shield_tail_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p id="dangerous_delete_info_p">确认删除此盾尾间隙数据?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="del_shield_tail_btn" onclick="javascript:return del_shield_tail_save('${request.contextPath}',this)" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- 新增管片姿态数据弹框 -->
<div id="new_shield_profile" class="cover new_cover">
    <form id="new_shield_profile_form" name="spform" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="intervalId" value="${intervalId!''}">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="shield_profile_dismiss();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">管片姿态数据</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span>环号：</span>
                        <input type="text" name="ringNum" class="settling_name">
                    </li>
                    <li>
                        <span>水平偏差：</span>
                        <input type="text" name="horizontalDev" class="x_axis">
                    </li>
                    <li>
                        <span>垂直偏差：</span>
                        <input type="text" name="verticalDev" class="y_axis">
                    </li>
                    <li>
                        <span>线路：</span>
                        <select class="form-control shieldSelect" name="leftOrRight">
                                <option value="l">左线</option>
                                <option value="r">右线</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="shield_profile_dismiss()">取消</button>
                <button type="button" class="btn btn-primary save_btn2" onclick="shield_profile_new_save('${request.contextPath}')">保存</button>
            </div>
        </div>
    </form>
</div>

<!-- 修改管片姿态数据弹框 -->
<div id="alter_shield_profile" class="cover new_cover">
    <form id="alter_shield_profile_form" name="spform" method="post" action="" onsubmit="return false;">
        <input type="hidden" name="intervalId" value="${intervalId!''}">
        <input type="hidden" name="id" id="intervalSaId">
        <div class="modal-content middle">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="shield_profile_dismiss();">
                    <span aria-hidden="true">×</span>
                </button>
                <span class="modal-title">管片姿态数据</span>
            </div>
            <div class="modal-body">
                <ul class="warn_info_list">
                    <li>
                        <span>环号：</span>
                        <input id="alter_shield_profile_ringNum" type="text" name="ringNum" class="settling_name">
                    </li>
                    <li>
                        <span>水平偏差：</span>
                        <input id="alter_shield_profile_horizontalDev" type="text" name="horizontalDev" class="x_axis">
                    </li>
                    <li>
                        <span>垂直偏差：</span>
                        <input id="alter_shield_profile_verticalDev" type="text" name="verticalDev" class="y_axis">
                    </li>
                    <li>
                        <span>线路：</span>
                        <select id="alter_shield_profile_leftRight" class="form-control shieldSelect" name="leftOrRight">
                                <option value="l">左线</option>
                                <option value="r">右线</option>
                        </select>
                    </li>
                </ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="shield_profile_dismiss()">取消</button>
                <button type="button" class="btn btn-primary save_btn2" onclick="shield_profile_alter_save('${request.contextPath}')">保存</button>
            </div>
        </div>
    </form>
</div>

<div class="modal fade" id="del_shield_profile_modal" tabindex="-1" role="dialog" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p id="dangerous_delete_info_p">确认删除此管片姿态数据?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
                <button id="del_shield_profile_btn" onclick="javascript:return del_shield_profile_save('${request.contextPath}',this)" class="btn btn-primary">确认</button>
            </div>
        </div>
    </div>
</div>

<!-- 上传文件弹框 -->
<div id="uploadBox" class="cover new_cover">
    <div class="modal-content middle">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close"  onclick="cancelUploadBox1();">
                <span aria-hidden="true">×</span>
            </button>
            <span class="modal-title">上传文件</span>
        </div>
        <div class="modal-body">
            <form id="form1" name="form1" action="<@s.url '/project-info/interval/spinfo/import'/>" method="post" enctype="multipart/form-data" >
                <label class="btn btn-default btn-file">
                    选择文件<input id="excel" class="input_file" type="file" name="file" accept="application/vnd.ms-excel" style="display:none;"/>
                </label>
                <span id="fileUpName"></span>
                <input type="hidden" name="intervalId" value="${intervalId!''}"/>
            </form>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left"
                    data-dismiss="modal" onclick="cancelUploadBox1();">取消</button>
            <button type="button" class="btn btn-primary save_btn2"
                    onclick="fsubmit(document.form1);">上传</button>
        </div>
    </div>
</div>

<input style="width:0px;height:0px;overflow:hidden;" type="file" id="dangerous_file_upload" onchange="dangerous_file_submit(this)" name="risk" />
<input style="width:0px;height:0px;overflow:hidden;" type="file" id="risk_file_upload" onchange="risk_file_submit(this)" name="risk" />

<script src="<@s.url'/plugins/bower/jquery-bootstrap-scrolling-tabs/jquery.scrolling-tabs.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/select2/dist/js/select2.min.js'/>"></script>
<script src="<@s.url'/plugins/js/item_section_right_dangerous.js'/>"></script>
<script src="<@s.url'/plugins/js/item_section_right_risk.js'/>"></script>
<script src="<@s.url'/plugins/js/item_section_right_shield_data.js'/>"></script>
<script src="<@s.url'/plugins/js/item_section_right_shield_tail.js'/>"></script>
<script src="<@s.url'/plugins/js/item_section_right_shield_profile.js'/>"></script>
<script type="text/javascript">
    $('.nav-tabs').scrollingTabs();
    /******************************** 工程进度维护 *******************************/
    //剖面图保存或更新
    function ppSaveOrUpdate(thisform){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/interval/ppinfo/save-or-update",
            data:$(thisform).serialize(),
            success:function(json){
                if(json.code == 1){
                    alert("保存成功");
                }else{
                    alert(json.result);
                }
            },
            error: function (data, status, e){
                alert("保存失败");
            }
        });
    }

    /************************* 沉降点监测数据管理 ******************************/
    // 下载模板文件
    function downloadTemplateFile(filename){
        location.href = "${request.contextPath}/common/file-download?filename="+filename;
    }

    // 下载上传的沉降点监测数据文件
    function mdredownload(filename){
        location.href = "${request.contextPath}/common/file-download?filename="+filename;
    }

    // 打开沉降点监测文件上传框
    function openUploadBox(boxId){
        // 选择文件后，显示文件路径
        console.log(boxId);
        registerUpfileName(["sectionsvg","ppsvg","mdreFile"],["sectionsvgUpName","ppsvgUpName","mdreFileName"]);

        $('#'+boxId).show();
    }

    // 取消沉降点监测文件上传框
    function cancelUploadBox(boxId){
        $('#'+boxId).hide();
    }

    //上传沉降点监测记录文件
    function mdreUpload(fileId){
        if ($("#"+fileId).val().length > 0) {
            $.ajaxFileUpload({
                url:"${request.contextPath}/project-info/interval/mdre-upload?fileId="+fileId+"&intervalId="+"${intervalId!''}", //用于文件上传的服务器端请求地址
                secureuri:false, //是否需要安全协议，一般设置为false
                fileElementId:fileId, //文件上传域的ID
                dataType:"json", //返回值类型 一般设置为json
                //data:{"fileId":"logo"},
                success:function(data,status){ //服务器成功响应处理函数
                    if(data.code == 1){ //上传成功
                        $("#pagination2").pagination('remote');
                        cancelUploadBox('mdreBox');
                    }else{
                        alert(data.result);
                    }
                },
                error:function(data,status,e){
                    alert(e);
                }
            });
        }else{
            alert("请选择文件");
        }
    }

    /***************************** 沉降点管理 **********************************/
    //沉降点信息保存
    function spsave(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/interval/spinfo/save",
            data:$("#spform").serialize(),
            success:function(json){
                if(json.code == 1){
                    $(".new_cover").hide();
                    $("#spform")[0].reset();
                    $("#pagination1").pagination('remote');
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

    // 沉降点新增按钮点击
    $(".new_btn").on("click",function(){
        $("#settelmentBox").show();
    })


    function spupdateclose(){
        $(".change_cover").hide();
    }
    function spupdatecancel(){
        $(".change_cover").hide();
    }
    function spupdatesave(){
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/interval/spinfo/update",
            data:$("#spupdateform").serialize(),
            success:function(json){
                if(json.code == 1){
                    $(".change_cover").hide();
                    $("#spupdateform")[0].reset();
                    $("#pagination1").pagination('remote');
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
    /********************************** 风险位置管理 ***************************/
    // 新建风险信息提交
    /**
     $('#dangerous_new_form').on('submit',function(){
        return dangerous_new_submit("${request.contextPath?json_string}",this);
    });
     */

    // 风险文本信息修改提交
    $('#dangerous_alter_text_form').on('submit',function(){
        return dangerous_alter_text_submit(this);
    });

    // 风险图片信息修改提交
    $('#dangerous_alter_image_form').on('submit',function(){
        return dangerous_alter_image_submit(this);
    });

    // 风险文档信息修改提交
    $('#dangerous_alter_docs_form').on('submit',function(){
        return dangerous_alter_docs_submit(this);
    });

    // 风险信息修改提交
    $('#dangerous_alter_docs_form').on('submit',function(){
        return dangerous_alter_docs_submit(this);
    });
    // 生成表格内链接
    function dangerous_link(elmId,elmTxt,functionName){
        var link = "<a data-id='"+elmId+"'";
        link += " onclick='javascript:return "+functionName+'(this)'+"'";
        link += ">" + elmTxt +"</a>";
        return link;
    }
    /********************************* common *******************************/
    // 关闭弹框
    function spclose(){
        $(".new_cover").hide();
        $(".cover").hide();
    }
    function spcancel(){
        $(".new_cover").hide();
    }

    //上传svg文件
    function ajaxFileUpload(fileId){
        if ($("#"+fileId).val().length > 0) {
            $.ajaxFileUpload({
                url:"${request.contextPath}/project-info/interval/ppinfo/svg-upload?fileId="+fileId, //用于文件上传的服务器端请求地址
                secureuri:false, //是否需要安全协议，一般设置为false
                fileElementId:fileId, //文件上传域的ID
                dataType:"json", //返回值类型 一般设置为json
                //data:{"fileId":"logo"},
                success:function(data,status){ //服务器成功响应处理函数
                    if(data.code == 1){ //上传成功
                        if(fileId == "ppsvg"){
                            $("#ppSvgUrl").val(data.result);
                            var currentPpSvg = data.result.substring(data.result.lastIndexOf('/')+1).split('_')[1];
                            $("#currentPpSvg").html(currentPpSvg);
                        }else if(fileId == "sectionsvg"){
                            $("#sectionSvgUrl").val(data.result);
                            var currentSectionSvg = data.result.substring(data.result.lastIndexOf('/')+1).split('_')[1];
                            $("#currentSectionSvg").html(currentSectionSvg);
                        }
                        spcancel();
                    }else{
                        alert(data.result);
                    }
                },
                error:function(data,status,e){
                    alert(e);
                }
            });
        }else{
            alert("请选择文件");
        }
    }

    // 更新第n个表格高度
    function updateTableHeight(n){
        // 更新表格高度
        $($('.dataTables_scrollBody').get(n-1)).css('height',function(){
            var top = $($('.dataTables_scrollBody').get(n-1)).offset().top;
            var bottom = $($('.box-footer').get(n-1)).offset().top;
            return bottom-top;
        });
    }

    var deleteSpId;
    var deleteMdreId;
    $("#spcancel").on("click",function(){
        $("#spcover").hide();
    });

    $("#spsure").on("click",function(){
        $("#spcover").hide();
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/interval/spinfo/delete",
            data:{"id":deleteSpId},
            success:function(json){
                if(json.code == 1){
                    $("#pagination1").pagination('remote');
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
        //current.remove();
    })

    // 修改按钮
    function spedit(id){
        //请求编辑的数据
        $.ajax({
            type:"get",
            url:"${request.contextPath}/project-info/interval/spinfo/to-edit",
            data:{"id":id},
            success:function(data){
                $(".change_cover").html(data);
                $(".change_cover").show();
            }
            ,error: function (data, status, e){
                alert("加载失败");
            }
        });
    }

    // 删除按钮
    function spdelete(id){
        $("#spcover").show();
        deleteSpId = id;
    }

    // 删除按钮
    function mdredelete(id){
        $("#mdrecover").show();
        deleteMdreId = id;
    }

    // 选择文件后，显示文件路径
    registerUpfileName(["sectionsvg","ppsvg","mdreFile"],["sectionsvgUpName","ppsvgUpName","mdreFileName"]);

    $("#mdrecancel").on("click",function(){
        $("#mdrecover").hide();
    })
    $("#mdresure").on("click",function(){
        $("#mdrecover").hide();
        $.ajax({
            type:"POST",
            dataType:"json",
            url:"${request.contextPath}/project-info/interval/mdreinfo/delete",
            data:{"id":deleteMdreId},
            success:function(json){
                if(json.code == 1){
                    $("#pagination2").pagination('remote');
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
        //current.remove();
    })


    // tab 管理
    $('a[data-toggle="tab"]').on('shown.bs.tab',function(e){
        if(e.target.innerText === '沉降点管理'){
            // 防止重复创建
            if ($("#pagination1").pagination()){
                updateTableHeight(1);
                return;
            };
            // 初始化表格
            var table1 = zookeTable('#roleTable1',{
                "columns":[           // 表明每一column的数据，跟json数据对应
                    {"data":"spName"},
                    {"data":"leftOrRight"},
                    {"data":"originMileage"},
                    {"data":"mapX"},
                    {"data":"mapY"},
                    {"data":"spSumAdd"},
                    {"data":"spSumSub"},
                    {"data":"spSpeedWarningVal"},
                    {"data":"operation"}
                ],
                "columnDefs":[
                    {
                        "render":function(data,type,row){
                            return "<a href=\'javascript:void(0);\'"+" onclick=\'spedit("+row.id.toString()+")\' class=\'edit_btn\'>修改</a>"+"<a href=\'javascript:void(0);\'"+" onclick=\'spdelete("+row.id.toString()+")\' class=\'del_btn\'>删除</a>";
                        },
                        "targets":-1
                    },
                    {
                        "render":function(data,type,row){
                            if (data == "l"){
                                return "左线";
                            }else{
                                return "右线";
                            }
                        },
                        "targets":1
                    }
                ]
            });

            /**
             * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
             */
            table1.on('draw.dt',function(){
            })

            var pagination1 = zookePagination('#pagination1',{
                pageSize:20,
                remote:{
                    url:"<@s.url'/project-info/interval/spinfo/find'/>",
                    pageParams:function(data){
                        return {
                            intervalId : "${intervalId!''}",
                            pageNum : data.pageIndex+1,
                            pageSize : data.pageSize
                        };
                    },
                    success:function(result){
                        // 刷新数据，result.data为获取到的表格数据
                        //console.log(result.data);
                        table1.clear();
                        table1.rows.add(result.list);
                        table1.draw();
                        updateTableHeight(1);
                    },
                    totalName:'page.totalRow'
                }
            });
        }else if(e.target.innerText === '沉降点监测数据管理'){
            // 防止重复创建
            if ($("#pagination2").pagination()){
                updateTableHeight(2);
                return;
            };
            // 初始化表格
            var table2 = zookeTable('#roleTable2',{
                "columns":[           // 表明每一column的数据，跟json数据对应
                    {"data":"monitorDate"},
                    {"data":"originFileName"},
                    {"data":"createTime"},
                    {"data":"operation"}
                ],
                "columnDefs":[{
                    "render":function(data,type,row){
                        return "<a href=\'javascript:void(0);\'"+" onclick=\'mdredelete("+row.id.toString()+")\' class=\'edit_btn\'>删除</a>"
                                +"<a href=\'javascript:void(0);\'"+" onclick=\'mdredownload(\""+row.fileName+"\")"+"\' class=\'del_btn\'>下载</a>";
                    },
                    "targets":-1
                }]
            });

            /**
             * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
             */
            table2.on('draw.dt',function(){
            })

            var pagination2 = zookePagination('#pagination2',{
                pageSize:20,
                remote:{
                    url:"<@s.url'/project-info/interval/mdreinfo/find'/>",
                    pageParams:function(data){
                        return {
                            intervalId : "${intervalId!''}",
                            pageNum : data.pageIndex+1,
                            pageSize : data.pageSize
                        };
                    },
                    success:function(result){
                        // 刷新数据，result.data为获取到的表格数据
                        //console.log(result.data);
                        table2.clear();
                        table2.rows.add(result.list);
                        table2.draw();
                        updateTableHeight(2);
                    },
                    totalName:'page.totalRow'
                }
            });
        }else if (e.target.innerText === '风险位置管理'){
            showTable('dangerous',[
                        {"data":"positionNo"}, // 位置
                        {"data":"leftOrRight"}, // 左右线
                        {"data":"gType"}, // 平面剖面
                        {"data":"textMsg"}, //文本
                        {"data":"riskImgUrl"}, // 图片
                        {"data":"riskPdf1Url"}, // 文档
                        {"data":"id"} // id
                    ],
                    [{"render":function(data,type,row){
                        var link_alter = dangerous_link(row.id,"修改","dangerous_alter_info");
                        var link_delete = dangerous_link(row.id,"删除","dangerous_delete_info");
                        return link_alter + '|' +link_delete;
                    },
                        "targets":-1
                    },
                        {"render":function(data,type,row){
                            return dangerous_link(row.id,"修改","dangerous_alter_docs");
                        },
                            "targets":-2
                        },
                        {"render":function(data,type,row){
                            return dangerous_link(row.id,"修改","dangerous_alter_images");
                        },
                            "targets":-3
                        },
                        {"render":function(data,type,row){
                            return dangerous_link(row.id,"修改","dangerous_alter_text");
                        },
                            "targets":-4
                        },
                        {"render":function(data,type,row){
                            if(data == '1'){
                                return "平面图";
                            }else if(data == '2'){
                                return "剖面图";
                            }else{
                                return "数据错误";
                            }
                        },
                            "targets":-5
                        },
                        {"render":function(data,type,row){
                            if(data == 'l'){
                                return "左线";
                            }else if(data == 'r'){
                                return "右线";
                            }else{
                                return "数据错误";
                            }
                        },
                            "targets":-6
                        }
                    ]
                    ,20,"${request.contextPath}","/project-info/interval/riskinfo/find","${intervalId!''}");
        }else if(e.target.innerText === '盾尾间隙数据管理'){
            showTable('shield_tail',[
                        {"data":"ringNum"}, // 环号
                        {"data":"leftOrRight"}, // 左右线
                        {"data":"dateTime"}, // 日期
                        {"data":"stUp"}, // 盾尾间隙上
                        {"data":"stDown"}, // 盾尾间隙下
                        {"data":"stLeft"}, // 盾尾间隙左
                        {"data":"stRight"}, // 盾尾间隙右
                        {"data":"id"} // id
                    ],
                    [{"render":function(data,type,row){
                        var link_alter = dangerous_link(row.id,"修改","alter_shield_tail");
                        var link_delete = dangerous_link(row.id,"删除","del_shield_tail");
                        return link_alter + '|' +link_delete;
                    },
                        "targets":-1
                    },{
                    "render":function(data,type,row){
                      return data == 'l'?"左线":"右线";
                    },"targets":1
                    }]
                    ,20,"${request.contextPath}","/project-info/interval/stinfo/find","${intervalId!''}");
        }else if(e.target.innerText === '管片姿态数据管理'){
            showTable('shield_profile',[
                        {"data":"ringNum"}, // 环号
                        {"data":"leftOrRight"}, // 左右线
                        {"data":"dateTime"}, // 日期
                        {"data":"horizontalDev"}, // 水平偏差
                        {"data":"verticalDev"}, // 垂直偏差
                        {"data":"id"} // id
                    ],
                    [{"render":function(data,type,row){
                        var link_alter = dangerous_link(row.id,"修改","alter_shield_profile");
                        var link_delete = dangerous_link(row.id,"删除","del_shield_profile");
                        return link_alter + '|' + link_delete;
                    },
                        "targets":-1
                    },{
                    "render":function(data,type,row){
                      return data == 'l'?"左线":"右线";
                    },"targets":1
                  }]
                    ,20,"${request.contextPath}","/project-info/interval/sainfo/find","${intervalId!''}");
        }
    });


//************** 沉降点管理模块数据导入导出功能 *****************//
    // 打开上传框
    function openUploadBox1(){
        $('#uploadBox').show();
    }

    // 取消上传框
    function cancelUploadBox1(){
        $('#uploadBox').hide();
    }

    // 选择文件后，显示文件路径
    registerUpfileName(["excel"],["fileUpName"]);

    //上传
    function fsubmit(obj){
        var excel = $("#excel").val();
        if(excel == null || excel==""){
            alert("请先选择文件");
            return;
        }
        obj.submit();
    }

    function downloadTemplateFile(filename){
        location.href = "${request.contextPath}/common/file-download?filename="+filename;
    }

    //导出excel数据
    function exportExcel(){
        $.ajax({
            type: "post",
            dataType:"json",
            url: "${request.contextPath}/project-info/interval/spinfo/export",
            data: {"intervalId":"${intervalId!''}"},
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

    //导出盾构excel数据
    function exportShieldToExcel(leftOrRight,ring,begindate,type,key){
        $.ajax({
            type: "post",
            dataType:"json",
            url: "${request.contextPath}/project-info/interval/sdinfo/export",
            data: {"intervalId":"${intervalId!''}","leftOrRight":leftOrRight,
                "date":begindate,"ring":ring,"type":type,"key":key},
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

    var desc = $("#desc").val();
    console.log("desc: "+desc);
    if(desc == "tab2"){
        console.log("desc: "+true);
        $("#tab2").tab('show');
    }
    if(desc == "tab7"){
        console.log("desc: "+true);
        $("#tab7").tab('show');
    }
    if(desc == "tab8"){
        console.log("desc: "+true);
        $("#tab8").tab('show');
    }

</script>
</body>
</html>
