<div class="cutter_content">
	<div class="com_head clearfix">
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
	<div class="knife_main">
		<div class="knife_main_bg">
			<img class="ks_bg" src="${(dpUrl)!'${request.contextPath!}/plugins/images/cutter_bg.png'}"/>
			<!-- 左边一列 -->
			<div class="fill pin total_power" tag="B0006" sfx=" KN">${(maps["${head}B0006${sufix}"])!'-'} KN</div>
			<div class="fill pin knife_torque" tag="B0004" sfx=" KNm">${(maps["${head}B0004${sufix}"])!'-'} KNm</div>
			<div class="fill pin forward_speed" tag="B0001" sfx=" mm/min">${(maps["${head}B0001${sufix}"])!'-'} mm/min</div>
			<div class="fill pin knife_velocity" tag="B0002" sfx=" rpm">${(maps["${head}B0002${sufix}"])!'-'} rpm</div>
			<div class="fill pin gear_oil" tag="B0011" sfx="°C">${(maps["${head}B0011${sufix}"])!'-'} °C</div>
			<div class="fill pin hydraulic_oil" tag="B0012" sfx="°C">${(maps["${head}B0012${sufix}"])!'-'} °C</div>
			<div class="pin prepare">预留</div>
			<div class="pin has">有</div>

			<!-- 行程，油压 -->
			<div class="fill pin route_num1" tag="C0001" sfx=" mm">${(maps["${head}C0001${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num1" tag="C0009" sfx=" bar">${(maps["${head}C0009${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num2" tag="C0007" sfx=" mm">${(maps["${head}C0007${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num2" tag="C0015" sfx=" bar">${(maps["${head}C0015${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num3" tag="C0003" sfx=" mm">${(maps["${head}C0003${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num3" tag="C0011" sfx=" bar">${(maps["${head}C0011${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num4" tag="C0005" sfx=" mm">${(maps["${head}C0005${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num4" tag="C0013" sfx=" bar">${(maps["${head}C0013${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num5" tag="E0008" sfx=" mm">${(maps["${head}E0008${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num5" tag="E0016" sfx=" bar">${(maps["${head}E0016${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num6" tag="E0002" sfx=" mm">${(maps["${head}E0002${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num6" tag="E0010" sfx=" bar">${(maps["${head}E0010${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num7" tag="E0006" sfx=" mm">${(maps["${head}E0006${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num7" tag="E0014" sfx=" bar">${(maps["${head}E0014${sufix}"])!'-'} bar</div>
			<div class="fill pin route_num8" tag="E0004" sfx=" mm">${(maps["${head}E0004${sufix}"])!'-'} mm</div>
			<div class="fill pin oil_num8" tag="E0012" sfx=" bar">${(maps["${head}E0012${sufix}"])!'-'} bar</div>
			<!-- 土压 -->
			<div class="fill pin earth_pressure1" tag="A0004" sfx=" bar">${(maps["${head}A0004${sufix}"])!'-'} bar</div>
			<div class="fill pin earth_pressure2" tag="A0005" sfx=" bar">${(maps["${head}A0005${sufix}"])!'-'} bar</div>
			<div class="fill pin earth_pressure3" tag="A0011" sfx=" bar">${(maps["${head}A0011${sufix}"])!'-'} bar</div>
			<div class="fill pin earth_pressure4" tag="A0007" sfx=" bar">${(maps["${head}A0007${sufix}"])!'-'} bar</div>
			<div class="fill pin earth_pressure5" tag="A0009" sfx=" bar">${(maps["${head}A0009${sufix}"])!'-'} bar</div>
			<!-- 铰接总压力 -->
			<div class="fill pin all_hinge" tag="E0019" sfx=" bar">${(maps["${head}E0019${sufix}"])!'-'} bar</div>
			<!-- 同步注浆 -->
			<div class="fill pin sync_grout_amount" tag="D0023" sfx=" m³">${(maps["${head}D0023${sufix}"]?string('#.##'))!'-'} m³</div>
			<!-- 注浆量 -->
			<div class="fill pin grout_amount1" tag="D0008" sfx=" m³">${(maps["${head}D0008${sufix}"]?string("#.##"))!'-'} m³</div>
			<div class="fill pin grout_amount2" tag="D0010" sfx=" m³">${(maps["${head}D0010${sufix}"]?string("#.##"))!'-'} m³</div>
			<div class="fill pin grout_amount3" tag="D0012" sfx=" m³">${(maps["${head}D0012${sufix}"]?string("#.##"))!'-'} m³</div>
			<div class="fill pin grout_amount4" tag="D0014" sfx=" m³">${(maps["${head}D0014${sufix}"]?string("#.##"))!'-'} m³</div>
			<!-- 压力 -->
			<div class="fill pin pressure1" tag="D0016" sfx=" bar">${(maps["${head}D0016${sufix}"])!'-'} bar</div>
			<div class="fill pin pressure2" tag="D0018" sfx=" bar">${(maps["${head}D0018${sufix}"])!'-'} bar</div>
			<div class="fill pin pressure3" tag="D0020" sfx=" bar">${(maps["${head}D0020${sufix}"])!'-'} bar</div>
			<div class="fill pin pressure4" tag="D0022" sfx=" bar">${(maps["${head}D0022${sufix}"])!'-'} bar</div>
		</div>
	</div>
</div>