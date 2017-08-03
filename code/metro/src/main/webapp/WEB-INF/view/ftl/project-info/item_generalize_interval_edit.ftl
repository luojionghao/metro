<div class="modal-content middle">
    <div class="modal-header">
        <button type="button" class="close dismiss" data-dismiss="modal" aria-label="Close" onclick="intervalUpdateClose();">
            <span aria-hidden="true">×</span>
        </button>
        <span class="modal-title">区间信息</span>
    </div>
    <div class="modal-body">
    <li>
        <span>区间名称：</span>
        <input type="hidden" name="id" value="${model.id?c!''}"/> 
        <input type="text" class="section_name_input" name="intervalName" value="${model.intervalName!''}">
    </li> 
    <li class="pro_mark">
        <span>工程标号：</span>
        <input type="number" name="intervalMark" class="section_name_input" value="${model.intervalMark!''}">
    </li>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default pull-left dismiss" data-dismiss="modal" onclick="intervalUpdateCancel();">取消</button>
        <button type="button" class="btn btn-primary" onclick="intervalNameUpdate();">保存</button>
    </div>
</div>