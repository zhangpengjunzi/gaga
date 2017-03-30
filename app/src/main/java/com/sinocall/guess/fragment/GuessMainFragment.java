package com.sinocall.guess.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.guess.R;
import com.sinocall.guess.model.GuessMainListModel;
import com.sinocall.guess.view.OvalImageView;
import com.sinocall.guess.view.RoundProgressBar;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/2.
 */

public class GuessMainFragment extends Fragment {

    @BindView(R.id.guess_image)
    OvalImageView guessImage;
    @BindView(R.id.guess_describe)
    TextView guessDescribe;
    @BindView(R.id.guess_left_text)
    TextView guessLeftText;
    @BindView(R.id.roundProgressBar)
    RoundProgressBar roundProgressBar;
    @BindView(R.id.award_number)
    TextView awardNumber;
    @BindView(R.id.guess_right_text)
    TextView guessRightText;
    @BindView(R.id.guess_fragment_linear)
    PercentLinearLayout guessFragmentLinear;
    @BindView(R.id.fragment_data)
    LinearLayout fragmentData;
    @BindView(R.id.no_data_text)
    TextView noDataText;
    @BindView(R.id.no_data_linear)
    LinearLayout noDataLinear;
    @BindView(R.id.red_number)
    TextView redNumber;
    @BindView(R.id.blue_number)
    TextView blueNumber;

    private GuessMainListModel.DataBean.ListBean model;//初始model
    private int state = 1;//状态。默认不是跳转页面，1代表有数据页面 2代表为加载无数据  3代表网络不好页面
    private DecimalFormat df = new DecimalFormat("#.##");//转换浮点型
    private int pro = 0;//圆形进度初始值
    private Thread thread;

    //更新model
    public void setAddModel(GuessMainListModel.DataBean.ListBean addModel) {
        this.model = addModel;
    }

    public GuessMainListModel.DataBean.ListBean getModel() {
        return model;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
        if (state == 2) {
            //无数据页面
            if (noDataText != null) noDataText.setText("没有更多题目了");
        } else if (state == 3) {
            if (noDataText != null) noDataText.setText("您的网络似乎不太好");
        }
    }

    /*实例化*/
    public static GuessMainFragment newInstance(GuessMainListModel.DataBean.ListBean model) {
        GuessMainFragment guessMainFragment = new GuessMainFragment();
        guessMainFragment.setAddModel(model);
        guessMainFragment.setState(1);
        return guessMainFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initParams() {
        if (model != null) {//不为空代表不是作为跳板的页面或多余的页面
            guessFragmentLinear.setVisibility(View.VISIBLE);
            if (state == 2 || state == 3) {
                noDataLinear.setVisibility(View.VISIBLE);
                fragmentData.setVisibility(View.GONE);
            } else {
                noDataLinear.setVisibility(View.GONE);
                fragmentData.setVisibility(View.VISIBLE);
                if (model != null) {
                    //图片
                    Glide.with(getActivity()).load(model.getPicUrl()).into(guessImage);
                    //描述
                    guessDescribe.setText(model.getDescription());
                    //左按钮描述
                    guessLeftText.setText(model.getLeft());
                    //右按钮描述
                    guessRightText.setText(model.getRight());
                    //进度
                    if (model.getCoinSwitch() != 0) {
                        final float process = (float) model.getRecordCoin() / (float) model.getCoinSwitch();
                        pro = 0;
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (pro < (int) (Float.parseFloat(df.format(process)) * 100)) {
                                    pro += 1;
                                    roundProgressBar.setProgress(pro);
                                    try {
                                        Thread.sleep(30);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                        thread.start();
                    }
                    //开奖条件
                    awardNumber.setText(model.getCoinSwitch() + "");
                    //左边已下注
                    redNumber.setText(model.getSupportLeft() + "");
                    //右边已下注
                    blueNumber.setText(model.getSupportright() + "");
                }
            }
        } else {
            guessFragmentLinear.setVisibility(View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.guess_fragment_main, null);
        ButterKnife.bind(this, view);
        initParams();
        return view;
    }

    /**
     * 根据投注方向改变投注金额显示
     *
     * @param choice 左边还是右边
     * @param number 投注金额
     */
    public void setSupport(int choice, int number) {
        if (choice == 1) {
            //左边下注修改
            if (redNumber != null) redNumber.setText(number + "");
            //改变model值，防止销毁再新建view时还是原来的数据
            model.setSupportLeft(number);
        } else {
            //右边下注修改
            if (blueNumber != null) blueNumber.setText(number + "");
            model.setSupportright(number);
        }
    }

    /**
     * 修改投注进度
     *
     * @param process
     */
    public void updateProcess(int process) {
        if (thread.isAlive()) {
            thread.isInterrupted();
        }
        //改变已下注金额
        model.setRecordCoin(process);
        if (model.getCoinSwitch() != 0 && roundProgressBar != null) {
            float number = (float) model.getRecordCoin() / (float) model.getCoinSwitch();
            roundProgressBar.setProgress((int) (Float.parseFloat(df.format(number)) * 100));
        }
    }


}
