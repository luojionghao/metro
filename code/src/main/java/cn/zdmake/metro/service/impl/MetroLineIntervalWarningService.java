package cn.zdmake.metro.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.zdmake.metro.background.vo.IntervalWarningVO;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.ZookeTransactionException;
import cn.zdmake.metro.dao.IMetroLineIntervalWarningDao;
import cn.zdmake.metro.model.MetroLineIntervalWarning;
import cn.zdmake.metro.service.IMetroLineIntervalWarningService;

/**
 * 地铁线路区间监测预警阈值业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalWarningService")
public class MetroLineIntervalWarningService extends BaseService<MetroLineIntervalWarning> implements IMetroLineIntervalWarningService{
	private static Logger logger = Logger.getLogger(MetroLineIntervalWarningService.class);

	@Autowired
	IMetroLineIntervalWarningDao lineIntervalWarningDao;

	/**
	 * 分页查询
	 * 线路区间左右线的监测预警阀值设置信息
	 * @param intervalId 线路区间id
	 * @param leftOrRight 左右线标识
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalWarning> findLineIntervalWarningInfo(
			Long intervalId, String leftOrRight, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);		
		params.put("leftOrRight", leftOrRight);		
		PageResultSet<MetroLineIntervalWarning> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalWarningDao);
	    return resultSet;
	}

	/**
	 * 保存监测预警阈值信息
	 * @param warning
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalWarning warning) {
		int r = lineIntervalWarningDao.insertObj(warning);
		if(r > 0){
			return warning.getId();
		}
		return null;
	}

	/**
	 * 通过id查询监测预警阈值信息
	 * @param intervalWariningId
	 * @return
	 */
	@Override
	public MetroLineIntervalWarning findObjById(Long intervalWariningId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalWariningId", intervalWariningId);
		return lineIntervalWarningDao.findObjById(params);
	}
	/**
	 * 删除监测预警阈值信息
	 * @param intervalWariningId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalWariningId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalWariningId", intervalWariningId);
		int r = lineIntervalWarningDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 更新监测预警阈值信息
	 * @param warning
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalWarning warning) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalWariningId", warning.getId());
		params.put("intervalId", warning.getIntervalId());
		params.put("param", warning.getParam());
		params.put("category",warning.getCategory());
		params.put("startRingNum", warning.getStartRingNum());
		params.put("endRingNum", warning.getEndRingNum());
		params.put("redWarningMax", warning.getRedWarningMax());
		params.put("orangeWarningMax", warning.getOrangeWarningMax());
		params.put("orangeWarningMin", warning.getOrangeWarningMin());
		params.put("redWarningMin", warning.getRedWarningMin());
		params.put("leftOrRight", warning.getLeftOrRight());
		params.put("isDel", warning.getIsDel());
		int r = lineIntervalWarningDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 获取某线路区间左或右线的监测预警阀值设置信息列表
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	@Override
	public List<MetroLineIntervalWarning> findAllByIntervalIdAndLr(
			Long intervalId, String leftOrRight) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return lineIntervalWarningDao.findAllByIntervalIdAndLr(params);
	}

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param leftOrRight
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean importExcelData(String intervalId, String leftOrRight,
			MultipartFile file) throws Exception{
		//读取excel文件内容
		InputStream instream = file.getInputStream();
		Workbook readwb = Workbook.getWorkbook(instream);
		Sheet sheet = readwb.getSheet(0);
		List<String[]> sheetList = getSheetData(sheet);
		if(CommonUtils.isNotNull(sheetList)){
			for(String[] value : sheetList){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("leftOrRight", leftOrRight);
				params.put("param", value[0]);
				params.put("startRingNum", value[2]);
				params.put("endRingNum", value[3]);
				MetroLineIntervalWarning result = lineIntervalWarningDao.findUniqueData(params);
			    if(result == null){ //新增数据
			    	MetroLineIntervalWarning save = new MetroLineIntervalWarning();
			    	save.setIntervalId(Long.parseLong(intervalId));
			    	save.setLeftOrRight(leftOrRight);
			    	save.setParam(value[0]);
					int category = 0;
			    	if(Integer.parseInt(value[2]) == 1){
			    		category +=1;
					}
					if(Integer.parseInt(value[3]) == 1){
						category +=2;
					}
					if(Integer.parseInt(value[4]) == 1){
						category +=4;
					}
					save.setCategory(category);
			    	save.setStartRingNum(Integer.parseInt(value[5]));
			    	save.setEndRingNum(Integer.parseInt(value[6]));
			    	save.setRedWarningMax(Float.parseFloat(value[7]));
			    	save.setRedWarningMin(Float.parseFloat(value[8]));
			    	save.setOrangeWarningMax(Float.parseFloat(value[9]));
			    	save.setOrangeWarningMin(Float.parseFloat(value[10]));
			    	Long i = insertObj(save);
			    	if(i == null){ //新增失败
			    		logger.error("新增数据失败，区间："+intervalId+" 左右线："+leftOrRight+" 参数："+value[0]+" 开始环号："+value[2]+" 结束环号："+value[3]);
			    	    throw new ZookeTransactionException("新增数据失败");
			    	}
			    }else{ //更新数据
					int category = 0;
					if(Integer.parseInt(value[2]) == 1){
						category +=1;
					}
					if(Integer.parseInt(value[3]) == 1){
						category +=2;
					}
					if(Integer.parseInt(value[4]) == 1){
						category +=4;
					}
					result.setCategory(category);
			    	result.setRedWarningMax(Float.parseFloat(value[7]));
			    	result.setRedWarningMin(Float.parseFloat(value[8]));
			    	result.setOrangeWarningMax(Float.parseFloat(value[9]));
			    	result.setOrangeWarningMin(Float.parseFloat(value[10]));
			    	boolean j = updateObj(result);
			    	if(!j){ //更新失败
			    		logger.error("更新数据失败，id："+result.getId());
			    	    throw new ZookeTransactionException("更新数据失败");
			    	}
			    }
			}
		}else{
			logger.error("解析导入的文件数据为空");
		}
		return true;
	}
	
	/**
	 * 通过excel的sheet对象获取数据
	 * 
	 * @param s
	 * @return
	 */
	private List<String[]> getSheetData(Sheet s) {
		int column = s.getColumns();
		int row = s.getRows();
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 1; i < row; i++) {
			String[] value = new String[column];
			for (int j = 0; j < column; j++) {
				value[j] = s.getCell(j, i).getContents();
			}
			result.add(value);
		}
		if(CommonUtils.isNotNull(result)){
			return result;
		}
		return null;
	}
	/**
	 * 获取所有监测预警阀值设置信息的路线左右线列表，用于historian接口查询该路线对应左右线的环数
	 * @return
	 */
	@Override
	public List<IntervalWarningVO> findQueryVOListForAll() {
		return lineIntervalWarningDao.findQueryVOListForAll();
	}
	/**
	 * 获取某线路左右线的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param params
	 * @return
	 */
	@Override
	public List<IntervalWarningVO> findQueryVOListForParams(Long intervalId,
			String leftOrRight, Integer ringNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		params.put("ringNum", ringNum);
		return lineIntervalWarningDao.findQueryVOListForParams(params);
	}

	/**
	 * 获取某线路左右线的检测预警阈值设置信息列表，用于historian接口查询符合环数要求的数据
	 * @param params
	 * @return
	 */
	@Override
	public List<IntervalWarningVO> findQueryVOListForParams1(Long intervalId, String leftOrRight, Integer ringNum, String params) {
		Map<String, Object> paras = new HashMap<String, Object>();
		paras.put("intervalId", intervalId);
		paras.put("leftOrRight", leftOrRight);
		paras.put("ringNum", ringNum);
		paras.put("params", params);
		return lineIntervalWarningDao.findQueryVOListForParams1(paras);
	}


}
