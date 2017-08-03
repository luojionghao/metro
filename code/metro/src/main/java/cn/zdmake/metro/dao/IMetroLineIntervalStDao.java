package cn.zdmake.metro.dao;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalSt;

import java.util.List;
import java.util.Map;

/**
 * 盾尾间隙数据dao
 * @author wangcan
 *
 * 2017年3月3日
 */
public interface IMetroLineIntervalStDao extends BaseDao<MetroLineIntervalSt> {

    /**
     * 查询指定区间盾尾间隙信息
     * @param params
     * @return
     */
    List<MetroLineIntervalSt> findLineIntervalSts(Map<String, Object> params);
    MetroLineIntervalSt findUniqueData(Map<String, Object> params);
}
