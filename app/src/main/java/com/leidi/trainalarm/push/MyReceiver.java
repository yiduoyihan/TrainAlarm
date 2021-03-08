package com.leidi.trainalarm.push;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leidi.trainalarm.android.MPush;
import com.leidi.trainalarm.android.MPushService;
import com.leidi.trainalarm.bean.CardMsgBean;
import com.leidi.trainalarm.bean.EventBean;
import com.leidi.trainalarm.bean.HomeEvent;
import com.leidi.trainalarm.bean.MsgBean;
import com.leidi.trainalarm.bean.ReceiverBean;
import com.leidi.trainalarm.bean.TrainLocation;
import com.leidi.trainalarm.bean.TrainLocationDetailBean;
import com.leidi.trainalarm.notification.Notificaitons;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.Constant;
import com.mpush.api.Constants;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

/**
 * 接收到推送消息后的处理
 *
 * @author caiwu
 */
public class MyReceiver extends BroadcastReceiver {

    private NotificationManager mNM;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
//        if (mNM == null) {
//            mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        }

        if (MPushService.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            byte[] bytes = intent.getByteArrayExtra(MPushService.EXTRA_PUSH_MESSAGE);
            int messageId = intent.getIntExtra(MPushService.EXTRA_PUSH_MESSAGE_ID, 0);
            String message = new String(bytes, Constants.UTF_8);
            EventBean eventBean = new Gson().fromJson(message, EventBean.class);
            ReceiverBean bean = new Gson().fromJson(eventBean.getContent(), ReceiverBean.class);
            //告警和通知区分
            System.out.println("=====MyReceiver=====" + message);
            //notice alarm
            switch (bean.getType()) {
                case Constant.MSG_HOME_REFUSH:
                    MsgBean bean1 = new Gson().fromJson(bean.getMsg(),MsgBean.class);
                    //发送数据到NotificationFragment中
                    //EventBus.getDefault().post(notificationEvent);
                    //弹出系统通知
                    Notificaitons.getInstance().sendMessagingStyleNotification(
                            bean1, context, (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
                    //发送到BaseActivity页面弹出提醒框
                    EventBus.getDefault().post(bean);
                    AppUtil.print("收到新的告警：" + bean.getMsg() + "=====" + messageId);
                    break;
                case Constant.MSG_NOTIFICATION:
                    //发送到消息页刷新列表
                    EventBus.getDefault().post(new HomeEvent());
                    //发送到BaseActivity页面弹出提醒框
                    //并且弹框
                    bean.setMsg("您收一条新的公告请及时查看");
                    bean.setType("notice");
                    EventBus.getDefault().post(bean);
                    AppUtil.print("收到新的公告：" + bean.getMsg() + "=====" + messageId);
                    break;
                case Constant.MSG_TRAIN_LOCATION:
//                    List<MsgDetailBean> beanList = new Gson().fromJson(bean.getMsg(), new TypeToken<List<MsgDetailBean>>() {
//                    }.getType());
                    //发送到Notificationfragment中用来显示火车在地图中的位置
                    TrainLocation trainBean = new Gson().fromJson(eventBean.getContent(), TrainLocation.class);
                    List<TrainLocationDetailBean> beans = new Gson().fromJson(trainBean.getMsg(), new TypeToken<List<TrainLocationDetailBean>>() {
                    }.getType());
                    EventBus.getDefault().post(beans.get(0));
                    break;
                case Constant.MSG_CARD:
                    CardMsgBean cardMsgBean = new Gson().fromJson(bean.getMsg(), CardMsgBean.class);
                    EventBus.getDefault().post(cardMsgBean);
                    break;
                default:
                    break;
            }


//            if (messageId > 0) {
//                MPush.I.ack(messageId);
//            }
//
//            if (TextUtils.isEmpty(message)) {
//                return;
//            }

//            NotificationDO ndo = fromJson(message);
//            if (ndo != null) {
//                Intent it = new Intent(context, MyReceiver.class);
//                it.setAction(MPushService.ACTION_NOTIFICATION_OPENED);
//                if (ndo.getExtras() != null) {
//                    it.putExtra("my_extra", ndo.getExtras().toString());
//                }
//                if (TextUtils.isEmpty(ndo.getTitle())) {
//                    ndo.setTitle("MPush");
//                }
//                if (TextUtils.isEmpty(ndo.getTicker())) {
//                    ndo.setTicker(ndo.getTitle());
//                }
//                if (TextUtils.isEmpty(ndo.getContent())) {
//                    ndo.setContent(ndo.getTitle());
//                }
//                Notifications.I.notify(ndo, it);
//            }
//                Notifications.I.notify(ndo, it,context);

//            Notificaitons.getInstance().sendMessagingStyleNotification(bean, context, mNM);
//            }

        } else if (MPushService.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//            Notificaitons.getInstance().clearAllNotification(mNM);
//            Notifications.I.clean(intent);
            String extras = intent.getStringExtra("my_extra");
            System.out.println("=====MyReceiver=====" + "通知被点击了， extras=" + extras);
        } else if (MPushService.ACTION_KICK_USER.equals(intent.getAction())) {
            System.out.println("=====MyReceiver=====用户被踢下线了");
        } else if (MPushService.ACTION_BIND_USER.equals(intent.getAction())) {
            System.out.println("=====MyReceiver=====绑定用户:"
                    + intent.getStringExtra(MPushService.EXTRA_USER_ID)
                    + (intent.getBooleanExtra(MPushService.EXTRA_BIND_RET, false) ? "成功" : "失败")
            );
        } else if (MPushService.ACTION_UNBIND_USER.equals(intent.getAction())) {
            System.out.println("=====MyReceiver=====解绑用户:" +
                    (intent.getBooleanExtra(MPushService.EXTRA_BIND_RET, false)
                            ? "成功"
                            : "失败")
            );

        } else if (MPushService.ACTION_CONNECTIVITY_CHANGE.equals(intent.getAction())) {
            System.out.println("=====MyReceiver=====" + (intent.getBooleanExtra(
                    MPushService.EXTRA_CONNECT_STATE, false)
                    ? "MPUSH连接建立成功" : "MPUSH连接断开"));
            MPush.I.resumePush();
        } else if (MPushService.ACTION_HANDSHAKE_OK.equals(intent.getAction())) {
            System.out.println("=====MyReceiver=====MPUSH握手成功, 心跳:" + intent.getIntExtra(MPushService.EXTRA_HEARTBEAT, 0));
        }
    }

    private NotificationDO fromJson(String message) {
        try {
            JSONObject messageDO = new JSONObject(message);
            if (messageDO != null) {
                JSONObject jo = new JSONObject(messageDO.optString("content"));
                NotificationDO ndo = new NotificationDO();
                ndo.setContent(jo.optString("content"));
                ndo.setTitle(jo.optString("title"));
                ndo.setTicker(jo.optString("ticker"));
                ndo.setNid(jo.optInt("nid", 1));
                ndo.setExtras(jo.optJSONObject("extras"));
                return ndo;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
