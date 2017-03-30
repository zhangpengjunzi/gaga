package com.sinocall.guess.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessInitModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.SPUtils;

import org.xutils.common.Callback;

/**
 * 用来随时请求初始化接口
 * Created by Administrator on 2017/3/11.
 */

public class GuessInitInterfaceService extends IntentService {



    public GuessInitInterfaceService() {
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        init();
    }

    private void init(){
        CckHttpClient.get(GuessApi.INIT, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessInitModel initModel= JSON.parseObject(result,GuessInitModel.class);
                if(initModel.getErrorVo().getCode().equals("1000")){
                    Common.initModel=initModel;
                    SPUtils.put(GuessInitInterfaceService.this,"userAgree",initModel.getData().getUserAgreement());
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
