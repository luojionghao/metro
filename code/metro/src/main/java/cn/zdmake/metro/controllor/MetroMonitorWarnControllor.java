package cn.zdmake.metro.controllor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.page.Page;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.JsTreeUtil;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.service.IMetroLineIntervalWarningService;
import cn.zdmake.metro.service.IMetroWarningRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ibm.icu.util.Calendar;
import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.model.MetroLineIntervalWarning;
import cn.zdmake.metro.model.MetroLineIntervalWarningRec;
import cn.zdmake.metro.service.ISysRightService;
import cn.zdmake.metro.vo.Jstree;

/**
 * 监测预警控制器
 * 
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/monitor/warn")
public class MetroMonitorWarnControllor extends BaseController {

	@Autowired
	private IMetroWarningRecService warningRecService;

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroLineIntervalWarningService warnService;

	/**
	 * 监测预警主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {
		return "/find-info/monitor_warn";
	}

	@RequestMapping("/to/warn-right")
	public String toWarnRight(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -5);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		PageResultSet<MetroLineIntervalWarning> params = warnService.findLineIntervalWarningInfo(intervalId,
				leftOrRight, 0, 230);
		modelMap.addAttribute("wps", params != null ? params.getList() : null);
		return "/find-info/monitor_warn_right";
	}

	/**
	 * 监测预警记录分页查询
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "监测预警", opreate = "查询预警记录")
	@RequestMapping("/find/warns")
	@ResponseBody
	public PageResultSet<MetroLineIntervalWarningRec> findWarnAll(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize, @RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("warnParam") String warnParam) {
		
		String beginTime = StringUtil.timeCnToEn(request.getParameter("beginTime"));
		String endTime = StringUtil.timeCnToEnAddOne(request.getParameter("endTime"));
		
		PageResultSet<MetroLineIntervalWarningRec> res = warningRecService.findWarningRecs(getCurrentUser().getId(),
				intervalId, leftOrRight, pageNum, pageSize, beginTime, endTime, warnParam);
		if (res == null) {
			res = new PageResultSet<MetroLineIntervalWarningRec>();
			res.setPage(new Page(0, pageSize, pageNum));
			res.setList(new ArrayList<MetroLineIntervalWarningRec>());
		}
		return res;
	}

	/**
	 * 主页铃铛定时获取预警记录
	 * 
	 * @return
	 */
	@RequestMapping("/find/warns/all")
	@ResponseBody
	public ModelMap findWarnsAll() {
		ModelMap modelMap = new ModelMap();
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecs(this.getCurrentUser().getId(),
				null);
		modelMap.addAttribute("total", res != null && res.size() > 0 ? res.size() : 0);
		modelMap.addAttribute("res", res);
		return modelMap;
	}

	@RequestMapping("/get/data/tree")
	@ResponseBody
	public List<Jstree> getDataRight() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		String[] urls = new String[4];
		urls[3] = "/monitor/warn/to/warn-right";
		Boolean[] diss = new Boolean[4];
		diss[0] = true;
		diss[1] = true;
		diss[2] = true;
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}
}
