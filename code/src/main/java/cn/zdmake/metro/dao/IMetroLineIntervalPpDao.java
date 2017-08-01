package cn.zdmake.metro.dao;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalPp;

/**
 * 地铁线路区间工程进度dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalPpDao extends BaseDao<MetroLineIntervalPp> {

	/**
	 * 通过intervalId查询model
	 * @param intervalId
	 * @return
	 */
	MetroLineIntervalPp findByIntervalId(Long intervalId);
	

}
