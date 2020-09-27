package com.leidi.trainalarm.bean;

/**
 * @author 阎
 * @date 2020/5/14
 */
public class MainBean {


    /**
     * content : 请马上下道避让，列车即将驶来。
     * msgId : e0bf6afefe86db371595400524477
     * msgType : 2
     */

    private String content;
    private String msgId;
    private int msgType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }
}

