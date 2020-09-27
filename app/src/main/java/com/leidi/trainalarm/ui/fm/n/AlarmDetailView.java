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

package com.leidi.trainalarm.ui.fm.n;

import android.app.Activity;
import android.content.Context;

import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.ui.fm.NotificationFragment;

/**
 * @author caiwu
 */
public interface AlarmDetailView {

    interface Result {

        void getListSuccess(NotificationBean bean);

        void getListFailed(String string);

        void deleteSuccess(BaseBean bean, int position);

        void deleteFailed(String string);

    }

    interface Presenter {
        //获取列表的数据
        void getListData(int pageSize, int pageNum, int regId, Activity fragment);

        void deleteItem(int alarmInfoId, int position, Activity fragment);
        /**
         * 销毁
         */
        void onDestroy();
    }
}
