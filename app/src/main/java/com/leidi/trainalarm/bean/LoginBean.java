package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/6/30
 * @description
 */
public class LoginBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : {"userInfo":{"id":3,"createBy":"admin","createTime":"2018-08-14 00:00:00","lastUpdateBy":"Kobe","lastUpdateTime":"2020-06-29 16:39:03","employeeName":null,"userName":"Kobe","password":"","salt":"","email":"kobe@qq.com","mobile":"18200932238","status":1,"deptId":7,"deptName":null,"roleNames":null,"userRoles":[]},"token":"tokenuuuuu3"}
     */

    private String msg;
    private int code;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userInfo : {"id":3,"createBy":"admin","createTime":"2018-08-14 00:00:00","lastUpdateBy":"Kobe","lastUpdateTime":"2020-06-29 16:39:03","employeeName":null,"userName":"Kobe","password":"","salt":"","email":"kobe@qq.com","mobile":"18200932238","status":1,"deptId":7,"deptName":null,"roleNames":null,"userRoles":[]}
         * token : tokenuuuuu3
         */

        private UserInfoBean userInfo;
        private String token;
        private String mpushServerIp;

        public String getMpushServerIp() {
            return mpushServerIp;
        }

        public void setMpushServerIp(String mpushServerIp) {
            this.mpushServerIp = mpushServerIp;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class UserInfoBean {
            /**
             * id : 3
             * createBy : admin
             * createTime : 2018-08-14 00:00:00
             * lastUpdateBy : Kobe
             * lastUpdateTime : 2020-06-29 16:39:03
             * employeeName : null
             * userName : Kobe
             * password :
             * salt :
             * email : kobe@qq.com
             * mobile : 18200932238
             * status : 1
             * deptId : 7
             * deptName : null
             * roleNames : null
             * userRoles : []
             */

            private int id;
            private String createBy;
            private String createTime;
            private String lastUpdateBy;
            private String lastUpdateTime;
            private String employeeName;
            private String userName;
            private String password;
            private String salt;
            private String email;
            private String mobile;
            private int status;
            private int deptId;
            private String deptName;
            private String roleNames;
            private List<?> userRoles;
            private String roleId;

            public String getRoleId() {
                return roleId;
            }

            public void setRoleId(String roleId) {
                this.roleId = roleId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCreateBy() {
                return createBy;
            }

            public void setCreateBy(String createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getLastUpdateBy() {
                return lastUpdateBy;
            }

            public void setLastUpdateBy(String lastUpdateBy) {
                this.lastUpdateBy = lastUpdateBy;
            }

            public String getLastUpdateTime() {
                return lastUpdateTime;
            }

            public void setLastUpdateTime(String lastUpdateTime) {
                this.lastUpdateTime = lastUpdateTime;
            }

            public String getEmployeeName() {
                return employeeName;
            }

            public void setEmployeeName(String employeeName) {
                this.employeeName = employeeName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getSalt() {
                return salt;
            }

            public void setSalt(String salt) {
                this.salt = salt;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getDeptId() {
                return deptId;
            }

            public void setDeptId(int deptId) {
                this.deptId = deptId;
            }

            public String getDeptName() {
                return deptName;
            }

            public void setDeptName(String deptName) {
                this.deptName = deptName;
            }

            public String getRoleNames() {
                return roleNames;
            }

            public void setRoleNames(String roleNames) {
                this.roleNames = roleNames;
            }

            public List<?> getUserRoles() {
                return userRoles;
            }

            public void setUserRoles(List<?> userRoles) {
                this.userRoles = userRoles;
            }
        }
    }
}
