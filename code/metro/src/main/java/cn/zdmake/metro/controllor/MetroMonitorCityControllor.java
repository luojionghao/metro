package cn.zdmake.metro.controllor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.page.Page;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.model.*;
import cn.zdmake.metro.service.*;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticView;
import cn.zdmake.metro.vo.MonitorIntervalView;
import cn.zdmake.metro.base.aop.SysControllorLog;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.icu.util.Calendar;

import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.base.utils.Constants;
import cn.zdmake.metro.base.utils.IhistorianUtil;
import cn.zdmake.metro.base.utils.JsTreeUtil;
import cn.zdmake.metro.vo.IhistorianResponse;
import cn.zdmake.metro.vo.Jstree;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticsView;
import cn.zdmake.metro.vo.MonitorViewData;
import cn.zdmake.metro.vo.MonitorLrAlldicView;

import javax.annotation.Resource;

/**
 * 盾构信息监控控制器
 * 
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/monitor/info")
public class MetroMonitorCityControllor extends BaseController {

	private static Logger logger = Logger.getLogger(MetroMonitorCityControllor.class);

	@Resource
	ICommonService commonService;

	@Autowired
	private ISysRightService rightService;

	@Autowired
	private IMetroMonitorInfoCityService infoCityService;

	@Autowired
	private IMetroLineService lineService;

	@Autowired
	private IMetroLineIntervalService intervalService;

	@Autowired
	private IMetroLineIntervalSpService spService;

	@Autowired
	private IMetroLineIntervalPpService lineIntervalPpService;

	@Autowired
	private IMetroLineIntervalRpService lineIntervalRpService;

	// @Autowired
	// private IMetroLineIntervalLrService lineIntervalLrService;

	@Autowired
	private IMetroPhotoService photoService;

	@Autowired
	private IMetroWarningRecService warningRecService;

	/**
	 * 盾构信息监控主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/find-info/info_monitor";
	}

	/**
	 * 城市信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/city/index")
	public String cityMain(@RequestParam("cityId") String cityId) {
		modelMap.addAttribute("cityId", cityId);
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), null);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			modelMap.addAttribute("total0", total0);
			modelMap.addAttribute("total1", total1);
			modelMap.addAttribute("total2", total2);
			modelMap.addAttribute("totalSuccess", totalSuccess);
			modelMap.addAttribute("totalFail", totalFail);
			modelMap.addAttribute("totalm", total0 + total1 + total2);
		}
		return "/find-info/info_monitor_city";
	}

	/**
	 * 城市数据监测
	 * 
	 * @return
	 */
	@RequestMapping("/find/city/datas")
	@ResponseBody
	public PageResultSet<MonitorViewData> findMonitorCityAll(@RequestParam("cityId") Long cityId,
			@RequestParam("buildStatus") Integer buildStatus, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(cityId, null, buildStatus,
				pageNum, pageSize);
		if (mvds == null) {
			mvds = new PageResultSet<MonitorViewData>();
			mvds.setPage(new Page(0, pageSize, pageNum));
			mvds.setList(new ArrayList<MonitorViewData>());
		}
		return mvds;
	}

	/**
	 * 线路信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/line/index")
	public String lineMain(@RequestParam("lineId") Long lineId) {
		modelMap.addAttribute("lineId", lineId);
		MetroLine line = lineService.findObjById(lineId);
		modelMap.addAttribute("lineUrl", line.getProjectPdfUrl());
		Map<String, String> map = infoCityService.findCountMechineDatas(this.getCurrentUser().getId(), lineId);
		if (map != null) {
			int total0 = StringUtil.nullToInt(map.get("total0"));
			int total1 = StringUtil.nullToInt(map.get("total1"));
			int total2 = StringUtil.nullToInt(map.get("total2"));
			int totalSuccess = StringUtil.nullToInt(map.get("totalSuccess"));
			int totalFail = StringUtil.nullToInt(map.get("totalFail"));
			modelMap.addAttribute("total0", total0);
			modelMap.addAttribute("total1", total1);
			modelMap.addAttribute("total2", total2);
			modelMap.addAttribute("totalSuccess", totalSuccess);
			modelMap.addAttribute("totalFail", totalFail);
			modelMap.addAttribute("totalm", total0 + total1 + total2);
		}
		return "/find-info/info_monitor_line";
	}

	/**
	 * 线路数据监测
	 * 
	 * @return
	 */
	@RequestMapping("/find/line/datas")
	@ResponseBody
	public PageResultSet<MonitorViewData> findMonitorLineAll(@RequestParam("lineId") Long lineId,
			@RequestParam("buildStatus") int buildStatus, @RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) throws IOException {
		PageResultSet<MonitorViewData> mvds = infoCityService.findMonitorInfoCityData(null, lineId, buildStatus,
				pageNum, pageSize);
		if (mvds == null) {
			mvds = new PageResultSet<MonitorViewData>();
			mvds.setPage(new Page(0, pageSize, pageNum));
			mvds.setList(new ArrayList<MonitorViewData>());
		}
		return mvds;
	}

	/**
	 * 区间信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/area/index")
	public String areaMain(@RequestParam("intervalId") Long intervalId) {
		modelMap.addAttribute("intervalId", intervalId);
		MetroLineInterval interval = intervalService.findObjById(intervalId);
		modelMap.addAttribute("intervalUrl", interval.getProjectPdfUrl());
		modelMap.addAttribute("riskPdfUrl", interval.getRiskPdfUrl());
		List<MetroLineIntervalSp> sps = spService.findLineIntervalSps(intervalId);
		modelMap.addAttribute("sps", sps);
		return "/find-info/info_monitor_area";
	}

	/**
	 * 区间信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/area/project")
	public String projectProccse(@RequestParam("intervalId") Long intervalId, @RequestParam("type") int type,
			@RequestParam("goNumA") int goNumAll, @RequestParam("pingLorR") String pingLorR) {
		MetroLineInterval interval = intervalService.findObjById(intervalId);
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(intervalId);
		modelMap.addAttribute("pp", pp);

		List<MetroLineIntervalLr> lrList = interval.getIntervalLrList();

		String PARAM_RING_NUM = "A0001"; // 当前环号
		// String PARAM_SEARCH_TIME = "K0003"; //查询时间

		MetroLine line = lineService.findObjById(interval.getLineId());
		List<String> keys = new ArrayList<String>();
		String lRingNumkey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l", PARAM_RING_NUM);
		String rRingNumkey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r", PARAM_RING_NUM);
		// String lSearchTimeKey = IhistorianUtil.getKey(line.getLineNo(),
		// interval.getIntervalMark(), "l", PARAM_SEARCH_TIME);
		// String rSearchTimeKey = IhistorianUtil.getKey(line.getLineNo(),
		// interval.getIntervalMark(), "r", PARAM_SEARCH_TIME);
		keys.add(lRingNumkey);
		keys.add(rRingNumkey);
		// keys.add(lSearchTimeKey);
		// keys.add(rSearchTimeKey);
		// 获取该路线左右线具体的环数
		String lRingNum = "";
		String rRingNum = "";
		IhistorianResponse ir = IhistorianUtil
				.getTodayRing(IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), ""));
		if (ir != null && ir.getCode() == 200) {
			Map<String, Object> result = ir.getResult();
			lRingNum = result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("ring"));
			rRingNum = result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("ring"));
			// String lSearchTime = result.get(lSearchTimeKey) == null ? null :
			// String.valueOf(result.get(lSearchTimeKey));
			// String rSearchTime = result.get(rSearchTimeKey) == null ? null :
			// String.valueOf(result.get(rSearchTimeKey));
			modelMap.addAttribute("lRingNum", lRingNum);
			modelMap.addAttribute("lCountRing",
					result.get("L") == null ? null : String.valueOf(((Map<?, ?>) result.get("L")).get("count")));
			modelMap.addAttribute("rRingNum", rRingNum);
			modelMap.addAttribute("rCountRing",
					result.get("R") == null ? null : String.valueOf(((Map<?, ?>) result.get("R")).get("count")));
		}

		if (CommonUtils.isNotNull(lrList)) {
			modelMap.addAttribute("lr_l", lrList.get(0));
			if (lrList.get(0).getRingNum() != null && lrList.get(0).getRingNum() > 0) {
				modelMap.addAttribute("lvl", StringUtil.nullToDouble(lRingNum) / lrList.get(0).getRingNum());
				modelMap.addAttribute("lvlz", StringUtil.nullToDouble(lRingNum) / lrList.get(0).getRingNum() * 100);
			} else {
				modelMap.addAttribute("lvl", 0);
				modelMap.addAttribute("lvlz", 0);
			}
			if (lrList.size() > 1) {
				modelMap.addAttribute("lr_r", lrList.get(1));
				if (lrList.get(1).getRingNum() != null && lrList.get(1).getRingNum() > 0) {
					modelMap.addAttribute("rvr", StringUtil.nullToDouble(rRingNum) / lrList.get(1).getRingNum());
					modelMap.addAttribute("rvrz", StringUtil.nullToDouble(rRingNum) / lrList.get(1).getRingNum() * 100);
				} else {
					modelMap.addAttribute("rvr", 0);
					modelMap.addAttribute("rvrz", 0);
				}
			}
		}

		String lLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "l");
		String rLinekey = IhistorianUtil.getKey(line.getLineNo(), interval.getIntervalMark(), "r");
		modelMap.addAttribute("lLinekey", lLinekey);
		modelMap.addAttribute("rLinekey", rLinekey);

		modelMap.addAttribute("intervalId", intervalId);

		Date date = new Date();
		modelMap.addAttribute("lSearchTime", date);
		modelMap.addAttribute("rSearchTime", date);
		String url = "";
		if (type == 1) {
			// 获取沉降点数据
			List<MetroLineIntervalSp> metroLineIntervalSpList = spService.findLineIntervalSps(intervalId);
			modelMap.addAttribute("ntervalSpList", metroLineIntervalSpList);
			if (pingLorR.equals("l")) {
				modelMap.addAttribute("pingLorR", pingLorR);
			} else if (pingLorR.equals("r")) {
				modelMap.addAttribute("pingLorR", pingLorR);
			}
			if (goNumAll != 0) {
				modelMap.addAttribute("goNumAll", goNumAll);
			}
			url = "/find-info/project1";
		} else if (type == 2) {
			if (goNumAll != 0) {
				modelMap.addAttribute("goNumAll", goNumAll);
			}
			url = "/find-info/projectL";
		} else if (type == 3) {
			if (goNumAll != 0) {
				modelMap.addAttribute("goNumAll", goNumAll);
			}
			url = "/find-info/projectR";
		}
		return url;
	}

	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/datas", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findMonitorLineData(String line) {
		CommonResponse commonResponse = new CommonResponse();

		IhistorianResponse ir = IhistorianUtil.getDataByLine(line);
		if (ir != null && ir.getCode() == 200) {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(ir.getResult());
		} else {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(null);
		}
		return commonResponse;
	}

	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/risk", method = RequestMethod.GET)
	@ResponseBody
	public List<MetroLineIntervalRp> findMonitorLineRisk(@RequestParam("intervalId") String intervalId) {
		List<MetroLineIntervalRp> rps = lineIntervalRpService.findLineIntervalRpInfo(Long.parseLong(intervalId));
		return rps;
	}

	/**
	 * 获取线路信息
	 */
	@RequestMapping(value = "/find/line/monitor/coordinates", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse findoordinatesData(@RequestParam("line") String line) {
		CommonResponse commonResponse = new CommonResponse();

		IhistorianResponse ir = IhistorianUtil.getCoordinatesByLine(line);
		if (ir != null && ir.getCode() == 200) {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(ir.getResult());
		} else {
			commonResponse.setCode(Constants.CODE_SUCCESS);
			commonResponse.setResult(null);
		}
		return commonResponse;
	}

	@RequestMapping("/find/interval/monitor/datas")
	@ResponseBody
	public MonitorIntervalView findMonitorIntervalData(@RequestParam("intervalId") Long intervalId,
			@RequestParam("intervalSpId") Long intervalSpId) {
		MonitorIntervalView monitorIntervalView = infoCityService.findMonitorIntervalDatas(intervalId, intervalSpId);
		return monitorIntervalView;
	}

	/**
	 * 上传风险组段划分信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo")
	public String riskinfo(@RequestParam("intervalId") Long intervalId) {

		MetroLineInterval metroline = intervalService.findObjById(intervalId);
		request.setAttribute("riskPdfUrl", metroline.getRiskPdfUrl());
		request.setAttribute("intervalId", String.valueOf(metroline.getId()));
		return "/find-info/risk";
	}

	/**
	 * 风险组段划分文件上传
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "盾构远程监控", opreate = "风险组段划分文件上传")
	@RequestMapping(value = "/risk-pdf/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadRiskPdf(@RequestParam("iId") Long intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		// TODO 输出格式需要统一
		// ?? 输出类型
		// CommonResponse r = new CommonResponse();
		try {
			CommonResponse uploadResult = commonService.fileUpload(file);
			if (uploadResult.getCode() == Constants.CODE_FAIL) { // 上传文件失败
				return "上传失败";
				// return GsonUtils.toJson(uploadResult);
			}
			String pdfUrl = (String) uploadResult.getResult();
			boolean result = intervalService.editRiskPdfUrl(intervalId, pdfUrl);
			if (result) { // 入库成功
				/*
				 * r.setCode(Constants.CODE_SUCCESS); r.setResult("上传成功");
				 */
			} else { // 上传失败
				logger.error("文件上传成功，入库失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				return "文件上传成功，入库失败";
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			return "文件上传异常";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		MetroLineInterval metroline = intervalService.findObjById(intervalId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riskPdfUrl", metroline.getRiskPdfUrl());
		map.put("intervalId", metroline.getId());
		
		return map;
	}

	/**
	 * 风险组段划分文件删除
	 */
	@SysControllorLog(menu = "盾构远程监控", opreate = "风险组段划分文件删除")
	@RequestMapping(value = "/risk-pdf/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteRiskPdf(@RequestParam("intervalId") Long intervalId) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = intervalService.editRiskPdfUrl(intervalId, null);
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else { // 删除失败
				logger.error("删除失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除失败");
			}
		} catch (Exception e) {
			logger.error("城市工程文件删除异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("城市工程文件删除异常");
		}
		return commonResponse;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/index")
	public String lrMain(@RequestParam("intervalId") Long intervalId, @RequestParam("leftOrRight") String leftOrRight) {
		MetroLineInterval interval = intervalService.findObjById(intervalId);
		for (MetroLineIntervalLr lineIntervalLr : interval.getIntervalLrList()) {
			if (lineIntervalLr.getLeftOrRight().equals(leftOrRight)) {
				modelMap.addAttribute("machineType", lineIntervalLr.getMachineType());
			}
		}
		int b = infoCityService.findCurrRingNum(intervalId.toString(), leftOrRight);
		try {
			if (b - 5 >= 0) {
				request.getSession().setAttribute("b_curr_ring", b - 5);
			} else {
				request.getSession().setAttribute("b_curr_ring", 0);
			}
			request.getSession().setAttribute("e_curr_ring", b);
		} catch (Exception e) {
			request.getSession().setAttribute("b_curr_ring", 0);
			request.getSession().setAttribute("e_curr_ring", 0);
		}
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		return "/find-info/info_monitor_LRline";
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/knife")
	public ModelAndView lrMainToKnife(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		JModelAndView mv = new JModelAndView("/find-info/knife", request, response);
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		try {
			request.getSession().setAttribute("e_curr_ring", map.get(map.get("head") + "A0001.F_CV"));
			Integer b = 0;
			try {
				b = StringUtil.nullToInt(map.get(map.get("head") + "A0001.F_CV").toString()) - 5;
			} catch (Exception e) {
				b = 0;
			}
			request.getSession().setAttribute("b_curr_ring", b == null ? 0 : b);
		} catch (Exception e) {

		}

		List<MetroPhoto> phs = photoService.findMetroPhotosByParams(intervalId, leftOrRight);
		if (phs != null && phs.size() > 0) {
			for (MetroPhoto mp : phs) {
				if (mp.getPhotoType() == 1) {
					mv.addObject("dpUrl", mp.getPhotoUrl());
				} else {
					mv.addObject("lxUrl", mp.getPhotoUrl());
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("maps", map);
		mv.addObject("head", map.get("head"));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/sprial")
	public ModelAndView lrMainToSprial(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		JModelAndView mv = new JModelAndView("/find-info/sprial", request, response);
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		try {
			request.getSession().setAttribute("e_curr_ring", map.get(map.get("head") + "A0001.F_CV"));
			Integer b = 0;
			try {
				b = StringUtil.nullToInt(map.get(map.get("head") + "A0001.F_CV").toString()) - 5;
			} catch (Exception e) {
				b = 0;
			}
			request.getSession().setAttribute("b_curr_ring", b == null ? 0 : b);
		} catch (Exception e) {

		}

		List<MetroPhoto> phs = photoService.findMetroPhotosByParams(intervalId, leftOrRight);
		if (phs != null && phs.size() > 0) {
			for (MetroPhoto mp : phs) {
				if (mp.getPhotoType() == 1 || mp.getPhotoType() == 3) {
					mv.addObject("dpUrl", mp.getPhotoUrl());
				} else {
					mv.addObject("lxUrl", mp.getPhotoUrl());
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("maps", map);
		mv.addObject("head", map.get("head"));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/slurry")
	public ModelAndView lrMainToslurry(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		JModelAndView mv = new JModelAndView("/find-info/slurry", request, response);
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		try {
			request.getSession().setAttribute("e_curr_ring", map.get(map.get("head") + "A0001.F_CV"));
			Integer b = 0;
			try {
				b = StringUtil.nullToInt(map.get(map.get("head") + "A0001.F_CV").toString()) - 5;
			} catch (Exception e) {
				b = 0;
			}
			request.getSession().setAttribute("b_curr_ring", b == null ? 0 : b);
		} catch (Exception e) {

		}

		List<MetroPhoto> phs = photoService.findMetroPhotosByParams(intervalId, leftOrRight);
		if (phs != null && phs.size() > 0) {
			for (MetroPhoto mp : phs) {
				if (mp.getPhotoType() == 3) {
					mv.addObject("dpUrl", mp.getPhotoUrl());
				} else {
					mv.addObject("lxUrl", mp.getPhotoUrl());
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("maps", map);
		mv.addObject("head", map.get("head"));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/guide")
	public ModelAndView lrMainToGuide(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		JModelAndView mv = new JModelAndView("/find-info/guide", request, response);
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		try {
			request.getSession().setAttribute("e_curr_ring", map.get(map.get("head") + "A0001.F_CV"));
			Integer b = 0;
			try {
				b = StringUtil.nullToInt(map.get(map.get("head") + "A0001.F_CV").toString()) - 5;
			} catch (Exception e) {
				b = 0;
			}
			request.getSession().setAttribute("b_curr_ring", b == null ? 0 : b);
		} catch (Exception e) {

		}

		List<MetroPhoto> phs = photoService.findMetroPhotosByParams(intervalId, leftOrRight);
		if (phs != null && phs.size() > 0) {
			for (MetroPhoto mp : phs) {
				if (mp.getPhotoType() == 1) {
					mv.addObject("dpUrl", mp.getPhotoUrl());
				} else {
					mv.addObject("lxUrl", mp.getPhotoUrl());
				}
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mv.addObject("nowTime", sdf.format(Calendar.getInstance().getTime()));
		mv.addObject("maps", map);
		mv.addObject("head", map.get("head"));
		mv.addObject("sufix", ".F_CV");
		mv.addObject("intervalId", intervalId);
		mv.addObject("leftOrRight", leftOrRight);
		return mv;
	}

	/**
	 * 刀盘螺旋导向基础数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/datas/now")
	@ResponseBody
	public Map<String, Object> getLrMonitorDatasNow(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		Map<String, Object> map = infoCityService.findIntervalLrDaoPanDatas(intervalId, leftOrRight);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("nowTime", sdf.format(Calendar.getInstance().getTime()));
		map.put("sufix", ".F_CV");
		List<MetroLineIntervalWarningRec> res = warningRecService.findLastWarningRecsIntervalId(intervalId, leftOrRight,
				null);
		map.put("res", res);
		return map;
	}

	/**
	 * 导向当前盾位置
	 * 
	 * @return
	 */
	@RequestMapping("/find/lr/monitor/datas")
	@ResponseBody
	public Map<String, Object> getLrMonitorDatas(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		Map<String, Object> map = infoCityService.findIntervalLrDaoxDatas(intervalId, leftOrRight);
		return map;
	}

	/**
	 * 综合数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/all/dic/datas")
	@ResponseBody
	public PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrAll(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		PageResultSet<MonitorLrAlldicView> mvds = infoCityService.findMonitorIntervalLrDics(intervalId, leftOrRight);
		if (mvds == null) {
			mvds = new PageResultSet<MonitorLrAlldicView>();
			mvds.setPage(new Page(0, 100, 1));
			mvds.setList(new ArrayList<MonitorLrAlldicView>());
		}
		return mvds;
	}

	/**
	 * 区间左右线信息主页
	 * 
	 * @return
	 */
	@RequestMapping("/to/lr/static/index")
	public String lrStaticMain(@RequestParam("intervalId") Long intervalId,
			@RequestParam("leftOrRight") String leftOrRight) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar cal = Calendar.getInstance();
		String endTime = sdf.format(cal.getTime());
		cal.add(Calendar.DATE, -7);
		String beginTime = sdf.format(cal.getTime());
		modelMap.addAttribute("beginDate", beginTime);
		modelMap.addAttribute("endDate", endTime);
		modelMap.addAttribute("intervalId", intervalId);
		modelMap.addAttribute("leftOrRight", leftOrRight);
		return "/find-info/info_statistic";
	}

	/**
	 * 材料消耗统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab1")
	@ResponseBody
	public Map<String, Object> getStaticTab1(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("beginRing") String beginRing,
			@RequestParam("endRing") String endRing, @RequestParam("paramName") String paramName) {

		List<List<Object>> list = infoCityService.findMonitorStaticTab1(intervalId, leftOrRight, beginRing, endRing,
				paramName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	/**
	 * 时间统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab2")
	@ResponseBody
	public Map<String, Object> getStaticTab2(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("beginRing") String beginRing,
			@RequestParam("endRing") String endRing) {

		List<List<Object>> result = infoCityService.findMonitorStaticTab2(intervalId, leftOrRight, beginRing, endRing);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		return map;
	}

	/**
	 * 进度统计
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab3")
	@ResponseBody
	public Map<String, Object> getStaticTab3(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("beginDate") String beginDate,
			@RequestParam("endDate") String endDate) {

		List<List<Object>> result = infoCityService.findMonitorStaticTab3(intervalId, leftOrRight, beginDate, endDate);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", result);
		return map;
	}

	/**
	 * 综合数据
	 * 
	 * @return
	 */
	@RequestMapping("/find/static/tab4")
	@ResponseBody
	public PageResultSet<MonitorIntervalLrStaticView> getStaticTab4(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("pageNum") String pageNum,
			@RequestParam("pageSize") String pageSize, @RequestParam("excelType") String excelType) {

		String beginTime = StringUtil.timeCnToEn(request.getParameter("beginTime"));
	String endTime = StringUtil.timeCnToEn(request.getParameter("endTime"));

		PageResultSet<MonitorIntervalLrStaticView> mvds = infoCityService.findMonitorStaticTab4(intervalId, leftOrRight,
				pageNum, pageSize, beginTime, endTime, excelType);

		if (mvds == null || mvds.getList() == null || mvds.getList().size() <= 0) {
			mvds = new PageResultSet<MonitorIntervalLrStaticView>();
			mvds.setPage(new Page(0, 10, 1));
			mvds.setList(new ArrayList<MonitorIntervalLrStaticView>());
		}
		return mvds;
	}

	@RequestMapping("/find/static/tab4d")
	@ResponseBody
	public CommonResponse getStaticTab4d(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("pageNum") String pageNum,
			@RequestParam("pageSize") String pageSize, @RequestParam("excelType") String excelType) {

		String beginTime = StringUtil.timeCnToEn(request.getParameter("beginTime"));
		String endTime = StringUtil.timeCnToEn(request.getParameter("endTime"));

		PageResultSet<MonitorIntervalLrStaticView> mvds = infoCityService.findMonitorStaticTab4(intervalId, leftOrRight,
				pageNum, pageSize, beginTime, endTime, excelType);

		CommonResponse commonResponse = new CommonResponse();
		try {
			String excelFileName = writeExcel(mvds.getList(), excelType);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	@RequestMapping("/find/static/tab5")
	@ResponseBody
	public MonitorIntervalLrStaticsView getStaticTab5(@RequestParam("intervalId") String intervalId,
			@RequestParam("leftOrRight") String leftOrRight, @RequestParam("beginRing") String beginRing,
			@RequestParam("endRing") String endRing, @RequestParam("model") String model,
			@RequestParam("type") String type) {

		String beginTime = StringUtil.timeCnToEn(request.getParameter("beginTime"));
		String endTime = StringUtil.timeCnToEn(request.getParameter("endTime"));

		String[] ks = request.getParameter("ks") != null ? request.getParameter("ks").split(",") : null;
		String[] kns = request.getParameter("kns") != null ? request.getParameter("kns").split(",") : null;
		String[] indxs = request.getParameter("indxs") != null ? request.getParameter("indxs").split(",") : null;

		MonitorIntervalLrStaticsView misv = infoCityService.findMonitorStaticTab5(intervalId, leftOrRight,
				StringUtil.nullToInt(model), type, beginTime, endTime, StringUtil.nullToInt(beginRing),
				StringUtil.nullToInt(endRing), ks, kns, indxs);
		return misv;
	}

	/**
	 * 获取数据权限树
	 * 
	 * @return
	 */
	@RequestMapping("/get/data/tree")
	@ResponseBody
	public List<Jstree> getDataRight() {
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		String[] urls = new String[4];
		urls[0] = "/monitor/info/to/city/index";
		urls[1] = "/monitor/info/to/line/index";
		urls[2] = "/monitor/info/to/area/index";
		urls[3] = "/monitor/info/to/lr/index";
		Boolean[] diss = new Boolean[4];
		return JsTreeUtil.getTreeData(request, city, urls, diss);
	}

	public String writeExcel(List<MonitorIntervalLrStaticView> datas, String type) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMdd");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String t = "日";
			if ("1".equals(type)) {// 日
				t = "日";
			} else if ("2".equals(type)) {
				t = "周";
			} else {
				t = "月";
			}
			String filename = generationStr + "_" + t + "汇总统计.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableCellFormat format = new WritableCellFormat();
			format.setAlignment(Alignment.CENTRE);
			WritableSheet sheet = book.createSheet(t + "汇总统计", 0);
			String[] rtitle = { "盾构掘进" + t + "报表", "工程名称", "统计时间", "本" + t + "掘进进度", "正常掘进时间", "管片拼装时间", "停机时间", "注浆量",
					"盾尾油脂量" };
			MonitorIntervalLrStaticView msv = datas.get(0);
			for (int i = 0; i < 9; i++) {
				if (i == 0) {
					sheet.mergeCells(0, i, 4, i);
					sheet.addCell(new Label(0, i, rtitle[0], format));
				} else if (i == 1) {
					sheet.mergeCells(1, i, 4, i);
					sheet.addCell(new Label(0, i, rtitle[1]));
					sheet.addCell(new Label(1, i, msv.getLineMark()));
				} else if (i == 2) {
					sheet.addCell(new Label(0, i, rtitle[2]));
					sheet.addCell(new Label(1, i, "起始日期"));
					if ("1".equals(type)) {// 日
						sheet.addCell(new Label(2, i, msv.getDate()));
					} else {
						sheet.addCell(new Label(2, i, msv.getBeginDate()));
					}
					sheet.addCell(new Label(3, i, "结束日期"));
					if ("1".equals(type)) {// 日
						sheet.addCell(new Label(4, i, msv.getDate()));
					} else {
						sheet.addCell(new Label(4, i, msv.getEndDate()));
					}
				} else if (i == 3) {
					sheet.addCell(new Label(0, i, rtitle[3]));
					sheet.addCell(new Label(1, i, "起始环号"));
					sheet.addCell(new Number(2, i, msv.getBeginRing()));
					sheet.addCell(new Label(3, i, "结束环号"));
					sheet.addCell(new Number(4, i, msv.getEndRing()));
				} else {
					sheet.mergeCells(1, i, 4, i);
					if (i == 4) {
						sheet.addCell(new Label(0, i, rtitle[4]));
						sheet.addCell(new Label(1, i, msv.getK0001() + "小时"));
					} else if (i == 5) {
						sheet.addCell(new Label(0, i, rtitle[5]));
						sheet.addCell(new Label(1, i, msv.getK0002() + "小时"));
					} else if (i == 6) {
						sheet.addCell(new Label(0, i, rtitle[6]));
						sheet.addCell(new Label(1, i, msv.getK0003() + "小时"));
					} else if (i == 7) {
						sheet.addCell(new Label(0, i, rtitle[7]));
						sheet.addCell(new Label(1, i, msv.getD0023() + "m³"));
					} else {
						sheet.addCell(new Label(0, i, rtitle[8]));
						sheet.addCell(new Label(1, i, msv.getG0001() + "L"));
					}
				}
			}
			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 10);
			sheet.setColumnView(3, 10);
			sheet.setColumnView(4, 10);
			book.write();
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
