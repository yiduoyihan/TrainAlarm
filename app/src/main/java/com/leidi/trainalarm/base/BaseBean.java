package com.leidi.trainalarm.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 *
 * @author yan
 *
 */

public class BaseBean {


    /**
     * msg : 退出登录成功
     * code : 200
     */

    private String msg;
    private int code;

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


}
