package cn.zdmake.metro.base.utils;

public class Constants {
	
	/** 登录用户session标识key */
	public final static String SESSION_CURRENT_USER = "session_current_user";
	/** 返回代码key */
	public final static String CODE = "code";
	/** 返回信息key */
	public final static String MSG = "msg";
	/** 用户名或密码为空-提示代码 */
	public final static String USER_EMPTY_NAME_OR_PSW_CODE = "100";
	/** 用户名或密码为空-提示信息 */
	public final static String USER_EMPTY_NAME_OR_PSW_MSG = "用户名或密码为空";
	/** 用户名或密码错误-提示代码 */
	public final static String USER_ERROR_NAME_OR_PSW_CODE = "101";
	/** 用户名或密码错误-提示信息 */
	public final static String USER_ERROR_NAME_OR_PSW_MSG = "用户名或密码错误";
	
	/**
	 * 验证码
	 */
	public final static String SESSION_VCODE_CODE = "session_vcode_code";
	/**
	 * 产生验证码时间
	 */
	public final static String SESSION_VCODE_TIME = "session_vcode_time";
	/**
	 * 验证码错误
	 */
	public final static String VCODE_ERROR_MSG = "验证码错误";
	/**
	 * 验证码超时
	 */
	public final static String VCODE_OVERTIME_MSG = "验证码过期";
	
	/** 服务器异常-错误代码 */
	public final static String EXCEPTION_SERVER_CODE = "003";
	/** 服务器异常-错误提示 */
	public final static String EXCEPTION_SERVER_MSG = "服务器异常";
	
	/**
	 * 成功代码
	 */
	public final static int CODE_SUCCESS = 1;
	/**
	 * 失败代码
	 */
	public final static int CODE_FAIL = 0;
	
	/**
	 * 用户session活动标识
	 */
	public final static String SESSION_LISTENER_MARK = "session_listener_mark";
	
	/**
	 * 角色菜单权限标记
	 */
	public final static String ROLE_MENU_RIGHT = "ROLE_MENU_RIGHT";
	
	/**
	 * 用户数据权限标记
	 */
	public final static String USER_DATA_RIGHT = "USER_DATA_RIGHT";
}
