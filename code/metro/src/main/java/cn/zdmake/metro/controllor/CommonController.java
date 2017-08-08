package cn.zdmake.metro.controllor;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import jxl.common.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.service.ICommonService;

/**
 * 公用操作控制器
 * 
 * @author hank
 *
 *         2016年8月18日
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	private static Logger logger = Logger.getLogger(CommonController.class);

	@Resource
	ICommonService commonService;

	/**
	 * 文件上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "/file-upload", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse fileUpload() {
		CommonResponse commonResponse = commonService.fileUpload(request);
		return commonResponse;
	}

	/**
	 * 文件下载
	 * 
	 * @param filename
	 *            下载文件名
	 * @return
	 */
	@RequestMapping("/file-download")
	public String fileDownload(@RequestParam("filename") String filename) {
		// CommonResponse r = new CommonResponse();
		try {
			String filepath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH") + "/" + filename;
			File file = new File(new String(filepath.getBytes("utf-8"), "utf-8"));
			if (!file.exists()) {
				logger.error("下载的资源不存在");
				return "/common/download_error";
				// r.setCode(0);
				// r.setResult("下载的资源不存在");
			}
			response.setContentType("application/x-download");
			// 设置响应头，控制浏览器下载该文件
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMdd");
			String generationStr = simpleFormat.format(date);
			String downloadName = generationStr + "_" + filename.substring(filename.indexOf("_") + 1);
			// response.setHeader("content-disposition",
			// "attachment;filename="+URLEncoder.encode(downloadName,"utf-8").replace("+","%20"));
			response.setHeader("content-disposition", String.format("attachment;filename*=utf-8'zh_cn'%s",
					URLEncoder.encode(downloadName, "utf-8").replace("+", "%20")));
			// 读取文件，保存到文件输入流
			FileInputStream in = new FileInputStream(file);
			// FileInputStream in = new FileInputStream(filepath);
			// 创建输出流
			OutputStream out = response.getOutputStream();
			// 创建缓冲区
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len); // 输出缓冲区的内容到浏览器实现下载
			}
			in.close(); // 关闭输入流
			out.close(); // 关闭输出流
			// r.setCode(Constants.CODE_SUCCESS);
			// r.setResult("下载成功");
		} catch (Exception e) {
			logger.error("文件下载异常", e);
			return "/common/download_error";
			// r.setCode(0);
			// r.setResult("文件下载异常");
		}
		// return GsonUtils.toJson(r);
		return null;
	}
}
