package cn.zdmake.metro.dao;

import java.util.Map;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroDictionary;

/**
 * 字典表数据处理接口
 * @author MAJL
 *
 */
public interface IMetroDictionaryDao extends BaseDao<MetroDictionary> {
	/**
	 * 通过名称代号查询字典对象
	 * @return
	 */
	MetroDictionary selectByName(String dicName);

	Integer checkPhotoName(Map<String, Object> params);

}
