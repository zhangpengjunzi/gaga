package com.sinocall.guess.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sinocall.guess.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GuessMainDialog extends PopupWindow {


    TextView popText;

    LinearLayout popBtn;
    @BindView(R.id.share_pop_btn)
    FrameLayout sharePopBtn;
    @BindView(R.id.new_pop_btn)
    FrameLayout newPopBtn;
    @BindView(R.id.report_pop_btn)
    FrameLayout reportPopBtn;
    // 反射布局
    private View conentView;

    /*进入我的抽奖纪录页面*/
    LinearLayout mine_award_btn;

    private Activity activity;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public GuessMainDialog(final Activity context, View.OnClickListener onClickListener) {

        // 反射出布局
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.guess_dialog_main, null);
        activity = context;
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        ButterKnife.bind(this,conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        sharePopBtn.setOnClickListener(onClickListener);
        newPopBtn.setOnClickListener(onClickListener);
        reportPopBtn.setOnClickListener(onClickListener);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.AnimTools);
        // 刷新状态
        this.update();

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
    }

    /*设置内容*/
    public void setPopText(String text) {
        popText.setText(text);
    }

    /*显示一项内容还是两项内容,默认两项，两项不需要调用这个方法*/
    public void setType() {
        mine_award_btn.setVisibility(View.GONE);
    }

    /*设置第一项内容的背景颜色*/
    public void setBackGround(int color) {
        popBtn.setBackgroundColor(activity.getResources().getColor(color));
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent,-parent.getWidth()-parent.getWidth()/10*7,0);// 相对位置
        } else {
            this.dismiss();
        }

    }
}
