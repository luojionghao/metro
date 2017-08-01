package cn.zdmake.metro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.model.MetroUserDataRel;
import cn.zdmake.metro.service.IMetroDataRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.zdmake.metro.dao.IMetroDataRightDao;

@Service("dataRightService")
public class MetroDataRightService implements IMetroDataRightService {
	@Autowired
	private IMetroDataRightDao drDao;
	
	@Override
	public List<MetroUserDataRel> findUserDataRightByUserId(String userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);		
		return drDao.findObjsList(params);
	}

	@Override
	public boolean saveDataRightInfo(String userId, String dataRight) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		drDao.deleteObj(params);
		if(dataRight!=null&&!"".equals(dataRight)){
			String[] dataRights = dataRight.split(",");
			List<MetroUserDataRel> udrlist = new ArrayList<MetroUserDataRel>();
			MetroUserDataRel udr = null;
			for(String drt:dataRight.split(",")){
				String[] dr = drt.split(";");
				udr = new MetroUserDataRel();
				udr.setCityId(Long.parseLong(dr[0]));
				udr.setLineId(Long.parseLong(dr[1]));
				udr.setIntervalId(Long.parseLong(dr[2]));
				udr.setLeftOrRight(dr[3]);
				udr.setUserId(Long.parseLong(userId));
				udrlist.add(udr);
			}
			params.put("udrlist", udrlist);
			drDao.insertObjs(params);
			
		}
		return true;
	}

}
