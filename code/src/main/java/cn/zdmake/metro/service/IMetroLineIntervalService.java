package cn.zdmake.metro.service;

import cn.zdmake.metro.model.MetroLineInterval;

import java.util.Map;

/**
 * 地铁城市线路区间业务接口
 * @author hank
 *
 * 2016年8月25日
 */
public interface IMetroLineIntervalService {
	
	/**
	 * 保存线路区间信息
	 * @param interval
	 * @param userId
	 * @return
	 */
	Long insertObj(MetroLineInterval interval,Long userId);
	/**
	 * 删除线路区间信息
	 * @param intervalId
	 * @return
	 */
	boolean deleteObj(Long intervalId);
	/**
	 * 更新线路区间信息
	 * @param interval
	 * @return
	 */
	boolean updateObj(MetroLineInterval interval);
	/**
	 * 通过id查询地铁城市线路区间信息
	 * @param intervalId
	 * @return
	 */
	MetroLineInterval findObjById(Long intervalId);

	/**
	 * 修改风险组段划分文档url
	 * @param intervalId 区间id用于地图
	 * @param pdfUrl 风险组段划分文档url
	 * @return
	 */
	boolean editRiskPdfUrl(Long intervalId,String pdfUrl);

    Map<String,Object> getShieldData(Long intervalId,String leftOrRight,String date,String ring,String key,String type);


}
