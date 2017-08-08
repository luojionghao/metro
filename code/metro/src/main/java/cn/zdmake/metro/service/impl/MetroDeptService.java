package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.dao.IMetroDeptDao;
import cn.zdmake.metro.model.MetroDept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.service.IMetroDeptService;

@Service("deptService")
public class MetroDeptService implements IMetroDeptService{
	@Autowired
	private IMetroDeptDao deptDao;
	
	@Override
	public List<MetroDept> findAllDeptInfo() {
		return deptDao.findObjsList(new HashMap<String, Object>());
	}

	@Override
	public boolean addMetroDeptInfo(String deptName) {
		return deptDao.insertObj(new MetroDept(deptName))>0?true:false;
	}

	@Override
	public boolean editMetroDeptInfo(String deptId, String deptName) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		params.put("deptName", deptName);
		return deptDao.updateObj(params)>0?true:false;
	}

	@Override
	public boolean delMetroDeptInfo(String deptId) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		return deptDao.deleteObj(params)>0?true:false;
	}

}
