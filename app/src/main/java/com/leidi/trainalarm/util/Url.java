package com.leidi.trainalarm.util;

import rxhttp.wrapper.annotation.DefaultDomain;

/**
 * @author 阎
 * @date 2020/5/18
 * @description
 */
public class Url {
    //设置为默认域名
    @DefaultDomain()
    public static String baseUrl = "http://192.168.8.26:8002/";
//    public static String baseUrl = "http://123.138.236.26:8002/";

    public static String login = "app/login";
    public static String login_out = "app/logout";
    public static String change_pwd = "app/user/changePassword";
    public static String upload_location = "app/track/uploadPoint";
    public static String upload_history_location = "app/track/uploadHistoricalPoint";
    public static String notification_list = "app/alarmInfo/getList";
    public static String DELETE_NOTIFICATION_ITEM = "/app/alarmInfo/deleteAlarm";
    public static String home_list = "/app/notice/getList";
    public static String get_all_user = "/app/user/getAllUserLabel";
    public static String get_track = "/app/track/getTrackList";
    public static String delete_home_item = "";
    public static String get_railway = "/app/railwayline/getAllRailwayline";
    public static String get_alarmSet = "/app/alarmSet/getPersonAlarmSetList";
    public static String callbackAlarm = "/app/alarmInfo/callbackAlarm";

}

