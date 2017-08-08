package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zdmake.metro.base.page.Page;
import cn.zdmake.metro.base.page.PageResultSet;
import cn.zdmake.metro.base.service.BaseService;
import cn.zdmake.metro.dao.IMetroUserDao;
import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.service.IMetroUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class MetroUserService extends BaseService<MetroUser> implements IMetroUserService {
	@Autowired
	IMetroUserDao userDao;

	@Override
	public PageResultSet<MetroUser> findMetroUserInfo(String deptId, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		return this.getPageResultSet(params, pageNum, pageSize, userDao);
	}

	@Override
	public boolean addMetroUserInfo(String username, String password, String name, String deptIds, String roleId) {
		MetroUser user = new MetroUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setName(name);
		user.setRoleId(Long.parseLong(roleId));
		int count = userDao.insertObj(user);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", user.getId());
		params.put("deptIds", deptIds.split(","));
		count = count + userDao.addUserDeptRel(params);
		return count > 1 ? true : false;
	}

	@Override
	public boolean editMetroUserInfo(String userId, String username, String name, String oldDeptIds, String deptIds,
			String roleId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("username", username);
		params.put("name", name);
		params.put("roleId", roleId);
		int count = userDao.updateObj(params);

		if (deptIds != null) {
			params.clear();
			params.put("userId", userId);
			params.put("deptIds", oldDeptIds.split(","));
			count = count + userDao.delUserDeptRel(params);
			params.put("deptIds", deptIds.split(","));
			count = count + userDao.addUserDeptRel(params);
		}
		return count > 2 ? true : false;
	}

	@Override
	public boolean delMetroUserInfo(String deptId, String userId, int deptSize) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("deptId", deptId);
		int count = 0;
		if (deptSize == 1) {
			count = userDao.deleteObj(params);// 当用户只有一个部门时
		} else {
			count = userDao.deleteObjDeptRel(params);// 当用户有多个部门时
		}
		return count > 0 ? true : false;
	}

	@Override
	public boolean editMetroUserPassword(String userId, String password) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("password", password);
		return userDao.editUserPassword(params) > 0 ? true : false;
	}

	@Override
	public boolean addMetroUserToDept(String deptId, String userIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("deptId", deptId);
		params.put("userIds", userIds.split(","));
		return false;
	}

	@Override
	public boolean findMetroUserUsername(String username) {
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		return userDao.findMetroUserUsername(params) > 0 ? true : false;
	}

	@Override
	public List<MetroUser> findAllUser() {
		return null;
	}

	@Override
	public PageResultSet<MetroUser> findAllUser(int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>();
		int total = userDao.countObjsr(params);
		Page page = new Page(total, pageSize, pageNum);
		if (total > 0) {
			params.put("start", page.getBeginIndex());
			params.put("pageSize", page.getPageSize());
			List<MetroUser> userList = userDao.findObjsListr(params);
			PageResultSet<MetroUser> pageResult = null;
			if (userList != null && userList.size() > 0) {
				pageResult = new PageResultSet<>();
				pageResult.setList(userList);
				pageResult.setPage(page);
			}
			return pageResult;
		}
		return null;
	}

	/**
	 * 通过id查询用户信息
	 * 
	 * @param userId
	 *            用户Id
	 * @return
	 */
	@Override
	public MetroUser findObjById(Long userId) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		return userDao.findObjById(params);
	}

}
