/**
 * 
 */
package cn.zdmake.metro.base.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @className:CommonUtils.java
 * @classDescription:TODO
 * @author: luowq
 * @createTime: 2015年10月28日
 */
public class CommonUtils {
	
	/**
	 * 成功代码
	 */
	public static final int CODE_SUCCESS = 1;
	/**
	 * 失败代码
	 */
	public static final int CODE_FAIL = 0;
	
	/**
	 * 省份级别
	 */
	public static final int AREA_LEVEL_PROVINCE = 1;
	/**
	 * 城市级别
	 */
	public static final int AREA_LEVEL_CITY = 2;
	/**
	 * 区域级别
	 */
	public static final int AREA_LEVEL_TOWN = 3;
	

	/**
	 * 从字符串中截取数字
	 * @param str
	 * @return
	 */
	public static List<String> getNumberFromString(String str){
		if(str != null){
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(str.trim());
			List<String> r = new ArrayList<String>();
			while(m.find()){
				r.add(m.group());
			}
			if(r.size() > 0){
				return r;
			}
		}
		return null;
	}

	//如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
	/*答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。

如：

X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 192.168.1.100

用户真实IP为： 192.168.1.110*/
	public static String getIpAddr(HttpServletRequest request) { 
        String ip  = request.getHeader("x-forwarded-for" ); 
         if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) { 
            ip  = request.getHeader("Proxy-Client-IP" ); 
        } 
         if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) { 
            ip  = request.getHeader("WL-Proxy-Client-IP" ); 
        }
        /* if (ip == null || ip.length() == 0 || "unknown" .equalsIgnoreCase(ip)) { 
            ip  =  request.getRemoteAddr(); 
        } */
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (Exception e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) { // "***.***.***.***".length() = 15
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
         
         return  ip; 
    } 
	
	/**
	 * 判断对象是否不为空
	 * 
	 * @param t
	 * @return
	 */
	public static <T> boolean isNotNull(T t) {
		if (null == t)
			return false;

		if (t instanceof String) {
			String tm = (String) t;
			if ("".equals(tm))
				return false;
		} else if (t instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection tm = (Collection) t;
			if (0 == tm.size())
				return false;
		} else if (t instanceof Map) {
			@SuppressWarnings("rawtypes")
			Map tm = (Map) t;
			if (0 == tm.size())
				return false;
		} else if (t.getClass().isArray()) {
			if (t instanceof byte[]) {
				byte[] tm = (byte[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof short[]) {
				short[] tm = (short[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof int[]) {
				int[] tm = (int[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof long[]) {
				long[] tm = (long[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof float[]) {
				float[] tm = (float[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof double[]) {
				double[] tm = (double[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof char[]) {
				char[] tm = (char[]) t;
				if (0 == tm.length)
					return false;
			} else if (t instanceof boolean[]) {
				boolean[] tm = (boolean[]) t;
				if (0 == tm.length)
					return false;
			} else {
				@SuppressWarnings("unchecked")
				T[] tm = (T[]) t;
				if (0 == tm.length)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 复制对象属性值(支持不同对象)
	 * 
	 * @param source
	 * @param target
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void copy(Object source, Object target) {
		Class sourceClass = source.getClass();// 得到对象的Class
		Class targetClass = target.getClass();// 得到对象的Class
		Field[] sourceFields = sourceClass.getDeclaredFields();// 得到Class对象的所有属性
		Field[] targetFields = targetClass.getDeclaredFields();// 得到Class对象的所有属性
		for (Field sourceField : sourceFields) {
			String name = sourceField.getName();// 属性名
			Class type = sourceField.getType();// 属性类型
			String methodName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			try {
				Method getMethod = sourceClass.getMethod("get" + methodName);// 得到属性对应get方法
				Object value = getMethod.invoke(source);// 执行源对象的get方法得到属性值
				for (Field targetField : targetFields) {
					String targetName = targetField.getName();// 目标对象的属性名
					if (targetName.equals(name)) {
						Method setMethod = targetClass.getMethod("set"
								+ methodName, type);// 属性对应的set方法
						setMethod.invoke(target, value);// 执行目标对象的set方法
					}
				}
			} catch (Exception e) {
			}
		}
	}
	/**
	 * 加盐MD5（source.hashCode() + source+source.length() + source.hashCode()）
	 * @param source 目标字符串
	 * @return 加盐MD5字符串
	 */
	public static String getMD5WithSalt(String source){
		return getMD5( source.hashCode() + source +source.length()+ source.hashCode() );
	}
	

	/**
	 * 获取字符串MD5
	 * @param source
	 * @return
	 */
	public static String getMD5(String source) {

		String s = null;
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] b = source.getBytes("UTF-8");
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(b);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {

				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];

				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);

		} catch (Exception e) {
			//e.printStackTrace();
		}
		return s;
	}
	
	public static String getElementFromXml(String element,String xml){
		Document doc;  
	    try {  
	        doc = DocumentHelper.parseText(xml);  
	        Element rootElt = doc.getRootElement();  
	        String result = rootElt.elementText(element);  
	        return result;
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }
	    return null;
	}
	
	public static String getImageGenerationFileName(String fileNameSuffix){
		Date date = new Date();
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
		String generationFileName = simpleFormat.format(date)+ new Random().nextInt(1000);
		String filename = generationFileName+"."+fileNameSuffix;
		return filename;
	}
	public static void main(String[] args) {
		
		String t1 = "<xml><test>sss</test></xml>";
		System.out.println(getElementFromXml("test", t1));
		System.out.println(isNotNull(0l));
		String r = " ssss123ss1sdda334 f 560";
		List<String> t = getNumberFromString(r);
		System.out.println(t.toString());
		
		System.out.println(ConfigProperties.getValueByKey("WEIXIN_PAY_SIGN_KEY"));

	}
	
}
