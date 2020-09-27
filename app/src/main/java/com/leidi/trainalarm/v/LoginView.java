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

package com.leidi.trainalarm.v;

/**
 * @author caiwu
 */
public interface LoginView {

    interface Result {
        /**
         * 展示progress
         */
        void showProgress();

        /**
         * 隐藏progress
         */
        void hideProgress();

        /**
         * 登录成功
         */
        void navigateToHome();

        /**
         * 登录失败
         *
         * @param stringId 失败的原因
         */
        void loginFailed(String stringId);
    }

    interface Presenter {
        /**
         * 登录
         *
         * @param username 用户名
         * @param password 密码
         * @param regId    推送标识 设备ID
         * @description 11111
         */
        void login(String username, String password, String regId);


        /**
         * 销毁
         * */
        void onDestroy();
    }
}
