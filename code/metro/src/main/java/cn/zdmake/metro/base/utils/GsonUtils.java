package cn.zdmake.metro.base.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class GsonUtils {

	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 创建GSON
	 * @return
	 */
	public static Gson getGson(){
		return new GsonBuilder().serializeNulls().setDateFormat(DATE_FORMAT).disableHtmlEscaping().create();
	}
 
    /**
     * 将对象转化为字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
    	return getGson().toJson(obj);
    }
 
    /**
     * 将字符转化为对象
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String jsonString , Class<T> clazz){
        return getGson().fromJson(jsonString,clazz);
    }
    
    /**
     * 将字符转化为对象
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String jsonString , Type type){
        return getGson().fromJson(jsonString,type);
    }
 
}
