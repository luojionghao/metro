package cn.zdmake.metro.controllor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroLine;
import cn.zdmake.metro.service.IMetroTubeMapService;
import cn.zdmake.metro.service.ISysRightService;
import cn.zdmake.metro.vo.TubeMapIntervalLr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 线网图请求控制器
 * @author MAJL
 *
 */
@Controller
@RequestMapping("/tube/map")
public class MetroTubeMapControllor extends BaseController {
	
	@Autowired
	private ISysRightService rightService;
	
	@Autowired
	private IMetroTubeMapService tmService;
	
	/**
	 * 地铁线网图主页
	 * @return
	 */
	@RequestMapping("/index")
	public String list(){
		
		return "/find-info/tube_map";
	}
	
	/**
	 * 查找线路数据
	 * @return
	 */
	@RequestMapping("/find/line/datas")
	@ResponseBody
	public List<MetroLine> findUserDatasById(){
		MetroCity city = rightService.getRightDatasByUserId(this.getCurrentUser().getId());
		return city.getLineList();
	}
	
	/**
	 * 查找线路数据
	 * @return
	 */
	@RequestMapping("/find/line/interval/datas")
	@ResponseBody
	public Map<String, TubeMapIntervalLr> findUserDatasByIntervalId(@RequestParam("intervalId") String intervalId) {
		Long userId = this.getCurrentUser().getId();
		Date loginTime = this.getCurrentUser().getLoginTime();
		TubeMapIntervalLr tm = tmService.findLrInfo(userId, Long.parseLong(intervalId), loginTime);
		Map<String, TubeMapIntervalLr> map = new HashMap<String, TubeMapIntervalLr>();
		map.put("data", tm);
		
		return map;
	}
}
