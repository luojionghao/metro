package cn.zdmake.metro.dao;

import cn.zdmake.metro.base.dao.BaseDao;
import cn.zdmake.metro.model.MetroLineIntervalRp;

import java.util.Map;

/**
 * 地铁线路区间风险位置dao
 * @author hank
 *
 * 2016年8月16日
 */
public interface IMetroLineIntervalRpDao extends BaseDao<MetroLineIntervalRp> {

    /**
     * 修改
     * @param params
     * @return
     */
    int updatePdf(Map<String, Object> params);

}
