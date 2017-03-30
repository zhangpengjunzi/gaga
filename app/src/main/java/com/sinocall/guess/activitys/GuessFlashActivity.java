package com.sinocall.guess.activitys;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.SystemUtils;

/**
 * 引导页
 */
public class GuessFlashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         /*强制竖屏*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //是否是第一次安装，默认为true
        if((boolean) SPUtils.get(this,"first_step", true)){
            //进入引导页
            goGuide();
        }else{
            //进入主页
            goMain();
        }
    }

    //跳转到启动页
    private void goMain(){
        SystemUtils.StartByParams(this,GuessStartPageActivity.class,null);
        GuessFlashActivity.this.finish();
    }

    //跳转到引导页
    private void goGuide(){
        SystemUtils.StartByParams(this,GuessGuideActivity.class,null);
        GuessFlashActivity.this.finish();
    }

}
