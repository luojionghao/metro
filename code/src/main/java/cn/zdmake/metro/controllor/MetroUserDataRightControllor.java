package cn.zdmake.metro.controllor;

import java.util.ArrayList;
import java.util.List;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.model.MetroUserDataRel;
import cn.zdmake.metro.service.IMetroCityService;
import cn.zdmake.metro.service.IMetroDataRightService;
import cn.zdmake.metro.service.IMetroUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;

/**
 * 数据权限管理
 * 
 * @author hank
 *
 *         2016年8月22日
 */
@Controller
@RequestMapping("/data/right")
public class MetroUserDataRightControllor extends BaseController {
	private static Logger logger = Logger.getLogger(MetroUserDataRightControllor.class);

	@Autowired
	private IMetroDataRightService dataRightService;

	@Autowired
	private IMetroUserService userService;

	// @Autowired
	// private IMetroRoleService roleService;

	@Autowired
	private IMetroCityService cityService;

	/**
	 * 用户数据权限管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {
		// 查询数据权限树
		/*
		 * SessionUser sessionUser = getCurrentUser(); request.setAttribute("userId",
		 * sessionUser.getId());
		 */
		return "/sysm/smf_data";
	}

	/**
	 * 查找用户
	 * 
	 * @return
	 */
	@RequestMapping("/find/users")
	@ResponseBody
	public PageResultSet<MetroUser> findUsersAll(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		
		PageResultSet<MetroUser> userResult = userService.findAllUser(pageNum, pageSize);
		return userResult;
	}

	/**
	 * 获取用户数据权限
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "数据权限管理", opreate = "编辑数据权限")
	@RequestMapping("/get/user/data-right")
	public ModelAndView findUserDataRightByUserId(@RequestParam("userId") Long userId) {
		JModelAndView mv = new JModelAndView("/sysm/smf_data_edit", request, response);
		// 用户的所有数据权限列表
		List<MetroUserDataRel> udrlist = dataRightService.findUserDataRightByUserId(userId);
		List<String> authList = new ArrayList<String>();
		for (MetroUserDataRel rel : udrlist) {
			authList.add(
					rel.getCityId() + ";" + rel.getLineId() + ";" + rel.getIntervalId() + ";" + rel.getLeftOrRight());
		}
		// 用户信息
		MetroUser user = userService.findObjById(userId);
		// 城市地铁线路信息
		Long cityId = 1l; // 默认广州
		MetroCity city = cityService.findObjById(cityId);

		mv.addObject("auths", authList);
		mv.addObject("city", city);
		mv.addObject("user", user);
		return mv;
	}

	/**
	 * 保存用户数据权限信息
	 * 
	 * @param userId
	 * @param dataRight
	 * @return
	 */
	@RequestMapping(value = "/save/user/data-right", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveEditUserDataRight(@RequestParam("userId") Long userId, @RequestParam("dataRight") String dataRight) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			// dataRigth? "1;1;3;l,1;1;3;r" {城市;线路;区间;左右标记}
			boolean result = dataRightService.saveDataRightInfo(userId, dataRight);
			if (result) {
				commonResponse.setCode(1);
				commonResponse.setResult("保存用户数据权限信息成功");
			} else {
				commonResponse.setCode(0);
				commonResponse.setResult("保存用户数据权限信息出错");
			}
		} catch (Exception e) {
			logger.error("保存用户数据权限信息异常", e);
			commonResponse.setCode(0);
			commonResponse.setResult("保存用户数据权限信息异常");
		}
		return commonResponse;
	}

}
