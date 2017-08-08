package cn.zdmake.metro.controllor;

import java.util.List;
import java.util.Random;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.security.MD5;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.service.IMetroUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroDept;
import cn.zdmake.metro.model.MetroRole;
import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.service.IMetroDeptService;
import cn.zdmake.metro.service.IMetroRoleService;

@Controller
@RequestMapping("/user")
public class MetroUserControllor extends BaseController {
	@Autowired
	private IMetroUserService userService;

	@Autowired
	private IMetroDeptService deptService;

	@Autowired
	private IMetroRoleService roleService;

	/**
	 * 用户管理主页
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "部门查询")
	@RequestMapping("/index")
	public String list() {
		// 查询所有机构
		List<MetroDept> dlist = deptService.findAllDeptInfo();
		modelMap.addAttribute("dlist", dlist);
		return "/sysm/smf_user";
	}

	/**
	 * 部门对应的用户列表
	 * 
	 * @return
	 */
	@RequestMapping("/to/right")
	public String userInfo(@RequestParam("deptId") Long deptId) {
		modelMap.addAttribute("deptId", deptId);
		return "/sysm/smf_user_right";
	}

	/**
	 * 查找不同部门的用户
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "部门用户查询")
	@RequestMapping("/find/users")
	@ResponseBody
	public PageResultSet<MetroUser> findUserByDeptId(@RequestParam("deptId") String deptId,
			@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {

		PageResultSet<MetroUser> userResult = userService.findMetroUserInfo(deptId, pageNum, pageSize);
		return userResult;
	}

	/**
	 * 保存部门信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "部门信息保存")
	@RequestMapping("/save/dept/info")
	@ResponseBody
	public ModelMap saveDeptInfo(@RequestParam("operate") String operate) {

		boolean result = true;
		if ("add".equals(operate)) {// 添加
			String deptName = request.getParameter("deptName");
			result = deptService.addMetroDeptInfo(deptName);
		} else if ("edit".equals(operate)) {// 修改
			String deptId = request.getParameter("deptId");
			String deptName = request.getParameter("deptName");
			result = deptService.editMetroDeptInfo(deptId, deptName);
		} else {
			String deptId = request.getParameter("deptId");
			result = deptService.delMetroDeptInfo(deptId);
		}
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);

		return modelMap;
	}

	/**
	 * 用户编辑页面
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "用户信息编辑")
	@RequestMapping("/to/edit")
	public ModelAndView editRoleInfo(@RequestParam("operate") String operate, @RequestParam("userId") Long userId,
			@RequestParam("deptId") Long deptId) {

		JModelAndView mv = new JModelAndView("/sysm/smf_user_edit", request, response);
		// 查询所有机构信息
		List<MetroDept> dlist = deptService.findAllDeptInfo();
		mv.addObject("dlist", dlist);
		// 查询所有角色信息 超级管理员除外
		List<MetroRole> rlist = roleService.findAllRoleInfo();
		mv.addObject("rlist", rlist);
		mv.addObject("deptId", deptId);
		if ("1".equals(operate) && userId != null) {
			MetroUser user = userService.findObjById(userId);
			mv.addObject("user", user);
		}
		return mv;
	}

	/**
	 * 保存用户信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "用户信息保存")
	@RequestMapping("/save/user/info")
	@ResponseBody
	public ModelMap saveUserInfo(@RequestParam("operate") String operate, @RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("name") String name,
			@RequestParam("deptIds") String deptIds, @RequestParam("roleId") String roleId) {

		boolean result = true;

		ModelMap modelMap = new ModelMap();
		if ("0".equals(operate)) {// 添加
			boolean b = userService.findMetroUserUsername(username);
			if (b) {
				modelMap.addAttribute("code", 1);
			} else {
				result = userService.addMetroUserInfo(username, MD5.MD5Encode(password), name, deptIds, roleId);
			}
		} else if ("1".equals(operate)) {// 修改
			String userId = request.getParameter("userId");
			String oldDeptIds = request.getParameter("oldDeptIds");
			result = userService.editMetroUserInfo(userId, username, name, oldDeptIds, deptIds, roleId);
		}

		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "用户删除")
	@RequestMapping("/del/user")
	@ResponseBody
	public ModelMap delUserInfo(@RequestParam("deptId") String deptId, @RequestParam("userId") Long userId) {
		MetroUser u = userService.findObjById(userId);
		boolean result = userService.delMetroUserInfo(deptId, userId.toString(),
				u.getDeptList() == null ? StringUtil.nullToInt(null) : u.getDeptList().size());
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 重置密码
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "用户管理", opreate = "用户密码重置")
	@RequestMapping("/reset/user/pass")
	@ResponseBody
	public ModelMap resetUserPassword(@RequestParam("userId") String userId,
			@RequestParam("password") String password) {
		if (password == null || "".equals(password)) {
			password = String.valueOf(nextInt(100000, 999999));
		}
		boolean result = userService.editMetroUserPassword(userId, MD5.MD5Encode(password));
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("password", password);
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	/**
	 * 检查用户账号是否存在
	 * 
	 * @return
	 */
	@RequestMapping("/check/user/username")
	@ResponseBody
	public ModelMap checkUsername(@RequestParam("username") String username) {

		boolean result = userService.findMetroUserUsername(username);
		ModelMap modelMap = new ModelMap();
		modelMap.addAttribute("result", result);
		return modelMap;
	}

	public int nextInt(final int min, final int max) {
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
}
