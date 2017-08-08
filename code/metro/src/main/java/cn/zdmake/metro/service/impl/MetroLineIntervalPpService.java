package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

//import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroLineIntervalPpDao;
import cn.zdmake.metro.model.MetroLineIntervalPp;
import cn.zdmake.metro.service.IMetroLineIntervalPpService;

/**
 * 地铁线路区间工程进度业务接口实现
 * @author hank
 *
 * 2016年9月1日
 */
@Service("lineIntervalPpService")
public class MetroLineIntervalPpService extends BaseService<MetroLineIntervalPp> implements IMetroLineIntervalPpService{
	
	// private static Logger logger = Logger.getLogger(MetroLineIntervalPpService.class);

	@Autowired
	IMetroLineIntervalPpDao lineIntervalPpDao;
	/**
	 * 通过intervalId查询model
	 * @param intervalId
	 * @return
	 */
	@Override
	public MetroLineIntervalPp findByIntervalId(Long intervalId) {
		return lineIntervalPpDao.findByIntervalId(intervalId);
	}
	/**
	 * 保存
	 */
	@Override
	public Long insertObj(MetroLineIntervalPp pp) {
		int r = lineIntervalPpDao.insertObj(pp);
		if(r > 0){
			return pp.getId();
		}
		return null;
	}
	/**
	 * 更新
	 */
	@Override
	public boolean updateObj(MetroLineIntervalPp pp) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalPpId", pp.getId());
		params.put("intervalId", pp.getIntervalId());
		params.put("mapPointa", pp.getMapPointa());
		params.put("mapPointb", pp.getMapPointb());
		params.put("svgPointa", pp.getSvgPointa());
		params.put("svgPointb", pp.getSvgPointb());
		params.put("sectionPointa", pp.getSectionPointa());
		params.put("sectionPointb", pp.getSectionPointb());
		params.put("sectionSvgPointa", pp.getSectionSvgPointa());
		params.put("sectionSvgPointb", pp.getSectionSvgPointb());
		params.put("ppSvgUrl", pp.getPpSvgUrl());
		params.put("sectionSvgUrl", pp.getSectionSvgUrl());
		int r = lineIntervalPpDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}

}
