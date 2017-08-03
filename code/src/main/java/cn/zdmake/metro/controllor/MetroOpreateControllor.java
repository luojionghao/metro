package cn.zdmake.metro.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.model.MetroUserOpreateRec;
import cn.zdmake.metro.service.IMetroOpreateService;

@Controller
@RequestMapping("/opreate")
public class MetroOpreateControllor extends BaseController {

	@Autowired
	private IMetroOpreateService opreateService;

	/**
	 * 操作记录管理主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String list() {

		return "/sysm/smf_record";
	}

	/**
	 * 查找操作记录
	 * 
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            每页项数
	 * @return 操作记录
	 */
	// @SysControllorLog(menu="操作记录管理",opreate="查询操作记录")
	@RequestMapping("/find/opreates")
	@ResponseBody
	public PageResultSet<MetroUserOpreateRec> findOpreateRecAll(@RequestParam("pageNum") int pageNum,
			@RequestParam("pageSize") int pageSize) {
		PageResultSet<MetroUserOpreateRec> recordResult = opreateService.findMetroOpreateInfo(pageNum, pageSize);
		return recordResult;
	}
}
