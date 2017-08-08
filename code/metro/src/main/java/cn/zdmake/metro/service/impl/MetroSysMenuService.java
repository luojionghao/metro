package cn.zdmake.metro.service.impl;

import java.util.HashMap;
import java.util.List;

import cn.zdmake.metro.dao.IMetroMenuDao;
import cn.zdmake.metro.model.MetroSysMenu;
import cn.zdmake.metro.service.IMetroSysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MetroSysMenuService implements IMetroSysMenuService {
	
	@Autowired
	private IMetroMenuDao menuDao;
	
	@Override
	public List<MetroSysMenu> findMenuAll(int level) {
		return menuDao.findObjsList(new HashMap<String, Object>());
	}

}
