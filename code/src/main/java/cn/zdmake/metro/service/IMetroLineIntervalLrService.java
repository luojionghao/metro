package cn.zdmake.metro.service;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroLineIntervalLr;

/**
 * 地铁线路区间左右线信息数据业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalLrService {
	/**
	 * 分页查询
	 * 左右线信息数据
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalLr> findLineIntervalLrInfo(Long intervalId, String leftOrRight, int pageNum, int pageSize);
	/**
	 * 更新线路区间左右线信息
	 * @param interval
	 * @return
	 */
	boolean updateObj(MetroLineIntervalLr intervalLr);
	/**
	 * 保存线路区间左右线信息
	 * @param intervalLr
	 * @return
	 */
	Long insertObj(MetroLineIntervalLr intervalLr);
}
