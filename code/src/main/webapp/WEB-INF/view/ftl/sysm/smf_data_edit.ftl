
	<div class="modal-content middle">
      	<div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="closeCover();">
	          	<span aria-hidden="true">×</span>
	        </button>
	        <span class="modal-title">数据权限编辑</span>
      	</div>
	    <div class="modal-body">
	        <ul class="user_info">
	        	<li>
	        		<span>用户账号：</span><span>${(user.username)!''}</span>
	        	</li>
	        	<li>
	        		<span>角色名称：</span><span>${(user.role.roleName)!''}</span>
	        	</li>
	         <#if city.lineList ?? && city.lineList?size != 0>
                <li><span>数据权限控制：<span></li>
			 </#if>
	        </ul>
			<div class="pop_data_block" style="overflow-y: scroll;overflow-x: scroll;">
				<div class="sidebar">
					<ul class="sidebar-menu line_menu">
					  <#if city.lineList ?? && city.lineList?size != 0>
					    <#--<li class="line_title"><em>数据权限控制：</em></li>-->
					    <#list city.lineList as line>
					       <#if line_index == 0>
					           <li class="active treeview line_section">
					       <#else>
					           <li class="treeview line_section">
					       </#if>
					       <a href="#" id="line_btn">
				            	<span>${line.lineName!''}</span>
				            	<i class="fa fa-angle-left pull-right"></i>
				          	</a>
				          	<#if line.intervalList ?? && line.intervalList?size != 0>
				          	 <ul class="treeview-menu">
				          	   <#list line.intervalList as interval>
				          	      <li>
				          	          <a href="#">
				          	              <#assign authStr = city.id?c+";"+line.id?c+";"+interval.id?c+";"+"l">
				          	              <#if auths?seq_contains(authStr)>
				          	                 <input type="checkbox" checked="checked" value="${authStr!''}"/>
				          	              <#else>
				          	                 <input type="checkbox" value="${authStr!''}"/>
				          	              </#if>
					            		  <span>${interval.intervalName!''}-左线</span>
					            	  </a>
					              </li>
					               <li>
				          	          <a href="#">
				          	              <#assign authStr = city.id?c+";"+line.id?c+";"+interval.id?c+";"+"r">
				          	              <#if auths?seq_contains(authStr)>
				          	                 <input type="checkbox" checked="checked" value="${authStr!''}"/>
				          	              <#else>
				          	                 <input type="checkbox" value="${authStr!''}"/>
				          	              </#if>
					            		  <span>${interval.intervalName!''}-右线</span>
					            	  </a>
					              </li>
				          	     <!--   <#if interval.intervalDataList ?? && interval.intervalDataList?size !=0>
					          	      <#list interval.intervalDataList as intervalData>
					          	       <li>
					          	          <a href="#">
					          	              <#assign authStr = city.id?c+";"+line.id?c+";"+interval.id?c+";"+intervalData.leftOrRight>
					          	              <#if auths?seq_contains(authStr)>
					          	                 <input type="checkbox" checked="checked" value="${authStr!''}"/>
					          	              <#else>
					          	                 <input type="checkbox" value="${authStr!''}"/>
					          	              </#if>
						            		  <span>${interval.intervalName!''}-${(intervalData.leftOrRight == "l")?string("左线","右线")}</span>
						            	  </a>
						            	</li>
					          	      </#list>
				          	        </#if>
				          	     -->
				          	   </#list>
				          	  </ul>
				          	</#if>
				          </li>
					    </#list>
					  <#else>
					         暂无数据
					  </#if>
					</ul>
				</div>
	        </div>
	    </div>
      	<div class="modal-footer">
        	<button type="button" class="btn btn-default pull-left" data-dismiss="modal" onclick="cancel();">取消</button>
        	<button type="button" class="btn btn-primary save_btn" onclick="save();">保存</button>
      	</div>
   	</div>