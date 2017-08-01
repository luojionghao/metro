package cn.zdmake.metro.controllor;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import cn.zdmake.metro.base.controller.BaseController;
import cn.zdmake.metro.base.model.CommonResponse;
import cn.zdmake.metro.base.security.MD5;
import cn.zdmake.metro.base.utils.GsonUtils;
import cn.zdmake.metro.base.utils.MathUtil;
import cn.zdmake.metro.base.utils.MemcachedUtil;
import cn.zdmake.metro.service.IMetroUserService;
import cn.zdmake.metro.vo.SessionUser;
import jxl.common.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zdmake.metro.base.listener.OnlineUserBindingListener;
import cn.zdmake.metro.base.security.VCode;
import cn.zdmake.metro.base.utils.Constants;
import cn.zdmake.metro.model.MetroSysMenu;
import cn.zdmake.metro.model.MetroUser;
import cn.zdmake.metro.service.ILoginService;
import cn.zdmake.metro.service.ISysRightService;

/**
 * 登录控制器
 * @author hank
 *
 * 2016年8月15日
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private ISysRightService rightService;
	
	@Autowired
	private IMetroUserService userService;
	
	/**
	 * 登陆页
	 * @return
	 */
	@RequestMapping("/index")
	public String toLogin(){
		modelMap.addAttribute("login_out", request.getAttribute("login_out"));
		modelMap.addAttribute("login_msg", request.getAttribute(Constants.MSG));
		return "/login/login";
	}
	
	/**
	 * 退出系统
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		session.removeAttribute(Constants.SESSION_CURRENT_USER);
		session.invalidate();
		return "forward:/login/index";
	}
	
	/**
	 * 获取验证码
	 */
	@RequestMapping(value="/get/vcode/img",method=RequestMethod.GET)
	public void getVCodeImg(){
        // 将四位数字的验证码保存到Session中。              
              
        // 禁止图像缓存。         
        response.setHeader("Pragma", "no-cache");         
        response.setHeader("Cache-Control", "no-cache");         
        response.setDateHeader("Expires", 0);         
        response.setContentType("image/jpeg");         
        // 将图像输出到Servlet输出流中                
        try {
        	ServletOutputStream sos = response.getOutputStream();
			ImageIO.write(VCode.getVCodeImg(session), "jpeg", sos);
			sos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
	}
	
	/**
	 * 用户登录
	 * @return
	 */
	@RequestMapping("/check/login")
	@ResponseBody
	public String checkLogin(){
		CommonResponse r = new CommonResponse();
		String u_name = request.getParameter("username");
		String u_pass = request.getParameter("password");
		String u_vcode = request.getParameter("vcode");
		try{
			String vcode = (String) request.getSession().getAttribute(Constants.SESSION_VCODE_CODE);
			Long startT = (Long) request.getSession().getAttribute(Constants.SESSION_VCODE_TIME);
			if(vcode.equals(u_vcode.toUpperCase())){
				long s = (System.currentTimeMillis()-startT)/1000/60;
				//验证码有效时间为3分钟
				if(s<3){
					MetroUser muser = loginService.checkUserPass(u_name, MD5.MD5Encode(u_pass));
					if(muser!=null){
						SessionUser sessionUser = createSessionUser(muser);
						request.getSession().setAttribute(Constants.SESSION_CURRENT_USER, sessionUser);
						try{
							/*
							Object obj = request.getSession().getServletContext().getAttribute(Constants.SESSION_CURRENT_USER+muser.getId());
							if(obj!=null&&!"".equals(obj)){
								HttpSession se = (HttpSession) obj;							
								se.removeAttribute(Constants.SESSION_CURRENT_USER);
								session.invalidate();
							}*/
								MemcachedUtil.put(Constants.SESSION_CURRENT_USER + muser.getId(), sessionUser.getLoginTime());

						}catch(Exception e){
							System.out.print("memche_error:"+e.getMessage());
						}
						request.getSession().setAttribute(Constants.SESSION_LISTENER_MARK, new OnlineUserBindingListener(sessionUser));
						r.setCode(Constants.CODE_SUCCESS);						
					}else{
						r.setCode(Constants.CODE_FAIL);
						r.setResult(Constants.USER_ERROR_NAME_OR_PSW_MSG);
					}					
				}else{//验证码超时失效
					r.setCode(4);
					r.setResult(Constants.VCODE_OVERTIME_MSG);
				}
			}else{//验证码错误
				r.setCode(3);
				r.setResult(Constants.VCODE_ERROR_MSG);
			}
		
		}catch(Exception e){
			logger.error("系统繁忙",e);
			r.setCode(5);
			r.setResult("系统繁忙");
		}
		return GsonUtils.toJson(r);
	}
	
	@RequestMapping("/to-main")
	public String toMain(){
		List<MetroSysMenu> menus = rightService.getRightMenusByRoleId(this.getCurrentUser().getRoleId());
		modelMap.addAttribute("menus", menus);
		modelMap.addAttribute("name", this.getCurrentUser().getName());
		modelMap.addAttribute("username", this.getCurrentUser().getUsername());
		return "/login/main";
	}
	
	private SessionUser createSessionUser(MetroUser user){
		SessionUser sessionUser = new SessionUser();
		Date date = Calendar.getInstance().getTime();
		sessionUser.setLoginTime(date);
		sessionUser.setLoginIp(getRemoteHost(request));
		sessionUser.setId(user.getId());
		sessionUser.setUsername(user.getUsername());
		sessionUser.setRoleId(user.getRoleId());
		sessionUser.setName(user.getName());
		sessionUser.setSex(user.getSex());
		return sessionUser;
	}
	
	public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	/**
	 * 修改密码主页
	 * @return
	 */
	@RequestMapping("/to/pass/edit/index")
	public String updatePassword(){
		modelMap.addAttribute("name", this.getCurrentUser().getName());
		return "/login/change_password";
	}
	
	/**
	 * 保存新密码
	 */
	@RequestMapping("/save/edit/pass/info")
	@ResponseBody
	public ModelMap saveNewPassword(){
		ModelMap modelMap = new ModelMap();
		String oldpass = request.getParameter("oldpass");
		String newpass = request.getParameter("newpass");
		MetroUser u = loginService.checkUserPass(this.getCurrentUser().getUsername(), MD5.MD5Encode(oldpass));
		if(u!=null){
			boolean b = userService.editMetroUserPassword(this.getCurrentUser().getId().toString(), MD5.MD5Encode(newpass));
			if(b){
				modelMap.addAttribute("code", 1);
			}else{
				modelMap.addAttribute("code", 0);
			}
		}else{
			modelMap.addAttribute("code", 2);
		}
		return modelMap;
	}
}
