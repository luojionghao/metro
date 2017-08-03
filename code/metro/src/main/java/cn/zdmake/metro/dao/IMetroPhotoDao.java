package cn.zdmake.metro.dao;

import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroPhoto;

public interface IMetroPhotoDao extends BaseDao<MetroPhoto>{

	/**
	 * 查找某种类型的的所有图片
	 * @param photoType
	 * @return
	 */
	List<MetroPhoto> findByType(int photoType);

	List<MetroPhoto> findObjsListByIntervalId(Map<String, Object> params);

}
