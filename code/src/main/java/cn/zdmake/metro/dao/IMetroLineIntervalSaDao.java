package cn.zdmake.metro.dao;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalSa;

import java.util.List;
import java.util.Map;

/**
 * 管片姿态数据dao
 * @author wangcan
 *
 * 2017年3月3日
 */
public interface IMetroLineIntervalSaDao extends BaseDao<MetroLineIntervalSa> {

    /**
     * 查询指定区间管片姿态信息
     * @param params
     * @return
     */
    List<MetroLineIntervalSa> findLineIntervalSas(Map<String, Object> params);
    MetroLineIntervalSa findUniqueData(Map<String, Object> params);

}
