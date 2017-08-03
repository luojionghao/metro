package cn.zdmake.metro.service.impl;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.base.utils.ZookeTransactionException;
import cn.zdmake.metro.dao.IMetroLineIntervalSaDao;
import cn.zdmake.metro.model.MetroLineIntervalSa;
import cn.zdmake.metro.service.IMetroLineIntervalSaService;
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
 * 地铁线路区间管片姿态业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalSaService")
public class MetroLineIntervalSaService extends BaseService<MetroLineIntervalSa> implements IMetroLineIntervalSaService {
	private static Logger logger = Logger.getLogger(MetroLineIntervalSaService.class);

	@Autowired
	IMetroLineIntervalSaDao lineIntervalSaDao;

	/**
	 * 分页查询
	 * 线路区间管片姿态信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalSa> findLineIntervalSaInfo(
			Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalSa> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalSaDao);
		return resultSet;
	}

	/**
	 * 保存区间管片姿态信息
	 * @param sa
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineIntervalSa sa) {
		int r = lineIntervalSaDao.insertObj(sa);
		if(r > 0){
			return sa.getId();
		}
		return null;
	}
	/**
	 * 通过id查询管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	@Override
	public MetroLineIntervalSa findObjById(Long intervalSaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", intervalSaId);
		return lineIntervalSaDao.findObjById(params);
	}
	/**
	 * 删除管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	@Override
	public boolean deleteObj(Long intervalSaId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", intervalSaId);
		int r = lineIntervalSaDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 更新管片姿态信息
	 * @param sa
	 * @return
	 */
	@Override
	public boolean updateObj(MetroLineIntervalSa sa) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalSaId", sa.getId());
		params.put("ringNum", sa.getRingNum());
		params.put("intervalId", sa.getIntervalId());
		params.put("leftOrRight", sa.getLeftOrRight());
		params.put("horizontalDev", sa.getHorizontalDev());
		params.put("verticalDev", sa.getVerticalDev());
		int r = lineIntervalSaDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		return lineIntervalSaDao.findLineIntervalSas(params);
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
				MetroLineIntervalSa result = lineIntervalSaDao.findUniqueData(params);
				if(result == null){ //新增数据
					MetroLineIntervalSa save = new MetroLineIntervalSa();
					save.setIntervalId(Long.parseLong(intervalId));
					save.setLeftOrRight(leftOrRight);
                    save.setHorizontalDev(Float.parseFloat(value[2]));
                    save.setVerticalDev(Float.parseFloat(value[3]));
                    save.setDateTime(StringUtil.stringToDate(value[4]));
					Long i = insertObj(save);
					if(i == null){ //新增失败
						logger.error("新增数据失败，区间："+intervalId+" 环号："+ value[0]);
						throw new ZookeTransactionException("新增数据失败");
					}
				}else{ //更新数据
					result.setHorizontalDev(Float.parseFloat(value[2]));
					result.setVerticalDev(Float.parseFloat(value[3]));
					result.setDateTime(StringUtil.stringToDate(value[4]));
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
