package cn.zdmake.metro.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@SuppressWarnings("rawtypes")
public class MonitorIntervalView {
	private String title;//标题 [二号线-[15标]-公园前区间]监测数据曲线
	private Long intervalSpId;//
	private List<String> dataTime;// ["2014-09-13 19:33:44","2014-09-13 19:33:44","2014-09-13 19:33:44"]	
	private List<List> grandSettlement;//[[-14,-3],[-13,3.0],[-12,5.0]]
	private List<List> speedSettlement;//[[-14,-3],[-13,3.0],[-12,5.0]]
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getIntervalSpId() {
		return intervalSpId;
	}
	public void setIntervalSpId(Long intervalSpId) {
		this.intervalSpId = intervalSpId;
	}
	public List<String> getDataTime() {
		return dataTime;
	}
	public void setDataTime(List<String> dataTime) {
		this.dataTime = dataTime;
	}
	
	public List<List> getGrandSettlement() {
		return grandSettlement;
	}
	public void setGrandSettlement(List<List> grandSettlement) {
		this.grandSettlement = grandSettlement;
	}
	public List<List> getSpeedSettlement() {
		return speedSettlement;
	}
	public void setSpeedSettlement(List<List> speedSettlement) {
		this.speedSettlement = speedSettlement;
	}
	public static void main(String[] args) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		System.out.print(sdf.format(date));
	}
}
