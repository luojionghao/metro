package cn.zdmake.metro.service;

import java.util.List;

import cn.zdmake.metro.model.MetroCity;
import cn.zdmake.metro.model.MetroSysMenu;

/**
 * 用户权限数据查询接口
 * @author MAJL
 *
 */
public interface ISysRightService {
	/**
	 * 通过用户角色Id返回对应的权限内菜单信息
	 * @param roleId 角色Id
	 * @return
	 */
	List<MetroSysMenu> getRightMenusByRoleId(Long roleId);
	
	/**
	 * 通过用户Id返回对应的用户数据权限信息
	 * @param userId
	 * @return
	 */
	MetroCity getRightDatasByUserId(Long userId);
	
	/**
	 * 重置角色菜单权限
	 * @param roleId
	 * @return
	 */
	boolean setRightMenusByRoleId(Long roleId);
	
	/**
	 * 重置用户数据权限
	 * @param userId
	 * @return
	 */
	boolean setRightDatasByUserId(Long userId);
}
