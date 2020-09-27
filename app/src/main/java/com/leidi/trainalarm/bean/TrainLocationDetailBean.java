package com.leidi.trainalarm.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author 阎
 * @date 2020/7/23
 * @description
 */
public class TrainLocationDetailBean {

    /**
     * actions : del
     * type : 2
     * num : 3699
     * name : 3699
     * time : 2020-08-10 15:39:04
     */

    private String actions;
    private String type;
    private String num;
    private String name;
    private String time;
    private String lat;
    private String lon;
    private String speed;

    private String location;
    private String distance;

    private String stationNext;
    private String stationLast;
    private String direction;//1上行2下行


    public String getStationNext() {
        return stationNext;
    }

    public void setStationNext(String stationNext) {
        this.stationNext = stationNext;
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


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
