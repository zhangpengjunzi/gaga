package com.sinocall.guess.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jungly.gridpasswordview.GridPasswordView;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessAwardListAdapter;
import com.sinocall.guess.adapter.GuessBuyCreateSerciveAdapter;
import com.sinocall.guess.dialog.GuessBuyServiceUtils;
import com.sinocall.guess.dialog.GuessBuyShoppingUtils;
import com.sinocall.guess.dialog.GuessLessMoneyUtils;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessAwardListModel;
import com.sinocall.guess.model.GuessCreateModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.DESUtils;
import com.sinocall.guess.utils.MD5Utils;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.LinearListView;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/17.
 */

public class GuessSettingAwardActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.award_number_state)
    TextView awardNumberState;
    @BindView(R.id.award_number_linear)
    PercentLinearLayout awardNumberLinear;
    @BindView(R.id.award_number_list)
    LinearListView awardNumberList;
    @BindView(R.id.scroll_award)
    ScrollView scrollAward;

    private GuessAwardListAdapter adapter;
    private GuessBuyServiceUtils serviceUtils;

    //当前选中额度
    private String number;
    private String award_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_setting_award);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void clickRequest() {
        super.clickRequest();
        initData();
    }

    @Override
    public void rightClick() {
        super.rightClick();
        if(TextUtils.isEmpty(number)){
            ToastUtils.showToast(this,"请选择题目开奖额度");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("number", number);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initView() {
        initTitle("开奖条件", "完成");
        initData();
        awardNumberList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取位置
                int position = Integer.parseInt(((TextView) view.findViewById(R.id.position_text)).getText().toString());
                if (adapter.getList().get(position).getSelect() == 1) {
                    return;//当前被选中
                }
                //判断是否可以选择该额度
                if (adapter.getList().get(position).getEnable() == 1) {
                    //没有被选中，则将原来被选中的置0，选择被点击的位置
                    for (int i = 0; i < adapter.getList().size(); i++) {
                        if (adapter.getList().get(i).getSelect() == 1) {
                            adapter.getList().get(i).setSelect(0);
                            break;
                        }
                    }
                    adapter.getList().get(position).setSelect(1);
                    awardNumberState.setText("满" + adapter.getList().get(position).getNum() + "即开奖");
                    awardNumberList.setAdapter(adapter);
                    //当前选中的开奖额度
                    number = adapter.getList().get(position).getNum() + "";
                } else {
                    //没有选择权限，弹出购买弹框
                    havePower();
                }
            }
        });
    }

    private void initData() {
        scrollAward.setVisibility(View.GONE);
        showLoaddingDialog("正在加载...");
        CckHttpClient.get(GuessApi.AWARD_LIST, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessAwardListModel awardListModel = JSON.parseObject(result, GuessAwardListModel.class);
                dealData(awardListModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessSettingAwardActivity.this, ex.getMessage());
                scrollAward.setVisibility(View.GONE);
                setNoDataArea(true, "您的网络不太好，点击重新加载");
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

    /**
     * 处理请求数据
     *
     * @param awardListModel
     * @param result
     */
    private void dealData(GuessAwardListModel awardListModel, String result) {
        if (awardListModel.getErrorVo().getCode().equals("1000")) {
            scrollAward.setVisibility(View.VISIBLE);
            List<GuessAwardListModel.DataBean.ListBean> listBean = awardListModel.getData().getList();
            award_text = getIntent().getStringExtra("award_text");//上个页面返回的数据
            for (int i = 0; i < listBean.size(); i++) {
                if(!TextUtils.isEmpty(award_text)){//上个页面有传递值
                    if(Integer.parseInt(award_text)==listBean.get(i).getNum()){
                        listBean.get(i).setSelect(1);
                        awardNumberState.setText("满" + listBean.get(i).getNum() + "即开奖");
                        number = listBean.get(i).getNum() + "";
                        break;
                    }
                }else{
                    if (listBean.get(i).getEnable() == 1) {//上个页面没有传递值
                        listBean.get(i).setSelect(1);//默认选中第一个可以选择的
                        awardNumberState.setText("满" + listBean.get(i).getNum() + "即开奖");
                        number = listBean.get(i).getNum() + "";
                        break;
                    }
                }

            }
            adapter = new GuessAwardListAdapter(this, listBean);
            awardNumberList.setAdapter(adapter);
        } else {
            getErrorMessage(result);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buy_btn:
                serviceUtils.dismiss();
                //判断用户是否设置了支付密码
                if ((Integer) SPUtils.get(this, "password", 0) == 0) {
                    ToastUtils.showToast(this, "未设置支付密码");
                    startActivity(new Intent(this, GuessMineSettingsActivity.class));
                    return;
                }
                //购买发布次数按钮
                if (shoppingUtils == null) {
                    shoppingUtils = new GuessBuyShoppingUtils(this, this);
                }
                //设置密码输入监听
                shoppingUtils.getPassView().setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
                    @Override
                    public void onTextChanged(String psw) {

                    }

                    @Override
                    public void onInputFinish(String psw) {
                        //当密码输入完毕，请求支付
                        buyReleaseNumber(buy_id, psw);
                    }
                });
                shoppingUtils.show();
                break;
        }
    }

    /**
     * 没有权限，弹出购买弹框
     */
    private void havePower() {
        showLoaddingDialog("正在初始化...");
        Bundle bundle = new Bundle();
        bundle.putString("type", 4 + "");
        CckHttpClient.get(GuessApi.SELECT_LIST, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessCreateModel createModel = JSON.parseObject(result, GuessCreateModel.class);
                dealCreateData(createModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessSettingAwardActivity.this, ex.getMessage());
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

    //输入支付密码购买
    private GuessBuyShoppingUtils shoppingUtils;
    //盘口购买产品适配
    private GuessBuyCreateSerciveAdapter buyCreateSerciveAdapter;
    //用户选择的购买项
    private int buy_id;

    /**
     * 处理数据
     *
     * @param createModel
     * @param result
     */
    private void dealCreateData(GuessCreateModel createModel, String result) {
        if (createModel.getErrorVo().getCode().equals("1000")) {
            if (serviceUtils == null) {
                serviceUtils = new GuessBuyServiceUtils(this, this);
                serviceUtils.setCenterText("发布题目可设置的开奖额度");
            }
            serviceUtils.setTitle("你当前可免费设置的最大额度为" + createModel.getData().getCurrentNum() + "盘缠，提升至");
            //添加购买数据
            List<GuessCreateModel.DataBean.ListBean> listBeen = createModel.getData().getList();
            buyCreateSerciveAdapter = new GuessBuyCreateSerciveAdapter(this, listBeen);
            serviceUtils.getGriView().setAdapter(buyCreateSerciveAdapter);
            buy_id = buyCreateSerciveAdapter.getList().get(0).getId();//默认第一项被选中
            //点击事件
            serviceUtils.getGriView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if (buyCreateSerciveAdapter.getPostion() == i) return;
                    buyCreateSerciveAdapter.setSelection(i);
                    buyCreateSerciveAdapter.notifyDataSetChanged();
                    buy_id = buyCreateSerciveAdapter.getList().get(i).getId();
                }
            });
            serviceUtils.show();
        }
    }


    //是否支付成功
    private boolean isPaySuccess = false;
    private GuessLessMoneyUtils lessMoneyUtils;

    private void buyReleaseNumber(int id, String password) {
        showLoaddingDialog("正在支付...");
        Bundle bundle = new Bundle();
        bundle.putString("id", "" + id);
        try {
            bundle.putString("payPass", DESUtils.encryptDES(MD5Utils.hexdigest(password), Common.DES_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CckHttpClient.get(GuessApi.BUY_SERVICE, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel = JSON.parseObject(result, SimPleModel.class);
                if (simPleModel.getErrorVo().getCode().equals("1000")) {
                    ToastUtils.showToast(GuessSettingAwardActivity.this, "购买成功");
                    isPaySuccess = true;
                    shoppingUtils.dismiss();
                    //重新加载数据
                    initData();
                } else {
                    if (simPleModel.getErrorVo().getCode().equals("1003")) {
                        //余额不足
                        shoppingUtils.dismiss();
                        if (lessMoneyUtils == null) {
                            lessMoneyUtils = new GuessLessMoneyUtils(GuessSettingAwardActivity.this, GuessSettingAwardActivity.this);
                        }
                        lessMoneyUtils.show();
                    } else {
                        ToastUtils.showToast(GuessSettingAwardActivity.this, simPleModel.getErrorVo().getMsg());
                        showSoftInputView();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessSettingAwardActivity.this, ex.getMessage());
                if (!isPaySuccess) {//若不是支付成功之后因别的原因导致报错，则弹出键盘
                    showSoftInputView();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                if (shoppingUtils != null) {//清空密码
                    shoppingUtils.getPassView().clearPassword();
                }
            }
        });
    }
}
