<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>盾构信息监控/区间</title>
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="<@s.url'/plugins/bower/bootstrap/dist/css/bootstrap.min.css'/>">
	<link rel="stylesheet" href="<@s.url'/plugins/bower/AdminLTE/dist/css/AdminLTE.min.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/bower/select2/dist/css/select2.min.css'/>" media="all">
	<link rel="stylesheet" href="<@s.url'/plugins/css/base.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/index.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/common.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/progress.css'/>">
    <link rel="stylesheet" href="<@s.url'/plugins/css/table.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url'/plugins/css/info_monitor.css'/>">

    <link rel="stylesheet" href="<@s.url '/plugins/bower/jstree/dist/themes/default/style.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/tree.css'/>" media="all">
    <link rel="stylesheet" href="<@s.url '/plugins/css/item_generalize.css'/>">

</head>
<body class="sidebar-mini skin-blue wysihtml5-supported">
	<input type="hidden" id="intervalId" name="intervalId" value="${(intervalId)!''}">
	<div class="nav-tabs-custom">
        <ul class="nav nav-tabs">
 			<li class="active">
          		<a id="tab1" href="#tab_1" data-toggle="tab" aria-expanded="true">工程介绍</a>
          	</li>
          	<li class="">
          		<a id="tab2" href="#tab_2" data-toggle="tab" aria-expanded="false">平面图</a>
          	</li>
          	<li class="">
          		<a id="tab3L" href="#tab_3L" data-toggle="tab" aria-expanded="false">剖面图-左线</a>
          	</li>
            <li class="">
                <a id="tab3R" href="#tab_3R" data-toggle="tab" aria-expanded="false">剖面图-右线</a>
            </li>
          	<li class="">
          		<a id="tab4" href="#tab_4" data-toggle="tab" aria-expanded="false">监测数据</a>
          	</li>
          	<li class="">
          		<a id="tab5" href="#tab_5" data-toggle="tab" aria-expanded="false">风险组段划分</a>
          	</li>
        </ul>
        <div class="tab-content">
          	<div class="tab-pane active" id="tab_1">
                <iframe id="iframe_content" name="iframe_content" scrolling="auto" <#if intervalUrl?? && intervalUrl != "">src="${(intervalUrl)+'#view=FitH&toolbar=0&navpanes=0&scrollbar=0&zoom=90'!''}"</#if> frameborder="0" width="100%" height="100%">
                </iframe>
          	</div>
          	<div class="tab-pane" id="tab_2" style="overflow:hidden">
          		<iframe id="iframe_content2" name="iframe_content" scrolling="auto" src="" frameborder="0" width="100%" height="100%">
                </iframe>
          	</div>
            <div class="tab-pane" id="tab_3L" style="overflow:hidden">
                <iframe id="iframe_content3L" name="iframe_content" scrolling="auto" src="" frameborder="0" width="100%" height="100%">
                </iframe>
            </div>
            <div class="tab-pane" id="tab_3R" style="overflow:hidden">
                <iframe id="iframe_content3R" name="iframe_content" scrolling="auto" src="" frameborder="0" width="100%" height="100%">
                </iframe>
            </div>
          <!-- 	<div class="tab-pane" id="tab_3">
            	剖面图
          	</div> -->
          	<div class="tab-pane clearfix" id="tab_4">
          		<div class="tabWrap">
	          		<div class="settlement_header ">
	                	<span class="select_text">选择沉降点：</span>
		                <div class="input-group margin">
		                    <select id="intervalSpId" class="form-control">
			          			<#if sps??>
			          			<#list sps as o>
			          			<option value="${(o.id)!''}">${(o.spName)!''}</option>
			          			</#list>
			          			</#if>  
		                    </select>
		                    <span class="input-group-btn">
		                        <button id="settlement_sbtn" class="btn btn-info btn-sm">查询</button>
		                    </span>
		                </div>
	                </div>
	                <div class="gridWrap">
	                	<div id="settlement_point_grid1" class="gridArea"></div>
	                </div>
	        	</div>
          	</div>
          	<div class="tab-pane" id="tab_5">
                <iframe id="iframe_content4" name="iframe_content" class="iframe-content" scrolling="auto"  src="" frameborder="0" width="100%">
                </iframe>
          	</div>
        </div>
  	</div>

    <!-- jQuery 2.2.0 -->
    <script src="<@s.url'/plugins/bower/jquery/dist/jquery.min.js'/>"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="<@s.url'/plugins/bower/bootstrap/dist/js/bootstrap.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/echarts/dist/echarts.min.js'/>"></script>
    <script src="<@s.url'/plugins/js/progressbar.js'/>"></script>
    <script src="<@s.url'/plugins/js/base.js'/>"></script>
    <script src="<@s.url'/plugins/bower/axios/dist/axios.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/jquery-dateFormat/dist/jquery-dateFormat.min.js'/>"></script>
    <script src="<@s.url'/plugins/bower/select2/dist/js/select2.min.js'/>"></script>
  	<script type="text/javascript">
	    
        var ax_request = axios.create({
            baseURL:'${request.contextPath}/monitor/info',
            timeout:6000
        });

        var isping = true;
        var ispingGo = true;
        var ispouL = true;
        var ispouLGo = true;
        var ispouR = true;
        var ispouRGo = true;
        var isrisk = true;
        var goNumAll = 0;
        var pingLorR;

        $('#tab2').on('shown.bs.tab',function(e){
            if(isping) {
                isping = false;
                if(ispingGo) {
                    $("#iframe_content2").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=1");
                }else {
                    $("#iframe_content2").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=1&goNum="+goNumAll+"&pingLorR="+pingLorR);

                }

            }
        });

        $('#tab3L').on('shown.bs.tab',function(e){
            if(ispouL) {
                ispouL = false;
                if(ispouLGo) {
                    $("#iframe_content3L").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=2");
                }else{
                    $("#iframe_content3L").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=2&goNum="+goNumAll);
                }
            }
        });
        $('#tab3R').on('shown.bs.tab',function(e){
            if(ispouR) {
                ispouR = false;
                if(ispouRGo) {
                    $("#iframe_content3R").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=3");
                }else{
                    ispouRGo=false;
                    $("#iframe_content3R").attr("src", "${request.contextPath}/monitor/info/to/area/project?intervalId=${(intervalId)!0}&type=3&goNum="+goNumAll);
                }
            }
        });
        
        $('#tab4').on('shown.bs.tab',function(e){
            
            $('#intervalSpId').select2();
            // 查询按钮回调
            $('#settlement_sbtn').on('click',function(){
                // 选择的沉降点
            	getData();
				loadingShow("${request.contextPath!}");
            });

            var container = $('#settlement_point_grid1')[0];
            var spc_1 = echarts.getInstanceByDom(container) || echarts.init(container);
            var timeStr = $.format.date(new Date(),'yyyy-MM-dd hh:mm:ss');
            var dataTime = [];

            // 指定图表的配置项和数据
            var option = {
                title: {
                    left: 'left',
                    text: '沉降点监测数据'
                },
                tooltip: {
                	alwaysShowContent:true
                },
                grid:[
                    {x:'5%',y:'15%',width:"85%",height:"32%"},
                    {x:'5%',y:'62%',width:"85%",height:"32%"}
                ],
                xAxis: [
                    {gridIndex:0,min:-30,max:50,name:"距离 m"},
                    {gridIndex:1,min:-30,max:50,name:"距离 m"}
                ],
                yAxis:[
                    {type:"value",gridIndex:0,name:"累计沉降值 mm",boundaryGap:true},
                    {type:"value",gridIndex:1,name:"沉降速率 mm/d",boundaryGap:true}
                ],
                dataZoom:{type:'inside',xAxisIndex:[0,1]},
                series: [{
                    name: '累计沉降值',
                    type: 'line',
                    xAxisIndex:0,
                    yAxisIndex:0,
                    label:{
                    	normal:{
                    		show:true
                    	}
                    },
                    data: []
                },{
                    name: '沉降速率',
                    type: 'line',
                    xAxisIndex:1,
                    yAxisIndex:1,
                    label:{
                    	normal:{
                    		show:true
                    	}
                    },
                    data: []
                }]
            };            
            spc_1.setOption(option);
            //getData();
            
            function getData(){
                // 发起请求
                ax_request.get('/find/interval/monitor/datas?intervalId='+$("#intervalId").val()+'&intervalSpId='+$("#intervalSpId").val())
                    .then(function(res){    
                    	if (res.data.dataTime){          		
	                    	dataTime = res.data.dataTime.map(function(date){
	                    		return date;
	                    	});
                    	}
                        option.title.text = res.data.title;
                        option.series[0].data = res.data.grandSettlement;
                        option.series[1].data = res.data.speedSettlement;
                        option.tooltip.formatter = function(params,ticket,callback){
                            var tag = params.seriesName+":"+params.data[1];
                            tag += '<br/>';
                            tag += '数据时间:'+dataTime[params.dataIndex];
                            return tag;
                        };
                        spc_1.clear();
                        spc_1.setOption(option);                    	
                    	loadingDismiss();
                    })
                    .catch(function(err){
                    	console.error(err);
                    	loadingDismiss();
                    });
            }
        });
        $('#tab5').on('shown.bs.tab',function(e){
            if(isrisk) {
                isrisk = false;
                //$("#iframe_content4").attr("src", "${request.contextPath}/monitor/info/riskinfo?intervalId=${(intervalId)!0}");
                $("#iframe_content4").attr("src", "${(riskPdfUrl)!''}");
            }
        });


        function autoFrameSize(){
            $("#iframe_content4").css('height',function(){
                var screenHeight = window.innerHeight;
                var offset = $("#iframe_content4")[0].offsetTop;
                return screenHeight-offset;
            });
        }
        $(window).resize(autoFrameSize);
        autoFrameSize();

        function gotoTab2(goNum,leftOrRinght) {
            if(!isping) {
                $('#tab2').tab('show');
                if (leftOrRinght == "左线") {
                    $("#iframe_content2")[0].contentWindow.goNumL(goNum);
                } else {
                    $("#iframe_content2")[0].contentWindow.goNumR(goNum);
                }
            }else {
                goNumAll=goNum;
                ispingGo=false;
                if (leftOrRinght == "左线") {
                    pingLorR="l"
                } else {
                    pingLorR="r";
                }

                $('#tab2').tab('show');
            }
        }
        function gotoTab3L(goNum) {
            if(!ispouL) {
                $('#tab3L').tab('show');
                $("#iframe_content3L")[0].contentWindow.goNum(goNum);
            }else {
                goNumAll=goNum;
                ispouLGo=false;
                $('#tab3L').tab('show');
            }
        }
        function gotoTab3R(goNum) {
            if(!ispouR) {
                $('#tab3R').tab('show');
                $("#iframe_content3R")[0].contentWindow.goNum(goNum);
            }else {
               goNumAll=goNum;
               ispouRGo=false;
                $('#tab3R').tab('show');
            }
        }

	</script>
</body>
</html>
