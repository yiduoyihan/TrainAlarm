package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/9/11
 * @description
 */
 public  class AlarmSetBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : [{"alarmSettingId":108,"userId":1,"userName":"admin","firstAlarm":"4","secondAlarm":"6","thirdAlarm":"9","relieveAlarm":"1","createTime":"2020-09-03 11:27:28","updateTime":null,"delFlag":1,"orderNum":0,"spareColumn1":null,"spareColumn2":null,"spareColumn3":null,"spareColumn4":null,"spareColumn5":null,"createBy":"admin","updateBy":null,"alarmLevel":null,"userList":null}]
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
         * alarmSettingId : 108
         * userId : 1
         * userName : admin
         * firstAlarm : 4
         * secondAlarm : 6
         * thirdAlarm : 9
         * relieveAlarm : 1
         * createTime : 2020-09-03 11:27:28
         * updateTime : null
         * delFlag : 1
         * orderNum : 0
         * spareColumn1 : null
         * spareColumn2 : null
         * spareColumn3 : null
         * spareColumn4 : null
         * spareColumn5 : null
         * createBy : admin
         * updateBy : null
         * alarmLevel : null
         * userList : null
         */

        private int alarmSettingId;
        private int userId;
        private String userName;
        private String firstAlarm;
        private String secondAlarm;
        private String thirdAlarm;
        private String relieveAlarm;
        private String createTime;
        private Object updateTime;
        private int delFlag;
        private int orderNum;
        private Object spareColumn1;
        private Object spareColumn2;
        private Object spareColumn3;
        private Object spareColumn4;
        private Object spareColumn5;
        private String createBy;
        private Object updateBy;
        private Object alarmLevel;
        private Object userList;

        public int getAlarmSettingId() {
            return alarmSettingId;
        }

        public void setAlarmSettingId(int alarmSettingId) {
            this.alarmSettingId = alarmSettingId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
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

        public String getThirdAlarm() {
            return thirdAlarm;
        }

        public void setThirdAlarm(String thirdAlarm) {
            this.thirdAlarm = thirdAlarm;
        }

        public String getRelieveAlarm() {
            return relieveAlarm;
        }

        public void setRelieveAlarm(String relieveAlarm) {
            this.relieveAlarm = relieveAlarm;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
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

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getAlarmLevel() {
            return alarmLevel;
        }

        public void setAlarmLevel(Object alarmLevel) {
            this.alarmLevel = alarmLevel;
        }

        public Object getUserList() {
            return userList;
        }

        public void setUserList(Object userList) {
            this.userList = userList;
        }
    }
}
