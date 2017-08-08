package cn.zdmake.metro.model;

public class MonitorInfoCity implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8287307440408089951L;
	
	private String lineName;//线路名称
	private Integer lineNo;//线路编号
	private String intervalName;//区间名称
	private Integer intervalMark;//区间标号
	private Integer buildStatus;//工程状态
	private String leftOrRight;//左右线标记
	private Float ringNum;//总环数
	
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Integer getLineNo() {
		return lineNo;
	}
	public void setLineNo(Integer lineNo) {
		this.lineNo = lineNo;
	}
	public Integer getIntervalMark() {
		return intervalMark;
	}
	public void setIntervalMark(Integer intervalMark) {
		this.intervalMark = intervalMark;
	}
	public Integer getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public String getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
	public Float getRingNum() {
		return ringNum;
	}
	public void setRingNum(Float ringNum) {
		this.ringNum = ringNum;
	}
	
}
