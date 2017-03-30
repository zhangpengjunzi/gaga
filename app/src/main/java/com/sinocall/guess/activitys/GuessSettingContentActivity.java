package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设置精彩选项
 * Created by Administrator on 2017/2/17.
 */

public class GuessSettingContentActivity extends BaseActivity {

    @BindView(R.id.select_one_text)
    EditText selectOneText;
    @BindView(R.id.select_two_text)
    EditText selectTwoText;
    @BindView(R.id.content_linear)
    LinearLayout contentLinear;

    //从上个页面返回的数据，如果为空，代表以前没有选择内容
    private String select_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_setting_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initTitle("竞猜选项", "完成");
        //点击页面其他地方，隐藏软键盘
        contentLinear.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideSoftInputView();
                return false;
            }
        });
        select_text=getIntent().getStringExtra("select_text");
        if(!TextUtils.isEmpty(select_text)){//将传递过来的内容赋值
            String [] selects=select_text.split("/");
            selectOneText.setText(selects[0]);
            selectOneText.setSelection(selects[0].length());
            selectTwoText.setText(selects[1]);
            selectTwoText.setSelection(selects[1].length());
        }
    }

    @Override
    public void rightClick() {
        super.rightClick();
        //判断输入是否合法

        if(selectOneText.getText().toString().contains(" ")||selectTwoText.getText().toString().contains(" ")){
            ToastUtils.showToast(this,"竞猜选项内容不能包含空格");
            return;
        }

        if(TextUtils.isEmpty(selectOneText.getText().toString())||TextUtils.isEmpty(selectTwoText.getText().toString())){
            ToastUtils.showToast(this,"竞猜选项内容不能为空");
            return;
        }

        if(TextUtils.equals(selectOneText.getText().toString(),selectTwoText.getText().toString())){
            ToastUtils.showToast(this,"两种竞猜选项内容不能相同");
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("first",selectOneText.getText().toString());
        intent.putExtra("second",selectTwoText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
