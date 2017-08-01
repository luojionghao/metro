package cn.zdmake.metro.base.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.zdmake.metro.base.utils.Constants;
import cn.zdmake.metro.base.utils.MemcachedUtil;
import cn.zdmake.metro.vo.SessionUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * 访问拦截器，用于验证登录权限
 * @author hank
 *
 * 2016年7月26日
 */
public class VisitorInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		SessionUser su = (SessionUser) session.getAttribute(Constants.SESSION_CURRENT_USER);
		if(su!= null){ //已登录

			try{
				Date now = (Date) MemcachedUtil.get(Constants.SESSION_CURRENT_USER+su.getId());
				System.out.print("prehandle"+su.getId()+"\n");
				if(su.getLoginTime().getTime()<now.getTime()){
					System.out.print(su.getId()+":{memched:"+now.getTime()+"},{session:"+su.getLoginTime().getTime()+"}\n");
					session.invalidate();
					request.setAttribute("login_out", 1);
					request.setAttribute(Constants.MSG, "您被强制下线");
					request.getRequestDispatcher("/login/index").forward(request, response);
					return false;
				}
			}catch(Exception e){

			}
			return true;
		}else{ //未登录或session过期
			//response.sendRedirect(request.getContextPath()+"/user/to-error");
			request.setAttribute(Constants.MSG, "请重新登录");
			request.getRequestDispatcher("/login/index").forward(request, response);
			return false;
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	

}
