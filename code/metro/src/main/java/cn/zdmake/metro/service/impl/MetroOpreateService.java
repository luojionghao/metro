package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.dao.IMetroOpreateDao;
import cn.zdmake.metro.model.MetroUserOpreateRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.service.IMetroOpreateService;

@Service("opreateService")
public class MetroOpreateService extends BaseService<MetroUserOpreateRec> implements IMetroOpreateService {
	@Autowired
	private IMetroOpreateDao opreateDao;
	
	@Override
	public PageResultSet<MetroUserOpreateRec> findMetroOpreateInfo(int pageNum,
																   int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, opreateDao);
	}

	@Override
	public boolean addLogs(List<MetroUserOpreateRec> logs) {
		Map<String, Object> params = new HashMap<>();
		params.put("logs", logs);
		int count = opreateDao.insertObjs(params);
		return count>0?true:false;
	}

}
