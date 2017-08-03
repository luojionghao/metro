package cn.zdmake.metro.service;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroLineIntervalSa;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 地铁线路区间管片姿态业务接口
 * @author luowq
 *
 */
public interface IMetroLineIntervalSaService {
	/**
	 * 保存区间管片姿态信息
	 * @param sa
	 * @return
	 */
	Long insertObj(MetroLineIntervalSa sa);
	/**
	 * 通过id查询管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	MetroLineIntervalSa findObjById(Long intervalSaId);
	/**
	 * 删除管片姿态信息
	 * @param intervalSaId
	 * @return
	 */
	boolean deleteObj(Long intervalSaId);
	/**
	 * 更新管片姿态信息
	 * @param sa
	 * @return
	 */
	boolean updateObj(MetroLineIntervalSa sa);
	/**
	 * 分页查询
	 * 线路区间管片姿态信息
	 * @param intervalId 线路区间id
	 * @param pageNum 页码
	 * @param pageSize 单页记录数
	 * @return
	 */
	PageResultSet<MetroLineIntervalSa> findLineIntervalSaInfo(Long intervalId, int pageNum, int pageSize);
	/**
	 * 查询某个区间的管片姿态信息
	 * @param intervalId
	 * @return
	 */
	List<MetroLineIntervalSa> findLineIntervalSas(Long intervalId);

	/**
	 * 导入excel数据
	 * @param intervalId
	 * @param file
	 * @return
	 * @throws Exception
	 */
	boolean importExcelData(String intervalId, MultipartFile file) throws Exception;
}
