package com.leidi.trainalarm.bean;

/**
 * @author 阎
 * @date 2020/5/14
 */
public class EventBean {


    /**
     * content : {"msg":"列车据您已不足8公里，请进行及时下道避让。","type":"alarm"}
     * msgId : android_4bf2e46ec89c38c71596769966233
     * msgType : 3
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

