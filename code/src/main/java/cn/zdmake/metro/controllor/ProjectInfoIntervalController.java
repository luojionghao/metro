package cn.zdmake.metro.controllor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.*;
import cn.zdmake.metro.model.*;
import cn.zdmake.metro.service.*;
import com.ibm.icu.util.Calendar;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.vo.Jstree;
import cn.zdmake.metro.vo.Jstree.State;

/**
 * 区间数据管理控制器
 * 
 * @author hank
 *
 *         2016年8月15日
 */
@Controller
@RequestMapping("/project-info/interval")
public class ProjectInfoIntervalController extends BaseController {

	private static Logger logger = Logger.getLogger(ProjectInfoIntervalController.class);

	@Resource
	IMetroLineIntervalMdReService lineIntervalMdReService;
	@Resource
	ICommonService commonService;
	@Resource
	IMetroCityService cityService;
	@Resource
	IMetroLineIntervalPpService lineIntervalPpService;
	@Resource
	IMetroLineIntervalRpService lineIntervalRpService;
	@Resource
	IMetroLineIntervalSpService lineIntervalSpService;
	@Resource
	IMetroLineIntervalService lineIntervalService;
	@Resource
	IMetroLineIntervalMdService lineIntervalMdService;
	@Resource
	IMetroDictionaryService dictionaryService;
	@Resource
	IMetroLineIntervalSaService lineIntervalSaService;
	@Resource
	IMetroLineIntervalStService lineIntervalStService;

	/**
	 * 区间数据管理首页
	 */
	@RequestMapping("/index")
	public String intervalIndex() {
		return "/project-info/item_section";
	}
	// 沉降点编辑删除

	/**
	 * 沉降点信息编辑页面
	 * 
	 * @return
	 */
	@RequestMapping("/spinfo/to-edit")
	public ModelAndView spinfoToEdit() {
		String id = request.getParameter("id");
		MetroLineIntervalSp sp = lineIntervalSpService.findObjById(Long.parseLong(id));
		JModelAndView mv = new JModelAndView("/project-info/item_section_right_edit", request, response);
		mv.addObject("model", sp);
		return mv;
	}

	/**
	 * 删除沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "删除沉降点")
	@RequestMapping(value = "/spinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSpinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String id = request.getParameter("id");
			boolean result = lineIntervalSpService.deleteObj(Long.parseLong(id));
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除沉降点信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除沉降点信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "删除管片姿态")
	@RequestMapping(value = "/sainfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteSainfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String id = request.getParameter("id");
			boolean result = lineIntervalSaService.deleteObj(Long.parseLong(id));
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除管片姿态信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除管片姿态信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "删除盾尾间隙")
	@RequestMapping(value = "/stinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteStinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String id = request.getParameter("id");
			boolean result = lineIntervalStService.deleteObj(Long.parseLong(id));
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除盾尾间隙信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除盾尾间隙信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 删除沉降点监测上传记录信息 删除文件的同时，需要删除文件对应的数据
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "删除沉降点监测文件")
	@RequestMapping(value = "/mdreinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteMdreinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			// 需要获取文件解析并删除两个表记录
			String id = request.getParameter("id");
			boolean result = lineIntervalMdReService.deleteLineIntervalMdReInfo(Long.parseLong(id));
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除沉降点监测上传记录信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除沉降点监测上传记录信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 加载区间数据信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/intervalinfo")
	public String intervalinfo() {
		String intervalId = request.getParameter("intervalId");
		String desc = request.getParameter("desc");
		MetroLineIntervalPp pp = lineIntervalPpService.findByIntervalId(Long.parseLong(intervalId));
		MetroLineInterval interval = lineIntervalService.findObjById(Long.parseLong(intervalId));
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		request.setAttribute("pp", pp);
		request.setAttribute("intervalId", intervalId);
		request.setAttribute("interval", interval);
		request.setAttribute("desc", desc);
		request.setAttribute("riskPdfUrl", interval.getRiskPdfUrl());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		request.setAttribute("beginTime", sdf.format(Calendar.getInstance().getTime()));
		request.setAttribute("dics", dicSet.getList());
		return "/project-info/item_section_right";
	}

	/**
	 * 上传svg文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ppinfo/svg-upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public CommonResponse uploadFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				return result;
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 上传剖面图大地坐标文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/img-map-xy-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadImgMapXyFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			String intervalId = request.getParameter("intervalId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				MetroLineInterval interval = new MetroLineInterval();
				interval.setId(Long.parseLong(intervalId));
				interval.setImgMapXyUrl((String) result.getResult());
				boolean updateR = lineIntervalService.updateObj(interval);
				if (updateR) {
					return result;
				} else {
					logger.error("文件上传成功，更新数据库失败");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("上传失败");
				}
			} else {
				logger.error("文件上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 上传沉降点监测记录文件
	 * 
	 * @param request
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "上传沉降点监测文件")
	@RequestMapping(value = "/mdre-upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public CommonResponse uploadMdreFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			String intervalId = request.getParameter("intervalId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				boolean updateR = lineIntervalMdReService.uploadLineIntervalMdReData(Long.parseLong(intervalId),
						(String) result.getResult());
				if (updateR) {
					return result;
				} else {
					logger.error("文件上传成功，更新数据库失败");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("上传失败");
				}
			} else {
				logger.error("文件上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传失败");
			}
		} catch (Exception e) {
			logger.error("文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 分页查找区间沉降点信息
	 * 
	 * @return
	 */
	@RequestMapping("/spinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSp> findSpinfo() {
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		PageResultSet<MetroLineIntervalSp> resultSet = lineIntervalSpService
				.findLineIntervalSpInfo(Long.parseLong(intervalId), pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间盾尾间隙信息
	 * 
	 * @return
	 */
	@RequestMapping("/stinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSt> findStinfo() {
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		PageResultSet<MetroLineIntervalSt> resultSet = lineIntervalStService
				.findLineIntervalStInfo(Long.parseLong(intervalId), pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间管片姿态信息
	 * 
	 * @return
	 */
	@RequestMapping("/sainfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalSa> findSainfo() {
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		PageResultSet<MetroLineIntervalSa> resultSet = lineIntervalSaService
				.findLineIntervalSaInfo(Long.parseLong(intervalId), pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间沉降点监测数据上传记录信息
	 * 
	 * @return
	 */
	@RequestMapping("/mdreinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalMdRe> findMdreinfo() {
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		PageResultSet<MetroLineIntervalMdRe> resultSet = lineIntervalMdReService
				.findLineIntervalMdReInfo(Long.parseLong(intervalId), pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 分页查找区间风险位置信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo/find")
	@ResponseBody
	public PageResultSet<MetroLineIntervalRp> findRiskinfo() {
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		PageResultSet<MetroLineIntervalRp> resultSet = lineIntervalRpService
				.findLineIntervalRpInfo(Long.parseLong(intervalId), pageNum, pageSize);
		return resultSet;
	}

	/**
	 * 获取风险位置信息
	 * 
	 * @return
	 */
	@RequestMapping("/riskinfo/to-edit")
	@ResponseBody
	public MetroLineIntervalRp riskinfoToEdit() {
		String id = request.getParameter("id");
		MetroLineIntervalRp rp = lineIntervalRpService.findObjById(Long.parseLong(id));
		return rp;
	}

	/**
	 * 保存风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "新增风险位置")
	@RequestMapping(value = "/riskinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savRiskinfo(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalRpService.insertObj(rp);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存风险位置信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存风险位置信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "更新风险位置")
	@RequestMapping(value = "/riskinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRiskinfo(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalRpService.updateObj(rp);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 删除风险位置信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "删除风险位置")
	@RequestMapping(value = "/riskinfo/delete", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteRiskinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String id = request.getParameter("id");
			boolean result = lineIntervalRpService.deleteObj(Long.parseLong(id));
			if (result) { // 删除成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("删除成功");
			} else {
				logger.error("删除风险位置信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("删除出错");
			}
		} catch (Exception e) {
			logger.error("删除风险位置信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("删除异常");
		}
		return commonResponse;
	}

	/**
	 * 上传风险位置文件
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/riskinfo/file-upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public CommonResponse uploadRiskFile(MultipartHttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String fileId = request.getParameter("fileId");
			MultipartFile mf = request.getFile(fileId);
			CommonResponse result = commonService.fileUpload(mf);
			if (result.getCode() == Constants.CODE_SUCCESS) {
				return result;
			} else {
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("上传风险位置失败");
			}
		} catch (Exception e) {
			logger.error("风险位置文件上传异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("风险位置文件上传异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置图片
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "更新风险位置图片")
	@RequestMapping(value = "/riskImg-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadRiskImg(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();

		try {
			boolean result = lineIntervalRpService.updateRiskImg(rp.getId(), rp.getRiskImgUrl());
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置图片出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置图片异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 更新风险位置文档
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "更新风险位置文档")
	@RequestMapping(value = "/riskPdf-upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public CommonResponse uploadRiskPdf(MetroLineIntervalRp rp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalRpService.updateRiskPdf(rp.getId(), rp.getRiskPdf1Url(), rp.getRiskPdf2Url(),
					rp.getRiskPdf3Url());
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新风险位置文档出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新风险位置文档异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "新增沉降点")
	@RequestMapping(value = "/spinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savSpinfo(MetroLineIntervalSp sp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalSpService.insertObj(sp);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存沉降点信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存沉降点信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新沉降点信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "编辑沉降点")
	@RequestMapping(value = "/spinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateSpinfo(MetroLineIntervalSp sp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSpService.updateObj(sp);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新沉降点出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新沉降点异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "新增管片姿态")
	@RequestMapping(value = "/sainfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savSainfo(MetroLineIntervalSa sa) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalSaService.insertObj(sa);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存管片姿态信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存管片姿态信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新管片姿态信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "编辑管片姿态")
	@RequestMapping(value = "/sainfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateSainfo(MetroLineIntervalSa sa) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.updateObj(sa);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新管片姿态出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新管片姿态异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 保存盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "新增盾尾间隙")
	@RequestMapping(value = "/stinfo/save", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse savStinfo(MetroLineIntervalSt st) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			Long result = lineIntervalStService.insertObj(st);
			if (result != null) { // 保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("保存成功");
			} else {
				logger.error("保存盾尾间隙信息出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("保存出错");
			}
		} catch (Exception e) {
			logger.error("保存盾尾间隙信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存异常");
		}
		return commonResponse;
	}

	/**
	 * 更新盾尾间隙信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "编辑盾尾间隙")
	@RequestMapping(value = "/stinfo/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateStinfo(MetroLineIntervalSt st) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.updateObj(st);
			if (result) { // 更新保存成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("更新成功");
			} else {
				logger.error("更新盾尾间隙出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("更新出错");
			}
		} catch (Exception e) {
			logger.error("更新盾尾间隙异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("更新异常");
		}
		return commonResponse;
	}

	/**
	 * 导出沉降点数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "导出沉降点数据")
	@RequestMapping(value = "/spinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportLrinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String intervalId = request.getParameter("intervalId");
			// String leftOrRight = request.getParameter("leftOrRight");
			String desc = request.getParameter("desc");
			List<MetroLineIntervalSp> datas = lineIntervalSpService.findLineIntervalSps(Long.parseLong(intervalId));
			String excelFileName = writeExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出沉降点数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出盾构数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "导出盾构数据")
	@RequestMapping(value = "/sdinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportShieldinfo() {
		// TODO @RequestParam替换，以及参数date的格式
		CommonResponse commonResponse = new CommonResponse();
		try {
			String intervalId = request.getParameter("intervalId");
			String leftOrRight = request.getParameter("leftOrRight");
			String date = StringUtil.timeCnToEn(request.getParameter("date"));
			String ring = request.getParameter("ring");
			String type = request.getParameter("type");
			String key = request.getParameter("key");
			Map<String, Object> datas = lineIntervalService.getShieldData(Long.parseLong(intervalId), leftOrRight, date,
					ring, key, type);
			MetroLineInterval metroLineInterval = lineIntervalService.findObjById(Long.parseLong(intervalId));
			String intervalName = metroLineInterval.getIntervalName();
			String machineNo = "";
			for (MetroLineIntervalLr lr : metroLineInterval.getIntervalLrList()) {
				if (leftOrRight.equals(lr.getLeftOrRight())) {
					machineNo = lr.getMachineNo();
				}
			}
			String excelFileName = writeShieldToExcel(datas, key, intervalName, machineNo);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出盾构数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出盾尾间隙数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "导出盾尾间隙数据")
	@RequestMapping(value = "/stinfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportStinfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String intervalId = request.getParameter("intervalId");
			// String leftOrRight = request.getParameter("leftOrRight");
			String desc = request.getParameter("desc");
			List<MetroLineIntervalSt> datas = lineIntervalStService.findLineIntervalSts(Long.parseLong(intervalId));
			String excelFileName = writeStExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出沉降点数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 导出沉降点数据信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "导出管片姿态数据")
	@RequestMapping(value = "/sainfo/export", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse exportSainfo() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String intervalId = request.getParameter("intervalId");
			// String leftOrRight = request.getParameter("leftOrRight");
			String desc = request.getParameter("desc");
			List<MetroLineIntervalSa> datas = lineIntervalSaService.findLineIntervalSas(Long.parseLong(intervalId));
			String excelFileName = writeSaExcel(datas, desc);
			if (excelFileName != null) { // 成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult(excelFileName);
			} else {
				logger.error("导出出错");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导出出错");
			}
		} catch (Exception e) {
			logger.error("导出管片姿态数据信息异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导出异常");
		}
		return commonResponse;
	}

	/**
	 * 将沉降点数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeExcel(List<MetroLineIntervalSp> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "沉降点数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("沉降点数据", 0);
			String[] title = { "沉降点名称", "线路", "初始里程", "大地坐标X", "大地坐标Y", "累计沉降正值", "累计沉降负值", "沉降速率预警值" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSp data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getSpName()));
					if (data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getOriginMileage())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getMapX().floatValue())));
					sheet.addCell(new Label(4, i + 1, String.valueOf(data.getMapY().floatValue())));
					sheet.addCell(
							new Label(5, i + 1, String.valueOf(data.getSpSumAdd() != null ? data.getSpSumAdd() : "")));
					sheet.addCell(
							new Label(6, i + 1, String.valueOf(data.getSpSumSub() != null ? data.getSpSumSub() : "")));
					sheet.addCell(new Label(7, i + 1,
							String.valueOf(data.getSpSpeedWarningVal() != null ? data.getSpSpeedWarningVal() : "")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("沉降点数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("沉降点数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将盾构数据信息写入到excel
	 * 
	 * @param datas
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeShieldToExcel(Map<String, Object> datas, String key, String intervalName, String machineNo) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + intervalName + "_" + machineNo + "_" + key + "_盾构数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet(key + "_盾构数据", 0);
			String[] title = { "字段值", "日期" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				int i = 0;
				for (Map.Entry<String, Object> entry : datas.entrySet()) {
					sheet.addCell(
							new Label(0, i + 1, String.valueOf(entry.getValue() != null ? entry.getValue() : "")));
					sheet.addCell(new Label(1, i + 1, StringUtil.timeToString(entry.getKey())));
					i++;
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("盾构数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("盾构数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将盾尾间隙数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeStExcel(List<MetroLineIntervalSt> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "盾尾间隙数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("盾尾间隙数据", 0);
			String[] title = { "环号", "线路", "盾尾间隙上", "盾尾间隙下", "盾尾间隙左", "盾尾间隙右", "日期时间" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSt data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getRingNum()));
					if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getStUp())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getStDown())));
					sheet.addCell(new Label(4, i + 1, String.valueOf(data.getStLeft())));
					sheet.addCell(new Label(5, i + 1, String.valueOf(data.getStRight())));
					sheet.addCell(new Label(6, i + 1, DateUtil.format(data.getDateTime(), "YYYY/MM/dd HH:mm:ss")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("盾尾间隙数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("盾尾间隙数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 将管片姿态数据信息写入到excel
	 * 
	 * @param datas
	 * @param desc
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeSaExcel(List<MetroLineIntervalSa> datas, String desc) {
		WritableWorkbook book = null;
		try {
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date) + (new Random().nextInt(900) + 100);
			String filename = generationStr + "_" + "管片姿态数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath + "/" + filename));
			WritableSheet sheet = book.createSheet("管片姿态数据", 0);
			String[] title = { "环号", "线路", "水平偏差", "垂直偏差", "日期时间" };
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 0, title[i]));
			}
			if (CommonUtils.isNotNull(datas)) {
				for (int i = 0; i < datas.size(); i++) {
					MetroLineIntervalSa data = datas.get(i);
					sheet.addCell(new Label(0, i + 1, data.getRingNum()));
					if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("l")) {
						sheet.addCell(new Label(1, i + 1, "左线"));
					} else if (data.getLeftOrRight() != null && data.getLeftOrRight().equals("r")) {
						sheet.addCell(new Label(1, i + 1, "右线"));
					} else {
						sheet.addCell(new Label(1, i + 1, ""));
					}
					sheet.addCell(new Label(2, i + 1, String.valueOf(data.getHorizontalDev())));
					sheet.addCell(new Label(3, i + 1, String.valueOf(data.getVerticalDev())));
					sheet.addCell(new Label(4, i + 1, DateUtil.format(data.getDateTime(), "YYYY/MM/dd HH:mm:ss")));
				}
			}
			book.write();
			return filename;
		} catch (Exception e) {
			logger.error("管片姿态数据信息写入excel异常", e);
		} finally {
			if (book != null) {
				try {
					book.close();
				} catch (Exception e) {
					logger.error("管片姿态数据信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}

	/**
	 * 导入盾尾间隙数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "上传盾尾间隙数据")
	@RequestMapping(value = "/stinfo/import", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importStinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalStService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");

				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导入失败");

				/*
				 * request.setAttribute("msg", "导入失败"); return "/common/error";
				 */
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			/*
			 * request.setAttribute("msg", "导入异常"); return "/common/error";
			 */

			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导入异常");

		}
		return commonResponse;
	}

	/**
	 * 导入管片姿态数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "上传管片姿态数据")
	@RequestMapping(value = "/sainfo/import", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse importSainfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSaService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");

				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("导入失败");

				/*
				 * request.setAttribute("msg", "导入失败"); return "/common/error";
				 */
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			/*
			 * request.setAttribute("msg", "导入异常"); return "/common/error";
			 */

			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("导入异常");

		}
		return commonResponse;
	}

	/**
	 * 导入沉降点数据信息
	 * 
	 * @return
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "上传沉降点数据")
	@RequestMapping(value = "/spinfo/import", method = RequestMethod.POST)
	public String importLrinfo(@RequestParam("intervalId") String intervalId,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			boolean result = lineIntervalSpService.importExcelData(intervalId, file);
			if (result) { // 导入成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("导入成功");
			} else { // 导入失败
				logger.error("导入失败");
				/*
				 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传成功，入库失败");
				 */
				request.setAttribute("msg", "导入失败");
				return "/common/error";
			}
		} catch (Exception e) {
			logger.error("导入异常", e);
			request.setAttribute("msg", "导入异常");
			return "/common/error";
			/*
			 * r.setCode(Constants.CODE_FAIL); r.setResult("文件上传异常");
			 */
		}
		return "forward:/project-info/interval/intervalinfo?intervalId=" + intervalId + "&desc=tab2";
	}

	/**
	 * 保存或更新工程进度信息
	 */
	@SysControllorLog(menu = "区间数据管理", opreate = "编辑工程进度信息")
	@RequestMapping(value = "/ppinfo/save-or-update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse saveOrUpdateLineIntervalPpInfo(MetroLineIntervalPp pp) {
		CommonResponse commonResponse = new CommonResponse();
		try {
			if (CommonUtils.isNotNull(pp.getId())) { // 更新
				boolean result1 = lineIntervalPpService.updateObj(pp);
				if (result1) { // 更新保存成功
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("更新成功");
				} else {
					logger.error("更新城市线路区间工程进度出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("更新出错");
				}
			} else { // 保存
				Long result2 = lineIntervalPpService.insertObj(pp);
				if (result2 != null) {
					commonResponse.setCode(Constants.CODE_SUCCESS);
					commonResponse.setResult("保存成功");
				} else {
					logger.error("保存城市线路区间工程进度出错");
					commonResponse.setCode(Constants.CODE_FAIL);
					commonResponse.setResult("保存出错");
				}
			}
		} catch (Exception e) {
			logger.error("保存或更新城市线路区间工程进度异常", e);
			commonResponse.setCode(Constants.CODE_FAIL);
			commonResponse.setResult("保存或更新异常");
		}
		return commonResponse;
	}

	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public Jstree getTreeData() {
		Long cityId = 1l; // 默认广州
		MetroCity city = cityService.findObjById(cityId);
		Jstree tree = new Jstree();
		// 组装树
		if (CommonUtils.isNotNull(city)) {
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			// tree.setUrl(request.getContextPath()+"/project-info/interval/cityinfo"+"?cityId="+city.getId());
			State cityState = tree.new State();
			cityState.setOpened(true);
			cityState.setDisabled(true);
			tree.setState(cityState);
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if (CommonUtils.isNotNull(city.getLineList())) {
				for (MetroLine line : city.getLineList()) {
					Jstree cityChild = new Jstree();
					cityChild.setId("line_" + line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					// cityChild.setUrl(request.getContextPath()+"/project-info/interval/lineinfo"+"?lineId="+line.getId());
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					lineState.setDisabled(true);
					cityChild.setState(lineState);
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if (CommonUtils.isNotNull(line.getIntervalList())) {
						for (MetroLineInterval interval : line.getIntervalList()) {
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_" + interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							lineChild.setUrl(request.getContextPath() + "/project-info/interval/intervalinfo"
									+ "?intervalId=" + interval.getId());
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							if (cityChilds.size() == 0 && lineChilds.size() == 0) {
								intervalState.setSelected(true);
							}
							lineChild.setState(intervalState);
							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		return tree;
	}

	/**
	 * 沉降点监测数据上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "/md-re/upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse uploadLineIntervalMdReData() {
		CommonResponse commonResponse = new CommonResponse();
		try {
			String intervalId = request.getParameter("intervalId");
			CommonResponse uploadResult = commonService.fileUpload(request);
			if (Constants.CODE_SUCCESS != uploadResult.getCode()) { // 上传成功
				return uploadResult;
			}
			String uploadFileUrl = (String) uploadResult.getResult();
			boolean result = lineIntervalMdReService.uploadLineIntervalMdReData(Long.parseLong(intervalId),
					uploadFileUrl);
			if (result) { // 上传成功
				commonResponse.setCode(Constants.CODE_SUCCESS);
				commonResponse.setResult("上传成功");
			} else { // 上传失败
				logger.error("沉降点监测数据上传失败");
				commonResponse.setCode(Constants.CODE_FAIL);
				commonResponse.setResult("沉降点监测数据上传失败");
			}
		} catch (Exception e) {
			logger.error("沉降点监测数据上传异常", e);
			commonResponse.setCode(0);
			commonResponse.setResult("沉降点监测数据上传异常");
		}
		return commonResponse;
	}

}
