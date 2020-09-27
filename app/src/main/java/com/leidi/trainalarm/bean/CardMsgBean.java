package com.leidi.trainalarm.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author 阎
 * @date 2020/9/15
 * @description
 */
//,Comparable<CardMsgBean>
public class CardMsgBean implements MultiItemEntity{

    /**
     * stationNext : 中软
     * distance : 7.04
     * num : aw13
     * name : aw13
     * isNearPeople : true
     * location : 0.27777777777777773
     * time : 2020-09-15 16:26:09
     * actions : add
     * speed : 200
     * stationLast : 太平站
     * direction : 1
     */

    private String stationNext;
    private String distance;
    private String num;
    private String name;
    private String isNearPeople;
    private String location;
    private String time;
    private String actions;
    private String speed;
    private String stationLast;
    private String direction;

    private boolean isHasTitle;
    private String firstAlarm;
    private String secondAlarm;
    private String thiredAlarm;

    private int point1;
    private int line1;
    private int point2;
    private int line2;
    private int point3;
    private int line3;

    private int alarmLv;//告警级别

    public static final int ITEM_UP = 1;
    public static final int ITEM_DOWN = 2;
    private int itemType;

    private float startWeight;
    private float endWeight;


    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public float getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(float startWeight) {
        this.startWeight = startWeight;
    }

    public float getEndWeight() {
        return endWeight;
    }

    public void setEndWeight(float endWeight) {
        this.endWeight = endWeight;
    }

    public boolean isHasTitle() {
        return isHasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        isHasTitle = hasTitle;
    }

    public String getFirstAlarm() {
        return firstAlarm;
    }

    public void setFirstAlarm(String firstAlarm) {
        this.firstAlarm = firstAlarm;
    }

    public String getSecondAlarm() {
        return secondAlarm;
    }

    public void setSecondAlarm(String secondAlarm) {
        this.secondAlarm = secondAlarm;
    }

    public String getThiredAlarm() {
        return thiredAlarm;
    }

    public void setThiredAlarm(String thiredAlarm) {
        this.thiredAlarm = thiredAlarm;
    }

    public int getPoint1() {
        return point1;
    }

    public void setPoint1(int point1) {
        this.point1 = point1;
    }

    public int getLine1() {
        return line1;
    }

    public void setLine1(int line1) {
        this.line1 = line1;
    }

    public int getPoint2() {
        return point2;
    }

    public void setPoint2(int point2) {
        this.point2 = point2;
    }

    public int getLine2() {
        return line2;
    }

    public void setLine2(int line2) {
        this.line2 = line2;
    }

    public int getPoint3() {
        return point3;
    }

    public void setPoint3(int point3) {
        this.point3 = point3;
    }

    public int getLine3() {
        return line3;
    }

    public void setLine3(int line3) {
        this.line3 = line3;
    }

    public int getAlarmLv() {
        return alarmLv;
    }

    public void setAlarmLv(int alarmLv) {
        this.alarmLv = alarmLv;
    }

    public String getStationNext() {
        return stationNext;
    }

    public void setStationNext(String stationNext) {
        this.stationNext = stationNext;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsNearPeople() {
        return isNearPeople;
    }

    public void setIsNearPeople(String isNearPeople) {
        this.isNearPeople = isNearPeople;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getStationLast() {
        return stationLast;
    }

    public void setStationLast(String stationLast) {
        this.stationLast = stationLast;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

//    @Override
//    public int compareTo(CardMsgBean o) {
//        return this.distance.compareTo(o.getDistance());
//    }
}
