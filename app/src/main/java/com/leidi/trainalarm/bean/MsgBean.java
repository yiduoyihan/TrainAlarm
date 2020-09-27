package com.leidi.trainalarm.bean;

/**
 * @author 阎
 * @date 2020/9/9
 * @description
 */
public class MsgBean {


    /**
     * alarmMsg : 下行aa14次列车据您还有9公里
     * lineNo : L006
     * distance : 9
     * action : add
     * trainMileage : 0
     * trainNo : aa14
     * alarmLevel : 3
     * direction : 2
     */

    private String alarmMsg;
    private String lineNo;
    private String distance;
    private String action;
    private String trainMileage;
    private String trainNo;
    private String alarmLevel;
    private String direction;

    public String getAlarmMsg() {
        return alarmMsg;
    }

    public void setAlarmMsg(String alarmMsg) {
        this.alarmMsg = alarmMsg;
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTrainMileage() {
        return trainMileage;
    }

    public void setTrainMileage(String trainMileage) {
        this.trainMileage = trainMileage;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public void setTrainNo(String trainNo) {
        this.trainNo = trainNo;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
