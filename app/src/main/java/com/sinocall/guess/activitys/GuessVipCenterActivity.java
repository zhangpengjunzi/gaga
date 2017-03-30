package com.sinocall.guess.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessVipCenterAdapter;
import com.sinocall.guess.dialog.GuessNoticeDialog;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessLevelDetailsModel;
import com.sinocall.guess.model.GuessVipCenterModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.SystemUtils;
import com.sinocall.guess.view.CircleImageView;
import com.zhy.android.percent.support.PercentFrameLayout;

import org.xutils.common.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 会员中心
 * Created by Administrator on 2017/2/27.
 */

public class GuessVipCenterActivity extends BaseActivity {
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.introduce_btn)
    TextView introduceBtn;
    @BindView(R.id.last_month_score)
    TextView lastMonthScore;
    @BindView(R.id.month_score)
    TextView monthScore;
    @BindView(R.id.vip0_image)
    ImageView vip0Image;
    @BindView(R.id.vip0_text)
    TextView vip0Text;
    @BindView(R.id.vip0_bottom)
    ImageView vip0Bottom;
    @BindView(R.id.vip0_frame)
    FrameLayout vip0Frame;
    @BindView(R.id.vip1_image)
    ImageView vip1Image;
    @BindView(R.id.vip1_text)
    TextView vip1Text;
    @BindView(R.id.vip1_bottom)
    ImageView vip1Bottom;
    @BindView(R.id.vip1_frame)
    FrameLayout vip1Frame;
    @BindView(R.id.vip2_image)
    ImageView vip2Image;
    @BindView(R.id.vip2_text)
    TextView vip2Text;
    @BindView(R.id.vip2_bottom)
    ImageView vip2Bottom;
    @BindView(R.id.vip2_frame)
    FrameLayout vip2Frame;
    @BindView(R.id.vip3_image)
    ImageView vip3Image;
    @BindView(R.id.vip3_text)
    TextView vip3Text;
    @BindView(R.id.vip3_bottom)
    ImageView vip3Bottom;
    @BindView(R.id.vip3_frame)
    FrameLayout vip3Frame;
    @BindView(R.id.vip4_image)
    ImageView vip4Image;
    @BindView(R.id.vip4_text)
    TextView vip4Text;
    @BindView(R.id.vip4_bottom)
    ImageView vip4Bottom;
    @BindView(R.id.vip4_frame)
    FrameLayout vip4Frame;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;
    @BindView(R.id.user_headimage)
    CircleImageView userHeadimage;
    @BindView(R.id.vip_center_frame)
    PercentFrameLayout vipCenterFrame;
    @BindView(R.id.common_scroll)
    NestedScrollView commonScroll;
    @BindView(R.id.common_linear)
    LinearLayout commonLinear;

    private GuessVipCenterAdapter adapter;
    private LRecyclerViewAdapter lAdapter;
    private List<String> listVip;
    //当前是否正处在用户当前等级的vip
    private boolean isVip = true;
    //用户目前的vip等级
    private int vipLevel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_vip_center);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        //适配器
        initRecyclerview(recyclerView);
        adapter = new GuessVipCenterAdapter(this);
        lAdapter = new LRecyclerViewAdapter(adapter);
        lAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (noticeDialog == null) {
                    noticeDialog = new GuessNoticeDialog(GuessVipCenterActivity.this);
                }
                switch (position) {
                    case 0:
                        noticeDialog.setNoticeContent(R.string.notice_one);
                        break;
                    case 1:
                        noticeDialog.setNoticeContent(R.string.notice_two);
                        break;
                    case 2:
                        noticeDialog.setNoticeContent(R.string.notice_three);
                        break;
                    case 3:
                        noticeDialog.setNoticeContent(R.string.notice_four);
                        break;
                    case 4:
                        noticeDialog.setNoticeContent(R.string.notice_five);
                        break;
                    case 5:
                        noticeDialog.setNoticeContent(R.string.notice_six);
                        break;
                }
                noticeDialog.show();
            }
        });
        //去掉下拉刷新和上拉加载
        recyclerView.setAdapter(lAdapter);
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadMoreEnabled(false);
        showLoaddingDialog("正在加载...");
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        CckHttpClient.get(GuessApi.VIP_CENTER, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessVipCenterModel centerModel = JSON.parseObject(result, GuessVipCenterModel.class);
                dealData(centerModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessVipCenterActivity.this, ex.getMessage());
                recyclerView.setVisibility(View.GONE);
                commonScroll.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    /*会员提示弹框*/
    private GuessNoticeDialog noticeDialog;
    /*会员等级集合数据*/
    private List<GuessVipCenterModel.DataBean.LevelListBean> listBean;

    /**
     * 处理请求数据
     *
     * @param centerModel
     * @param result
     */
    private void dealData(GuessVipCenterModel centerModel, String result) {
        recyclerView.setVisibility(View.VISIBLE);
        commonScroll.setVisibility(View.GONE);
        if (centerModel.getErrorVo().getCode().equals("1000")) {
            /*上月积分*/
            lastMonthScore.setText(centerModel.getData().getLastMonthPoint() + "");
            /*本月积分*/
            monthScore.setText(centerModel.getData().getCurrentMonthPoint() + "");
            /*会员等级积分*/
            listBean = centerModel.getData().getLevelList();
            for (int i = 0; i < listBean.size(); i++) {
                switch (i) {
                    case 0:
                        vip0Text.setText(listBean.get(i).getNum() + "");
                        break;
                    case 1:
                        vip1Text.setText(listBean.get(i).getNum() + "");
                        break;
                    case 2:
                        vip2Text.setText(listBean.get(i).getNum() + "");
                        break;
                    case 3:
                        vip3Text.setText(listBean.get(i).getNum() + "");
                        break;
                    case 4:
                        vip4Text.setText(listBean.get(i).getNum() + "");
                        break;
                }
            }
            /*根据会员等级设置图标*/
            vipLevel = centerModel.getData().getMyLevelId();
            setVipImage(vipLevel);
            //设置会员福利数据
            listVip = new ArrayList<>();
            listVip.add(centerModel.getData().getDiscount());
            listVip.add(centerModel.getData().getDailyBuyNum());
            listVip.add(centerModel.getData().getRepayRate());
            listVip.add(centerModel.getData().getDailyPublishNum());
            listVip.add(centerModel.getData().getDailyTakePartNum());
            listVip.add(centerModel.getData().getTopicLimit() + "");
            adapter.setLevelId(isVip);
            adapter.setDataList(listVip);
            recyclerView.refreshComplete(6);
        } else {
            getErrorMessage(result);
        }
    }

    /**
     * 根据会员等级设置会员图标
     *
     * @param id
     */
    private void setVipImage(int id) {
        for (int i = 0; i < listBean.size(); i++) {
            if (id == listBean.get(i).getId()) {
                if (id == vipLevel) {
                    updateUI(i, true);
                } else {
                    updateUI(i, false);
                }
                break;
            }
        }
    }

    /**
     *
     * @param i 位置
     * @param isClick 是否需要改变vip状态
     */
    private void updateUI(int i, boolean isClick) {
        switch (i + 1) {
            case 1:
                if (isClick) {
                    vip0Image.setImageResource(R.mipmap.vip0_clicked);
                    vip1Image.setImageResource(R.mipmap.vip1_unclicked);
                    vip2Image.setImageResource(R.mipmap.vip2_unclicked);
                    vip3Image.setImageResource(R.mipmap.vip3_unclicked);
                    vip4Image.setImageResource(R.mipmap.vip4_unclicked);
                }
                vip0Bottom.setVisibility(View.VISIBLE);
                vip1Bottom.setVisibility(View.GONE);
                vip2Bottom.setVisibility(View.GONE);
                vip3Bottom.setVisibility(View.GONE);
                vip4Bottom.setVisibility(View.GONE);
                break;
            case 2:
                if (isClick) {
                    vip0Image.setImageResource(R.mipmap.vip0_unclicked);
                    vip1Image.setImageResource(R.mipmap.vip1_clicked);
                    vip2Image.setImageResource(R.mipmap.vip2_unclicked);
                    vip3Image.setImageResource(R.mipmap.vip3_unclicked);
                    vip4Image.setImageResource(R.mipmap.vip4_unclicked);
                }
                vip0Bottom.setVisibility(View.GONE);
                vip1Bottom.setVisibility(View.VISIBLE);
                vip2Bottom.setVisibility(View.GONE);
                vip3Bottom.setVisibility(View.GONE);
                vip4Bottom.setVisibility(View.GONE);
                break;
            case 3:
                if (isClick) {
                    vip0Image.setImageResource(R.mipmap.vip0_unclicked);
                    vip1Image.setImageResource(R.mipmap.vip1_unclicked);
                    vip2Image.setImageResource(R.mipmap.vip2_clicked);
                    vip3Image.setImageResource(R.mipmap.vip3_unclicked);
                    vip4Image.setImageResource(R.mipmap.vip4_unclicked);
                }
                vip0Bottom.setVisibility(View.GONE);
                vip1Bottom.setVisibility(View.GONE);
                vip2Bottom.setVisibility(View.VISIBLE);
                vip3Bottom.setVisibility(View.GONE);
                vip4Bottom.setVisibility(View.GONE);
                break;
            case 4:
                if (isClick) {
                    vip0Image.setImageResource(R.mipmap.vip0_unclicked);
                    vip1Image.setImageResource(R.mipmap.vip1_unclicked);
                    vip2Image.setImageResource(R.mipmap.vip2_unclicked);
                    vip3Image.setImageResource(R.mipmap.vip3_clicked);
                    vip4Image.setImageResource(R.mipmap.vip4_unclicked);
                }
                vip0Bottom.setVisibility(View.GONE);
                vip1Bottom.setVisibility(View.GONE);
                vip2Bottom.setVisibility(View.GONE);
                vip3Bottom.setVisibility(View.VISIBLE);
                vip4Bottom.setVisibility(View.GONE);
                break;
            case 5:
                if (isClick) {
                    vip0Image.setImageResource(R.mipmap.vip0_unclicked);
                    vip1Image.setImageResource(R.mipmap.vip1_unclicked);
                    vip2Image.setImageResource(R.mipmap.vip2_unclicked);
                    vip3Image.setImageResource(R.mipmap.vip3_unclicked);
                    vip4Image.setImageResource(R.mipmap.vip4_clicked);
                }
                vip0Bottom.setVisibility(View.GONE);
                vip1Bottom.setVisibility(View.GONE);
                vip2Bottom.setVisibility(View.GONE);
                vip3Bottom.setVisibility(View.GONE);
                vip4Bottom.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新头像
        String re=(String) SPUtils.get(this, "headImage", "");
        if (!SPUtils.get(this, "headImage", "").equals("")) {
            Glide.with(this).load((String) SPUtils.get(this, "headImage", "")).into(userHeadimage);
        } else {
            Glide.with(this).load(R.mipmap.avatar_set).into(userHeadimage);
        }
    }

    //接受本地数据
    private GuessLevelDetailsModel localData;
    private int clickId=-1;//点击的vip位置，从0开始

    @OnClick({R.id.back_image, R.id.introduce_btn, R.id.vip0_frame, R.id.vip1_frame, R.id.vip2_frame, R.id.vip3_frame, R.id.vip4_frame})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                this.finish();
                break;
            case R.id.introduce_btn:
                //会员说明
                Bundle bundle = new Bundle();
                bundle.putString("name", "会员说明");
                bundle.putString("url", Common.initModel.getData().getGrowUpHelp());
                SystemUtils.StartByParams(this, GuessWebViewActivity.class, bundle);
                break;
            case R.id.vip0_frame:
                //vip0
                clickId=0;
                if (!TextUtils.isEmpty(Common.vip_1)) {
                    localData = JSON.parseObject(Common.vip_1, GuessLevelDetailsModel.class);
                    levelDetailsData(localData,listBean.get(clickId).getId());
                } else {
                    requestVipData(listBean.get(clickId).getId(),clickId+1);
                }
                break;
            case R.id.vip1_frame:
                //vip1
                clickId=1;
                if (!TextUtils.isEmpty(Common.vip_2)) {
                    localData = JSON.parseObject(Common.vip_2, GuessLevelDetailsModel.class);
                    levelDetailsData(localData,listBean.get(clickId).getId());
                } else {
                    requestVipData(listBean.get(clickId).getId(),clickId+1);
                }
                break;
            case R.id.vip2_frame:
                //vip2
                clickId=2;
                if (!TextUtils.isEmpty(Common.vip_3)) {
                    localData = JSON.parseObject(Common.vip_3, GuessLevelDetailsModel.class);
                    levelDetailsData(localData,listBean.get(clickId).getId());
                } else {
                    requestVipData(listBean.get(clickId).getId(),clickId+1);
                }
                break;
            case R.id.vip3_frame:
                //vip3
                clickId=3;
                if (!TextUtils.isEmpty(Common.vip_4)) {
                    localData = JSON.parseObject(Common.vip_4, GuessLevelDetailsModel.class);
                    levelDetailsData(localData,listBean.get(clickId).getId());
                } else {
                    requestVipData(listBean.get(clickId).getId(),clickId+1);
                }
                break;
            case R.id.vip4_frame:
                //vip4
                clickId=4;
                if (!TextUtils.isEmpty(Common.vip_5)) {
                    localData = JSON.parseObject(Common.vip_5, GuessLevelDetailsModel.class);
                    levelDetailsData(localData,listBean.get(clickId).getId());
                } else {
                    requestVipData(listBean.get(clickId).getId(),clickId+1);
                }
                break;
            case R.id.common_linear:
                showLoaddingDialog("正在加载...");
                initData();
                break;
        }
    }

    private void requestVipData(final int id,final int position) {
        showLoaddingDialog("正在加载...");
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + id);
        CckHttpClient.get(GuessApi.VIP_CENTER_DATA, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessLevelDetailsModel detailsModel = JSON.parseObject(result, GuessLevelDetailsModel.class);
                if (detailsModel.getErrorVo().getCode().equals("1000")) {
                    //将json缓存在本地
                    switch (position) {
                        case 1:
                            Common.vip_1 = result;
                            break;
                        case 2:
                            Common.vip_2 = result;
                            break;
                        case 3:
                            Common.vip_3 = result;
                            break;
                        case 4:
                            Common.vip_4 = result;
                            break;
                        case 5:
                            Common.vip_5 = result;
                            break;
                    }
                    levelDetailsData(detailsModel, id);
                } else {
                    getErrorMessage(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessVipCenterActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
            }
        });
    }

    private void levelDetailsData(GuessLevelDetailsModel detailsModel, int id) {
        setVipImage(id);
        //重新赋值
        listVip = new ArrayList<>();
        listVip.add(detailsModel.getData().getDiscount() + "");
        listVip.add(detailsModel.getData().getDailyBuyNum() + "");
        listVip.add(detailsModel.getData().getRepayRate() + "");
        listVip.add(detailsModel.getData().getDailyPublishNum() + "");
        listVip.add(detailsModel.getData().getDailyTakePartNum() + "");
        listVip.add(detailsModel.getData().getTopicLimit() + "");
        if (id == vipLevel || vipLevel == 0) {//当viplevel默认是0或者根据等级来设置图标
            adapter.setLevelId(true);
        } else {
            adapter.setLevelId(false);
        }
        adapter.setDataList(listVip);
        recyclerView.refreshComplete(6);
    }
}
