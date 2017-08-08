package cn.zdmake.metro.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroWarningRecDao;
import cn.zdmake.metro.model.MetroLineIntervalWarningRec;
import cn.zdmake.metro.service.IMetroWarningRecService;

@Service("warningRecService")
public class MetroWarningRecService extends BaseService<MetroLineIntervalWarningRec> implements
		IMetroWarningRecService {
	
	@Autowired
	private IMetroWarningRecDao recDao;
	
	@Override
	public List<MetroLineIntervalWarningRec> findLastWarningRecs(Long userId,
			Date p_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return recDao.findLastWarningRecs(params);
	}

	@Override
	public List<MetroLineIntervalWarningRec> findLastWarningRecsIntervalId(Long intervalId, String leftOrRight, Date p_date) {
		Map<String, Object> params = new HashMap<>();
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		return recDao.findLastWarningRecsByIntervalId(params);
	}

	@Override
	public PageResultSet<MetroLineIntervalWarningRec> findWarningRecs(
			Long userId, String intervalId, String leftOrRight, int pageNum,
			int pageSize, String beginTime, String endTime, String warnParam) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("intervalId", intervalId);
		params.put("leftOrRight", leftOrRight);
		if(beginTime!=null&&!"".equals(beginTime)&&endTime!=null&&!"".equals(endTime)){
			String[] bs = beginTime.split("/");
			String[] ns = endTime.split("/");
			params.put("beginTime", bs[2]+"-"+bs[0]+"-"+bs[1]);
			params.put("endTime", ns[2]+"-"+ns[0]+"-"+ns[1]);
		}
		if(warnParam!=null&&!"-1".equals(warnParam)){
			params.put("warnParam", warnParam);
		}		
		return this.getPageResultSet(params, pageNum, pageSize, recDao);
	}
	/**
	 * 批量插入监测预警数据记录
	 * @param list
	 * @return
	 */
	@Override
	public boolean insertObjs(List<MetroLineIntervalWarningRec> list) throws Exception{
		int i = recDao.insertObjs(list);
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}

}
