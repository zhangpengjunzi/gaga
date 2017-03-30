package com.sinocall.guess.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

/**
 * 获得渠道名
 */
public class ChannelUtil {

    private static final String TAG = "ChannelUtil";
    //获取渠道名
    public static String getChannel(Context context) {

        String channel = "";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = applicationInfo.metaData.get("UMENG_CHANNEL")+"";
            //Log.d(TAG, " channel == " + channel);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channel;
    }

    //获取Android Imei
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    //获取软件版本
    public static String getVersionName(Context context) {
        String version = "";
        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // #debug
        }
        return version;
    }

}
