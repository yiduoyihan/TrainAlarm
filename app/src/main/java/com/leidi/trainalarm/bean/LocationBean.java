package com.leidi.trainalarm.bean;

/**
 * @author 阎
 * @date 2020/5/14
 */
public class LocationBean {


    private float speed;
    private float accuracy;
    private String pickPointTime;
    private String latitude;
    private String longitude;

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public String getPickPointTime() {
        return pickPointTime;
    }

    public void setPickPointTime(String pickPointTime) {
        this.pickPointTime = pickPointTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

