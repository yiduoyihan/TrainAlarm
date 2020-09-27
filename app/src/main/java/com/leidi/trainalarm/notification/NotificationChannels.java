package com.leidi.trainalarm.notification;

/**
 * Created by peter on 2018/6/27.
 */

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.leidi.trainalarm.R;

import java.util.Arrays;

/**
 * Create notification channel
 *
 * @author peter
 */

public class NotificationChannels {
    public final static String CRITICAL = "critical";
    public final static String BACKGROUND = "background";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createAllNotificationChannels(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (nm == null) {
            return;
        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel critical = new NotificationChannel(
                    CRITICAL,
                    context.getString(R.string.channel_critical),
                    NotificationManager.IMPORTANCE_HIGH);
            critical.enableVibration(true);

            NotificationChannel bg = new NotificationChannel(
                    BACKGROUND,
                    context.getString(R.string.background),
                    NotificationManager.IMPORTANCE_HIGH );
            bg.enableVibration(true);

            nm.createNotificationChannels(Arrays.asList(critical, bg));
        }
    }
}
