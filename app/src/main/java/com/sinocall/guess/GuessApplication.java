package com.sinocall.guess;

import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.sinocall.guess.utils.ChannelUtil;
import com.sinocall.guess.utils.DeviceUtils;
import com.sinocall.guess.utils.NetWorkUtils;
import com.sinocall.guess.utils.ScreenUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

/**
 * Created by Administrator on 2017/2/8.
 */

public class GuessApplication extends Application {
    private static  GuessApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initHeadData();
    }

    /**
     * 获取Application全局对象
     */
    public static  GuessApplication getInstance(){
        return application;
    }

    /**
     * 初始化极光推送和友盟社会化分享
     */
    private void init(){
        application=this;
        x.Ext.init(this);//xutils3初始化
        UMShareAPI.get(this);//友盟SDK初始化
        MobclickAgent.setSessionContinueMillis(60*1000);//默认1分钟从桌面回到应用算是再次打开应用
        MobclickAgent.setCatchUncaughtExceptions(true);
        //设置三个平台的appid与secret
        PlatformConfig.setWeixin("wx609d6a597f58bae5","9d938718e85b14100f862387170f3f31");
        PlatformConfig.setSinaWeibo("2989361011","378368886e3e823194efedd889d45ceb","http://sns.whalecloud.com/sina2/callback");
        PlatformConfig.setQQZone("1106018996","btSWJQ4QDf8VxNES");
    }

    /**
     * 初始化请求头信息
     */
    private void initHeadData(){
        DeviceUtils.channelId=ChannelUtil.getChannel(this);
        if (DeviceUtils.channelId != null){
            String[] ids = DeviceUtils.channelId.split("_");
            if (ids != null && ids.length > 1){
                DeviceUtils.channelId = ids[1];
            }
        }else{
            DeviceUtils.channelId="1";
        }
        DeviceUtils.os=0;
        DeviceUtils.osv= Build.VERSION.RELEASE;
        DeviceUtils.ver= ChannelUtil.getVersionName(this);
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        DeviceUtils.mac=info.getMacAddress();
        DeviceUtils.apn=info.getSSID();
        DeviceUtils.lat=0;
        DeviceUtils.lon=0;
        DeviceUtils.net= NetWorkUtils.getProvider(this)+"-"+NetWorkUtils.getCurrentNetworkType(this);
        DeviceUtils.r="w"+ ScreenUtils.getWindowWidth(this)+"h"+ScreenUtils.getWindowHeigh(this);
        DeviceUtils.brand= Build.BRAND;
        DeviceUtils.ua=Build.MANUFACTURER+"-"+Build.MODEL;
        DeviceUtils.platform=0;
    }
}
