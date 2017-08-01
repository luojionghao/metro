package cn.zdmake.metro.service.impl;
import java.util.HashMap;
import java.util.Map;

import cn.zdmake.metro.dao.ILoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.service.ILoginService;

@Service("loginService")
public class LoginService implements ILoginService{
	@Autowired
	private ILoginDao loginDao;
	
	@Override
	public MetroUser checkUserPass(String username, String pass) {
		Map<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("password", pass);
		return loginDao.findUserByNameAndPass(params);
	}

	@Override
	public boolean updateUserOnlineStatus(Long userId, String loginIp, int status) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("loginIp", loginIp);
		params.put("status", status);
		return loginDao.updateUserOnlineStatus(params)>0?true:false;
	}

}
