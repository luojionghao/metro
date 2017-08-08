package cn.zdmake.metro.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.ZookeTransactionException;
import cn.zdmake.metro.dao.IMetroLineIntervalSpDao;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.model.MetroLineIntervalSp;
import cn.zdmake.metro.service.IMetroLineIntervalSpService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 地铁线路区间沉降点业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalSpService")
public class MetroLineIntervalSpService extends BaseService<MetroLineIntervalSp> implements IMetroLineIntervalSpService{
	private static Logger logger = Logger.getLogger(MetroLineIntervalSpService.class);

	@Autowired
	IMetroLineIntervalSpDao lineIntervalSpDao;

	/**
	 * 分页查询
	 * 线路区间沉降点信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalSp> findLineIntervalSpInfo(
			Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalSp> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalSpDao);
		return resultSet;
	}

	/**
	 * 保存区间沉降点信息
	 * @param sp
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalSp sp) {
		int r = lineIntervalSpDao.insertObj(sp);
		if(r > 0){
			return sp.getId();
		}
		return null;
	}
	/**
	 * 通过id查询沉降点信息
	 * @param intervalSpId
	 * @return
	 */
	@Override
	public MetroLineIntervalSp findObjById(Long intervalSpId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSpId", intervalSpId);
		return lineIntervalSpDao.findObjById(params);
	}
	/**
	 * 删除沉降点信息
	 * @param intervalSpId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalSpId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSpId", intervalSpId);
		int r = lineIntervalSpDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 更新沉降点信息
	 * @param sp
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalSp sp) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSpId", sp.getId());
		params.put("intervalId", sp.getIntervalId());
		params.put("leftOrRight", sp.getLeftOrRight());
		params.put("spName", sp.getSpName());
		params.put("originMileage", sp.getOriginMileage());
		params.put("mapX", sp.getMapX());
		params.put("mapY", sp.getMapY());
		params.put("spSumAdd", sp.getSpSumAdd());
		params.put("spSumSub", sp.getSpSumSub());
		params.put("spSpeedWarningVal", sp.getSpSpeedWarningVal());
		int r = lineIntervalSpDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<MetroLineIntervalSp> findLineIntervalSps(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		return lineIntervalSpDao.findLineIntervalSps(params);
	}

	@Override
	public boolean importExcelData(String intervalId, MultipartFile file) throws Exception {
		//读取excel文件内容
		InputStream instream = file.getInputStream();
		Workbook readwb = Workbook.getWorkbook(instream);
		Sheet sheet = readwb.getSheet(0);
		List<String[]> sheetList = getSheetData(sheet);
		if(CommonUtils.isNotNull(sheetList)){
			for(String[] value : sheetList){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("spName", value[0]);
				MetroLineIntervalSp result = lineIntervalSpDao.findUniqueData(params);
				if(result == null){ //新增数据
					MetroLineIntervalSp save = new MetroLineIntervalSp();
					save.setIntervalId(Long.parseLong(intervalId));

					save.setSpName(value[0]);
					if(value[1].equals("左线")){
						save.setLeftOrRight("l");
					}else if(value[1].equals("右线")){
						save.setLeftOrRight("r");
					}else {
						save.setLeftOrRight("");
					}
					save.setOriginMileage(Float.valueOf(value[2]));
					save.setMapX(new BigDecimal(value[3]));
					save.setMapY(new BigDecimal(value[4]));
					if(value[5]!=null && !value[5].equals("")) {
						save.setSpSumAdd(Float.valueOf(value[5]));
					}
					if(value[6] != null && !value[6].equals("")) {
						save.setSpSumSub(Float.valueOf(value[6]));
					}
					if(value[7] != null && !value[7].equals("")) {
						save.setSpSpeedWarningVal(Float.valueOf(value[7]));
					}
					Long i = insertObj(save);
					if(i == null){ //新增失败
						logger.error("新增数据失败，区间："+intervalId+" 沉降点名称："+ value[0]);
						throw new ZookeTransactionException("新增数据失败");
					}
				}else{ //更新数据
					if(value[1].equals("左线")){
						result.setLeftOrRight("l");
					}else if(value[1].equals("右线")){
						result.setLeftOrRight("r");
					}else {
						result.setLeftOrRight("");
					}
					result.setOriginMileage(Float.valueOf(value[2]));
					result.setMapX(new BigDecimal(value[3]));
					result.setMapY(new BigDecimal(value[4]));
					if(value[5] != null && !value[5].equals("")) {
						result.setSpSumAdd(Float.valueOf(value[5]));
					}
					if(value[6] != null && !value[6].equals("")) {
						result.setSpSumSub(Float.valueOf(value[6]));
					}
					if(value[7] != null && !value[7].equals("")) {
						result.setSpSpeedWarningVal(Float.valueOf(value[7]));
					}
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
}
