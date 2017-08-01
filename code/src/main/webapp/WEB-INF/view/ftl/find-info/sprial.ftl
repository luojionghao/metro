
<div class="spiral_content">
	<div class="com_head clearfix">
		<div class="com_head_left clearfix">
			<div class="current_loop">
				<div class="current_loop_text">当前环</div>
				<div class="fill current_loop_num" tag="A0001">${(maps["${head}A0001${sufix}"])!'-'}</div>
				<button class="fill stop_btn" tag="A0002" map='{"0":"停机","1":"停机","3":"推进","4":"拼装","def":"停机"}'>
					<#if maps??&&head??&&sufix??&&maps["${head}A0002${sufix}"]??>
					<#if maps["${head}A0002${sufix}"]=4>
						拼装
					<#elseif maps["${head}A0002${sufix}"]=3>
						推进
					<#else>
						停机
					</#if>
					</#if>
				</button>
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
					<span class="fill" prop="ringNum" pfx="总环数: ">总环数:${(maps["ringNum"])!'-'}</span>
					<span class="fill now_time" prop="nowTime" pfx="时间：">时间：${(nowTime)!''}</span>
				</div>
			</div>
		</div>
		<div class="com_head_right">
			<ul class="knife_data ver_center clearfix">
				<li>
					<div class="fill first_row" pfx="里程  " tag="J0001" sfx=" m">里程  ${(maps["${head}J0001${sufix}"])!'-'}m</div>
					<div class="fill" pfx="推进行程 " tag="A0003" sfx=" mm">推进行程 ${(maps["${head}A0003${sufix}"])!'-'}mm</div>
				</li>
				<li>
					<div class="fill first_row" tag="J0011" pfx="滚动角 " sfx=" °">滚动角 ${(maps["${head}J0011${sufix}"])!'-'}°</div>
					<div class="fill" tag="J0012" pfx="俯仰角 " sfx=" °">俯仰角 ${(maps["${head}J0012${sufix}"])!'-'}°</div>
				</li>
				<li>
					<div class="fill first_row" tag="J0020" pfx="前盾水平偏差 " sfx=" mm">前盾水平偏差  ${(maps["${head}J0020${sufix}"])!'-'}mm</div>
					<div class="fill" tag="J0021" pfx="前盾垂直偏差 " sfx=" mm">前盾垂直偏差  ${(maps["${head}J0021${sufix}"])!'-'}mm</div>
				</li>
			</ul>
		</div>
	</div>
	<div class="knife_main">
		<div class="spiral_bg">
			<img class="ks_bg" src="${(lxUrl)!'${request.contextPath!}/plugins/images/spiral_bg.png'} "/>
			<!-- 土压 -->
			<ul class="spiral_soil_pressure">
				
			</ul>
			
			<div class="fill pin soil_pressure1" tag="A0004" sfx=" bar">${(maps["${head}A0004${sufix}"])!'-'} bar</div>
			<div class="fill pin soil_pressure2" tag="A0005" sfx=" bar">${(maps["${head}A0005${sufix}"])!'-'} bar</div>
			<div class="fill pin soil_pressure3" tag="A0011" sfx=" bar">${(maps["${head}A0011${sufix}"])!'-'} bar</div>
			<div class="fill pin soil_pressure4" tag="A0007" sfx=" bar">${(maps["${head}A0007${sufix}"])!'-'} bar</div>
			<div class="fill pin soil_pressure5" tag="A0009" sfx=" bar">${(maps["${head}A0009${sufix}"])!'-'} bar</div>
			<!-- 前后舱压力 -->
			<div class=" pin cabin_pressure1">
				<span class="fill" tag="G0008">${(maps["${head}G0008${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0016" sfx=" bar">${(maps["${head}G0016${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_pressure2">
				<span class="fill" tag="G0024">${(maps["${head}G0024${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0032" sfx=" L">${(maps["${head}G0032${sufix}"])!'-'} L</span>
			</div>
			<div class=" pin cabin_pressure3">
				<span class="fill" tag="G0002">${(maps["${head}G0002${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0010" sfx=" bar">${(maps["${head}G0010${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_pressure4">
				<span class="fill" tag="G0018">${(maps["${head}G0018${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0026" sfx=" L">${(maps["${head}G0026${sufix}"])!'-'} L</span>
			</div>
			<div class=" pin cabin_pressure5">
				<span class="fill" tag="G0007">${(maps["${head}G0007${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0015" sfx=" bar">${(maps["${head}G0015${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_pressure6">
				<span class="fill" tag="G0023">${(maps["${head}G0023${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0031" sfx=" L">${(maps["${head}G0031${sufix}"])!'-'} L</span>
			</div>
			<div class=" pin cabin_pressure7">
				<span class="fill" tag="G0003">${(maps["${head}G0003${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0011" sfx=" bar">${(maps["${head}G0011${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_pressure8">
				<span class="fill" tag="G0019">${(maps["${head}G0019${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0027" sfx=" L">${(maps["${head}G0027${sufix}"])!'-'} L</span>
			</div>
			<!-- 前后舱量 -->
			<div class=" pin cabin_amount1">
				<span class="fill" tag="G0006">${(maps["${head}G0006${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0014" sfx=" bar">${(maps["${head}G0014${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_amount2">
				<span class="fill" tag="G0022">${(maps["${head}G0022${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0030" sfx=" L">${(maps["${head}G0030${sufix}"])!'-'} L</span>
			</div>
			<div class=" pin cabin_amount3">
				<span class="fill" tag="G0004">${(maps["${head}G0004${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0012" sfx=" bar">${(maps["${head}G0012${sufix}"])!'-'} bar</span>
			</div>
			<div class=" pin cabin_amount4">
				<span class="fill" tag="G0020">${(maps["${head}G0020${sufix}"])!'-'}</span>
				/
				<span class="fill" tag="G0028" sfx=" L">${(maps["${head}G0028${sufix}"])!'-'} L</span>
			</div>
			<!-- 转速 -->
			<div class="fill pin rotate_speed" tag="F0001" sfx=" rpm">${(maps["${head}F0001${sufix}"])!'-'} rpm</div>
			<!-- 扭矩 -->
			<div class="fill pin torque" tag="F0002" sfx=" kNm">${(maps["${head}F0002${sufix}"])!'-'} kNm</div>
			<!-- 油压 -->
			<div class="fill pin oil_pressure" tag="F0005" sfx=" bar">${(maps["${head}F0005${sufix}"])!'-'} bar</div>
			<!-- 膨润土总量 -->
			<div class="fill pin total_bentonite" tag="I0020" sfx=" m³">${(maps["${head}I0020${sufix}"])!'-'} m³</div>
			<!-- 后闸门开度 -->
			<div class="fill pin strobe_open" tag="F0006" sfx=" mm">${(maps["${head}F0006${sufix}"])!'-'} mm</div>
			<!-- 泡沫 -->
			<div class="fill pin gas_flow" tag="H0040" sfx=" L/min">${(maps["${head}H0040${sufix}"])!'-'} L/min</div>
			<div class="fill pin liquid_flow" tag="H0041" sfx=" L/min">${(maps["${head}H0041${sufix}"])!'-'} L/min</div>
			<div class="fill pin multiple" tag="H0042">${(maps["${head}H0042${sufix}"])!'-'}</div>
			<div class="fill pin stoste" tag="H0043">${(maps["${head}H0043${sufix}"])!'-'}</div>
		</div>
	</div>
</div>

