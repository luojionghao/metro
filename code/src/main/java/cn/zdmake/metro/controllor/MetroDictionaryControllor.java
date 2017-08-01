package cn.zdmake.metro.controllor;

import java.util.List;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.GsonUtils;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.model.MetroPhoto;
import cn.zdmake.metro.service.IMetroDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import cn.zdmake.metro.base.aop.SysControllorLog;
import cn.zdmake.metro.base.mv.JModelAndView;
import cn.zdmake.metro.base.utils.Constants;
import cn.zdmake.metro.model.MetroDictionary;
import cn.zdmake.metro.service.ICommonService;
import cn.zdmake.metro.service.IMetroPhotoService;
/**
 * 字典管理控制器
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/dic")
public class MetroDictionaryControllor extends BaseController {
	
	@Autowired
	private IMetroDictionaryService dicService;
	
	@Autowired
	private IMetroPhotoService photoService;
	
	@Autowired
	private ICommonService commonService;
	/**
	 * 字典管理主页
	 * @return
	 */
	@RequestMapping("/index")
	public String list(){
		
		return "/sysm/smf_dictionary";
	}
	
	@RequestMapping("/check/photo/name")
	@ResponseBody
	public ModelMap checkPhotoName(){
		ModelMap modelMap = new ModelMap();
		String pname = request.getParameter("pname");
		boolean b = dicService.checkPhotoName(pname);
		if(b){
			modelMap.addAttribute("code", 1);
		}else{
			modelMap.addAttribute("code", 0);
		}		
		return modelMap;
	}
	/**
	 * 查找字典内容
	 * @return
	 */
	@SysControllorLog(menu="字典管理",opreate="查询")
	@RequestMapping("/find/dics")
	@ResponseBody
	public String findDictionaryAll(){
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageResultSet<MetroDictionary> userResult= dicService.findMetroDictionaryInfo(
				StringUtil.nullToInt(pageNum), StringUtil.nullToInt(pageSize));
		return GsonUtils.toJson(userResult);
	}
	
	
	/**
	 * 查找图片内容
	 * @return
	 */
	@SysControllorLog(menu="字典管理",opreate="查询")
	@RequestMapping("/find/photos")
	@ResponseBody
	public String findPhotosAll(){
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageResultSet<MetroPhoto> photoResult= photoService.findMetroPhotos(
				StringUtil.nullToInt(pageNum), StringUtil.nullToInt(pageSize));
		return GsonUtils.toJson(photoResult);
	}
	
	/**
	 * 保存图片编辑信息
	 * @param file
	 * @return
	 */
	@SysControllorLog(menu="字典管理",opreate="信息编辑")
	@RequestMapping("/save/photo/info")
	public String savePhotoInfo(@RequestParam(value="photoImg",required=false)MultipartFile file){
		String operate = request.getParameter("operate");
		String photoUrl = request.getParameter("photoUrl");
		String photoName = request.getParameter("photoName");
		String photoType = request.getParameter("photoType");
		String photoId = null;
		if(photoUrl==null||"".equals(photoUrl)){
			CommonResponse uploadResult = commonService.fileUpload(file);
			if(uploadResult.getCode() == Constants.CODE_SUCCESS){ //上传文件失败
				photoUrl = uploadResult.getResult().toString();
			}
		}
		if(operate!=null&&"1".equals(operate)){
			photoId = request.getParameter("photoId");
		}
		photoService.saveMetroPhoto(photoId!=null&&!"".equals(photoId)?Long.parseLong(photoId):null,
				photoName, photoUrl, Integer.parseInt(photoType));			
		return "forward:/dic/index";
	}
	
	/**
	 * 保存图片编辑信息
	 * @return
	 */
	@SysControllorLog(menu="字典管理",opreate="删除记录")
	@RequestMapping("/del/photo/info")
	@ResponseBody
	public ModelMap delInfo(){
		ModelMap modelMap = new ModelMap();
		String photoId = request.getParameter("photoId");
		boolean b = photoService.delMetroPhoto(Long.parseLong(photoId));
		if(b){
			modelMap.addAttribute("code", 1);
		}else{
			modelMap.addAttribute("code", 0);
		}		
		return modelMap;
	}
	
	/**
	 * 编辑页面
	 * @return
	 */
	@RequestMapping("/to/edit")
	public ModelAndView editRoleInfo(){
		JModelAndView mv = new JModelAndView("/sysm/smf_dictionary_edit",request,response);
		String operate = request.getParameter("operate");
		if(operate!=null&&"0".equals(operate)){
			mv.addObject("operate", 0);
		}else{
			String photoId = request.getParameter("photoId");
			List<MetroPhoto> list = photoService.findMetroPhotosByParams(photoId!=null&&!"".equals(photoId)?Long.parseLong(photoId):null);
			mv.addObject("obj", list!=null&&list.size()>0?list.get(0):null);
			mv.addObject("operate", 1);
		}
		return mv;		
	}
	

}
