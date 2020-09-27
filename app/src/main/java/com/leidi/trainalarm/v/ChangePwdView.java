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

public interface ChangePwdView {

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
         * 请求成功
         */
        void requestSuccess();

        /**
         * 请求失败
         *
         * @param stringId 失败的原因
         */
        void requestFailed(String stringId);
    }

    interface Presenter {
        /**
         * 登录
         *
         * @param oldPwd 旧密码
         * @param newPWd 新密码
         * @description 11111
         */
        void submitMessage(String oldPwd, String newPWd);

        /**
         * 将View在此处赋值null 避免内存泄漏
         */
        void onDestory();
    }
}
