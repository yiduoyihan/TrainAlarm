package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/6/30
 * @description
 */
public class HomeListBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : [{"id":17,"createBy":"admin","createTime":"2020-07-16 00:00:00","lastUpdateBy":null,"lastUpdateTime":null,"noticeTitle":"测试2","noticeType":null,"noticeContent":"<h1><strong style=\"background-color: rgb(255, 255, 0); color: rgb(230, 0, 0);\"><em><s><u>测试2<\/u><\/s><\/em><\/strong><strong style=\"color: rgb(230, 0, 0);\"><em><s><u><span class=\"ql-cursor\">�<\/span><\/u><\/s><\/em><\/strong><\/h1>","delFlag":1,"status":1,"remark":null,"roleId":0,"deptId":0,"roleName":"全部","deptName":"全部"}]
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
         * id : 17
         * createBy : admin
         * createTime : 2020-07-16 00:00:00
         * lastUpdateBy : null
         * lastUpdateTime : null
         * noticeTitle : 测试2
         * noticeType : null
         * noticeContent : <h1><strong style="background-color: rgb(255, 255, 0); color: rgb(230, 0, 0);"><em><s><u>测试2</u></s></em></strong><strong style="color: rgb(230, 0, 0);"><em><s><u><span class="ql-cursor">�</span></u></s></em></strong></h1>
         * delFlag : 1
         * status : 1
         * remark : null
         * roleId : 0
         * deptId : 0
         * roleName : 全部
         * deptName : 全部
         */

        private int id;
        private String createBy;
        private String createTime;
        private Object lastUpdateBy;
        private Object lastUpdateTime;
        private String noticeTitle;
        private Object noticeType;
        private String noticeContent;
        private int delFlag;
        private int status;
        private Object remark;
        private int roleId;
        private int deptId;
        private String roleName;
        private String deptName;

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

        public Object getLastUpdateBy() {
            return lastUpdateBy;
        }

        public void setLastUpdateBy(Object lastUpdateBy) {
            this.lastUpdateBy = lastUpdateBy;
        }

        public Object getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Object lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }

        public String getNoticeTitle() {
            return noticeTitle;
        }

        public void setNoticeTitle(String noticeTitle) {
            this.noticeTitle = noticeTitle;
        }

        public Object getNoticeType() {
            return noticeType;
        }

        public void setNoticeType(Object noticeType) {
            this.noticeType = noticeType;
        }

        public String getNoticeContent() {
            return noticeContent;
        }

        public void setNoticeContent(String noticeContent) {
            this.noticeContent = noticeContent;
        }

        public int getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(int delFlag) {
            this.delFlag = delFlag;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }
    }
}
