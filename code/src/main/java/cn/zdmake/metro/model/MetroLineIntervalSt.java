package cn.zdmake.metro.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 盾尾间隙数据
 * @author wangcan
 *
 */
public class MetroLineIntervalSt implements Serializable {


    private static final long serialVersionUID = 688677392147301799L;

    private Long id;//系统ID
    private Long intervalId; //线路区间id
    private String leftOrRight;//左右线标记
    private String ringNum;//环号
    private Float stUp;//盾尾间隙上
    private Float stDown;//盾尾间隙下
    private Float stLeft;//盾尾间隙左
    private Float stRight;//盾尾间隙右
    private Date dateTime;//日期时间
    private Date updateTime;//更新时间

    public MetroLineIntervalSt() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取intervalId
     * @return intervalId intervalId
     */
    public Long getIntervalId() {
        return intervalId;
    }

    /**
     * 设置intervalId
     * @param intervalId intervalId
     */
    public void setIntervalId(Long intervalId) {
        this.intervalId = intervalId;
    }

    public String getLeftOrRight() {
        return leftOrRight;
    }

    public void setLeftOrRight(String leftOrRight) {
        this.leftOrRight = leftOrRight;
    }

    public String getRingNum() {
        return ringNum;
    }

    public void setRingNum(String ringNum) {
        this.ringNum = ringNum;
    }

    public Float getStUp() {
        return stUp;
    }

    public void setStUp(Float stUp) {
        this.stUp = stUp;
    }

    public Float getStDown() {
        return stDown;
    }

    public void setStDown(Float stDown) {
        this.stDown = stDown;
    }

    public Float getStLeft() {
        return stLeft;
    }

    public void setStLeft(Float stLeft) {
        this.stLeft = stLeft;
    }

    public Float getStRight() {
        return stRight;
    }

    public void setStRight(Float stRight) {
        this.stRight = stRight;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
