package cn.zdmake.metro.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.utils.GsonUtils;
import cn.zdmake.metro.base.utils.StringUtil;
import cn.zdmake.metro.model.MetroUserOpreateRec;
import cn.zdmake.metro.service.IMetroOpreateService;

@Controller
@RequestMapping("/opreate")
public class MetroOpreateControllor extends BaseController {
	
	@Autowired
	private IMetroOpreateService opreateService;
	
	/**
	 * 操作记录管理主页
	 * @return
	 */
	@RequestMapping("/index")
	public String list(){
		
		return "/sysm/smf_record";
	}
	
	/**
	 * 查找操作记录
	 * @return
	 */
	//@SysControllorLog(menu="操作记录管理",opreate="查询操作记录")
	@RequestMapping("/find/opreates")
	@ResponseBody
	public String findOpreateRecAll(){
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		PageResultSet<MetroUserOpreateRec> recordResult= opreateService.findMetroOpreateInfo(StringUtil.nullToInt(pageNum), StringUtil.nullToInt(pageSize));
		return GsonUtils.toJson(recordResult);
	}
}
