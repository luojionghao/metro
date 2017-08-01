package cn.zdmake.metro.base.utils;

import cn.zdmake.metro.model.MonitorInfoCity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StringUtil {
	public static int nullToInt(String val){
		if(val==null||"".equals(val)){
			return 0;
		}else{
			return Integer.parseInt(val);
		}
	}
	
	public static double nullToDouble(String val){
		if(val==null||"".equals(val)){
			return 0.0;
		}else{
			return Double.parseDouble(val);
		}
	}

	public static float nullToFloat(String val){
		if(val==null||"".equals(val)){
			return 0;
		}else{
			return Float.parseFloat(val);
		}
	}
	
	public static Date stringToDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String timeToString(String date){
		Date date1=new Date();
		long time = date1.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(date);
		Date d = new Date(lt*1000);
		return sdf.format(d);
	}
	
	public static String nullToString(String str){
		return str!=null&&!"".equals(str)?str:" ";
	}
	
	public static String timeCnToEn(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date d = sdf.parse(date);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (NullPointerException n){
			n.printStackTrace();
		}
		return "09/26/2016";
	}
	public static String timeCnToEnAddOne(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			Date d = sdf.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			cal.add(Calendar.DATE, 1);
			sdf = new SimpleDateFormat("MM/dd/yyyy");
			return sdf.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}catch (NullPointerException n){
			n.printStackTrace();
		}
		return "09/26/2016";
	}

	public static Map<String,Object> getMapAndMileage(Map<String,Object> map, MonitorInfoCity mic, String mapX, String mapY, String mapZ, String mileage){

		mapX = "GZ"+mic.getLineNo()+"_"+mic.getIntervalMark()+mic.getLeftOrRight().toUpperCase()+"."+mapX+".F_CV";
		mapY = "GZ"+mic.getLineNo()+"_"+mic.getIntervalMark()+mic.getLeftOrRight().toUpperCase()+"."+mapY+".F_CV";
		mapZ = "GZ"+mic.getLineNo()+"_"+mic.getIntervalMark()+mic.getLeftOrRight().toUpperCase()+"."+mapZ+".F_CV";
		mileage = "GZ"+mic.getLineNo()+"_"+mic.getIntervalMark()+mic.getLeftOrRight().toUpperCase()+"."+mileage+".F_CV";

		if(map.get(mileage) == null){
			return null;
		}

		Map<String,Object> maps = new HashMap<>();
		maps.put("mapX",map.get(mapX));
		maps.put("mapY",map.get(mapY));
		maps.put("mapZ",map.get(mapZ));
		maps.put("mileage",map.get(mileage));
		return maps;
	}
}
