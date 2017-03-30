package com.sinocall.guess.activitys;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/24.
 */

public class GuessAboutUsActivity extends BaseActivity {
    @BindView(R.id.version_code)
    TextView versionCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_about_us);
        ButterKnife.bind(this);
        initTitle("关于猜猜看","");
        versionCode.setText(getVersionCode());
    }

    public String getVersionCode()//获取版本号(内部识别号)
    {
        PackageInfo pi = null;
        try {
            pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
