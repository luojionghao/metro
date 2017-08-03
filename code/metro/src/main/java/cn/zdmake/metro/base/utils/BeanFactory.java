package cn.zdmake.metro.base.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * spring bean获取,主要用于测试使用，无需应用启动
 * @author hank
 *
 * 2016年4月8日
 */
public class BeanFactory {
	private static ApplicationContext ctx;
	
	public static Object getBean(String beanName){
		if(ctx == null){
			ctx = new ClassPathXmlApplicationContext(
					new String[]{"classpath:/xml/applicationContext.xml",
								 "classpath:/xml/springMVC-servlet.xml"});
		}
		return ctx.getBean(beanName);
	}
	
	public static void main(String[] args) {
		//IMenuService menuService = (IMenuService) BeanFactory.getBean("menuService");
		/*IComponentService c = (IComponentService) BeanFactory.getBean("componentService");
		String openAppid= ConfigProperties.getValueByKey("ZOOKE_OPEN_WEIXIN_APPID");
		String openAppsecret= ConfigProperties.getValueByKey("ZOOKE_OPEN_WEIXIN_APPSECRET");
		String componentVerifyTicket = RedisUtil.hget(Constants.REDIS_COMPONENT_VERIFY_TICKET);
		
		String t = c.api_create_preauthcode(openAppid, openAppsecret, componentVerifyTicket);
		System.out.println(t);*/
	}
}
