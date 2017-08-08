package cn.zdmake.metro.service;

import java.util.List;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroPhoto;

/**
 * 图片管理业务接口
 * @author MAJL
 *
 */
public interface IMetroPhotoService {
	
	/**
	 * 分页查询
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageResultSet<MetroPhoto> findMetroPhotos(int pageNum, int pageSize);
	
	/**
	 * 保存图片编辑信息
	 * @param photoId 图片id
	 * @param photoName 图片名称
	 * @param photoUrl 图片访问url
	 * @param photoType 图片类型
	 * @return
	 */
	boolean saveMetroPhoto(Long photoId, String photoName, String photoUrl, int photoType);
	
	/**
	 * 删除图片信息
	 * @param photoId 图片Id
	 * @return
	 */
	boolean delMetroPhoto(Long photoId);
	
	/**
	 * 精确查找图片信息
	 * @param photoId 图片Id
	 * @return
	 */
	List<MetroPhoto> findMetroPhotosByParams(Long photoId);
	
	/**
	 * 查找某种类型的的所有图片
	 * @param photoType
	 * @return
	 */
	List<MetroPhoto> findByType(int photoType);
	
	/**
	 * 精确查找图片信息
	 * @param 
	 * @return
	 */
	List<MetroPhoto> findMetroPhotosByParams(Long intervalId, String leftOrRight);

}
