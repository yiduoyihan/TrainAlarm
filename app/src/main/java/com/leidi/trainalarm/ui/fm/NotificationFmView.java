/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.leidi.trainalarm.ui.fm;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.AlarmSetBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.bean.RailWayBean;

/**
 * @author caiwu
 */
public interface NotificationFmView {

    interface Result {
        /**
         * 获取铁路线数据成功
         *
         * @param bean 封装的数据bean
         */
        void addRailWayOnMap(RailWayBean bean);

        void getAlarmSettingSuccess(AlarmSetBean bean);

        /**
         * 判断token过期时候推出到登录页面
         */
        void exitApp();

        /**
         * 所有请求失败时候的回调方法
         *
         * @param string 失败的原因，包括code和msg
         */
        void requestFailed(String string);

    }

    interface Presenter {
        /**
         * 获取铁路线的信息
         *
         * @param token    token
         * @param deviceId 设备ID
         * @param context  上下文（用来添加到网络请求的life中）
         */
        void getRailWay(String token, String deviceId, Fragment context);

        void getAlarmSet(String token, String deviceId, Fragment context);

        /**
         * 上传定位数据
         *
         * @param lat    lat
         * @param lng    lng
         * @param time   定位时间
         * @param speed  速度
         * @param radius 角度
         */
        void uploadLocation(double lat, double lng, String time, float radius, float speed);

        /**
         * 上传记录在本地的一些定位信息
         */
        void uploadHistoryLocation();

        /**
         * 清空关联
         */
        void onDestroy();
    }
}
