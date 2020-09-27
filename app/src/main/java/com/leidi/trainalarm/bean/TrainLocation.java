package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/8/7
 * @description
 */
public class TrainLocation {
    /**
     * msg : [{"actions":"del","type":"2","num":"3699","name":"3699","time":"2020-08-10 15:39:04"}]
     * type : train
     */

    private String type;
   private String msg;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
