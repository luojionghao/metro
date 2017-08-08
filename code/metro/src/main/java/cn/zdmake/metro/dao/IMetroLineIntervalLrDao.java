package cn.zdmake.metro.dao;

import java.util.List;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalLr;

/**
 * 地铁线路区间左右线信息数据dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalLrDao extends BaseDao<MetroLineIntervalLr> {
	
	/**
	 * 查找线路区间的左右线信息数据列表
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalLr> findObjsForLineInterval(Long intervalId);
}
