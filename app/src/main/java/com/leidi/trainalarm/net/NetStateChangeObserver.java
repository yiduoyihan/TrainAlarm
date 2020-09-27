package com.leidi.trainalarm.net;

/**
 * @author 阎
 * @date 2020/8/4
 * @description
 */
public interface NetStateChangeObserver {
    void onNetDisconnected();
    void onNetConnected(NetworkType networkType);
}
