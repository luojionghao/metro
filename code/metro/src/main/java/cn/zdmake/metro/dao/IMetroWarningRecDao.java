package cn.zdmake.metro.dao;

import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalWarningRec;
/**
 * 监测预警记录数据操作接口
 * @author MAJL
 *
 */
public interface IMetroWarningRecDao extends BaseDao<MetroLineIntervalWarningRec> {
	
	/**
	 * 监测预警记录查询 每次查询大于一分钟前新增的预警记录
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecs(
			Map<String, Object> params);

	/**
	 * 监测预警记录查询 每次查询大于一分钟前新增的预警记录
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecsByIntervalId(
			Map<String, Object> params);

	/**
	 * 批量插入监测预警数据记录
	 * @param list
	 * @return
	 */
	int insertObjs(List<MetroLineIntervalWarningRec> list);
}
