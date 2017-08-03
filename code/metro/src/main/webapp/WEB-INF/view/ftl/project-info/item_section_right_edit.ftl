<input type="hidden" name="id" value="${model.id?c!''}"/>
<div class="modal-content middle">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close"  onclick="spupdateclose();">
			<span aria-hidden="true">×</span>
		</button>
		<span class="modal-title">沉降点信息</span>
	</div>
	<div class="modal-body">
		<ul class="warn_info_list">
			<li><span>沉降点名称：</span> <input type="text" name="spName" value="${model.spName!''}"
				class="settling_name"></li>
			<li><span>初始里程：</span> <input type="text" name="originMileage" value="${model.originMileage!''}">
			</li>
			<li><span>大地坐标X：</span> <input type="text" name="mapX" value="${model.mapX!''}"
				class="x_axis"></li>
			<li><span>大地坐标Y：</span> <input type="text" name="mapY" value="${model.mapY!''}"
				class="y_axis"></li>
			<li><span>累计沉降正值：</span> <input type="text" name="spSumAdd" value="${model.spSumAdd!''}"
				class="upright"></li>
			<li><span>累计沉降负值：</span> <input type="text" name="spSumSub" value="${model.spSumSub!''}"
				class="negative"></li>
			<li><span>沉降速率预警值：</span> <input type="text" value="${model.spSpeedWarningVal!''}"
				name="spSpeedWarningVal" class="wran_value"></li>
			<li>
                 <span>线路：</span>
                 <select class="state_select" name="leftOrRight">
                 <#if model.leftOrRight == "l">
                     <option value="l" selected="selected">左线</option>
                     <option value="r">右线</option>
                 <#else>
                     <option value="l">左线</option>
                     <option value="r" selected="selected">右线</option>
                 </#if>
                 </select>
	        </li>
		</ul>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default pull-left"
			data-dismiss="modal" onclick="spupdatecancel();">取消</button>
		<button type="button" class="btn btn-primary save_btn2"
			onclick="spupdatesave();">保存</button>
	</div>
</div>