package cn.zdmake.metro.service;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroLineIntervalSt;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 地铁线路区间盾尾间隙业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalStService {
	/**
	 * 保存区间盾尾间隙信息
	 * @param st
	 * @return
	 */
	Long insertObj(MetroLineIntervalSt st);
	/**
	 * 通过id查询盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	MetroLineIntervalSt findObjById(Long intervalStId);
	/**
	 * 删除盾尾间隙信息
	 * @param intervalStId
	 * @return
	 */
	boolean deleteObj(Long intervalStId);
	/**
	 * 更新盾尾间隙信息
	 * @param st
	 * @return
	 */
	boolean updateObj(MetroLineIntervalSt st);
	/**
	 * 分页查询
	 * 线路区间盾尾间隙信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSt> findLineIntervalStInfo(Long intervalId, int pageNum, int pageSize);
	/**
	 * 查询某个区间的盾尾间隙信息
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalSt> findLineIntervalSts(Long intervalId);

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, MultipartFile file) throws Exception;
}
