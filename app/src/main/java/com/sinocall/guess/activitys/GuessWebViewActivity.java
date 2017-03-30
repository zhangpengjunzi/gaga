package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.service.GuessInitInterfaceService;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.ProgressWebview;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/11.
 */

public class GuessWebViewActivity extends BaseActivity {


    @BindView(R.id.webView)
    ProgressWebview webView;
    private String name;//页面名称
    private String url;//加载url
    private int type;//根据不同的需求在右上角添加不同的功能
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_webview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        url = getIntent().getStringExtra("url");
        type=getIntent().getIntExtra("type",-1);
        if (!TextUtils.isEmpty(name)){
            if(name.equals("合伙人")){
                initTitle(name, "分享");
            }else{
                initTitle(name, "");
            }
        }
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        if(!TextUtils.isEmpty(url)){
            webView.loadUrl(url);
        }else{
            //请求数据
            ToastUtils.showToast(this,"请检查您的网络设置");
            startService(new Intent(this, GuessInitInterfaceService.class));
        }
    }

    @Override
    public void rightClick() {
        super.rightClick();
        switch (type){
            case 1:
                //分享
                openShareBoard(shareBoardlistener);
                break;
        }
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            String imageUrl = "";//设置为空，用本地图片资源
            String title = getResources().getString(R.string.partner_title);
            String description =  getResources().getString(R.string.partner_content);
            //分享链接
            String url = Common.initModel.getData().getMakeMoneyShare();
            if (TextUtils.isEmpty(url)) {
                ToastUtils.showToast(GuessWebViewActivity.this, "分享链接不能为空，请检查您的网络设置");
                return;
            }
            share(imageUrl, url, title, description, share_media);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        umShareAPI.onActivityResult(requestCode,resultCode,data);
    }

}
