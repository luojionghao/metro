package cn.zdmake.metro.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.base.utils.CommonUtils;
import cn.zdmake.metro.dao.IMetroLineIntervalMdDao;
import cn.zdmake.metro.model.MetroLineIntervalMdRe;
import cn.zdmake.metro.service.IMetroLineIntervalMdReService;
import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.base.utils.ZookeTransactionException;
import cn.zdmake.metro.dao.IMetroLineIntervalMdReDao;
import cn.zdmake.metro.model.MetroLineIntervalMd;

/**
 * 地铁线路区间上传监测数据记录业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalMdReService")
public class MetroLineIntervalMdReService extends BaseService<MetroLineIntervalMdRe> implements IMetroLineIntervalMdReService {
	
	private static Logger logger = Logger.getLogger(MetroLineIntervalMdReService.class);

	@Autowired
	IMetroLineIntervalMdReDao lineIntervalMdReDao;
	@Autowired
	IMetroLineIntervalMdDao lineIntervalMdDao;

	/**
	 * 分页查询
	 * 上传监测数据记录信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	@Override
	public PageResultSet<MetroLineIntervalMdRe> findLineIntervalMdReInfo(
			Long intervalId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		PageResultSet<MetroLineIntervalMdRe> resultSet = getPageResultSet(params, pageNum, pageSize, lineIntervalMdReDao);
		return resultSet;
	}

	/**
	 * 沉降点监测数据上传
	 * @return
	 */
	@Override
	public boolean uploadLineIntervalMdReData(Long intervalId,String uploadFileUrl){
		String msg = null;
		//上传文件，入库上传记录
		MetroLineIntervalMdRe mdRe = new MetroLineIntervalMdRe();
		String fileName = uploadFileUrl.substring(uploadFileUrl.lastIndexOf("/")+1);
		String originFileName = fileName.substring(fileName.indexOf("_")+1);
		mdRe.setFileName(fileName);
		mdRe.setOriginFileName(originFileName);
		mdRe.setFileUrl(uploadFileUrl);
		mdRe.setIntervalId(intervalId);
		mdRe.setIsDel(0);
		//获取该文件服务器地址
		String filepath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH")+"/"+fileName;
		//读取excel文件内容到map
		InputStream instream = null;
		Workbook readwb = null;
		try {
			instream = new FileInputStream(filepath);
			readwb = Workbook.getWorkbook(instream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = readwb.getSheet(0);
		Map<String, String> map = getSheetMap(sheet);
		//获取检测日期
		String dateKey = "监测时间";
		if(map.keySet().contains(dateKey)){
			String dataValue = map.get(dateKey);
			String[] dateSplit = dataValue.split("#");
			String timeStr = dateSplit[0].trim();
			String dateStr = dateSplit[3].trim();
			SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd HH:mm");
			Date monitorDate = null;
			try {
				monitorDate = f.parse(dateStr+" "+timeStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mdRe.setMonitorDate(monitorDate);
		}
		int result1 = lineIntervalMdReDao.insertObj(mdRe);
		if(result1 > 0){//插入上传记录成功
			//读取文件，匹配数据入库
			List<MetroLineIntervalMd> mdList = getMdReInfoFromSheetMap(map, mdRe.getId());
			if(CommonUtils.isNotNull(mdList)){
				int result2 = lineIntervalMdDao.insertObjs(mdList); //需要查重么
				if(result2 > 0){ //批量入库记录数据成功
					return true;
				}else{
					msg = "批量入库记录数据出错";
					logger.error(msg);
					throw new ZookeTransactionException(msg);
				}
			}else{
				msg = "文件内记录为空";
				logger.error(msg);
				throw new ZookeTransactionException(msg);
			}
		}else{
			msg = "插入上传记录出错";
			logger.error(msg);
			throw new ZookeTransactionException(msg);
		}
	}
	
	/**
	 * 从文件中读取上传的沉降点监测数据列表
	 */
	private List<MetroLineIntervalMd> getMdReInfoFromSheetMap(Map<String, String> map,Long mdReId){
		List<MetroLineIntervalMd> mdList = new ArrayList<MetroLineIntervalMd>();
		//根据文件来查重还是根据沉降点来查重
		for(String key : map.keySet()){
			if(!key.trim().equals("监测时间")){ //为沉降点详细信息
				String value = map.get(key);
				String[] split = value.split("#");
				String startElevation = split[0];
				String upnoceElevation = split[1];
				String thisElevation = split[2];
				String thisVar = split[3];
				String sumVar = split[4];
				String mdNo = key.trim();
				MetroLineIntervalMd md = new MetroLineIntervalMd();
				md.setMdReId(mdReId);
				md.setStartElevation(Float.valueOf(startElevation.trim()));
				md.setUpnoceElevation(Float.valueOf(upnoceElevation.trim()));
				md.setThisElevation(Float.valueOf(thisElevation.trim()));
				md.setThisVar(Float.valueOf(thisVar.trim()));
				md.setSumVar(Float.valueOf(sumVar.trim()));
				md.setMdNo(mdNo);
				mdList.add(md);
			}
		}
		
		if(CommonUtils.isNotNull(mdList)){
			return mdList;
		}else{
			return null;
		}
	}
	
	/**
	 * 通过excel的sheet对象获取数据，key为第一列，value为后面所有列（用#隔开）
	 * 直接读取有数据的
	 * 
	 * @param s
	 * @return
	 */
	private Map<String, String> getSheetMap(Sheet s) {
		int column = s.getColumns();
		int row = s.getRows();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < row; i++) {
			String key = s.getCell(0, i).getContents();
			if(i < 9 && !key.contains("监测时间")){
				continue;
			}
			if(key.startsWith("说明")){
				 break;
			}
			String value = "";
			for (int j = 1; j < column; j++) {
				if(key.contains("监测时间") && j==1){ //处理excel的24小时时间格式
					Cell cell = s.getCell(j, i);
					value += "#" + formateExcelTime(cell);
				}else{
					Cell cell = s.getCell(j, i);
					value += "#" + cell.getContents();
				}
				// System.out.println(cell.getContents());
			}
			map.put(key, value.substring(1));
		}
		map.remove("");
		return map;
	}
	
	 /**
     * <概要描述> excel文件中时间类型数据格式转换 默认读取出来回变成12小时制，这里转换成24小时制
     * @param excel文件中时间类型数据
     * @return String
     * @throws 无
     */
    public static String formateExcelTime(Cell formatecell) {
        try {
            java.util.Date mydate = null;
            DateCell datecll = (DateCell) formatecell;
            mydate = datecll.getDate();
            long time = (mydate.getTime() / 1000) - 60 * 60 * 8;//格林乔治 东八区
            mydate.setTime(time * 1000);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            return formatter.format(mydate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	

	/**
	 * 沉降点监测数据记录删除
	 * @param intervalMdReId
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean deleteLineIntervalMdReInfo(Long intervalMdReId)
			throws Exception {
		String msg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalMdReId", intervalMdReId);
		int r = lineIntervalMdReDao.deleteObj(params);
		if(r > 0){
			int r2 = lineIntervalMdDao.deleteByIntervalMdReId(intervalMdReId);
			if(r2 > 0){
				return true;
			}else{
				msg = "删除记录成功，删除记录数据为0";
				logger.error(msg);
				return true;
				//throw new ZookeTransactionException(msg);
			}
		}else{
			msg = "删除记录出错";
			logger.error(msg);
			throw new ZookeTransactionException(msg);
		}
	}
}
