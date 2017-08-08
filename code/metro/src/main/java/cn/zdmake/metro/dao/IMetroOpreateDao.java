package cn.zdmake.metro.dao;

import java.util.Map;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroUserOpreateRec;

/**
 * 用户操作记录管理接口
 * @author MAJL
 *
 */
public interface IMetroOpreateDao extends BaseDao<MetroUserOpreateRec> {

	int insertObjs(Map<String, Object> params);
	
}
