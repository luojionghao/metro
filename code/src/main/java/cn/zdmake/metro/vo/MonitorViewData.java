package cn.zdmake.metro.vo;
/**
 * 城市列表数据VO
 * @author MAJL
 *
 */
public class MonitorViewData {
	private String intervalName;//区间名称
	private String leftOrRight;//左右线标记
	private Integer A0002;//工作状态
	private Float A0001;//当前环数
	private int communiStatus;//通信状态
	private Float totalRingNum;//总环数
	private Float B0006;//总推力
	private Float A0013;//平均土压
	private Float B0004;//刀盘扭矩
	private Float B0001;//推进速度
	private Float B0002;//刀盘转速
	private Float D0015;//注浆总量
	private Integer buildStatus;//工程状态
	public String getIntervalName() {
		return intervalName;
	}
	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}
	public String getLeftOrRight() {
		return leftOrRight;
	}
	public void setLeftOrRight(String leftOrRight) {
		this.leftOrRight = leftOrRight;
	}
	public Integer getA0002() {
		return A0002;
	}
	public void setA0002(Integer a0002) {
		A0002 = a0002;
	}

	public Float getA0001() {
		return A0001;
	}
	public void setA0001(Float a0001) {
		A0001 = a0001;
	}
	public Float getTotalRingNum() {
		return totalRingNum;
	}
	public void setTotalRingNum(Float totalRingNum) {
		this.totalRingNum = totalRingNum;
	}
	public Float getB0006() {
		return B0006;
	}
	public void setB0006(Float b0006) {
		B0006 = b0006;
	}
	public Float getA0013() {
		return A0013;
	}
	public void setA0013(Float a0013) {
		A0013 = a0013;
	}
	public Float getB0004() {
		return B0004;
	}
	public void setB0004(Float b0004) {
		B0004 = b0004;
	}
	public Float getB0001() {
		return B0001;
	}
	public void setB0001(Float b0001) {
		B0001 = b0001;
	}
	public Float getB0002() {
		return B0002;
	}
	public void setB0002(Float b0002) {
		B0002 = b0002;
	}
	public Float getD0015() {
		return D0015;
	}
	public void setD0015(Float d0015) {
		D0015 = d0015;
	}
	public Integer getBuildStatus() {
		return buildStatus;
	}
	public void setBuildStatus(Integer buildStatus) {
		this.buildStatus = buildStatus;
	}

	public int getCommuniStatus() {
		return communiStatus;
	}

	public void setCommuniStatus(int communiStatus) {
		this.communiStatus = communiStatus;
	}
}
