		<div class="modal-content middle">
	      	<div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="cancel();">
		          	<span aria-hidden="true">×</span>
		        </button>
		        <span class="modal-title">用户信息</span>
	      	</div>
		    <div class="modal-body">
		    	<input type="hidden" id="userId" name="userId" value="${(user.id)!''}">
		    	<input type="hidden" id="oldDeptIds" name="oldDeptIds" value="<#if user??&&user.deptList??><#list user.deptList as udp><#if udp_index==0>${(udp.id)}<#else>,${(udp.id)}</#if></#list></#if>">
		        <ul class="user_info_block user_info_list">
		        	<li class="clearfix">
		        		<span>账号：</span>
		        		<input type="text" id="u_name" name="u_name" value="${(user.username)!''}">
		        	</li>
		        	<#if user??&&user.id??>
		        		
		        	<#else>
		        	<li class="clearfix">
		        		<span>密码：</span>
		        		<input type="password" id="u_pass" name="u_pass">
		        	</li>
		        	</#if>
		        	<li class="clearfix">
		        		<span>姓名：</span>
		        		<input type="text" id="name" name="name" value="${(user.name)!''}">
		        	</li>
		        </ul>
		        <div class="user_info_block clearfix">
		        	<span class="select_org_text">所属机构：</span>
		        	<ul class="select_org_list">
		        	<#if dlist??>
		        	<#list dlist as d>
		        		<li>
		        			<input type="checkbox" 
		            		<#if user??>
								<#list user.deptList as ud>
								<#if ud.id==d.id>
									checked="checked"
								</#if>
								</#list>
							<#else>
								<#if deptId==d.id>
									checked="checked"
								</#if>
							</#if>

		        			value="${(d.id)}" class="depts">${(d.deptName)}
		        		</li>
		        	</#list>
		        	</#if>
		        	</ul>
		        </div>
		        <div class="user_info_block select_role_block">
		        	<span>角色：</span>
		        	<select id="roleId" name="roleId">
		        		<#if rlist??>
		        		<#list rlist as r>
		        		<option value="${(r.id)}" <#if user??&&user.roleId??&&user.roleId==r.id>selected="selected"</#if>>${(r.roleName)}</option>
		        		</#list>
		        		</#if>
		        	</select>
		        </div>
		    </div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="cancel();">取消</button>
	        	<button type="button" class="btn btn-primary save_btn" onclick="saveUserInfo();">保存</button>
	      	</div>
    	</div>