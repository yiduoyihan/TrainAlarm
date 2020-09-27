package com.leidi.trainalarm.notification;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.leidi.trainalarm.ui.login.LoginActivity;
import com.leidi.trainalarm.ui.main.MainActivity;
import com.leidi.trainalarm.util.Constant;

import java.util.ArrayList;
import java.util.List;

import static com.leidi.trainalarm.notification.Notificaitons.ACTION_MEDIA_STYLE;
import static com.leidi.trainalarm.notification.Notificaitons.ACTION_MESSAGING_STYLE;


/**
 * @author peter
 * @date 2018/6/28
 */

public class NotificationService extends Service {
    private final static String TAG = "NotificationService";

    public final static String ACTION_SEND_PROGRESS_NOTIFICATION = "com.android.peter.notificationdemo.ACTION_SEND_PROGRESS_NOTIFICATION";

    private Context mContext;
    private NotificationManager mNM;
    private boolean mIsLoved;
    private boolean mIsPlaying = true;

    private List<NotificationContentWrapper> mContent = new ArrayList<>();
    private int mCurrentPosition = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        initNotificationContent();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            Log.i(TAG, "onStartCommand action = " + intent.getAction());
            switch (intent.getAction()) {
                case ACTION_MEDIA_STYLE:
                    dealWithActionMediaStyle(intent);
                    break;
                case ACTION_MESSAGING_STYLE:
                    if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN_SUCCESS, false)) {
                        intent.setClass(this, MainActivity.class);
                    } else {
                        intent.setClass(this, LoginActivity.class);
                    }
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    startActivity(intent);
                    break;
                case ACTION_SEND_PROGRESS_NOTIFICATION:
                    dealWithActionSendProgressNotification();
                    break;
                default:
                    //do nothing
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initNotificationContent() {
        mContent.clear();
    }

    private void dealWithActionMediaStyle(Intent intent) {

        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN_SUCCESS, false)) {
            intent.setClass(this, LoginActivity.class);
        } else {
            intent.setClass(this, MainActivity.class);
        }
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
//        String option = intent.getStringExtra(EXTRA_OPTIONS);
//        Log.i(TAG, "media option = " + option);
//        if (option == null) {
//            return;
//        }
//        switch (option) {
//            case MEDIA_STYLE_ACTION_PAUSE:
////                Notificaitons.getInstance().sendMediaStyleNotification(this,mNM,false);
//                break;
//            case MEDIA_STYLE_ACTION_PLAY:
////                Notificaitons.getInstance().sendMediaStyleNotification(this,mNM,true);
//                break;
//            case MEDIA_STYLE_ACTION_NEXT:
//                break;
//            case MEDIA_STYLE_ACTION_DELETE:
//                mNM.cancel(NOTIFICATION_MEDIA_STYLE);
//                break;
//            default:
//                //do nothing
//        }
    }

    private void dealWithActionReplay(Intent intent) {
//        Bundle result = RemoteInput.getResultsFromIntent(intent);
//        if (result != null) {
//            String content = result.getString(REMOTE_INPUT_RESULT_KEY);
//            Log.i(TAG, "content = " + content);
//            mNM.cancel(NOTIFICATION_REMOTE_INPUT);
//        }
    }

    private void dealWithActionSendProgressNotification() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
//                    Notificaitons.getInstance().sendProgressViewNotification(mContext,mNM,i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void dealWithActionCustomHeadsUpView(Intent intent) {
//        String headsUpOption = intent.getStringExtra(EXTRA_OPTIONS);
//        Log.i(TAG, "heads up option = " + headsUpOption);
//        if (headsUpOption == null) {
//            return;
//        }
//        switch (headsUpOption) {
//            case ACTION_ANSWER:
//                mNM.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
//                break;
//            case ACTION_REJECT:
//                mNM.cancel(NOTIFICATION_CUSTOM_HEADS_UP);
//                break;
//            default:
//                //do nothing
//        }
    }

    private NotificationContentWrapper getNotificationContent() {
        switch (mCurrentPosition) {
            case -1:
                mCurrentPosition = 2;
                break;
            case 3:
                mCurrentPosition = 0;
                break;
            default:
                // do nothing
        }
        return mContent.get(mCurrentPosition);
    }
}
