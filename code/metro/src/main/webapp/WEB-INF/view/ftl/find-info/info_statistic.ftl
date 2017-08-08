<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>盾构信息监控/统计报表</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap-select.min.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/font-awesome/css/font-awesome.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datatables/media/css/dataTables.bootstrap.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/datepicker/datepicker3.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/select2/dist/css/select2.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/bottom-nav.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/monitor_warn.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/info_monitor.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/statistic.css'/>" media="all">
</head>
<body>
<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!'0'}">
<input type="hidden" id="leftOrRight" name="leftOrRight" value="${(leftOrRight)!'l'}">
<div class="nav-tabs-custom">
    <div class="tab-content">
        <div id="tab-pane1" class="tab-pane" style="overflow:hidden">
            <div class="tabWrap">
                <div class="com_stat_header">
                    <span class="cylinder_num">
                        起始环号：
                        <input type="number" id="beginRing" value="${(b_curr_ring)!0}">
                        <em class="to">至</em>
                        <input type="number" id="endRing" value="${(e_curr_ring)!0}">
                    </span>
                    统计项目：
                    <select class="selectpicker" id="paramName">
                        <option value="H0001">泡沫量（m³）</option>
                        <option value="D0023">同步注浆量（m³）</option>
                        <option value="I0001">膨润土量（L）</option>
                        <option value="G0001">盾尾油脂量（bar）</option>
                    </select>
                    <button class="query_btn" id="query_btn1">查询</button>
                </div>
                <div class="chartWrap">
                    <div id="chart-material" class="chart"></div>
                </div>
            </div>
        </div>
        <div id="tab-pane2" class="tab-pane">
            <div class="tabWrap">
                <div class="com_stat_header">
                    <span class="cylinder_num">
                        起始环号：
                        <input type="number" id="beginRing1" value="${(b_curr_ring)!'0'}">
                        <em class="to">至</em>
                        <input type="number" id="endRing1" value="${(e_curr_ring)!'0'}">
                    </span>
                    <button class="query_btn" id="query_btn2">查询</button>
                </div>
                <div class="chartWrap">
                    <div id="chart-time" class="chart"></div>
                </div>
            </div>
        </div>
        <div id="tab-pane3" class="tab-pane">
            <div class="tabWrap">
                <div class="com_stat_header clearfix">
                    <div class="wran_head_left">
                        <ol class="title_list clearfix">
                            <li>统计日期：</li>
                            <li>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" value="${(beginDate)!'7/16/2016'}" class="pull-right date_input" id="beginDate">
                                </div>
                            </li>
                            <li class="to">至</li>
                            <li>
                                <div class="input-group date">
                                    <div class="input-group-addon">
                                        <i class="fa fa-calendar"></i>
                                    </div>
                                    <input type="text" value="${(endDate)!'7/16/2016'}" class="pull-right date_input" id="endDate">
                                </div>
                            </li>
                            <li class="query_btn_box">
                                <button class="query_btn" id="query_btn3">查询</button>
                            </li>
                        </ol>
                    </div>
                </div>
                <div class="chartWrap">
                    <div id="chart-progress" class="chart"></div>
                </div>
            </div>
        </div>
        <div id="tab-pane4" class="tab-pane">
            <div class="box">
                <div class="box-header">
                    <div class="wran_head clearfix">
                        <div class="wran_head_left">
                            <ol class="title_list clearfix">
                                <li>统计日期：</li>
                                <li>
                                    <div class="input-group date">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" value="${(beginDate)!'7/16/2016'}" class="pull-right date_input" id="beginTime4">
                                    </div>
                                </li>
                                <li class="to">至</li>
                                <li>
                                    <div class="input-group date">
                                        <div class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </div>
                                        <input type="text" value="${(endDate)!'7/16/2016'}" class="pull-right date_input" id="endTime4">
                                    </div>
                                </li>
                            </ol>
                        </div>
                        <div class="wran_head_right">
                            统计项目：
                            <select class="warn_parameter_select" id="excelType">
                                <option value="1">日报</option>
                                <option value="2">周报</option>
                                <option value="3">月报</option>
                            </select>
                            <button class="query_btn" id="query_btn4">查询</button>
                            <button class="query_btn" id="export_btn4">导出</button>
                        </div>
                    </div>
                </div>
                <div class="box-body">
                    <div class="row no-pad">
                        <div class="col-sm-12">
                            <table id="tab4tables" class="table table-bordered table-hover dataTable">
                                <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>起始环号</th>
                                    <th>结束环号</th>
                                    <th>推进环数</th>
                                    <th>推进时间</th>
                                    <th>拼装时间</th>
                                    <th>停止时间</th>
                                    <th>同步注浆量</th>
                                    <th>盾尾油脂量</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="box-footer">
                    <div class="row">
                        <div class="col-sm-12">
                            <div id="pagination2"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="tab-pane5" class="tab-pane">
            <div class="tabWrap">
                <div class="tab5header data-header wran_head">
                    <div class="tab5Left">
                        <div class="tab5Date">
                          <input type="radio" name="radiobox" class="tab5checkbox" value="t">
                          统计日期：
                          <div class="input-group date">
                              <div class="input-group-addon">
                                  <i class="fa fa-calendar"></i>
                              </div>
                              <input type="text" value="${(beginDate)!'7/16/2016'}" class="pull-right date_input" id="beginTime5">
                          </div>
                          至
                          <div class="input-group date">
                              <div class="input-group-addon">
                                  <i class="fa fa-calendar"></i>
                              </div>
                              <input type="text" value="${(endDate)!'7/16/2016'}" class="pull-right date_input" id="endTime5">
                          </div>
                        </div>
                        <div class="tab5Ring">
                          <input type="radio" name="radiobox" class="tab5checkbox" value="r" checked>
                          统计环号：
                          <input class="num_input" style="width:60px;" type="number"
                          <#if b_curr_ring?? && b_curr_ring gte 0>
                                     value="${(b_curr_ring)!0}"
                          <#else>
                                     value="0"
                          </#if>
                                     id="beginRing5">
                          <em class="to">至</em>
                          <input class="num_input" type="number" style="width:60px;" value="${(e_curr_ring)!0}" id="endRing5">
                        </div>
                        <div class="tab5dataModel">
                          数据模式：
                          <select class="selectpicker" id="static-datamodel">
                              <option value="100">默认采样值</option>
                              <option value="1">最大值</option>
                              <option value="2">最小值</option>
                              <option value="3">平均值</option>
                          </select>
                        </div>
                        <div class="tab5Type">
                          <label for="dataType1">
                            <input id="dataType1" type="checkbox" class="tab5typeCheck tab5checkbox" disabled>
                            推进模式
                          </label>
                          <label for="dataType2">
                            <input id="dataType2" type="checkbox" class="tab5typeCheck tab5checkbox" disabled>
                            拼装模式
                          </label>
                          <label for="dataType3">
                            <input id="dataType3" type="checkbox" class="tab5typeCheck tab5checkbox" disabled>
                            停机模式
                          </label>
                        </div>
                    </div>
                    <button class="tab5Right data_query_btn query_btn">查询</button>
                </div>
                <div class="dataChartWrap">
                    <div id="data_main" class="chart" style="width: 100%; height:100%;"></div>
                </div>
                <div class="module_btn">
                    <button id="md1" title="土压[上] 总推力 扭矩 掘进速度 刀盘转速" class="">默认模式一</button>
                    <button id="md2" title="土压力 总推力 铰接压力" class="">默认模式二</button>
                    <button id="md3" title="千斤顶油压差(上下) 千斤顶行程差(上下) 盾尾垂直偏差 垂直趋势" class="">默认模式三</button>
                    <button id="md4" title="液压油箱温差 齿轮油油温" class="">默认模式四</button>
                    <button id="md5" title="千斤顶油压差(左右) 千斤顶行程差(左右) 盾尾水平偏差 水平趋势" class="">默认模式五</button>
                    <button id="md6" title="发泡剂注入流量 掘进速度" class="">默认模式六</button>
                    <button id="md7" title="注浆量 掘进速度" class="">默认模式七</button>
                    <button id="md8" title="前(后)盾水平偏差 前(后)盾垂直偏差 " class="">默认模式八</button>
                </div>
            </div>
        </div>
    </div>
    <ul id="bottomNav" class="nav nav-tabs">
        <li>
            <a id="tab1" href="#tab-pane1" data-toggle="tab">材料消耗</a>
        </li>
        <li>
            <a id="tab2" href="#tab-pane2" data-toggle="tab">时间统计</a>
        </li>
        <li>
            <a id="tab3" href="#tab-pane3" data-toggle="tab">进度统计</a>
        </li>
        <li>
            <a id="tab4" href="#tab-pane4" data-toggle="tab">汇总统计</a>
        </li>
        <li>
            <a id="tab5" href="#tab-pane5" data-toggle="tab">数据分析</a>
        </li>
    </ul>
</div>
<!-- 数据分析弹窗 -->
<div class="cover query_cover">
    <input type="hidden" id="pnames">
    <input type="hidden" id="knames">
    <input type="hidden" id="yAxiesList">
    <div class="query_pop middle">
        <div class="query_content clearfix">
            <div class="query_left">
                <div class="query_parameter_head">
                    左边参数
                    <select id="left_parameter" class="left_parameter" value="KN">
                        <option></option>
                    </select>
                </div>
                <ul id="query_left_main" class="arg_list"></ul>
            </div>
            <div class="query_right">
                <div class="query_parameter_head">
                    右边参数
                    <select id="right_parameter" class="right_parameter">
                        <option></option>
                    </select>
                </div>
                <ul id="query_right_main" class="arg_list"></ul>
            </div>
            <div class="query_pop_bottom">
                <a href="###" class="cancel_btn">取消</a>
                <a href="###" class="sure_btn">确定</a>
            </div>
        </div>
    </div>
</div>


<script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap-select.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.js'/>"></script>
<script src="<@s.url'/plugins/bower/datepicker/bootstrap-datepicker.zh-CN.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/datatables/media/js/jquery.dataTables.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/Mricode.Pagination/mricode.pagination.js'/>"></script>
<script src="<@s.url'/plugins/js/table.js'/>"></script>
<script src="<@s.url'/plugins/bower/echarts/dist/echarts.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
<script src="<@s.url'/plugins/bower/select2/dist/js/select2.full.min.js'/>"></script>
<script src="<@s.url'/plugins/js/arguments.js'/>"></script>
<script src="<@s.url'/plugins/js/base.js'/>"></script>

<script>
    // 请求器
    var ax_request = axios.create({
        baseURL:'${request.contextPath}/monitor/info',
        timeout:60000
    });
    // 日期初始化
    $(".date_input").datepicker({language:"zh-CN"});

    function tab1(){
        var container = $('#chart-material')[0];
        var chart = echarts.getInstanceByDom(container) || echarts.init(container);
        var option = {
            title: {
                left: 'left',
                text: '材料消耗统计'
            },
            tooltip: {},
            xAxis:[{
                type:'category',
                name:"环号",
                data:[]
            }],
            yAxis:[
                {type:'value',name:"泡沫量（m³）"}
            ],
            dataZoom:{
                type:'inside'
            },
            legend: {
                right:'right',
                data:['泡沫量(bar)']
            },
            series: [{
                name: '泡沫量(bar)1',
                type: 'bar',
                data: [],
                label:{
                    normal:{
                        show:true
                    }
                },
            }]
        };
        ax_request.get('/find/static/tab1',{
            params:{
                intervalId:$("#intervalId").val(),
                leftOrRight:$("#leftOrRight").val(),
                beginRing:$("#beginRing").val(),
                endRing:$("#endRing").val(),
                paramName:$("#paramName").val()
            }
        }).then(function(res){
            $("#query_btn1").removeAttr('disabled');
            $("#query_btn1").text('查询');
            option.xAxis[0].data = res.data.list[0];
            option.yAxis[0].name = $("#paramName option:selected").text();
            option.series[0].data = res.data.list[1];
            option.legend.data[0] = option.series[0].name = $("#paramName").find("option:selected").text();
            chart.clear();
            chart.setOption(option);
            loadingDismiss();
        }).catch(function(err){
            alert("查询超时");
            $("#query_btn1").removeAttr('disabled');
            $("#query_btn1").text('查询');
            loadingDismiss();
        });
    }
    // 材料消耗
    $('#tab1').on('shown.bs.tab',function(e){
        tab1();
    });

    $("#query_btn1").on("click",function(){
        $(this).attr('disabled', 'disabled');
        $(this).text('查询中...');
        tab1();
        loadingShow("${request.contextPath!}");
    });

    function tab2(){

        var container = $('#chart-time')[0];
        var chart = echarts.getInstanceByDom(container) || echarts.init(container);
        option = {
            title:{
                text:'时间统计'
            },
            legend:{
                left:"right",
                data:['推进时间','拼装时间','停止时间','总时间']
            },
            tooltip: {},
            xAxis:[{
                type: 'category',
                name:"环号",
                data: [],
                gridIndex:0
            },{
                type:'category',
                data: ['推进时间','拼装时间','停止时间'],
                gridIndex:1
            }],
            yAxis:[{
                type: 'value',
                name:"时间 (分钟)",
                gridIndex:0

            },{
                type: 'value',
                name:"时间 (分钟)",
                gridIndex:1
            }],
            grid:[
                {x:'10%',y:'15%',width:'80%',height:'35%'},
                {x:'10%',y:'65%',width:'35%',height:'30%'},
            ],
            series:[
                {
                    name: '推进时间',
                    type: 'bar',
                    stack: '时间统计',
                    xAxisIndex:0,
                    yAxisIndex:0,
                    data: []
                },{
                    name: '拼装时间',
                    type: 'bar',
                    stack: '时间统计',
                    xAxisIndex:0,
                    yAxisIndex:0,
                    data: []
                },{
                    name: '停止时间',
                    type: 'bar',
                    stack: '时间统计',
                    xAxisIndex:0,
                    yAxisIndex:0,
                    data: []
                },
                {
                    name: '总时间',
                    type: 'bar',
                    xAxisIndex:1,
                    yAxisIndex:1,
                    data: []
                },{
                    name:'时间占比',
                    type:'pie',
                    radius: [40,80],
                    center: ['75%','75%'],
                    roseType :'area',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    lableLine: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: [],
                    label:{
                        normal:{
                            show:true
                        }
                    }
                }
            ]

        }

        ax_request.get('/find/static/tab2',{
            params:{
                intervalId:$("#intervalId").val(),
                leftOrRight:$("#leftOrRight").val(),
                beginRing:$("#beginRing1").val(),
                endRing:$("#endRing1").val()
            }
        }).then(function(res){
            $("#query_btn2").removeAttr('disabled');
            $("#query_btn2").text('查询');
            option.xAxis[0].data = res.data.list[0];//环号数组
            option.series[0].data = res.data.list[1];//推进时间数组与环号数组对应
            option.series[1].data = res.data.list[2];//拼装时间数组与环号数组对应
            option.series[2].data = res.data.list[3];//停止时间数组与环号数组对应
            option.series[3].data = res.data.list[4];//环号数组所有环花费总时间(推进时间、拼装时间、停止时间)数组
            option.series[4].data = res.data.list[5];//环号数组所有环花费总时间(推进时间、拼装时间、停止时间)数组
            chart.clear();
            chart.setOption(option);
            loadingDismiss();
        }).catch(function(err){
            alert("查询超时");
            $("#query_btn2").removeAttr('disabled');
            $("#query_btn2").text('查询');
            loadingDismiss();
        });
    }
    // 时间统计
    $('#tab2').on('shown.bs.tab',function(e){
        tab2();
    });

    $("#query_btn2").on("click",function(){
        $(this).attr('disabled', 'disabled');
        $(this).text('查询中...');
        tab2();
        loadingShow("${request.contextPath!}");
    });

    function tab3(){
        var container = $('#chart-progress')[0];
        var chart = echarts.getInstanceByDom(container) || echarts.init(container);
        var option = {
            title:{
                text:"进度统计"
            },
            tooltip: {},
            dataZoom:{type:'inside'},
            xAxis:[{type:'category',data:[],name:"日期"}],
            yAxis:[{type:"value",name:"环数"}],
            series:[{
                name:'环数',
                type:'bar',
                data:[],
            }]
        }

        ax_request.get('/find/static/tab3',{
            params:{
                intervalId:$("#intervalId").val(),
                leftOrRight:$("#leftOrRight").val(),
                beginDate:$("#beginDate").val(),
                endDate:$("#endDate").val()
            }
        }).then(function(res){
            $("#query_btn3").removeAttr('disabled');
            $("#query_btn3").text('查询');
            option.xAxis[0].data = res.data.list[0];//日期数组
            var dataMap = res.data.list[1].map(function(val){
                var err = val==-1;
                return {
                    value:val,
                    itemStyle:{
                        normal:{
                            color:err?"#FEEA3E":"#3B6447"
                        }
                    },
                    label:{
                        normal:{
                            show:true,
                            position:'top',
                            formatter:err?"数据错误":"{c}",
                            textStyle:{
                                color:"#000"
                            }
                        }
                    }
                }
            });
            option.series[0].data = dataMap;//与日期数组对应的环数数组
            chart.setOption(option);
            loadingDismiss();
        }).catch(function(err){
            alert("查询超时");
            $("#query_btn3").removeAttr('disabled');
            $("#query_btn3").text('查询');
            loadingDismiss();
        });
    }
    // 进度统计
    $('#tab3').on('shown.bs.tab',function(e){
        tab3();
    });

    $("#query_btn3").on("click",function(){
        $(this).attr('disabled', 'disabled');
        $(this).text('查询中...');
        tab3();
        loadingShow("${request.contextPath!}");
    });

    // 汇总统计
    $('#tab4').on('shown.bs.tab',function(e){

        if ($("#pagination2").pagination()){
            var table = $('#tab4tables').DataTable();
            table.columns.adjust().draw();
            return;
        };
        // 初始化表格
        var table = zookeTable('#tab4tables',{
            "columns":[           // 表明每一column的数据，跟json数据对应
                {"data":"date"},
                {"data":"beginRing"},
                {"data":"endRing"},
                {"data":"ringNum"},
                {"data":"K0001"},
                {"data":"K0002"},
                {"data":"K0003"},
                {"data":"D0023"},
                {"data":"G0001"}
            ]
        });

        /**
         * 每次表格内容重新刷新绘制完成后执行一次，凡是表格包含按钮的注册事件都写在这里
         */
        table.on('draw.dt',function(){
            $(".box-body").css('height',function(){
                /**
                 var screenHeight = window.innerHeight;
                 var offset = $(".box-body")[0].offsetTop;
                 return screenHeight-offset;
                 */
            });
        })

        var pagination2 = zookePagination('#pagination2',{
            pageSize:20,
            remote:{
                url:"${request.contextPath}/monitor/info/find/static/tab4",
                pageParams:function(data){
                    return {
                        pageNum: data.pageIndex+1,
                        pageSize:data.pageSize,
                        intervalId:$("#intervalId").val(),
                        leftOrRight:$("#leftOrRight").val(),
                        beginTime:$("#beginTime4").val(),
                        endTime:$("#endTime4").val(),
                        excelType:$("#excelType").val()
                    };
                },
                success:function(result){
                    // 刷新数据，result.data为获取到的表格数据
                    $("#query_btn4").removeAttr('disabled');
                    $("#query_btn4").text('查询');
                    table.clear();
                    var dataList = result.list.map(function(data){
                        var ring = data.ringNum==-1?"数据错误":data.ringNum;
                        return $.extend({},data,{ringNum:ring});
                    });
                    console.log(result.list);
                    table.rows.add(dataList);
                    table.draw();
                    loadingDismiss();
                },
                totalName:'page.totalRow'
            }
        });
    })
    $("#query_btn4").on("click",function(){
        $(this).attr('disabled', 'disabled');
        $(this).text('查询中...');
        $("#page").pagination('setPageIndex',1);
        $("#pagination2").pagination('remote');
        loadingShow("${request.contextPath!}");
    });

    $("#export_btn4").on("click",function(){
        $(this).attr('disabled', 'disabled');
        $(this).text('导出中...');
        loadingShow("${request.contextPath!}");
        $.ajax({
            type: "post",
            dataType:"json",
            url: "${request.contextPath}/monitor/info/find/static/tab4d",
            data: {
                "pageNum": 1,
                "pageSize":10,
                "intervalId":$("#intervalId").val(),
                "leftOrRight":$("#leftOrRight").val(),
                "beginTime":$("#beginTime4").val(),
                "endTime":$("#endTime4").val(),
                "excelType":$("#excelType").val()
            },
            success: function(json){
                $("#export_btn4").removeAttr('disabled');
                $("#export_btn4").text('导出');
                if(json.code == 1){
                    var filename = json.result;
                    location.href = "${request.contextPath}/common/file-download?filename="+filename;
                    loadingDismiss();
                }else{
                    alert(json.result);
                }
            },
            error: function (data, status, e){
                //alert(e);
                alert("导出失败");
                loadingDismiss();
            }
        });
    });

    // 数据分析
    $('#tab5').on('shown.bs.tab',function(e){
        // 图表
        // 根据模式组，更新图表数轴设置
        function optionUpdate(option,mode){
            var left_axis = 1;
            var right_axis = 1;
            switch (mode){
                case 1:
                    left_axis = 2; right_axis = 3;
                    option.yAxis = [
                        {type:'value',name:'bar',position:'right'},
                        {type:'value',name:'kN',position:'left'},
                        {type:'value',name:'kNm',position:'left'},
                        {type:'value',name:'mm/min',position:'right'},
                        {type:'value',name:'rpm',position:'right'},
                    ];
                    break;
                case 2:
                    left_axis = 1; right_axis = 2;
                    option.yAxis = [
                        {type:'value',name:'bar',position:'left'},
                        {type:'value',name:'kN',position:'right'},
                        {type:'value',name:'bar'}
                    ];
                    break;
                case 3:
                    left_axis = 2; right_axis = 2;
                    option.yAxis = [
                        {type:'value',name:'bar',position:'left'},
                        {type:'value',name:'mm',position:'left'},
                        {type:'value',name:'mm',position:'right'},
                        {type:'value',name:'垂直趋势',position:'right'}
                    ]
                    break;
                case 4:
                    option.yAxis = [
                        {type:'value',name:'°C',position:'left'},
                        {type:'value',name:'°C',position:'right'},
                    ]
                    break;
                case 5:
                    left_axis = 2; right_axis = 2;
                    option.yAxis = [
                        {type:'value',name:'bar',position:'left'},
                        {type:'value',name:'mm',position:'left'},
                        {type:'value',name:'mm',position:'right'},
                        {type:'value',name:'水平趋势',position:'right'}
                    ]
                    break;
                case 6:
                    option.yAxis = [
                        {type:'value',name:'mm/min',position:'left'},
                        {type:'value',name:'L/min',position:'right'},
                    ]
                    break;
                case 7:
                    option.yAxis = [
                        {type:'value',name:'m³',position:'left'},
                        {type:'value',name:'mm/min',position:'right'},
                    ]
                    break;
                case 8:
                    right_axis = 0;
                    option.yAxis = [
                        {type:'value',name:'mm',position:'left'},
                    ]
                    break;
                default:
                    option.yAxis = [
                        {
                            type:'value',
                            name:left_checked_arg_list.unit,
                            position:'left',
                        },
                        {
                            type:'value',
                            name:right_checked_arg_list.unit,
                            position:'right',
                        }
                    ]

                    break;
            }
            option.grid.left = 40*(left_axis-1);
            option.grid.right = 40*(right_axis-1);
            option.dataZoom.startValue = null;
            option.dataZoom.endValue = null;
            for(i=0;i<left_axis;i++){
                option.yAxis[i].offset = 40*i;
            }
            for(i=0;i<right_axis;i++){
                option.yAxis[i+left_axis].offset = 40*i;
            }
        }
        var myChart = echarts.init(document.getElementById('data_main'));
        option = {
            title: {
                text: '数据分析'
            },
            legend: {
                left:'right',
                data:[]
            },
            tooltip: {},
            dataZoom:{
              type:'inside',
              filterMode:'empty'
            },
            grid: {
                left: '3%',
                right: '3%',
                bottom:'10%',
                containLabel: true
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: [],
                axisLabel:{
                    formatter:function(value,index){
                        var newVal = value?value.split(" ").join("\n"):"";
                        //var newVal = value.split(" ").join("\n");
                        return newVal;
                    }
                }
            },
            yAxis: [
                {
                    type: 'value',
                    name: 'bar',
                    position:'left'
                },
                {
                    type: 'value',
                    name: '°C',
                    position:'right'
                }
            ],
            series: []
        };
        myChart.setOption(option);
        var scaleStep = 0; // 放大步数
        var scaleLock = false; // 状态锁
        var currentMode = 0;  // 当前模式

        myChart.on('dataZoom',function(e){
          if(!scaleLock && getDataModel() == 100){
            scaleStep += 1;
            var oldStep = scaleStep;
            setTimeout(function(){
              console.log("old:"+oldStep+" new:"+scaleStep);
              if(!scaleLock && (oldStep == scaleStep)){
                // 500 ms 还没有改变
                scaleLock = true; // 上锁
                // 获取区间范围
                var startValueIndex = myChart.getOption().dataZoom[0].startValue;
                var endValueIndex = myChart.getOption().dataZoom[0].endValue;
                var req_startValue = option.xAxis.data[startValueIndex];
                var req_endValue = option.xAxis.data[endValueIndex];
                beginTime = transformDate(req_startValue);
                endTime = transformDate(req_endValue);
                console.log(req_startValue);
                console.log(req_endValue);

                ax_request.get('/find/static/tab5',{
                    params:{
                        intervalId:$("#intervalId").val(),
                        leftOrRight:$("#leftOrRight").val(),
                        beginTime:beginTime,
                        endTime:endTime,
                        beginRing:$("#beginRing5").val(),
                        endRing:$("#endRing5").val(),
                        model:currentMode,
                        type:'t',
                        ks:''+$("#pnames").val(),
                        kns:''+$("#knames").val(),
                        indxs:''+$("#yAxiesList").val(),
                        datamodel: getDataModel(),
                        datatype: getDataType()
                    }
                }).then(function(result){
                  var result_xAxis=result.data.keys?result.data.keys:[];
                  var result_series=result.data.values?result.data.values:[];
                  Array.prototype.splice.apply(option.xAxis.data,[startValueIndex,endValueIndex-startValueIndex].concat(result_xAxis));
                  option.series = option.series.map(function(item,index){
                    Array.prototype.splice.apply(item.data,[startValueIndex,endValueIndex-startValueIndex].concat(result_series[index].data));
                    return item;
                  });
                  option.dataZoom.startValue = result_xAxis[0];
                  option.dataZoom.endValue = result_xAxis[result_xAxis.length-1];
                  myChart.setOption(option);
                  // 成功处理
                  scaleLock = false;
                  scaleStep = 0;
                }).catch(function(err){
                  // 失败处理
                  console.log(err);
                });
              }
            },1800);
          }
        });

        // 数据转日期格式化
        function transformDate(dateStr){
          group = dateStr.split(' ');
          var d_group = group[0].split('-');
          var t_group = group[1].split(':');
          var t_second = t_group.length > 2 ? t_group[2] : "00";
          return d_group[0]+"年"+d_group[1]+"月"+d_group[2]+"日 "+t_group[0]+"时"+t_group[1]+"分"+t_second+"秒";
        }

        // 数据转日期格式化
        function transformDate1(dateStr){
            return dateStr+" 00时00分00秒";
        }

        // 数据转日期格式化
        function transformDate2(dateStr){
            var nowDate = new Date();
           // var nowStr = nowDate.getFullYear()+"年"+(nowDate.getMonth()+1)+"月"+nowDate.getDate()+"日";
            var nowStr = nowDate.Format("yyyy年MM月dd日")
            console.log(dateStr);
            console.log(nowStr);
            if(dateStr == nowStr){
                return nowDate.getFullYear()+"年"+(nowDate.getMonth()+1)+"月"
                        +nowDate.getDate()+"日 "+nowDate.getHours()+"时"+nowDate.getMinutes()+"分"+nowDate.getSeconds()+"秒";
            }
            return dateStr+" 00时00分00秒";
        }

        Date.prototype.Format = function (fmt) { //author: meizz
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "h+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        }

        // array 去重
        function arrayUnique(array) {
            var a = array.concat();
            for(var i=0; i<a.length; ++i) {
                for(var j=i+1; j<a.length; ++j) {
                    if(a[i] === a[j])
                        a.splice(j--, 1);
                }
            }
            return a;
        }

        var left_checked_arg_list= []; // 左边参数选择结果
        var right_checked_arg_list= []; // 右边参数选择结果

        // 查询弹窗的按钮事件
        $(".data_query_btn").on("click",function(){
            // 实现参数动态添加
            function createList(elm,data,isLeft){
                var ul = $(elm);
                ul.empty();
                data.forEach(function(item){
                    var check_box = $('<input />',{
                        type:'checkbox',
                        value:item.value,
                        name:item.text,
                        id:item.value+(isLeft?'l':'r'),
                        'class':'argument_check'
                    })
                    var label = $('<label>',{
                        text:item.text,
                        'for':item.value+(isLeft?'l':'r'),
                        'class':'arg_label'
                    });
                    check_box.on('change',function(){
                        var total_arg_list = left_checked_arg_list.concat(right_checked_arg_list);
                        var arg_list = isLeft? left_checked_arg_list:right_checked_arg_list;
                        var total = left_checked_arg_list.length+right_checked_arg_list.length;
                        var args = arg_list.map(function(elm){
                            return elm.value;
                        });
                        var all_args = total_arg_list.map(function(elm){
                            return elm.value;
                        });
                        var idx = $.inArray($(this).val(),args);
                        var iadx = $.inArray($(this).val(),all_args);

                        if($(this).is(':checked')){
                            if(total >= 5){ // 控制最多只能选5个参数
                                $(this).prop('checked',false);
                            }else{
                                if(idx === -1){ // 对应的参数未添加到组里
                                    if(iadx != -1){
                                        // 参数已经重复，不允许选择
                                        $(this).prop('checked',false);
                                        return;
                                    }
                                    arg_list.push({
                                        'value':$(this).val(),
                                        'name':$(this).attr('name')
                                    });
                                }
                            }
                        }else{
                            if(idx != -1){ // 去除对应的
                                arg_list.splice(idx,1);
                            }
                        }
                    })
                    var li = $('<li>',{"class":'arg_item'}).append(check_box).append(label);
                    ul.append(li);
                });
            }

            // 初始化左右选择器
            function initSelect(isLeft){
                var sel = isLeft?'#left_parameter':'#right_parameter';
                var opt = isLeft?'#query_left_main':'#query_right_main';
                var arg_list = isLeft?left_checked_arg_list:right_checked_arg_list;
                if($(sel).data('select2')!==undefined){
                    $(sel).select2('destroy');
                    $(sel).empty().append($('<option>'));
                    $(opt).empty();
                    $(sel).select2({ data:unit,placeholder:'请选择' });
                }else{
                    $(sel).select2({ data:unit,placeholder:'请选择' });
                }

                $(sel).unbind('select2:select').on('select2:select',function(evt){
                    var sel = evt.params.data.id;
                    var listData = param[sel];
                    arg_list.splice(0,arg_list.length);
                    var u = unit.filter(function(elm){
                        return elm.id === sel;
                    }).shift();
                    arg_list.unit = u['text'];
                    createList(opt,listData,isLeft);
                });
                // 清空上次选择结果
                arg_list.splice(0,arg_list.length);
            }

            initSelect(true);
            initSelect(false);
            $(".query_cover").show();
        })

        // 取消按钮
        $(".cancel_btn").on("click",function(){
            $(".query_cover").hide();
        })
        // 确定
        $(".sure_btn").on("click",function(){
            var combineList = left_checked_arg_list.concat(right_checked_arg_list);
            var yAxiesList = left_checked_arg_list.map(function(e){
                return 0;
            }).concat(right_checked_arg_list.map(function(e){
                return 1;
            }));
            var args = combineList.reduce(function(pre,cur){
                return pre + ',' + cur.value;
            },'').slice(1);
            var argNames = combineList.map(function(elm){
                return elm.name;
            });
            $("#pnames").val(args);
            $("#knames").val(argNames);
            $("#yAxiesList").val(yAxiesList);
            // TODO: 此处可以获取左边参数和右边参数的选择结果并发起ajax请求
            tab5(0);
            // 取消弹窗
            $(".query_cover").hide();
            loadingShow("${request.contextPath!}");
        })

        // 默认模板的按钮事件
        $(".module_btn button").on("click",function(){
            $(".module_btn button").removeClass('module_active');
            $(this).addClass('module_active');
            loadingShow("${request.contextPath!}");
        })

        // 模式一选择按钮点击
        $('#md1').on('click',function(){
            /**
             * 传参:
             * 土压上 A0004 bar 左 index 0
             * 总推力 B0006 kN 左 index 1
             * 扭矩   B0004 kNm 右 index 2
             * 掘进速度 B0001 'mm/min' 右 index 3
             * 刀盘转速 B0002 rpm 右 index 4
             */
            tab5(1);
        });
        // 模式二选择按钮点击
        $('#md2').on('click',function(){
            /**
             * 传参:
             * 土压力 A0004+A0005+A0007+A0009+A0011 bar 左 index 0
             * 总推力 B0006 kN 右 index 1
             * 铰接压力 E0019 kN 右 index 1
             */
            tab5(2);
        });
        // 模式三选择按钮点击
        $('#md3').on('click',function(){
            /**
             * 参数:
             * 千斤顶油压差上下 C0009-C0013 bar 左 index 0
             * 千斤顶行程差上下 C0001-C0005 mm 左 index 1
             * 盾尾垂直偏差 J0025 mm 右 index 2
             * 垂直趋势 J0027  右 index 3
             */
            tab5(3);
        });
        // 模式四选择按钮点击
        $('#md4').on('click',function(){
            /**
             * 参数：
             * 液压邮箱油温 B0012 °C 左 index 0
             * 齿轮油油温 B0011 °C 右 index 1
             */
            tab5(4);
        });
        // 模式五选择按钮点击
        $('#md5').on('click',function(){
            /**
             * 参数
             * 千斤顶油压差（左右） C0015-C0011 bar 左 index 0
             * 千斤顶行程差（左右） C0007-C0003 mm 左 index 1
             * 盾尾水平偏差 J0024 mm 右 index 2
             * 水平趋势 J0026 右 index 3
             */
            tab5(5);
        });
        // 模式六选择按钮点击
        $('#md6').on('click',function(){
            /**
             * 参数
             * 发泡剂注入流量 H0044 'L/min' 左 index 0
             * 掘进速度 B0001 'mm/min' 右 index 1
             */
            tab5(6);
        });
        // 模式七选择按钮点击
        $('#md7').on('click',function(){
            /**
             * 参数
             * 注浆量 D0023 m³ 左 index 0
             * 掘进速度 B0001 'mm/min' 右 index 1
             */
            tab5(7);
        });

        // 模式八
        $('#md8').on('click',function(){
            /**
             * 参数
             * 前盾水平偏差 J0020 m³ 左 index 0
             * 前盾垂直偏差 J0021 m³ 左 index 0
             * 尾盾水平偏差 J0024 'mm/min' 右 index 1
             * 尾盾垂直偏差 J0025 'mm/min' 右 index 1
             */
            tab5(8);
        });

        function getDataModel(){
          var title = $('button[data-id="static-datamodel"]').attr("title");
          switch(title){
            case "最大值": return 1;
            case "最小值": return 2;
            case "平均值": return 3;
            default : return 100;
          }
        }

        $('.tab5dataModel>.btn-group').on('hidden.bs.dropdown',function(){
          var title = $('button[data-id="static-datamodel"]').attr("title");
          if(title == '默认采样值'){
            // 清空所有复选按钮，复选按钮disable
            $('.tab5typeCheck').prop('checked',false);
            $('.tab5typeCheck').prop('disabled',true);
          }else{
            // 复选按钮状态设置为enable
            $('.tab5typeCheck').removeAttr('disabled');
              $('#dataType1').prop('checked',true);
          }
        });

        function getDataType(){
          var dataType1 = $('#dataType1').is(':checked') ? 3 : 0;
          var dataType2 = $('#dataType2').is(':checked') ? 4 : 0;
          var dataType3 = $('#dataType3').is(':checked') ? 5 : 0;
          return dataType1 + dataType2 + dataType3;
        }

        function tab5(model){
            currentMode = model;
            var v=$('input[name="radiobox"]:checked').val();

            beginTime = transformDate1($("#beginTime5").val());
            endTime = transformDate2($("#endTime5").val());
            if(v=='t'||v=='r'){
                ax_request.get('/find/static/tab5',{
                    params:{
                        intervalId:$("#intervalId").val(),
                        leftOrRight:$("#leftOrRight").val(),
                        beginTime:beginTime,
                        endTime:endTime,
                        beginRing:$("#beginRing5").val(),
                        endRing:$("#endRing5").val(),
                        model:model,
                        type:v,
                        ks:''+$("#pnames").val(),
                        kns:''+$("#knames").val(),
                        indxs:''+$("#yAxiesList").val(),
                        datamodel: getDataModel(),
                        datatype: getDataType()
                    }
                }).then(function(result){
                    if(!result.data.keys||!result.data.values){
                        alert("没有查询到对应数据");
                    }
                    option.legend.data=result.data.pNames;
                    option.xAxis.data=result.data.keys?result.data.keys:[];
                    option.series=result.data.values?result.data.values:[];
                    optionUpdate(option,model);
                    myChart.clear();
                    myChart.setOption(option);
                    loadingDismiss();
                }).catch(function(err){
                    console.error(err);
                    alert("查询超时");
                    loadingDismiss();
                });
            }else{
                alert('请选择起始日期或起始环号');
                loadingDismiss();
            }

        }
    });

    // 触发第一页加载
    $('#tab1').tab('show');
</script>
</body>
</html>
