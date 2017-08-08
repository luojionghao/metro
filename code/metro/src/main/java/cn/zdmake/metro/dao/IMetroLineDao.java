package cn.zdmake.metro.dao;

import java.util.List;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLine;

/**
 * 地铁线路dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineDao extends BaseDao<MetroLine> {
	
	/**
	 * 查找城市的线路列表
	 * @param cityId
	 * @return
	 */
	List<MetroLine> findObjsForCity(Long cityId);

}
