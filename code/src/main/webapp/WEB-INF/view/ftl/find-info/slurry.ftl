<div class="slurry_content">
    <div class="slurry_com_head clearfix">
		<div class="com_head_left clearfix">
			<div class="current_loop">
				<div class="current_loop_text">当前环</div>
				<div tag="A0001" class="fill current_loop_num">${(maps["${head}A0001${sufix}"])!'-'}</div>
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
			<ul class="knife_data ver_center clearfix">
				<li>
					<div class="fill first_row" tag="J0001" pfx="里程 " sfx=" m">里程 ${(maps["${head}J0001${sufix}"])!'-'}m</div>
					<div class="fill" tag="A0003" pfx="推进行程 " sfx=" mm">推进行程  ${(maps["${head}A0003${sufix}"])!'-'}mm</div>
				</li>
				<li>
					<div class="fill first_row" tag="J0011" pfx="滚动角 " sfx="°">滚动角 ${(maps["${head}J0011${sufix}"])!'-'}°</div>
					<div class="fill" tag="J0012" pfx="俯仰角 " sfx="°">俯仰角 ${(maps["${head}J0012${sufix}"])!'-'}°</div>
				</li>
				<li>
					<div class="fill first_row" tag="J0020" pfx="前盾水平偏差 " sfx="mm">前盾水平偏差  ${(maps["${head}J0020${sufix}"])!'-'}mm</div>
					<div class="fill" tag="J0021" pfx="前盾垂直偏差 " sfx="mm">前盾垂直偏差  ${(maps["${head}J0021${sufix}"])!'-'}mm</div>
				</li>
			</ul>
		</div>
	</div>
    <div id="slurry" class="slurry_main">
	    <div class="slurry_container">
	      <img class="slurry_bg" src="${(dpUrl)!'${request.contextPath!}/plugins/images/cutter_bg.png'}" alt="">
	      <!-- 压力泵 -->
	      <div id="pressure_pump_1" class="hold1">
	        <div class="ht t1"><span class="slurry_title">电流</span></div>
	        <div class="ht t2"><span class="slurry_title">速度</span></div>
	        <div class="vt v1" tag="R0007" sfx=" A"><span class="value">${(maps["${head}R0007${sufix}"])!'-'}A</span></div>
	        <div class="vt v2" tag="R0008" sfx=" %"><span class="value">${(maps["${head}R0008${sufix}"])!'-'}%</span></div>
	      </div>
	      <div id="pressure_pump_2" class="hold2">
	        <div class="ht"><span class="slurry_title">管路压力</span></div>
	        <div class="vt" tag="R0017" sfx=" Bar"><span class="value">${(maps["${head}R0017${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="pressure_pump_3" class="hold1">
	        <div class="ht t1"><span class="slurry_title">流量</span></div>
	        <div class="ht t2"><span class="slurry_title">密度</span></div>
	        <div class="vt v1" tag="R0018" sfx=" m3/h"><span class="value">${(maps["${head}R0018${sufix}"])!'-'}m3/h</span></div>
	        <div class="vt v2" tag="R0019" sfx=" kg/L"><span class="value">${(maps["${head}R0019${sufix}"])!'-'}kg/L</span></div>
	      </div>
	      <div id="pressure_pump_4" class="hold2">
	        <div class="ht"><span class="slurry_title">进口压力</span></div>
	        <div class="vt" tag="R0012" sfx=" Bar"><span class="value">${(maps["${head}R0012${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="pressure_pump_5" class="hold2">
	        <div class="ht"><span class="slurry_title">出口压力</span></div>
	        <div class="vt" tag="R0011" sfx=" Bar"><span class="value">${(maps["${head}R0011${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="pressure_pump_6" class="hold1">
	        <div class="ht t1"><span class="slurry_title">电流</span></div>
	        <div class="ht t2"><span class="slurry_title">速度</span></div>
	        <div class="vt v1" tag="R0009" sfx=" A"><span class="value">${(maps["${head}R0009${sufix}"])!'-'}A</span></div>
	        <div class="vt v2" tag="R0010" sfx=" %"><span class="value">${(maps["${head}R0010${sufix}"])!'-'}%</span></div>
	      </div>
	
	      <!-- 中继泵 -->
	      <div id="relay_pump_1" class="hold1">
	        <div class="ht t1"><span class="slurry_title">电流</span></div>
	        <div class="ht t2"><span class="slurry_title">速度</span></div>
	        <div class="vt v1" tag="R0013" sfx=" A"><span class="value">${(maps["${head}R0013${sufix}"])!'-'}A</span></div>
	        <div class="vt v2" tag="R0014" sfx=" %"><span class="value">${(maps["${head}R0014${sufix}"])!'-'}%</span></div>
	      </div>
	      <div id="relay_pump_2" class="hold1">
	        <div class="ht t1"><span class="slurry_title">流量</span></div>
	        <div class="ht t2"><span class="slurry_title">密度</span></div>
	        <div class="vt v1" tag="R0020" sfx=" m3/h"><span class="value">${(maps["${head}R0020${sufix}"])!'-'}m3/h</span></div>
	        <div class="vt v2" tag="R0021" sfx=" kg/L"><span class="value">${(maps["${head}R0021${sufix}"])!'-'}kg/L</span></div>
	      </div>
	      <div id="relay_pump_3" class="hold2">
	        <div class="ht"><span class="slurry_title">出口压力</span></div>
	        <div class="vt" tag="R0016" sfx=" Bar"><span class="value">${(maps["${head}R0016${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="relay_pump_4" class="hold2">
	        <div class="ht"><span class="slurry_title">进口压力</span></div>
	        <div class="vt" tag="R0015" sfx=" Bar"><span class="value">${(maps["${head}R0015${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <!-- 排浆泵 -->
	      <div id="exhaust_pump_1" class="hold1">
	        <div class="ht t1"><span class="slurry_title">流量</span></div>
	        <div class="ht t2"><span class="slurry_title">密度</span></div>
	        <div class="vt v1" tag="R0020" sfx=" m3/h"><span class="value">${(maps["${head}R0020${sufix}"])!'-'}m3/h</span></div>
	        <div class="vt v2" tag="R0021" sfx=" kg/L"><span class="value">${(maps["${head}R0021${sufix}"])!'-'}kg/L</span></div>
	      </div>
	      <div id="exhaust_pump_2" class="hold2">
	        <div class="ht"><span class="slurry_title">出口压力</span></div>
	        <div class="vt" tag="R0004" sfx=" Bar"><span class="value">${(maps["${head}R0004${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="exhaust_pump_3" class="hold2">
	        <div class="ht"><span class="slurry_title">进口压力</span></div>
	        <div class="vt" tag="R0003" sfx=" Bar"><span class="value">${(maps["${head}R0003${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <!-- 冲洗泵 -->
	      <div id="rinse_pump_1" class="hold3">
	        <div class="ht"><span class="slurry_title">补浆流量</span></div>
	        <div class="vt" tag="R0024" sfx=" m3/h"><span class="value">${(maps["${head}R0024${sufix}"])!'-'}m3/h</span></div>
	      </div>
	      <div id="rinse_pump_2" class="hold2">
	        <div class="ht"><span class="slurry_title">平均土压</span></div>
	        <div class="vt" tag="A0013" sfx=" Bar"><span class="value">${(maps["${head}A0013${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="rinse_pump_3" class="hold1">
	        <div class="ht t1"><span class="slurry_title">流量</span></div>
	        <div class="ht t2"><span class="slurry_title">压力</span></div>
	        <div class="vt v1" tag="R0023" sfx=" m3/h"><span class="value">${(maps["${head}R0023${sufix}"])!'-'}m3/h</span></div>
	        <div class="vt v2" tag="R0022" sfx=" Bar"><span class="value">${(maps["${head}R0022${sufix}"])!'-'}Bar</span></div>
	      </div>
	      <div id="rinse_pump_4" class="hold1">
	        <div class="ht t1"><span class="slurry_title">电流</span></div>
	        <div class="ht t2"><span class="slurry_title">速度</span></div>
	        <div class="vt v1" tag="R0005" sfx=" A"><span class="value">${(maps["${head}R0005${sufix}"])!'-'}A</span></div>
	        <div class="vt v2" tag="R0006" sfx=" %"><span class="value">${(maps["${head}R0006${sufix}"])!'-'}%</span></div>
	      </div>
	    </div>
	</div>
</div>