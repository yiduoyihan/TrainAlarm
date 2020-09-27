package com.leidi.trainalarm.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class ToastUtil {

    private static Toast toast;

    private static StringBuilder builder = new StringBuilder();
    /**参数为字符串*/
    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
    /**参数为String类id*/
    public static void showToast(Context context,
                                 int stringId) {
//        if (toast == null) {
//            toast = Toast.makeText(context,
//                    context.getResources().getString(stringId),
//                    Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(context.getResources().getString(stringId));
//        }
//        toast.show();
    }
    /**参数长度不定*/
    public static void showToast(Context context,
                                 String... arg) {
        for (String str :arg){
            builder.append(str);
        }
        if (toast == null) {
            toast = Toast.makeText(context,
                    builder,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(builder);
        }
        toast.show();
        builder.delete(0,builder.length());
    }

}
