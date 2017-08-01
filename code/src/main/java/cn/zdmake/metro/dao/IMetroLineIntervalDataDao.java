package cn.zdmake.metro.dao;

import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalData;

/**
 * 地铁线路区间导向数据dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalDataDao extends BaseDao<MetroLineIntervalData> {
	
	/**
	 * 查找线路区间的左右线导向数据列表
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalData> findObjsForLineInterval(Long intervalId);
	/**
	 * 获取某线路区间左或右线的导向数据信息列表
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	List<MetroLineIntervalData> findAllByIntervalIdAndLr(Map<String, Object> params);
	/**
	 * 通过唯一性条件查询唯一数据
	 * @param params
	 * @return
	 */
	MetroLineIntervalData findUniqueData(Map<String, Object> params);
}
