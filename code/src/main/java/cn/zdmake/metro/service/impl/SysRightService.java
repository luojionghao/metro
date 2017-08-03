package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.utils.MemcachedUtil;
import cn.zdmake.metro.dao.ISysRightDao;
import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroRole;
import cn.zdmake.metro.model.MetroSysMenu;
import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.service.ISysRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rightService")
public class SysRightService implements ISysRightService {

	@Autowired
	private ISysRightDao rightDao;

	@Override
	public List<MetroSysMenu> getRightMenusByRoleId(Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		List<MetroRole> roles = null;
		List<MetroSysMenu> msm = null;
		String key = "";
		try {
			// key = "menus"+roleId+Constants.ROLE_MENU_RIGHT;
			// if(MemcachedUtil.containsKey(key)){
			// Object obj = MemcachedUtil.get(key);
			// if(obj!=null){
			// return (List<MetroSysMenu>) obj;
			// }
			// }else{
			roles = rightDao.findRoleRightMenus(params);
			if (roles != null && roles.size() > 0) {
				msm = roles.get(0).getMenus();
				MemcachedUtil.put(key, msm);
				return msm;
			}
			// }
		} catch (Exception e) {
			return msm;
		}
		return null;
	}

	@Override
	public MetroCity getRightDatasByUserId(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		MetroCity mc = null;
		try {
			List<MetroUser> users = rightDao.findUserRightDatas(params);
			if (users != null && users.size() > 0) {
				mc = users.get(0).getCity();
				// MemcachedUtil.put(key, mc);
				return mc;
			}
		} catch (Exception e) {
			return mc;
		}
		return null;
	}
	

	@Override
	public boolean setRightMenusByRoleId(Long roleId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		// String key = "";
		// List<MetroRole> roles = rightDao.findRoleRightMenus(params);
		try {
			// if(roleId!=null){//重置单个角色
			// if(roles!=null&&roles.size()>0){
			// MetroRole r = roles.get(0);
			// key = "menus"+r.getId()+Constants.ROLE_MENU_RIGHT;
			// MemcachedUtil.put(key, r.getMenus());
			// }
			// }else{//重置所有角色
			// if(roles!=null&&roles.size()>0){
			// for(MetroRole r : roles){
			// key = "menus"+r.getId()+Constants.ROLE_MENU_RIGHT;
			// MemcachedUtil.put(key, r.getMenus());
			// }
			// }
			// }
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	@Override
	public boolean setRightDatasByUserId(Long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		// String key = "";
		// List<MetroUser> users = rightDao.findUserRightDatas(params);
		try {
			// if(userId!=null){//重置单个用户
			// if(users!=null&&users.size()>0){
			// MetroUser u = users.get(0);
			// key = "datas"+u.getId()+Constants.USER_DATA_RIGHT;
			// MemcachedUtil.put(key, u.getCity());
			// }
			// }else{//重置所有所有用户
			// if(users!=null&&users.size()>0){
			// for(MetroUser u : users){
			// key = "datas"+u.getId()+Constants.USER_DATA_RIGHT;
			// MemcachedUtil.put(key, u.getCity());
			// }
			// }
			// }
			return true;
		} catch (Exception e) {

		}
		return false;
	}

}
