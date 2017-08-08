package cn.zdmake.metro.dao;

import java.util.List;
import java.util.Map;
import cn.zdmake.metro.model.MetroRole;
import cn.zdmake.metro.model.MetroUser;

public interface ISysRightDao {
	/**
	 * 查找角色权限菜单
	 * @param params
	 * @return
	 */
	List<MetroRole> findRoleRightMenus(Map<String, Object> params);
	
	/**
	 * 查找用户权限数据
	 * @param params
	 * @return
	 */
	List<MetroUser> findUserRightDatas(Map<String, Object> params);
}
