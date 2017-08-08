package cn.zdmake.metro.service;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroDictionary;
/**
 * 字典表业务处理接口
 * @author MAJL
 *
 */
public interface IMetroDictionaryService {
	/**
	 * 分页查询
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroDictionary> findMetroDictionaryInfo(int pageNum, int pageSize);
	
	/**
	 * 检查图片名是否存在
	 * @param pname
	 * @return
	 */
	boolean checkPhotoName(String pname);
}
