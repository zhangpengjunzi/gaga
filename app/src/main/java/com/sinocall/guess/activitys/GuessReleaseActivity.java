package com.sinocall.guess.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;

/**
 * 我参与的于发布的展示页
 * Created by Administrator on 2017/2/13.
 */

public class GuessReleaseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_mine_release);
    }
}
