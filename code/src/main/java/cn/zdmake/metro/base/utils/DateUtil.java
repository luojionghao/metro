package cn.zdmake.metro.base.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.util.StringUtils;

public class DateUtil {
	/**
	 * 将源字符串sformate时间格式转化成目标rformate时间格式
	 * @param date
	 * @param sformate
	 * @param rformate
	 * @return
	 */
	public static String getFormatDate(String date,String sformate,String rformate){
		String dateResultStr="";
		if(!StringUtils.isEmpty(date)){
			try {
				Date dateD = new SimpleDateFormat(sformate).parse(date);
				dateResultStr = new SimpleDateFormat(rformate).format(dateD);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
		}
		return dateResultStr;
	}
	
	/**
	 * 通过时间按指定格式转换成字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
    public static String format(Date date,String pattern){
    	if(date == null) return "";
    	String result = null;
    	DateFormat formt = new SimpleDateFormat(pattern);
    	result = formt.format(date);	
    	return  result;
    }  
    
    /**
	 * 把字符串按指定时间格式转换成date类型
	 * @param str
	 * @param srcPattern
	 * @return
	 */
	public static Date formatDate(String str,String srcPattern){
		try{
			if(StringUtils.isEmpty(str))return null;
			SimpleDateFormat df = new SimpleDateFormat(srcPattern);
			Date date = df.parse(str);
			return date;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}	
