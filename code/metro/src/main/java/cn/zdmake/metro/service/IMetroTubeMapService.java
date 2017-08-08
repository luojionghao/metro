package cn.zdmake.metro.service;

import java.util.Date;

import cn.zdmake.metro.vo.TubeMapIntervalLr;
/**
 * 全网图业务处理接口
 * @author MAJL
 *
 */
public interface IMetroTubeMapService {
	/**
	 * 查找区间左右线数据
	 * @param userId 用户Id
	 * @param intervalId 区间Id
	 * @return
	 */
	TubeMapIntervalLr findLrInfo(Long userId,Long intervalId,Date loginTime);
}
