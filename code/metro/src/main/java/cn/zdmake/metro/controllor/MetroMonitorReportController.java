package cn.zdmake.metro.controllor;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.base.utils.GsonUtils;
import cn.zdmake.metro.base.utils.JsTreeUtil;
import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroLine;
import cn.zdmake.metro.model.MetroLineInterval;
import cn.zdmake.metro.service.IMetroLineIntervalService;
import cn.zdmake.metro.service.IMetroLineService;
import cn.zdmake.metro.service.ISysRightService;
import cn.zdmake.metro.vo.Jstree;
import cn.zdmake.metro.vo.SessionUser;
import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/monitor/report")
public class MetroMonitorReportController extends BaseController {

	@Autowired
	private ISysRightService rightService;

	@Autowired
	IMetroLineService lineService;

	@Autowired
	private IMetroLineIntervalService intervalService;

	@RequestMapping("/index")
	public String list() {
		return "/find-info/info_monitor_report";
	}

	@RequestMapping("/to/report")
	public String toReport(@RequestParam("intervalId") Long intervalId, @RequestParam("lineId") Long lineId) {

		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("lineId", lineId);

		return "/find-info/info_monitor_report_ring";
	}

	@RequestMapping("/ring/export")
	@ResponseBody
	public CommonResponse exportRing(@RequestParam("intervalId") Long intervalId, @RequestParam("lineId") Long lineId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("ring") String ring,
			@RequestParam("cityName") String cityName) {

		CommonResponse commonResponse = new CommonResponse();

		MetroLine line = lineService.findObjById(lineId);
		MetroLineInterval interval = intervalService.findObjById(intervalId);

		String fileName = cityName + line.getLineNo() + "_" + interval.getIntervalMark() + leftOrRight + "_" + ring
				+ ".pdf";
		String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
		File checkFile = new File(uploadPath + "/" + cityName + "/" + fileName);
		if (checkFile.exists()) {
			commonResponse.setCode(1);
			commonResponse.setResult(cityName + "/" + fileName);
		} else {
			commonResponse.setCode(0);
			commonResponse.setResult("不存在");
		}
		return commonResponse;
	}

	@RequestMapping("/tree-data/get")
	@ResponseBody
	public List<Jstree> getTreeData() {
		MetroCity city = rightService.getRightDatasByUserId(getCurrentUser().getId());
		String[] urls = new String[4];
		urls[2] = "/monitor/report/to/report";
		Boolean[] diss = new Boolean[3];
		diss[0] = true;
		diss[1] = true;
		diss[2] = false;
		return JsTreeUtil.getTreeDataForReport(request, city, urls, diss);
	}
}
