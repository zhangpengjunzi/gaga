package com.sinocall.guess.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.SystemUtils;

/**
 * Created by Administrator on 2016/7/8.
 */

public class GuessStartPageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_start_page);
        initView();
    }

    private void initView(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goMain();
            }
        },2000);
    }

    //判断是否已经登录过，若登录过，则直接进入主页面
    private void goMain(){
        if(SPUtils.get(this,"lId","").equals("")){
            SystemUtils.StartByParams(this,GuessLoginActivity.class,null);
        }else{
            SystemUtils.StartByParams(this,MainActivity.class,null);
        }
        this.finish();
    }



    //这里需要屏蔽返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
