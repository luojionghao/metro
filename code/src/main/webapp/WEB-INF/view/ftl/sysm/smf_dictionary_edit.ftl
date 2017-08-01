		<div class="modal-content middle">
			<input type="hidden" id="operate" name="operate" value="${(operate)!''}">
			<input type="hidden" id="photoId" name="photoId" value="${(obj.id)!''}">
	      	<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="cancel();">
		          	<span aria-hidden="true">×</span>
		        </button>
		        <h4 class="modal-title">刀盘信息</h4>
	      	</div>
		    <div class="modal-body">
                <ul class="cutter_info_list">
                    <li>
                        <span>图片名称：</span>
                        <input type="text" class="name_input" id="photoName" name="photoName" value="${(obj.photoName)!''}">
                    </li>
                    <li>
                        <span>图片类型：</span>
                        <select class="select_bg" id="photoType" name="photoType">
                            <option value="1" <#if obj??&&obj.photoType==1>selected="selected"</#if>>刀盘背景</option>
                            <option value="2" <#if obj??&&obj.photoType==2>selected="selected"</#if>>螺旋背景</option>
                            <option value="3" <#if obj??&&obj.photoType==3>selected="selected"</#if>>泥水背景</option>
                        </select>
                    </li>
                    <li class="clearfix">
                        <span>上传图片：</span>
                        <label class="btn btn-default btn-file btn-sm">
                        	选择文件<input type="file" style="display:none;" class="select_btn" id="photoImg" name="photoImg" title="已上传图片">
                        </label>
                        <span id="fileUpName"></span>
                        <input type="hidden" id="photoUrl" name="photoUrl" value="${(obj.photoUrl)!''}">
                    </li>
                </ul>
		    </div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="cancel();">取消</button>
	        	<button type="button" class="btn btn-primary save_btn" onclick="javascript:fsubmit(document.form1);">保存</button>
	      	</div>
    	</div>