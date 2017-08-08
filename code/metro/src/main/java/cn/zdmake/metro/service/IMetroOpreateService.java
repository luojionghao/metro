package cn.zdmake.metro.service;

import java.util.List;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroUserOpreateRec;

/**
 * 用户操作记录业务处理接口
 * @author MAJL
 *
 */
public interface IMetroOpreateService {
	/**
	 * 分页查询
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroUserOpreateRec> findMetroOpreateInfo(int pageNum, int pageSize);
	
	/**
	 * 批量入库操作日志
	 * @param logs
	 * @return
	 */
	boolean addLogs(List<MetroUserOpreateRec> logs);
}
