package cn.zdmake.metro.service;

import java.util.Date;
import java.util.List;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroLineIntervalWarningRec;

/**
 * 监测预警记录业务接口
 * @author MAJL
 *
 */
public interface IMetroWarningRecService {
	
	/**
	 * 主页铃铛查询
	 * @param userId
	 * @param p_date
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecs(Long userId, Date p_date);

	/**
	 * 主页铃铛查询
	 * @param intervalId
	 * @param leftOrRight
	 * @param p_date
	 * @return
	 */
	List<MetroLineIntervalWarningRec> findLastWarningRecsIntervalId(Long intervalId,String leftOrRight, Date p_date);
	
	/**
	 * 监测预警分页查询
	 * @param userId 用户Id
	 * @param intervalId 区间Id
	 * @param leftOrRight 左右线标记
	 * @param pageNum 当前页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalWarningRec> findWarningRecs(Long userId, String intervalId, String leftOrRight,
															   int pageNum, int pageSize, String beginTime, String endTime, String warnParam);
	
	/**
	 * 批量插入监测预警数据记录
	 * @param list
	 * @return
	 */
	boolean insertObjs(List<MetroLineIntervalWarningRec> list) throws Exception;
}
