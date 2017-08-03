package cn.zdmake.metro.service.impl;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.base.utils.ZookeTransactionException;
import cn.zdmake.metro.dao.IMetroLineIntervalStDao;
import cn.zdmake.metro.model.MetroLineIntervalSt;
import cn.zdmake.metro.service.IMetroLineIntervalStService;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地铁线路区间盾尾间隙业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalStService")
public class MetroLineIntervalStService extends BaseService<MetroLineIntervalSt> implements IMetroLineIntervalStService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalStService.class);

	@Autowired
	IMetroLineIntervalStDao lineIntervalStDao;

	/**
	 * 分页查询
	 * 线路区间盾尾间隙信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalSt> findLineIntervalStInfo(
			Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalSt> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalStDao);
		return resultSet;
	}

	/**
	 * 保存区间盾尾间隙信息
	 * @param st
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalSt st) {
		int r = lineIntervalStDao.insertObj(st);
		if(r > 0){
			return st.getId();
		}
		return null;
	}
	/**
	 * 通过id查询盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	@Override
	public MetroLineIntervalSt findObjById(Long intervalStId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", intervalStId);
		return lineIntervalStDao.findObjById(params);
	}
	/**
	 * 删除盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalStId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", intervalStId);
		int r = lineIntervalStDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 更新盾尾间隙信息
	 * @param st
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalSt st) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalStId", st.getId());
		params.put("intervalId", st.getIntervalId());
		params.put("ringNum", st.getRingNum());
		params.put("leftOrRight", st.getLeftOrRight());
		params.put("stUp", st.getStUp());
		params.put("stDown", st.getStDown());
		params.put("stLeft", st.getStLeft());
		params.put("stRight", st.getStRight());
		int r = lineIntervalStDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<MetroLineIntervalSt> findLineIntervalSts(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		return lineIntervalStDao.findLineIntervalSts(params);
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
				String leftOrRight = "";
				if(value[1].equals("左线")){
					leftOrRight="l";
				}else if(value[1].equals("右线")){
					leftOrRight="r";
				}
				params.put("intervalId", Long.parseLong(intervalId));
				params.put("ringNum", value[0]);
				params.put("leftOrRight", leftOrRight);
				MetroLineIntervalSt result = lineIntervalStDao.findUniqueData(params);
				if(result == null){ //新增数据
					MetroLineIntervalSt save = new MetroLineIntervalSt();
					save.setIntervalId(Long.parseLong(intervalId));
					save.setLeftOrRight(leftOrRight);
					save.setStUp(Float.parseFloat(value[2]));
					save.setStDown(Float.parseFloat(value[3]));
					save.setStLeft(Float.parseFloat(value[4]));
					save.setStRight(Float.parseFloat(value[5]));
					save.setDateTime(StringUtil.stringToDate(value[6]));

					Long i = insertObj(save);
					if(i == null){ //新增失败
						logger.error("新增数据失败，区间："+intervalId+" 环号："+ value[0]);
						throw new ZookeTransactionException("新增数据失败");
					}
				}else{ //更新数据
					result.setStUp(Float.parseFloat(value[2]));
					result.setStDown(Float.parseFloat(value[3]));
					result.setStLeft(Float.parseFloat(value[4]));
					result.setStRight(Float.parseFloat(value[5]));
					result.setDateTime(StringUtil.stringToDate(value[6]));
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
