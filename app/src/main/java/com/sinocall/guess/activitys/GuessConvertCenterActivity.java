package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.view.ProgressWebview;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/20.
 */

public class GuessConvertCenterActivity extends BaseActivity {


    @BindView(R.id.webView)
    ProgressWebview webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_convert_center);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle("兑换中心", "明细");
        String url = Common.initModel.getData().getExchangeCenter() + "?userId=" + SPUtils.get(this, "userId", 0) + "&lId=" + (String) SPUtils.get(this, "lId", "");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void rightClick() {
        super.rightClick();
        startActivity(new Intent(this, GuessConcertDetailsActivity.class));
    }
}
