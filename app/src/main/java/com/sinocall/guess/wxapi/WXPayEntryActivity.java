package com.sinocall.guess.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.sinocall.guess.utils.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	public static  String APP_ID;
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this,APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		int code = resp.errCode;

		switch (code){
			case 0://支付成功后的界面
				ToastUtils.showToast(this,"支付成功");
				break;
			case -1:
				ToastUtils.showToast(this, "签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、您的微信账号异常等。");
				break;
			case -2://用户取消支付后的界面
				ToastUtils.showToast(this,"支付已取消");
				break;
		}
		finish();
	}
}