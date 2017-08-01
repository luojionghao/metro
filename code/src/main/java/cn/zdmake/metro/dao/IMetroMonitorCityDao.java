package cn.zdmake.metro.dao;

import java.util.List;
import java.util.Map;

import cn.zdmake.metro.model.MonitorInfoCity;
import cn.zdmake.metro.model.KeyValue;
import cn.zdmake.metro.model.MetroLineIntervalMd;

/**
 * 盾构信息监控数据查询接口
 * @author MAJL
 *
 */
public interface IMetroMonitorCityDao {
	
	/**
	 * 统计记录数
	 * @param params
	 * @return
	 */
	int countMonitorInfoCityDatas(Map<String, Object> params);
	
	/**
	 * city分页查询
	 * @param params
	 * @return
	 */
	List<MonitorInfoCity> findMonitorInfoCityDatas(Map<String, Object> params);

	/**
	 * city查询
	 * @param params
	 * @return
	 */
	List<MonitorInfoCity> findAllMonitorInfoCityDatas(Map<String, Object> params);
	
	/**
	 * 城市盾构机运行状态统计
	 * @param params
	 * @return
	 */
	List<KeyValue> findCountMechineDatas(Map<String, Object> params);
	
	/**
	 * 区间左右线信息
	 * @param params
	 * @return
	 */
	MonitorInfoCity findMonitorInfoCity(Map<String, Object> params);
	
	/**
	 * 查询沉降点检测数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalMd> findMonitorInfoIntervalData(
			Map<String, Object> params);

	/**
	 * 通过mdNo查询沉降点检测数据
	 * @param params
	 * @return
	 */
	List<MetroLineIntervalMd> findMonitorInfoIntervalSettle(
			Map<String, Object> params);
}
