package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.Map;

import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroDictionaryDao;
import cn.zdmake.metro.service.IMetroDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.model.MetroDictionary;

@Service("dicService")
public class MetroDictionaryService extends BaseService<MetroDictionary> implements IMetroDictionaryService {
	@Autowired
	private IMetroDictionaryDao dicDao;

	@Override
	public PageResultSet<MetroDictionary> findMetroDictionaryInfo(int pageNum,
																  int pageSize) {
		Map<String, Object> params = new HashMap<>();
		return this.getPageResultSet(params, pageNum, pageSize, dicDao);
	}

	@Override
	public boolean checkPhotoName(String pname) {
		Map<String, Object> params = new HashMap<>();
		params.put("pname", pname);
		Integer count = dicDao.checkPhotoName(params);
		if(count!=null&&count>0){
			return true;
		}
		return false;
	}

}
