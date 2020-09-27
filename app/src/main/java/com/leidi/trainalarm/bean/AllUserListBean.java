package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/5/14
 */
public class AllUserListBean {

    /**
     * msg : 操作成功
     * code : 200
     * data : [{"userName":"admin","userId":1},{"userName":"Louis","userId":2},{"userName":"Iverson6","userId":6},{"userName":"Iverson7","userId":7},{"userName":"Iverson8","userId":8},{"userName":"Iverson9","userId":9},{"userName":"Iverson10","userId":10},{"userName":"110","userId":27},{"userName":"120","userId":31}]
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
         * userName : admin
         * userId : 1
         */

        private String userName;
        private int userId;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}

