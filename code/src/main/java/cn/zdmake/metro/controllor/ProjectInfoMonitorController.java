package cn.zdmake.metro.controllor;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.utils.GsonUtils;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.model.MetroCity;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.base.utils.Constants;
import cn.zdmake.metro.model.MetroDictionary;
import cn.zdmake.metro.model.MetroLine;
import cn.zdmake.metro.model.MetroLineInterval;
import cn.zdmake.metro.model.MetroLineIntervalWarning;
import cn.zdmake.metro.service.IMetroCityService;
import cn.zdmake.metro.service.IMetroDictionaryService;
import cn.zdmake.metro.service.IMetroLineIntervalWarningService;
import cn.zdmake.metro.vo.Jstree;
import cn.zdmake.metro.vo.Jstree.State;

/**
 * 监测预警设置管理控制器
 * @author hank
 *
 * 2016年8月15日
 */
@Controller
@RequestMapping("/project-info/monitor")
public class ProjectInfoMonitorController extends BaseController {
	
	private static Logger logger = Logger.getLogger(ProjectInfoMonitorController.class);
	
	@Resource
	IMetroLineIntervalWarningService lineIntervalWarningService;
	@Resource
	IMetroCityService cityService;
	@Resource
	IMetroDictionaryService dictionaryService;
	
	/**
	 * 监测预警设置首页
	 */
	@RequestMapping("/index")
	public String monitorSet(){
		
		return "/project-info/item_monitor";
	}
	
	/**
	 * 加载树
	 */
	@RequestMapping("/tree-data/get")
	@ResponseBody
	public String getTreeData(){
		Long cityId = 1l; //默认广州
		MetroCity city = cityService.findObjById(cityId);
		Jstree tree = new Jstree();
		//组装树
		if(CommonUtils.isNotNull(city)){
			tree.setId("city");
			tree.setIcon("city");
			tree.setText(city.getCityName());
			tree.setType("city");
			//tree.setUrl(request.getContextPath()+"/project-info/basic/cityinfo"+"?cityId="+city.getId());
			State cityState = tree.new State();
			cityState.setOpened(true);
			cityState.setDisabled(true);
			tree.setState(cityState);
			List<Jstree> cityChilds = new ArrayList<Jstree>();
			if(CommonUtils.isNotNull(city.getLineList())){
				for(MetroLine line : city.getLineList()){
					Jstree cityChild = new Jstree();
					cityChild.setId("line_"+line.getId());
					cityChild.setIcon("line");
					cityChild.setText(line.getLineName());
					cityChild.setType("line");
					//cityChild.setUrl(request.getContextPath()+"/project-info/basic/lineinfo"+"?lineId="+line.getId());
					State lineState = cityChild.new State();
					lineState.setOpened(true);
					lineState.setDisabled(true);
					cityChild.setState(lineState);
					List<Jstree> lineChilds = new ArrayList<Jstree>();
					if(CommonUtils.isNotNull(line.getIntervalList())){
						for(MetroLineInterval interval : line.getIntervalList()){
							Jstree lineChild = new Jstree();
							lineChild.setId("interval_"+interval.getId());
							lineChild.setIcon("area");
							lineChild.setText(interval.getIntervalName());
							lineChild.setType("area");
							//lineChild.setUrl(request.getContextPath()+"/project-info/basic/intervalinfo"+"?intervalId="+interval.getId());
							State intervalState = lineChild.new State();
							intervalState.setOpened(true);
							intervalState.setDisabled(true);
							lineChild.setState(intervalState);
							
							List<Jstree> intervalChilds = new ArrayList<Jstree>();
							Jstree l_intervalChild = new Jstree();
							l_intervalChild.setId("l_"+interval.getId());
							l_intervalChild.setIcon("left");
							l_intervalChild.setText("左线");
							l_intervalChild.setType("left");
							l_intervalChild.setUrl(request.getContextPath()+"/project-info/monitor/lrinfo?leftOrRight=l&intervalId="+interval.getId());
							State lState = l_intervalChild.new State();
							lState.setOpened(true);
							if(cityChilds.size() == 0 && lineChilds.size() == 0){ //默认选中第一个
								lState.setSelected(true);
							}
							l_intervalChild.setState(lState);
							Jstree r_intervalChild = new Jstree();
							r_intervalChild.setId("r_"+interval.getId());
							r_intervalChild.setIcon("right");
							r_intervalChild.setText("右线");
							r_intervalChild.setType("right");
							r_intervalChild.setUrl(request.getContextPath()+"/project-info/monitor/lrinfo?leftOrRight=r&intervalId="+interval.getId());
							State rState = r_intervalChild.new State();
							rState.setOpened(true);
							r_intervalChild.setState(rState);
							intervalChilds.add(l_intervalChild);
							intervalChilds.add(r_intervalChild);
							lineChild.setChildren(intervalChilds);

							lineChilds.add(lineChild);
						}
						cityChild.setChildren(lineChilds);
					}
					cityChilds.add(cityChild);
				}
				tree.setChildren(cityChilds);
			}
		}
		return GsonUtils.toJson(tree);
	}
	
	/**
	 * 监测预警阈值信息保存
	 * @return
	 */
	@SysControllorLog(menu="监测预警设置",opreate="新增监测预警阈值信息")
	@RequestMapping(value="/lrinfo/save",method=RequestMethod.POST)
	@ResponseBody
	public String lrinfoSave(MetroLineIntervalWarning warning){
		CommonResponse r = new CommonResponse();
		try{
			Long result = lineIntervalWarningService.insertObj(warning);
			if(result != null){ //保存成功
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult("保存成功");
			}else{
				logger.error("保存监测预警阈值信息出错");
				r.setCode(Constants.CODE_FAIL);
				r.setResult("保存出错");
			}
		}catch(Exception e){
			logger.error("保存监测预警阈值信息异常",e);
			r.setCode(Constants.CODE_FAIL);
			r.setResult("保存异常");
		}
		return GsonUtils.toJson(r);
	}
	
	/**
	 * 更新监测预警阈值信息
	 */
	@SysControllorLog(menu="监测预警设置",opreate="编辑监测预警阈值信息")
	@RequestMapping(value="/lrinfo/update",method=RequestMethod.POST)
	@ResponseBody
	public String updateLrinfo(MetroLineIntervalWarning warning){
		CommonResponse r = new CommonResponse();
		try{
			boolean result = lineIntervalWarningService.updateObj(warning);
			if(result){ //更新保存成功
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult("更新成功");
			}else{
				logger.error("更新城市线路信息出错");
				r.setCode(Constants.CODE_FAIL);
				r.setResult("更新出错");
			}
		}catch(Exception e){
			logger.error("更新城市线路信息异常",e);
			r.setCode(Constants.CODE_FAIL);
			r.setResult("更新异常");
		}
		return GsonUtils.toJson(r);
	}
	
	/**
	 * 删除监测预警阈值信息
	 */
	@SysControllorLog(menu="监测预警设置",opreate="删除监测预警阈值信息")
	@RequestMapping(value="/lrinfo/delete",method=RequestMethod.POST)
	@ResponseBody
	public String deleteLrinfo(){
		CommonResponse r = new CommonResponse();
		try{
			String id = request.getParameter("id");
			boolean result = lineIntervalWarningService.deleteObj(Long.parseLong(id));
			if(result){ //删除成功
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult("删除成功");
			}else{
				logger.error("删除监测预警阈值信息出错");
				r.setCode(Constants.CODE_FAIL);
				r.setResult("删除出错");
			}
		}catch(Exception e){
			logger.error("删除监测预警阈值信息异常",e);
			r.setCode(Constants.CODE_FAIL);
			r.setResult("删除异常");
		}
		return GsonUtils.toJson(r);
	}
	
	/**
	 * 将监测预警阈值信息写入到excel
	 * @param warns
	 * @return excel文件名
	 * @throws Exception
	 */
	public String writeExcel(List<MetroLineIntervalWarning> warns){
		WritableWorkbook book = null;
		try{
			Date date = new Date();
    		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
    		String generationStr = simpleFormat.format(date)+ (new Random().nextInt(900)+100);
			String filename = generationStr+ "_"+"监测预警数据.xls";
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File(uploadPath+"/"+filename));
			WritableSheet sheet = book.createSheet("监测预警阈值", 0);
			String[] title = {"参数代号","参数含义","推进预警","拼装预警","停机预警","开始环号","结束环号","红色预警上限","红色预警下限","橙色预警上限","橙色预警下限"};
			for(int i = 0;i < title.length;i++){
				sheet.addCell(new Label(i, 0, title[i]));
			}
			//NumberFormat nf = new jxl.write.NumberFormat("0.######");
			//WritableCellFormat wcfN = new WritableCellFormat(nf);
			//jxl.write.Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			if(CommonUtils.isNotNull(warns)){
				for(int i = 0;i<warns.size();i++){
					MetroLineIntervalWarning warn = warns.get(i);
					sheet.addCell(new Label(0, i+1, warn.getParam()));
					sheet.addCell(new Label(1, i+1, warn.getParamDic().getDicMean()));
					int category = warn.getCategory()==null?0:warn.getCategory();
					if(category == 1 || category == 3 || category == 5 || category == 7) {
						sheet.addCell(new Label(2, i + 1, "1"));
					}else {
						sheet.addCell(new Label(2, i + 1, "0"));
					}
					if(category == 2 || category == 3 || category == 6 || category == 7) {
						sheet.addCell(new Label(3, i + 1, "1"));
					}else {
						sheet.addCell(new Label(3, i + 1, "0"));
					}
					if(category == 4 || category == 5 || category == 6 || category == 7) {
						sheet.addCell(new Label(4, i + 1, "1"));
					}else {
						sheet.addCell(new Label(4, i + 1, "0"));
					}
					sheet.addCell(new Label(5, i+1, String.valueOf(warn.getStartRingNum())));
					sheet.addCell(new Label(6, i+1, String.valueOf(warn.getEndRingNum())));
					sheet.addCell(new Label(7, i+1, String.valueOf(warn.getRedWarningMax())));
					sheet.addCell(new Label(8, i+1, String.valueOf(warn.getRedWarningMin())));
					sheet.addCell(new Label(9, i+1, String.valueOf(warn.getOrangeWarningMax())));
					sheet.addCell(new Label(10, i+1, String.valueOf(warn.getOrangeWarningMin())));
				}
			}
			book.write();
			return filename;
		}catch (Exception e) {
			logger.error("监测预警阈值信息写入excel异常", e);
		}finally{
			if(book != null){
				try{
					book.close();
				}catch(Exception e){
					logger.error("监测预警阈值信息写入excel关闭IO异常", e);
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		List<MetroLineIntervalWarning> warns = new ArrayList<MetroLineIntervalWarning>();
		MetroLineIntervalWarning warn1 = new MetroLineIntervalWarning();
		warn1.setId(1l);
		warn1.setParam("P1");
		MetroLineIntervalWarning warn2 = new MetroLineIntervalWarning();
		warn2.setId(2l);
		warn2.setParam("P2");
		warns.add(warn1);
		warns.add(warn2);
		
		WritableWorkbook book = null;
		try{
			String uploadPath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			book = Workbook.createWorkbook(new File("G:/test.xls"));
			WritableSheet sheet = book.createSheet("测试下", 0);
			if(CommonUtils.isNotNull(warns)){
				for(int i = 0;i<warns.size();i++){
					MetroLineIntervalWarning warn = warns.get(i);
					sheet.addCell(new Label(0, i, warn.getParam()));
					sheet.addCell(new Number(1, i, warn.getId()));
				}
			}
			book.write();
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(book != null){
				try{
					book.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 导出监测预警阈值信息
	 */
	@SysControllorLog(menu="监测预警设置",opreate="导出监测预警阈值信息")
	@RequestMapping(value="/lrinfo/export",method=RequestMethod.POST)
	@ResponseBody
	public String exportLrinfo(){
		CommonResponse r = new CommonResponse();
		try{
			String intervalId = request.getParameter("intervalId");
			String leftOrRight = request.getParameter("leftOrRight");
			List<MetroLineIntervalWarning> warns = lineIntervalWarningService.findAllByIntervalIdAndLr(Long.parseLong(intervalId), leftOrRight);
			String excelFileName = writeExcel(warns);
			if(excelFileName != null){ //成功
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult(excelFileName);
			}else{
				logger.error("导出出错");
				r.setCode(Constants.CODE_FAIL);
				r.setResult("导出出错");
			}
		}catch(Exception e){
			logger.error("导出监测预警阈值信息异常",e);
			r.setCode(Constants.CODE_FAIL);
			r.setResult("导出异常");
		}
		return GsonUtils.toJson(r);
	}
	/**
	 * 导入监测预警阈值信息
	 * @return
	 */
	@SysControllorLog(menu="监测预警设置",opreate="导入监测预警阈值信息")
	@RequestMapping(value="/lrinfo/import",method=RequestMethod.POST)
	public String uploadCityProjectPdf(@RequestParam("intervalId")String intervalId,@RequestParam("leftOrRight")String leftOrRight,@RequestParam(value="file",required=false) MultipartFile file){
		CommonResponse r = new CommonResponse();
		try{
			boolean result = lineIntervalWarningService.importExcelData(intervalId,leftOrRight,file);
			if(result){ //导入成功
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult("导入成功");
			}else{ //导入失败
				logger.error("导入失败");
				request.setAttribute("msg", "导入失败");
				return "/common/error";
			}
		}catch(Exception e){
			logger.error("导入异常", e);
			request.setAttribute("msg", "导入异常");
			return "/common/error";
			/*r.setCode(Constants.CODE_FAIL);
			r.setResult("文件上传异常");*/
		}
		return "forward:/project-info/monitor/lrinfo?intervalId="+intervalId+"&leftOrRight="+leftOrRight;
	}
		
	/**
	 * 加载左右线的监测预警信息页面
	 * @return
	 */
	@RequestMapping("/lrinfo")
	public String lrinfo(){
		String intervalId = request.getParameter("intervalId");
		String leftOrRight = request.getParameter("leftOrRight");
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		request.setAttribute("dics", dicSet.getList());
		request.setAttribute("intervalId", intervalId);
		request.setAttribute("leftOrRight", leftOrRight);
		return "/project-info/item_monitor_right";
	}
	
	/**
	 * 监测预警信息编辑页面
	 * @return
	 */
	@RequestMapping("/lrinfo/to-edit")
	public ModelAndView lrinfoToEdit(){
		String id = request.getParameter("id");
		MetroLineIntervalWarning warn = lineIntervalWarningService.findObjById(Long.parseLong(id));
		PageResultSet<MetroDictionary> dicSet = dictionaryService.findMetroDictionaryInfo(0, 1000);
		JModelAndView mv = new JModelAndView("/project-info/item_monitor_right_edit", request, response);
		mv.addObject("model", warn);
		mv.addObject("dics", dicSet.getList());
		return mv;
	}
	
	/**
	 * 分页查找左右线的监测预警信息
	 * @return
	 */
	@RequestMapping("/lrinfo/find")
	@ResponseBody
	public String findLrinfo(){
		String intervalId = request.getParameter("intervalId");
		int pageNum = StringUtil.nullToInt(request.getParameter("pageNum"));
		int pageSize = StringUtil.nullToInt(request.getParameter("pageSize"));
		String leftOrRight = request.getParameter("leftOrRight");
		PageResultSet<MetroLineIntervalWarning> resultSet = lineIntervalWarningService.findLineIntervalWarningInfo(Long.parseLong(intervalId), leftOrRight, pageNum, pageSize);
		return GsonUtils.toJson(resultSet);
	}


	
}
