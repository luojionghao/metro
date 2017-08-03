package cn.zdmake.metro.service;

import javax.servlet.http.HttpServletRequest;

import cn.zdmake.metro.base.model.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * 公用操作业务接口
 * @author luowq
 *
 */
public interface ICommonService {
	/**
	 * 上传文件
	 * @param request
	 * @return
	 */
	CommonResponse fileUpload(HttpServletRequest request);
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	CommonResponse fileUpload(MultipartFile file);
}
