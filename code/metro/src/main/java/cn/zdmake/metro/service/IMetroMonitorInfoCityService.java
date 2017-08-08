package cn.zdmake.metro.service;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticView;
import cn.zdmake.metro.vo.MonitorIntervalLrStaticsView;
import cn.zdmake.metro.vo.MonitorIntervalView;
import cn.zdmake.metro.vo.MonitorLrAlldicView;
import cn.zdmake.metro.vo.MonitorViewData;

/**
 * 盾构信息监控城市业务接口
 * @author MAJL
 *
 */
public interface IMetroMonitorInfoCityService {
	/**
	 * 加条件分页查询
	 * @param cityId 城市ID
	 * @param buildStatus 工程状态
	 * @param pageNum 页码
	 * @param lineId 线路Id
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MonitorViewData> findMonitorInfoCityData(Long cityId, Long lineId, int buildStatus, int pageNum, int pageSize) throws IOException;
	
	/**
	 * 城市盾构机工作状态统计
	 * @param userId 用户Id
	 * @param lineId 线路Id
	 * @return
	 */
	Map<String, String> findCountMechineDatas(Long userId, Long lineId);
	
	/**
	 * 区间左右线数据
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	Map<String, Object> findIntervalLrDaoPanDatas(Long intervalId, String leftOrRight);
	
	/**
	 * 查询所有参数数据
	 * @param parseLong
	 * @param leftOrRight
	 * @return
	 */
	PageResultSet<MonitorLrAlldicView> findMonitorIntervalLrDics(
			long parseLong, String leftOrRight);
	
	/**
	 * 查询检测数据
	 * @param intervalId
	 * @param intervalSpId
	 * @return
	 */
	MonitorIntervalView findMonitorIntervalDatas(Long intervalId, Long intervalSpId);
	
	/**
	 * 获取区间左右线导向数据
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	Map<String, Object> findIntervalLrDaoxDatas(Long intervalId, String leftOrRight);
	
	/**
	 * 材料消耗统计
	 * @param intervalId 区间ID
	 * @param leftOrRight 左右线标记
	 * @param beginRing 开始环号
	 * @param endRing 结束环号
	 * @param paramName 参数名
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab1(String intervalId, String leftOrRight,
			String beginRing, String endRing, String paramName);
	
	/**
	 * 时间统计
	 * @param intervalId 区间ID
	 * @param leftOrRight 左右线标记
	 * @param beginRing 开始环号
	 * @param endRing 结束环号
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab2(String intervalId,
			String leftOrRight, String beginRing, String endRing);
	
	/**
	 * 进度统计
	 * @param intervalId 区间ID
	 * @param leftOrRight 左右线标记
	 * @param beginDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 */
	List<List<Object>> findMonitorStaticTab3(String intervalId,
			String leftOrRight, String beginDate, String endDate);
	
	/**
	 * 汇总统计
	 * @param intervalId 区间Id
	 * @param leftOrRight 左右线标记
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @param excelType 报表类型1日,2周,3月
	 * @return
	 */
	PageResultSet<MonitorIntervalLrStaticView> findMonitorStaticTab4(
			String intervalId, String leftOrRight, String pageNum,
			String pageSize, String beginTime, String endTime, String excelType);
	
	/**
	 * 综合分析
	 * @param model 模式 1 模式 2 .. 模式7  或0不以模式查询此时ks无用
	 * @param type 指定以查询条件（t日期或r环号）
	 * @param beginTime 开始日期
	 * @param endTime 结束日期
	 * @param beginRing 开始环号
	 * @param endRing 结束环号
	 * @param ks 参数名称集合
	 * @param kns 参数中文名
	 * @return
	 */
	MonitorIntervalLrStaticsView findMonitorStaticTab5(String intervalId, String leftOrRight, int model, String type,
													   String beginTime, String endTime, int beginRing, int endRing, String[] ks, String[] kns, String[] indxs);
	
	/**
	 * 获取当前环号
	 * @param intervalId
	 * @param leftOrRight
	 * @return
	 */
	int findCurrRingNum(String intervalId, String leftOrRight);
}
