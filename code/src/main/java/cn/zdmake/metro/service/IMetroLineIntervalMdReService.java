package cn.zdmake.metro.service;

import cn.zdmake.metro.model.MetroLineIntervalMdRe;
import cn.zdmake.metro.base.page.PageResultSet;

/**
 * 地铁线路区间上传监测数据记录业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalMdReService {
	/**
	 * 分页查询
	 * 上传监测数据记录信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalMdRe> findLineIntervalMdReInfo(Long intervalId, int pageNum, int pageSize);
	
	/**
	 * 沉降点监测数据上传
	 * @return
	 * @throws Exception 
	 */
	boolean uploadLineIntervalMdReData(Long intervalId,String uploadFileUrl);
	
	/**
	 * 沉降点监测数据记录删除
	 * @param intervalMdReId
	 * @return
	 * @throws Exception
	 */
	boolean deleteLineIntervalMdReInfo(Long intervalMdReId) throws Exception;
}
