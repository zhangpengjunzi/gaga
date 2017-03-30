package com.sinocall.guess.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.sinocall.guess.utils.Common;

/**
 * 接受推送与消息回调
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 * Created by Administrator on 2017/2/14.
 */

public class GTPushIntentService extends GTIntentService{


    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    /**
     * 接受cid
     * @param context
     * @param s
     */
    @Override
    public void onReceiveClientId(Context context, String s) {
        Log.e(TAG, "onReceiveClientId -> " + "clientid = " + s);
        Common.cid=s;//登录时传递到服务器
    }

    /**
     * 接受自定义消息
     * @param context
     * @param gtTransmitMessage
     */
    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        byte[] payload = gtTransmitMessage.getPayload();
        if (payload == null) {
        } else {
            //自定义json信息
            String data = new String(payload);
            Log.e(TAG,"data="+data);
            //发送广播提醒展示给用户信息
            Intent intent=new Intent("com.sinocall.guess.awardreceive");
            intent.putExtra("receiveData",data);
            sendBroadcast(intent);
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }
}
