
<div class="guide_content">
	<div class="com_head clearfix">
		<div class="com_head_left clearfix">
			<div class="current_loop">
				<div class="current_loop_text">当前环</div>
				<div class="fill current_loop_num" tag="A0001">${(maps["${head}A0001${sufix}"])!'-'}</div>
				<button tag="A0002" map='{"0":"停机","1":"停机","3":"推进","4":"拼装","def":"停机"}' class="fill stop_btn">
				<#if maps??&&head??&&sufix??&&maps["${head}A0002${sufix}"]??>
					<#if maps["${head}A0002${sufix}"]=4>
						拼装
					<#elseif maps["${head}A0002${sufix}"]=3>
						推进
					<#else>
						停机
					</#if>
					</#if>
				</button><!-- 工作状态 -->
			</div>
			<div class="line_info ver_center">
				<div class="line_info_text">
					<span class="fill" prop="lineName">${(maps["lineName"])!''}</span>
					-【<span class="fill" prop="intervalName">${(maps["intervalName"])!''}</span>】-
					<span class="fill" prop="leftOrRight" map='{"l":"左线L","r":"右线R"}'>
				<#if maps??&&maps["leftOrRight"]??>
				<#if maps["leftOrRight"]="l">
					左线L
				<#else>
					右线R
				</#if>
				</#if>
					</span>
				</div>
				<div class="line_info_time">
					<span class="fill" prop="ringNum" pfx="总环数:">总环数:${(maps["ringNum"])!'-'}</span>
					<span class="fill now_time" prop="nowTime" pfx="时间：">时间：${(nowTime)!''}</span>
				</div>
			</div>
		</div>
		<div class="com_head_right">
			<table class="guide_head_table">
				<tr>
					<td>名称</td>
					<td>前</td>
					<td>中</td>
					<td>后</td>
					<td>趋势</td>
				</tr>
				<tr>
					<td>垂直</td>
					<td class="fill" tag="J0021" sfx=" mm">${(maps["${head}J0021${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0023" sfx=" mm">${(maps["${head}J0023${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0025" sfx=" mm">${(maps["${head}J0025${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0027" sfx=" mm">${(maps["${head}J0027${sufix}"])!'-'}mm</td>
				</tr>
				<tr>
					<td>水平</td>
					<td class="fill" tag="J0020" sfx=" mm">${(maps["${head}J0020${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0022" sfx=" mm">${(maps["${head}J0022${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0024" sfx=" mm">${(maps["${head}J0024${sufix}"])!'-'}mm</td>
					<td class="fill" tag="J0026" sfx=" mm">${(maps["${head}J0026${sufix}"])!'-'}mm</td>
				</tr>
			</table>
		</div>
	</div>
	<div class="guide_main">
   		<div class="grid row no-pad">
            <div id="chart-left" class="grid-left col-xs-3"></div>
            <div class="grid-right col-xs-9">
                <canvas id="prosture" class="col-xs-12"></canvas>
                <div id="chart-right" class="col-xs-12"></div>
            </div>
        </div>
   		<div class="coord_data">
   			<div class="coord_data_title">坐标数据</div>
   			<table class="coord_data_table">
   				<tr>
   					<td>坐标</td>
   					<td>X</td>
   					<td>Y</td>
   					<td>Z</td>
                    <td>水平偏差</td>
                    <td>垂直偏差</td>
   				</tr>
   				<tr>
   					<td>前点坐标</td>
   					<td class="fill" tag="J0002" sfx=" m">${(maps["${head}J0002${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0003" sfx=" m">${(maps["${head}J0003${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0004" sfx=" m">${(maps["${head}J0004${sufix}"])!'-'}m</td>
					<td>${(maps["hfront"])!''}</td>
					<td>${(maps["vfront"])!''}</td>
   				</tr>
   				<tr>
   					<td>中点坐标</td>
   					<td class="fill" tag="J0005" sfx=" m">${(maps["${head}J0005${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0006" sfx=" m">${(maps["${head}J0006${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0007" sfx=" m">${(maps["${head}J0007${sufix}"])!'-'}m</td>
                    <td>${(maps["hMedium"])!''}</td>
                    <td>${(maps["vMedium"])!''}</td>
   				</tr>
   				<tr>
   					<td>后点坐标</td>
   					<td class="fill" tag="J0008" sfx=" m">${(maps["${head}J0008${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0009" sfx=" m">${(maps["${head}J0009${sufix}"])!'-'}m</td>
   					<td class="fill" tag="J0010" sfx=" m">${(maps["${head}J0010${sufix}"])!'-'}m</td>
                    <td>${(maps["hBack"])!''}</td>
                    <td>${(maps["vBack"])!''}</td>
   				</tr>
   			</table>
   			<div class="coord_data_title">
   				<span class="fill" tag="J0001" pfx="里程：" sfx=" m">里程：${(maps["${head}J0001${sufix}"])!'-'} m</span>
   				<span class="fill angle" tag="J0011" pfx="滚动角：" sfx=" °">滚动角：${(maps["${head}J0011${sufix}"])!'-'}°</span>
   				<span class="fill angle" tag="J0012" pfx="前盾体俯仰角：" sfx=" °">前盾体俯仰角：${(maps["${head}J0012${sufix}"])!'-'}°</span>
   			</div>
   			<table class="cutter_table">
   				<caption>刀盘纠偏量（：mm/环）</caption>
   				<tr id="knife_head_container">
   					<td>环号</td>
   					<#list 1..10 as i >
					   <#assign suma =((maps["${head}A0001${sufix}"])!0)-i   > 
					   <td>
					   <#if suma gt 0 >
					   ${suma}
					   <#else>
					   -
					   </#if>
					   </td>
   					</#list>
   				</tr>
   				<tr id="knife_h_container">
   					<td>水平</td>
   					<#if maps??>
					   <#if maps['ringNumH']??>
						   <#list maps['ringNumH'] as rnh >
						   <td>
						   	${(rnh)!'-'}
						   	</td>
						   </#list>
					   </#if>	
					 </#if>
   				</tr>
   				<tr id="knife_v_container">
   					<td>垂直</td>		
   					<#if maps??>							   
					   <#if maps['ringNumV']??>
						   <#list maps['ringNumV'] as rnv >
						   <td>
						   	${(rnv)!'-'}
						   	</td>
						   </#list>
					   </#if>
					</#if>									   
   				</tr>
   			</table>
   		</div>
	</div>
</div>
<script>
// 请求器
var ax_request = axios.create({
    baseURL:'${request.contextPath}/monitor/info',
    timeout:60000
});
// 左边导向图
var container_l = $('#chart-left')[0];
var chart = echarts.getInstanceByDom(container_l) || echarts.init(container_l);
var option = {
	grid: {
        left: '3%',
        right: '5%',
        bottom: '3%',
        top:'5%',
        containLabel: true
    },
    xAxis:{ 
        type:"value",
        min:-101,
        max:100,
        splitLine:{
            show:false
        },
    },
    yAxis:{ 
        type:"value", 
        min:0,
        max:100,
        scale:true,
        boundaryGap:true,
        nameGap:0,
        axisLabel:{
            rotate:-30
        }
    },
    series:[{
        name:'偏移量',
        type:'line',
        data:[[-50,27214],[60,27218]]
    }]
}


// 中间导向图
var p = new Prosture('prosture');
// 这里做数据请求，得到前盾水平偏差、前盾垂直偏差、后盾水平偏差、后盾垂直偏差，分别设置为p的x1,y1,x2,y2

	            //底部导向图 
var container_2 = $('#chart-right')[0];
var chart2 = echarts.getInstanceByDom(container_2) || echarts.init(container_2);
var option2 = {
	grid: {
        left: '3%',
        right:'3%',
        top:'5%',
        bottom:'6%',
        containLabel: true
    },
    xAxis:{ 
        type:"value",
        min:0,
        max:100,
        scale:true,
        boundaryGap:0,
        axisLabel:{
        	rotate:-30
        },
        nameGap:0,
    },
    yAxis:{ 
        type:"value", 
        min:-101,
        max:100,
        splitLine:{
            show:false
        },
    },
    series:[{
        name:'偏移量',
        type:'line',
        data:[[27210,-35],[27218,80]]
    }]
}

chart2.setOption(option2);
var b1=true; 
var b2=false;
var res1=null;
var res2=null;
// 发起请求
function getdata2(){	
	ax_request.get('/find/lr/monitor/datas/now?intervalId='+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val())
    .then(function(res){
    	if(res!=null){ 
    		res1=res;
    		b1=true;
    		putv(res1,res2);
    		/*
			//数据填充
			var df = new DataFetcher(res.data);
			df.fill($('.fill'));
			// 刀盘纠偏量表头
			var knif_header = "<td>环号</td>" + [0,1,2,3,4,5,6,7,8,9].map(function(idx){
				return '<td>'+(df.fetchByTag("A0001","-")-idx)+'</td>';
			})
			var knif_h = "<td>水平</td>" + res.data.ringNumH.map(function(elm){
				return '<td>'+elm+'</td>';
			});
			var knif_v = '<td>垂直</td>' + res.data.ringNumV.map(function(elm){
				return '<td>'+elm+'</td>';
			});
			$("#knife_head_container").html(knif_header);
			$('#knife_h_container').html(knif_h);
			$('#knife_v_container').html(knif_v);*/
			
    	}
    })
    .catch(function(err){});
}

function getdata1(){
	ax_request.get('/find/lr/monitor/datas?intervalId='+$("#intervalId").val()+"&leftOrRight="+$("#leftOrRight").val())
    .then(function(res){
    	if(res!=null){  
    		res2=res;
    		b2=true;
    		putv(res1,res2);
			/*
            p.x1 = res.data.x1;
            p.y1 = res.data.y1;
            p.x2 = res.data.x2;
            p.y2 = res.data.y2;
            p.update();
            
            option.series[0].data=eval(res.data.pyl);
            option.yAxis.min=res.data.pmin;
            option.yAxis.max=res.data.pmax;            	            
            chart.setOption(option);

            option2.series[0].data=eval(res.data.pylb); 
            option2.xAxis.min=res.data.bmin;
            option2.xAxis.max=res.data.bmax;  
            chart2.setOption(option2);*/
    	}
    })
    .catch(function(err){});
}

function putv(res1,res2){
	if(b1&&b2){		
		//数据填充
		var df = new DataFetcher(res1.data);
		df.fill($('.fill'));
		// 刀盘纠偏量表头
		var knif_header = "<td>环号</td>" + [0,1,2,3,4,5,6,7,8,9].map(function(idx){
			return '<td>'+(df.fetchByTag("A0001","-")-idx)+'</td>';
		})
		var knif_h = "<td>水平</td>" + res1.data.ringNumH.map(function(elm){
			return '<td>'+elm+'</td>';
		});
		var knif_v = '<td>垂直</td>' + res1.data.ringNumV.map(function(elm){
			return '<td>'+elm+'</td>';
		});
		$("#knife_head_container").html(knif_header);
		$('#knife_h_container').html(knif_h);
		$('#knife_v_container').html(knif_v);
		
        p.x1 = res2.data.x1;
        p.y1 = res2.data.y1;
        p.x2 = res2.data.x2;
        p.y2 = res2.data.y2;
        p.update();
        
        option.series[0].data=eval(res2.data.pyl);
        option.yAxis.min=res2.data.pmin;
        option.yAxis.max=res2.data.pmax;            	            
        chart.setOption(option);

        option2.series[0].data=eval(res2.data.pylb); 
        option2.xAxis.min=res2.data.bmin;
        option2.xAxis.max=res2.data.bmax;  
        chart2.setOption(option2);
        b1=false;
        b2=false;
	}
}
//第一次
//getdata();
//getdata1();

//定时刷新数据
/*
setInterval(function(){
	getdata();
	getdata1();
},8000);*/
</script>
