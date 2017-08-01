package cn.zdmake.metro.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.utils.ConfigProperties;
import cn.zdmake.metro.base.utils.FileUtil;
import cn.zdmake.metro.service.ICommonService;
import jxl.common.Logger;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.zdmake.metro.base.utils.Constants;

/**
 * 公用操作接口业务实现
 * @author hank
 *
 * 2016年8月19日
 */
@Service("commonService")
public class CommonService implements ICommonService {
	private static Logger logger = Logger.getLogger(CommonService.class);


	@Override
	public CommonResponse fileUpload(HttpServletRequest request) {
		CommonResponse r = new CommonResponse();
		try{
			DiskFileItemFactory factory = new DiskFileItemFactory();
	        factory.setSizeThreshold(1*1024*1024); //小于1M直接存内存，不需要临时文件
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setSizeMax(10*1024*1024); //最大上传文件大小为10M
	        List<FileItem> list = upload.parseRequest(request);
	        //logger.info("list:"+GsonUtils.toJson(list));
	        Iterator<FileItem> it = list.iterator();
	        while(it.hasNext()){
	        	FileItem fileItem = (FileItem) it.next();
	        	if(!fileItem.isFormField()){ //文件表单项
	        		if(fileItem.getSize() > 20*1024*1024){
	        			r.setCode(Constants.CODE_FAIL);
	        			r.setResult("上传失败，文件要小于20M");
	        			return r;
	        		}
	        		//生成18位文件名前缀，文件名格式为："18位数字"+"_"+"真实上传文件名"
	        		String fileName = fileItem.getName();
	    			//String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
	        		Date date = new Date();
	        		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
	        		String generationStr = simpleFormat.format(date)+ (new Random().nextInt(900)+100);
	    			String filename = generationStr+ "_" + fileName.substring(fileName.lastIndexOf("\\")+1);//generationStr+"."+fileNameSuffix;
	        		//上传路径
	    			String savePath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
	    			//开始上传
	        		boolean result = FileUtil.uploadFile(fileItem.getInputStream(), savePath, filename);
	        		if(result){ //上传成功
	        			String url = ConfigProperties.getValueByKey("FILE_VISIT_PATH")+"/"+filename;
	        			r.setCode(Constants.CODE_SUCCESS);
	        			r.setResult(url);
	        		}else{ //上传失败
	        			logger.error("文件上传失败");
	        			r.setCode(Constants.CODE_FAIL);
	        			r.setResult("文件上传失败");
	        		}
	        	}
	        }
		}catch(Exception e){
			logger.error("文件上传异常", e);
			r.setCode(0);
			r.setResult("文件上传异常");
		}
	    return r;
	}


	@Override
	public CommonResponse fileUpload(MultipartFile file) {
		CommonResponse r = new CommonResponse();
		try{
			if(file.getSize() > 20*1024*1024){
    			r.setCode(Constants.CODE_FAIL);
    			r.setResult("上传失败，文件要小于10M");
    			return r;
    		}
			//生成18位文件名前缀，文件名格式为："18位数字"+"_"+"真实上传文件名"
			String fileName = file.getOriginalFilename();
			Date date = new Date();
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
			String generationStr = simpleFormat.format(date)+ (new Random().nextInt(900)+100);
			String filename = generationStr+ "_" + fileName;//;
			//上传路径
			String savePath = ConfigProperties.getValueByKey("FILE_UPLOAD_PATH");
			//开始上传
			boolean result = FileUtil.uploadFile(file.getInputStream(), savePath, filename);
			if(result){ //上传成功
				String url = ConfigProperties.getValueByKey("FILE_VISIT_PATH")+"/"+filename;
				r.setCode(Constants.CODE_SUCCESS);
				r.setResult(url);
			}else{ //上传失败
				logger.error("文件上传失败");
				r.setCode(Constants.CODE_FAIL);
				r.setResult("文件上传失败");
			}
		}catch(Exception e){
			logger.error("文件上传异常", e);
			r.setCode(Constants.CODE_FAIL);
			r.setResult("文件上传异常");
		}
	    return r;
	}
}
