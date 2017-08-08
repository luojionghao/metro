
<input type="hidden" name="id" value="${model.id?c!''}"/>
<div class="modal-content middle">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="updateclose();">
            <span aria-hidden="true">×</span>
        </button>
        <span class="modal-title">预警信息</span>
    </div>
    <div class="modal-body">
        <ul class="warn_info_list">
            <li>
                <span class="arg_tag">参数：</span>
                <select id="arg_select_edit" class="para_select" name="param">
				<#if dics ?? && dics?size gt 0>
					<#list dics as dic>
						<#if dic.dicName = model.param>
                            <option value="${dic.dicName!''}" selected="selected">${dic.dicMean!''}</option>
						<#else>
                            <option value="${dic.dicName!''}">${dic.dicMean!''}</option>
						</#if>
					</#list>
				</#if>
                </select>
            </li>
            <li>
                <input id="categoryVal" type="hidden" name="category" class="" value="${model.category!''}">
                <div id="editWarnTypeCheck">
                    <span class="arg_tag">预警类型：</span>
                    <input id="edit-warn-advance" class="warn-type-check" value="1" v-model="warnCheck" type="checkbox" >
                    <label for="edit-warn-advance">推进预警</label>
                    <input id="edit-warn-assembling" class="warn-type-check" value="2" v-model="warnCheck" type="checkbox" >
                    <label for="edit-warn-assembling">拼装预警</label>
                    <input id="edit-warn-down" class="warn-type-check" value="4" v-model="warnCheck" type="checkbox" >
                    <label for="edit-warn-down">停机预警</label>
                </div>
            </li>
            <li>
                <span class="arg_tag">开始环号：</span>
                <input type="number" name="startRingNum" class="start_num" value="${model.startRingNum!''}">
            </li>
            <li>
                <span class="arg_tag">结束环号：</span>
                <input type="number" name="endRingNum" class="end_num" value="${model.endRingNum!''}">
            </li>
            <li>
                <span class="arg_tag">红色预警上限：</span>
                <input type="number" step="0.01" name="redWarningMax" class="red_upper" value="${model.redWarningMax!''}">
            </li>
            <li>
                <span class="arg_tag">橙色预警上限：</span>
                <input type="number" step="0.01" name="orangeWarningMax" class="orange_upper" value="${model.orangeWarningMax!''}">
            </li>
            <li>
                <span class="arg_tag">橙色预警下限：</span>
                <input type="number" step="0.01" name="orangeWarningMin" class="orange_floor"  value="${model.orangeWarningMin!''}">
            </li>
            <li>
                <span class="arg_tag">红色预警下限：</span>
                <input type="number" step="0.01" name="redWarningMin" class="red_floor" value="${model.redWarningMin!''}">
            </li>
        </ul>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="updatecancel();">取消</button>
        <button type="button" class="btn btn-primary" onclick="updatesave();">保存</button>
    </div>
</div>
