package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/6/30
 * @description
 */
public class UserListBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : [{"id":17,"createBy":"admin","createTime":"2020-07-16 00:00:00","lastUpdateBy":null,"lastUpdateTime":null,"noticeTitle":"测试2","noticeType":null,"noticeContent":"<h1><strong style=\"background-color: rgb(255, 255, 0); color: rgb(230, 0, 0);\"><em><s><u>测试2<\/u><\/s><\/em><\/strong><strong style=\"color: rgb(230, 0, 0);\"><em><s><u><span class=\"ql-cursor\">�<\/span><\/u><\/s><\/em><\/strong><\/h1>","delFlag":1,"status":1,"remark":null,"roleId":0,"deptId":0,"roleName":"全部","deptName":"全部"}]
     */

    private String msg;
    private int code;
    private List<UserBean> data;

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

    public List<UserBean> getData() {
        return data;
    }

    public void setData(List<UserBean> data) {
        this.data = data;
    }

    public static class UserBean {
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        private String age;


    }
}
