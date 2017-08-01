package cn.zdmake.metro.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.utils.IhistorianUtil;
import cn.zdmake.metro.dao.IMetroMonitorCityDao;
import cn.zdmake.metro.model.MetroLineIntervalLr;
import cn.zdmake.metro.dao.IMetroLineIntervalDao;
import cn.zdmake.metro.model.MonitorInfoCity;
import cn.zdmake.metro.vo.IhistorianResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroDataRightDao;
import cn.zdmake.metro.dao.IMetroLineIntervalLrDao;
import cn.zdmake.metro.model.MetroLineInterval;
import cn.zdmake.metro.model.MetroUserDataRel;
import cn.zdmake.metro.service.IMetroLineIntervalService;

/**
 * 地铁线路区间业务接口实现
 * @author hank
 *
 * 2016年8月17日
 */
@Service("lineIntervalService")
public class MetroLineIntervalService extends BaseService<MetroLineInterval> implements IMetroLineIntervalService{

	@Autowired
	private IMetroLineIntervalDao lineIntervalDao;
	@Autowired
	private IMetroDataRightDao drDao;
	@Autowired
	private IMetroLineIntervalLrDao lrDao;
	@Autowired
	private IMetroMonitorCityDao monitorCityDao;

	/**
	 * 保存线路区间信息
	 * @param interval
	 * @param userId
	 * @return
	 */
	@Override
	public Long insertObj(MetroLineInterval interval,Long userId) {
		
		int result = lineIntervalDao.insertObj(interval);
		if(result > 0){
			//新增数据权限关系
			MetroUserDataRel l_rel = new MetroUserDataRel();
			l_rel.setCityId(1l);
			l_rel.setLineId(interval.getLineId());
			l_rel.setIntervalId(interval.getId());
			l_rel.setLeftOrRight("l");
			l_rel.setUserId(userId);
			MetroUserDataRel r_rel = new MetroUserDataRel();
			r_rel.setCityId(1l);
			r_rel.setLineId(interval.getLineId());
			r_rel.setIntervalId(interval.getId());
			r_rel.setLeftOrRight("r");
			r_rel.setUserId(userId);
			List<MetroUserDataRel> udrlist = new ArrayList<MetroUserDataRel>();
			udrlist.add(l_rel);
			udrlist.add(r_rel);
			Map<String, Object> params = new HashMap<>();
			params.put("udrlist", udrlist);
			drDao.insertObjs(params);
			//新增区间左右线信息
			MetroLineIntervalLr l = new MetroLineIntervalLr();
			l.setIntervalId(interval.getId());
			l.setLeftOrRight("l");
			l.setBuildStatus(0);
			l.setStatus(1);
			MetroLineIntervalLr r = new MetroLineIntervalLr();
			r.setIntervalId(interval.getId());
			r.setLeftOrRight("r");
			r.setBuildStatus(0);
			r.setStatus(1);
			lrDao.insertObj(l);
			lrDao.insertObj(r);
			
			return interval.getId();
		}
		return null;
	}
	/**
	 * 通过id查询地铁城市线路区间信息
	 * @param intervalId
	 * @return
	 */
	@Override
	public MetroLineInterval findObjById(Long intervalId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		return lineIntervalDao.findObjById(params);
	}
	@Override
	public boolean deleteObj(Long intervalId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", intervalId);
		int r = lineIntervalDao.deleteObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public boolean updateObj(MetroLineInterval interval) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("intervalId", interval.getId());
		params.put("intervalMark", interval.getIntervalMark());
		params.put("lineId", interval.getLineId());
		params.put("intervalName", interval.getIntervalName());
		params.put("status", interval.getStatus());
		params.put("projectPdfUrl", interval.getProjectPdfUrl());
		params.put("imgMapXyUrl", interval.getImgMapXyUrl());
		params.put("remark", interval.getRemark());
		int r = lineIntervalDao.updateObj(params);
		if(r > 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 修改风险组段划分文档url
	 * @param id 区间id用于地图
	 * @param riskPdfUrl 风险组段划分文档url
	 * @return
	 */
	@Override
	public boolean editRiskPdfUrl(Long id, String riskPdfUrl) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", id);
		params.put("riskPdfUrl", riskPdfUrl);
		return lineIntervalDao.editRiskPdfUrl(params)>0?true:false;
	}

	@Override
	public Map<String, Object> getShieldData(Long intervalId, String leftOrRight, String date, String ring,String key, String type) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		MonitorInfoCity mic = monitorCityDao.findMonitorInfoCity(params);
		if(mic!=null){
			IhistorianResponse ir=null;
			Map<String, Object> list = new HashMap<>();
			String key1 = IhistorianUtil.getKey(mic.getLineNo(),
					mic.getIntervalMark(), mic.getLeftOrRight(), key);
			String[] bs = date.split("/");
			String b = bs[2]+"-"+bs[0]+"-"+bs[1];
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			if (type.equals("ring")){
				ir = IhistorianUtil.getExportdatabyringmin(key1,ring);
			}else if(type.equals("date")){
				try {
					ir = IhistorianUtil.getExportdatabydatemin(key1,sdf.parse(b));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
			if(ir!=null&&ir.getCode()==200&&ir.getResult()!=null){
				list = ir.getResult();
				return list;
			}
		}
		return null;
	}

}
