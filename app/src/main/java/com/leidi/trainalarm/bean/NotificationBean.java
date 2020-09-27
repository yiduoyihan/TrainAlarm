package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/5/14
 */
public class NotificationBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : [{"alarmInfoId":6,"alarmContent":"请马上下道避让，列车即将驶来。","deviceNo":"e0bf6afefe86db37","alarmReceiverId":1,"alarmReceiverName":"admin","alarmMileage":"50","longitude":null,"latitude":null,"createTime":"2020-07-06 14:36:04","callbackTime":null,"alarmCallback":0,"remark":null,"spareColumn1":null,"spareColumn2":null,"spareColumn3":null,"spareColumn4":null,"spareColumn5":null,"alarmLevel":"1","deviceMileage":"167.73362223979814","railwaylineId":2,"railwaylineNo":"L001","deviceLongitude":"108.829413","deviceLatitude":"34.212407","alarmDistance":"3","trainNo":null,"direction":1,"directionName":"上行","delFlag":1}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * alarmInfoId : 6
         * alarmContent : 请马上下道避让，列车即将驶来。
         * deviceNo : e0bf6afefe86db37
         * alarmReceiverId : 1
         * alarmReceiverName : admin
         * alarmMileage : 50
         * longitude : null
         * latitude : null
         * createTime : 2020-07-06 14:36:04
         * callbackTime : null
         * alarmCallback : 0
         * remark : null
         * spareColumn1 : null
         * spareColumn2 : null
         * spareColumn3 : null
         * spareColumn4 : null
         * spareColumn5 : null
         * alarmLevel : 1
         * deviceMileage : 167.73362223979814
         * railwaylineId : 2
         * railwaylineNo : L001
         * deviceLongitude : 108.829413
         * deviceLatitude : 34.212407
         * alarmDistance : 3
         * trainNo : null
         * direction : 1
         * directionName : 上行
         * delFlag : 1
         */

        private int alarmInfoId;
        private String alarmContent;
        private String deviceNo;
        private int alarmReceiverId;
        private String alarmReceiverName;
        private String alarmMileage;
        private Object longitude;
        private Object latitude;
        private String createTime;
        private Object callbackTime;
        private int alarmCallback;
        private Object remark;
        private Object spareColumn1;
        private Object spareColumn2;
        private Object spareColumn3;
        private Object spareColumn4;
        private Object spareColumn5;
        private String alarmLevel;
        private String deviceMileage;
        private int railwaylineId;
        private String railwaylineNo;
        private String deviceLongitude;
        private String deviceLatitude;
        private String alarmDistance;
        private Object trainNo;
        private int direction;
        private String directionName;
        private int delFlag;

        public int getAlarmInfoId() {
            return alarmInfoId;
        }

        public void setAlarmInfoId(int alarmInfoId) {
            this.alarmInfoId = alarmInfoId;
        }

        public String getAlarmContent() {
            return alarmContent;
        }

        public void setAlarmContent(String alarmContent) {
            this.alarmContent = alarmContent;
        }

        public String getDeviceNo() {
            return deviceNo;
        }

        public void setDeviceNo(String deviceNo) {
            this.deviceNo = deviceNo;
        }

        public int getAlarmReceiverId() {
            return alarmReceiverId;
        }

        public void setAlarmReceiverId(int alarmReceiverId) {
            this.alarmReceiverId = alarmReceiverId;
        }

        public String getAlarmReceiverName() {
            return alarmReceiverName;
        }

        public void setAlarmReceiverName(String alarmReceiverName) {
            this.alarmReceiverName = alarmReceiverName;
        }

        public String getAlarmMileage() {
            return alarmMileage;
        }

        public void setAlarmMileage(String alarmMileage) {
            this.alarmMileage = alarmMileage;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getCallbackTime() {
            return callbackTime;
        }

        public void setCallbackTime(Object callbackTime) {
            this.callbackTime = callbackTime;
        }

        public int getAlarmCallback() {
            return alarmCallback;
        }

        public void setAlarmCallback(int alarmCallback) {
            this.alarmCallback = alarmCallback;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getSpareColumn1() {
            return spareColumn1;
        }

        public void setSpareColumn1(Object spareColumn1) {
            this.spareColumn1 = spareColumn1;
        }

        public Object getSpareColumn2() {
            return spareColumn2;
        }

        public void setSpareColumn2(Object spareColumn2) {
            this.spareColumn2 = spareColumn2;
        }

        public Object getSpareColumn3() {
            return spareColumn3;
        }

        public void setSpareColumn3(Object spareColumn3) {
            this.spareColumn3 = spareColumn3;
        }

        public Object getSpareColumn4() {
            return spareColumn4;
        }

        public void setSpareColumn4(Object spareColumn4) {
            this.spareColumn4 = spareColumn4;
        }

        public Object getSpareColumn5() {
            return spareColumn5;
        }

        public void setSpareColumn5(Object spareColumn5) {
            this.spareColumn5 = spareColumn5;
        }

        public String getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(String alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public String getDeviceMileage() {
            return deviceMileage;
        }

        public void setDeviceMileage(String deviceMileage) {
            this.deviceMileage = deviceMileage;
        }

        public int getRailwaylineId() {
            return railwaylineId;
        }

        public void setRailwaylineId(int railwaylineId) {
            this.railwaylineId = railwaylineId;
        }

        public String getRailwaylineNo() {
            return railwaylineNo;
        }

        public void setRailwaylineNo(String railwaylineNo) {
            this.railwaylineNo = railwaylineNo;
        }

        public String getDeviceLongitude() {
            return deviceLongitude;
        }

        public void setDeviceLongitude(String deviceLongitude) {
            this.deviceLongitude = deviceLongitude;
        }

        public String getDeviceLatitude() {
            return deviceLatitude;
        }

        public void setDeviceLatitude(String deviceLatitude) {
            this.deviceLatitude = deviceLatitude;
        }

        public String getAlarmDistance() {
            return alarmDistance;
        }

        public void setAlarmDistance(String alarmDistance) {
            this.alarmDistance = alarmDistance;
        }

        public Object getTrainNo() {
            return trainNo;
        }

        public void setTrainNo(Object trainNo) {
            this.trainNo = trainNo;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public String getDirectionName() {
            return directionName;
        }

        public void setDirectionName(String directionName) {
            this.directionName = directionName;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }
    }
}

