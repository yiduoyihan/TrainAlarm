package com.leidi.trainalarm.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.leidi.trainalarm.R;
import com.leidi.trainalarm.bean.MsgBean;
import com.leidi.trainalarm.bean.NotificationEvent;

/**
 * @author peter
 * @date 2018/7/4
 */

public class Notificaitons {

    public final static int NOTIFICATION_ACTION = 1;
    public final static int NOTIFICATION_REMOTE_INPUT = 2;
    public final static int NOTIFICATION_MEDIA_STYLE = 6;
    public final static int NOTIFICATION_MESSAGING_STYLE = 7;
    public final static int NOTIFICATION_CUSTOM_HEADS_UP = 9;
    public final static int NOTIFICATION_CUSTOM = 10;

    public final static String ACTION_MEDIA_STYLE = "com.android.peter.notificationdemo.ACTION_MEDIA_STYLE";
    public final static String ACTION_MESSAGING_STYLE = "com.android.peter.notificationdemo.ACTION_MESSAGING_STYLE";

    private static volatile Notificaitons sInstance = null;

    private Notificaitons() {
    }

    public static Notificaitons getInstance() {
        if (sInstance == null) {
            synchronized (Notificaitons.class) {
                if (sInstance == null) {
                    sInstance = new Notificaitons();
                }
            }
        }

        return sInstance;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sendMessagingStyleNotification(MsgBean message, Context context, NotificationManager nm) {
        NotificationCompat.Style style = new NotificationCompat.MessagingStyle("")
                .addMessage(message.getAlarmMsg(), System.currentTimeMillis(), "您有一条新消息");

        NotificationCompat.Builder builder;
        //Android 8.0以上适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NotificationChannels.CRITICAL, "消息提醒",
                    NotificationManager.IMPORTANCE_HIGH);
            nm.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(context, NotificationChannels.CRITICAL);
        } else {
            builder = new NotificationCompat.Builder(context, NotificationChannels.CRITICAL);
        }
        builder.setContentTitle("您有一条新消息")
                .setContentText(message.getAlarmMsg())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.icon)
                .setStyle(style);

        Notification notification = builder.build();
        nm.notify(NOTIFICATION_MESSAGING_STYLE, notification);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendMediaStyleNotification(Context context, NotificationManager nm, String title, String content, boolean canDelete) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, NotificationService.class);
        intent.setAction(ACTION_MESSAGING_STYLE);
        PendingIntent pi = PendingIntent.getService(context, 0, intent, 0);

        //创建通知
        Notification.Builder nb = new Notification.Builder(context, NotificationChannels.BACKGROUND)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.icon)
                //设置通知标题
                .setContentTitle(title)
                //设置通知内容
                .setContentText(content)
                //设置点击通知后自动删除通知
                .setAutoCancel(!canDelete)
                //不可删除
                .setOngoing(canDelete)
                //设置显示通知时间
//                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi);
        //发送通知
        nm.notify(NOTIFICATION_MEDIA_STYLE, nb.build());

    }

    public void clearAllNotification(NotificationManager nm) {
        nm.cancelAll();
    }

    public void clearOneNotification(NotificationManager nm, int id) {
        nm.cancel(id);
    }

}
