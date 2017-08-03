package cn.zdmake.metro.controllor;

import java.util.List;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.StringUtil;
//import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.model.MetroRole;
import cn.zdmake.metro.model.MetroSysMenu;
import cn.zdmake.metro.service.IMetroRoleService;
import cn.zdmake.metro.service.IMetroSysMenuService;
import cn.zdmake.metro.service.ISysRightService;

@Controller
@RequestMapping("/role")
public class MetroRoleController extends BaseController {
	// private static Logger logger = Logger.getLogger(MetroRoleController.class);
	@Autowired
	private IMetroRoleService roleService;
	
	@Autowired
	private IMetroSysMenuService menuService;
	
	@Autowired
	private ISysRightService rightService;
	
	/**
	 * 角色管理主页
	 * @return
	 */
	@RequestMapping("/index")
	public String list(){
		return "/sysm/smf_role";
	}
	
	/**
	 * 查找角色
	 * @return
	 */
	@SysControllorLog(menu="角色管理",opreate="查询")
	@RequestMapping("/find/roles")
	@ResponseBody
	public PageResultSet<MetroRole> findRoleAll(){
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageResultSet<MetroRole> roleResult= roleService.findRoleList(StringUtil.nullToInt(pageNum), StringUtil.nullToInt(pageSize));
		return roleResult;
	}
	
	/**
	 * 保存角色信息
	 * @return
	 */
	@SysControllorLog(menu="角色管理",opreate="保存编辑信息")
	@RequestMapping("/save/role/info")
	@ResponseBody
	public ModelMap saveRoleInfo(){
		boolean result = true;
		String operate = request.getParameter("operate");
		String roleCode = request.getParameter("roleCode");
		String roleName = request.getParameter("roleName");
		String isUsed = request.getParameter("isUsed");
		String roleDescribe = request.getParameter("roleDescribe");
		String menuIds = request.getParameter("menuIds");
		if("0".equals(operate)){
			result = roleService.addRole(roleCode, roleName, isUsed, roleDescribe, menuIds);
		}else if("1".equals(operate)){
			String roleId = request.getParameter("roleId");
			String oldMenuIds = request.getParameter("oldMenuIds");
			result = roleService.editRole(roleId, roleCode, roleName, isUsed, roleDescribe, oldMenuIds, menuIds);
		}
		rightService.setRightMenusByRoleId(this.getCurrentUser().getRoleId());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}
	
	@SysControllorLog(menu="角色管理",opreate="编辑")
	@RequestMapping("/to/edit")
	public ModelAndView editRoleInfo(){
		JModelAndView mv = new JModelAndView("/sysm/smf_role_edit",request,response);
		String operate = request.getParameter("operate");
		String roleId = request.getParameter("roleId");
		List<MetroSysMenu> mlist = menuService.findMenuAll(2);
		mv.addObject("mlist", mlist);
		if("1".equals(operate)&&roleId!=null){
			MetroRole role = roleService.findRoleById(roleId);
			mv.addObject("role", role);
		}		
		return mv;		
	}
	
	/**
	 * 删除角色
	 * @return
	 */
	@SysControllorLog(menu="角色管理",opreate="删除")
	@RequestMapping("/del/role")
	@ResponseBody
	public ModelMap delUserInfo(){
		String roleId = request.getParameter("roleId");
		boolean result = roleService.delRole(roleId);
		rightService.setRightMenusByRoleId(this.getCurrentUser().getRoleId());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}
	

}
