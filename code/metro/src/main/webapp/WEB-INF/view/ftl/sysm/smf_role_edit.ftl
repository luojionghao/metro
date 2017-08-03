
    <!-- 新增弹窗 -->
		<div class="modal-content middle">
	      	<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="cancel()">
		          	<span aria-hidden="true">×</span>
		        </button>
		        <span class="modal-title">角色信息</span>
	      	</div>
		    <div class="modal-body">
		    	<input type="hidden" id="roleId" name="roleId" value="${(role.id)!''}">
		        <ul class="user_info">
		        	<li class="clearfix">
		        		<span>角色代号：</span>
		        		<input type="text" id="roleCode" name="roleCode" value="${(role.roleCode)!''}">
		        	</li>
		        	<li class="clearfix">
		        		<span>角色名称：</span>
		        		<input type="text" id="roleName" name="roleName" value="${(role.roleName)!''}">
		        	</li>
		        	<li class="clearfix">
		        		<span>是否启用：</span>
		        		<input type="hidden" id="isUsed" name="isUsed" value="${(role.isUsed)!'1'}">
		        		<input style="width:auto" type="radio" name="used" value="1" checked>是
		        		<input style="width:auto" type="radio" name="used" value="0" />否
		        	</li>
		        	<li class="clearfix">
		        		<span>角色描述：</span>
		        		<textarea id="roleDescribe" name="roleDescribe">${(role.roleDescribe)!""}</textarea>
		        	</li>
		        </ul>
				<div class="pop_data_block">
					<div class="sidebar">
						<ul class="sidebar-menu line_menu">
							<li class="line_title"><em>目录权限设置：</em></li>
							<#if mlist??>
								<#list mlist as o>
									 <li class="<#if o_index==0>active</#if> treeview line_section">
										<a href="#" id="line_btn">
							            	<span>${o.menuName}</span>
							            	<i class="fa fa-angle-left pull-right"></i>
							          	</a>
							          	<ul class="treeview-menu">
							          		<#if o.childMenus??>
							          			<#list o.childMenus as c>
									            	<li>
										            	<a href="#">
										            		<input type="checkbox"
										            		<#if role??>
																<#list role.menuRightList as mr>
																<#if mr.menuId==c.id>
																	checked="checked"
																</#if>
																</#list>
															</#if>
										            		 class="menus" value="${c.id}">
										            		<span>${c.menuName}</span>
										            	</a>
									            	</li>
							          			</#list>
							          		</#if>
							          	</ul>
									 </li>
								</#list>
							</#if>
				        </ul>
					</div>
		        </div>
		    </div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="cancel()">取消</button>
	        	<button type="button" class="btn btn-primary save_btn" onclick="saveRoleInfo();">保存</button>
	      	</div>
    	</div>

