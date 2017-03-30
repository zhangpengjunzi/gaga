package com.sinocall.guess.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.jungly.gridpasswordview.GridPasswordView;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessBuyCreateSerciveAdapter;
import com.sinocall.guess.adapter.GuessMineJoinAdapter;
import com.sinocall.guess.adapter.GuessMineReleaseAdapter;
import com.sinocall.guess.dialog.GuessBuyServiceUtils;
import com.sinocall.guess.dialog.GuessBuyShoppingUtils;
import com.sinocall.guess.dialog.GuessCreateNoticeDialog;
import com.sinocall.guess.dialog.GuessLessMoneyUtils;
import com.sinocall.guess.dialog.GuessReceiveDataDialog;
import com.sinocall.guess.dialog.GuessSelectUtils;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessCreateModel;
import com.sinocall.guess.model.GuessHandicapDetailsModel;
import com.sinocall.guess.model.GuessJoinModel;
import com.sinocall.guess.model.GuessMineReleaseModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.DESUtils;
import com.sinocall.guess.utils.MD5Utils;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.SystemUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.common.Callback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我参与的与我发布的
 * Created by Administrator on 2017/2/20.
 */

public class GuessMineJoinActivity extends BaseActivity implements View.OnClickListener, GuessMineJoinAdapter.ShareJoin, GuessMineReleaseAdapter.ShareRelease {


    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.mine_release_linear)
    LinearLayout mineReleaseLinear;
    @BindView(R.id.mine_join_linear)
    LinearLayout mineJoinLinear;
    @BindView(R.id.new_create_btn)
    TextView newCreateBtn;
    @BindView(R.id.recycler_view)
    LRecyclerView recyclerView;
    @BindView(R.id.recycler_release)
    LRecyclerView recyclerRelease;
    @BindView(R.id.mine_join_text)
    TextView mineJoinText;
    @BindView(R.id.mine_release_text)
    TextView mineReleaseText;

    //我参与的
    private GuessMineJoinAdapter adapter;
    private LRecyclerViewAdapter lAdapter;
    //我发布的
    private GuessMineReleaseAdapter rAdapter;
    private LRecyclerViewAdapter rLAdapter;
    //参与头部view，因为headview只能有一个父布局，因此需要两个headView
    private View headView;
    //发布头部view
    private View releaseHeadView;
    //左边类型与数量
    private TextView leftNumber, joinLeftNumber;
    //右边类型与数量
    private TextView rightNumber, joinRightNumber;
    //我参与的是否是第一次加载
    private boolean isFirst = true;
    private int count = 10;
    private int currentPage = 1;
    private List<GuessJoinModel.DataBean.HistoryTopicBean> hisList;
    private boolean isVisible = true;//我参与的是否显示
    private boolean isNetWork = true;//是否是网络原因导致不显示
    //我发布的是否是第一次加载
    private boolean rIsFirst = true;
    private int rCount = 10;
    private int rCurrentPage = 1;
    private List<GuessMineReleaseModel.DataBean.CreateListBean> rList;
    private boolean isReleaseVisible = true;//我发布的是否显示
    private boolean isReleaseNetWork = true;//是否是网络原因导致不显示
    //正处于哪个页面  0为我参与的 ，1为我发布的
    private int page_position = 0;
    //请求地址
    private String requestStr;
    //是否正在新建盘口
    private boolean isCreate = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_mine_release);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void clickRequest() {
        super.clickRequest();
        showLoaddingDialog("正在加载...");
        if (page_position == 0) {
            isFirst = true;
            currentPage = 1;
            initData(count, currentPage);
        } else {
            rIsFirst = true;
            rCurrentPage = 1;
            initData(rCount, rCurrentPage);
        }
    }

    private void initRecycler() {
        if (page_position == 0 && adapter == null) {
            initRecyclerview(recyclerView);
            //头部view
            headView = LayoutInflater.from(this).inflate(R.layout.guess_head_view_join, (ViewGroup) findViewById(android.R.id.content), false);
            findHeadViewById(headView, page_position);
            //适配器
            adapter = new GuessMineJoinAdapter(this);
            adapter.setShareJoin(this);
            lAdapter = new LRecyclerViewAdapter(adapter);
            lAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    GuessJoinModel.DataBean.HistoryTopicBean joinModel=adapter.getDataList().get(position);
                    if(joinModel.getStatus()==1){
                        //已开奖，点击弹出详情
                        getDetailsRequest(joinModel.getId());
                    }else{
                        //没开奖,只能分享
                        ToastUtils.showToast(GuessMineJoinActivity.this,"暂未开奖哦");
                    }
                }
            });
            recyclerView.setAdapter(lAdapter);
            setVisibleGone();
            showLoaddingDialog("正在加载...");
            initData(count, currentPage);//请求数据
            recyclerView.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //下拉刷新
                    isFirst = true;
                    currentPage = 1;
                    initData(count, currentPage);
                }
            });
            recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //加载更多
                    isFirst = false;
                    initData(count, currentPage);
                }
            });
        } else if (page_position == 1 && rAdapter == null) {
            initRecyclerview(recyclerRelease);
            //发布头部view
            releaseHeadView = LayoutInflater.from(this).inflate(R.layout.guess_head_view_release, (ViewGroup) findViewById(android.R.id.content), false);
            findHeadViewById(releaseHeadView, page_position);
            //适配器
            rAdapter = new GuessMineReleaseAdapter(this);
            rAdapter.setShareRelease(this);
            rLAdapter = new LRecyclerViewAdapter(rAdapter);
            rLAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    GuessMineReleaseModel.DataBean.CreateListBean createListBean=rAdapter.getDataList().get(position);
                    if(createListBean.getStatus()==1){
                        //已开奖，点击弹出详情
                        getDetailsRequest(createListBean.getId());
                    }else{
                        //没开奖,只能分享
                        ToastUtils.showToast(GuessMineJoinActivity.this,"暂未开奖哦");
                    }
                }
            });
            recyclerRelease.setAdapter(rLAdapter);
            showLoaddingDialog("正在加载...");
            setVisibleGone();
            initData(rCount, rCurrentPage);//请求数据
            recyclerRelease.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //下拉刷新
                    rIsFirst = true;
                    rCurrentPage = 1;
                    initData(rCount, rCurrentPage);
                }
            });
            recyclerRelease.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //加载更多
                    rIsFirst = false;
                    initData(rCount, rCurrentPage);
                }
            });
        } else {
            if (page_position == 0) {
                if (!isVisible) {
                    recyclerView.setVisibility(View.GONE);
                    if (isNetWork) {
                        setNoDataArea(true, "您的网络不太好，点击重新加载");
                    } else {
                        setNoDataArea(false, "还没有参与任何题目哦");
                    }
                } else {
                    setVisibleGone();
                }
            } else {
                if (!isReleaseVisible) {
                    recyclerRelease.setVisibility(View.GONE);
                    if (isReleaseNetWork) {
                        setNoDataArea(true, "您的网络不太好，点击重新加载");
                    } else {
                        setNoDataArea(false, "还没有发布任何题目哦");
                    }
                } else {
                    setVisibleGone();
                }
            }
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        //获取页码
        page_position = getIntent().getIntExtra("page_position", 0);
        changePage(page_position);
        cropType = 1;
    }

    private void findHeadViewById(View view, int page_position) {
        if (page_position == 0) {
            joinLeftNumber = (TextView) view.findViewById(R.id.left_join_number);
            joinRightNumber = (TextView) view.findViewById(R.id.right_join_number);
        } else {
            leftNumber = (TextView) view.findViewById(R.id.left_text_number);
            rightNumber = (TextView) view.findViewById(R.id.right_text_number);
        }
    }

    private void initData(int ps, int pn) {
        //获取请求地址
        if (page_position == 0) requestStr = GuessApi.MINE_JOIN;
        else requestStr = GuessApi.MINE_RELEASE;
        //请求数据
        Bundle bundle = new Bundle();
        bundle.putString("ps", ps + "");
        bundle.putString("pn", pn + "");
        CckHttpClient.get(requestStr, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (page_position == 0) {
                    GuessJoinModel joinModel = JSON.parseObject(result, GuessJoinModel.class);
                    dealData(result, joinModel);
                } else {
                    GuessMineReleaseModel releaseModel = JSON.parseObject(result, GuessMineReleaseModel.class);
                    dealReleaseData(releaseModel, result);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineJoinActivity.this, ex.getMessage());
                if (page_position == 0) {
                    recyclerView.setVisibility(View.GONE);
                    isVisible = false;
                    isNetWork = true;
                } else {
                    recyclerRelease.setVisibility(View.GONE);
                    isReleaseVisible = false;
                    isReleaseNetWork = true;
                }
                setNoDataArea(true, "您的网络不太好，点击重新加载");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                if (page_position == 0) {
                    recyclerView.refreshComplete(count);
                } else {
                    recyclerRelease.refreshComplete(count);
                }
            }
        });
    }

    private GuessReceiveDataDialog dataDialog;

    public void getDetailsRequest(int id){
        Bundle bundle=new Bundle();
        bundle.putString("topicId",id+"");
        CckHttpClient.get(GuessApi.HANDICAP_DETAILS, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessHandicapDetailsModel model=JSON.parseObject(result,GuessHandicapDetailsModel.class);
                if(model.getErrorVo().getCode().equals("1000")){
                    //弹出对话框
                    if(dataDialog==null){
                        dataDialog=new GuessReceiveDataDialog(GuessMineJoinActivity.this);
                    }
                    dataDialog.setMineData(model);
                    dataDialog.show();
                }else{
                    getErrorMessage(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineJoinActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 处理我发布的数据
     *
     * @param releaseModel
     * @param result
     */
    private void dealReleaseData(GuessMineReleaseModel releaseModel, String result) {
        if (releaseModel.getErrorVo().getCode().equals("1000")) {
            recyclerRelease.setVisibility(View.VISIBLE);
            isReleaseVisible = true;
            setVisibleGone();
            //是否有下一页
            if (rCount == releaseModel.getData().getCreateList().size()) {
                rCurrentPage++;
            } else {
                if(isFirst){
                    recyclerRelease.setLoadMoreEnabled(false);
                }
            }
            if (rIsFirst) {//第一次加载或下拉刷新
                rList = releaseModel.getData().getCreateList();
                if (rList.size() > 0) {
                    rAdapter.setDataList(rList);
                } else {
                    setNoDataArea(false, "还没有发布任何题目哦");
                    recyclerRelease.setVisibility(View.GONE);
                    isReleaseVisible = false;
                    isReleaseNetWork = false;
                }
                //先删除头部在添加头部，做到数据更新
                rLAdapter.removeHeaderView();
                leftNumber.setText(releaseModel.getData().getDayCreate() + "");
                rightNumber.setText(releaseModel.getData().getTotalCreate() + "");
                rLAdapter.addHeaderView(releaseHeadView);
            } else {//加载更多
                List<GuessMineReleaseModel.DataBean.CreateListBean> addList = releaseModel.getData().getCreateList();
                if (addList.size() > 0) {
                    rList.addAll(addList);
                    rAdapter.setDataList(rList);
                    if (addList.size() < rCount) {
                        recyclerRelease.setNoMore(true);
                    }
                } else {
                    //加载完毕
                    recyclerRelease.setNoMore(true);
                }
            }
        } else {
            getErrorMessage(result);
        }
    }


    /**
     * 处理我参与的数据
     *
     * @param result
     * @param joinModel
     */
    private void dealData(String result, GuessJoinModel joinModel) {
        if (joinModel.getErrorVo().getCode().equals("1000")) {
            recyclerView.setVisibility(View.VISIBLE);
            isVisible = true;
            setVisibleGone();
            //是否有下一页
            if (count == joinModel.getData().getHistoryTopic().size()) {
                currentPage++;
            } else {
                if(isFirst){
                    recyclerView.setLoadMoreEnabled(false);
                }
            }
            if (isFirst) {//第一次加载或下拉刷新
                hisList = joinModel.getData().getHistoryTopic();
                if (hisList.size() > 0) {
                    adapter.setDataList(hisList);
                } else {
                    setNoDataArea(false, "还没有参与任何题目哦");
                    recyclerView.setVisibility(View.GONE);
                    isVisible = false;
                    isNetWork = false;
                }
                //先删除头部在添加头部，做到数据更新
                lAdapter.removeHeaderView();
                joinLeftNumber.setText(joinModel.getData().getDayWinNum() + "/" + joinModel.getData().getDayTotalNum());
                joinRightNumber.setText(joinModel.getData().getWinNum() + "/" + joinModel.getData().getTotalNum());
                lAdapter.addHeaderView(headView);
            } else {//加载更多
                List<GuessJoinModel.DataBean.HistoryTopicBean> addList = joinModel.getData().getHistoryTopic();
                if (addList.size() > 0) {
                    hisList.addAll(addList);
                    adapter.setDataList(hisList);
                    if (addList.size() < count) {
                        recyclerView.setNoMore(true);
                    }
                } else {
                    //加载完毕
                    recyclerView.setNoMore(true);
                }
            }
        } else {
            getErrorMessage(result);
        }
    }

    private GuessCreateNoticeDialog noticeDialog;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.back_image, R.id.mine_release_linear, R.id.mine_join_linear, R.id.new_create_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                this.finish();
                break;
            case R.id.mine_release_linear:
                if (page_position == 1) return;//若相同，则不需执行
                page_position = 1;
                changePage(page_position);
                break;
            case R.id.mine_join_linear:
                //切换到我参与的
                if (page_position == 0) return;//若相同，则不需执行
                page_position = 0;
                changePage(page_position);
                break;
            case R.id.new_create_btn:
                //请求数据判断用户是否有新建次数
                if(!(Boolean) SPUtils.get(this,"createNotice",false)){
                    noticeDialog=new GuessCreateNoticeDialog(this);
                    noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //隐藏监听
                            havePower();
                            newCreateBtn.setClickable(false);
                            SPUtils.put(GuessMineJoinActivity.this,"createNotice",true);
                        }
                    });
                    noticeDialog.show();
                }else{
                    havePower();
                    newCreateBtn.setClickable(false);
                }
                break;
            case R.id.buy_btn:
                serviceUtils.dismiss();
                //用户是否设置了支付密码
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
            case R.id.select_one:
                //拍照
                selectUtils.dismiss();
                isCreate=true;
                selectPhoto(this);
                break;
            case R.id.select_two:
                //图库选择
                isCreate=true;
                selectUtils.dismiss();
                pickFromGallery(this);
                break;
        }
    }

    /**
     * 裁剪成功后的逻辑
     */
    @Override
    public void cropAfter() {
        super.cropAfter();
        SystemUtils.StartByParams(this, GuessSendInfoActivity.class, null);
        this.finish();
    }

    //两种页码切换
    private void changePage(int position) {
        if (position == 0) {
            mineJoinLinear.setBackgroundColor(getResources().getColor(android.R.color.white));
            mineJoinText.setTextColor(getResources().getColor(R.color.actionbar_color));
            mineReleaseLinear.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
            mineReleaseText.setTextColor(getResources().getColor(android.R.color.white));
            recyclerView.setVisibility(View.VISIBLE);
            recyclerRelease.setVisibility(View.GONE);
        } else {
            mineJoinLinear.setBackgroundColor(getResources().getColor(R.color.actionbar_color));
            mineJoinText.setTextColor(getResources().getColor(android.R.color.white));
            mineReleaseLinear.setBackgroundColor(getResources().getColor(android.R.color.white));
            mineReleaseText.setTextColor(getResources().getColor(R.color.actionbar_color));
            recyclerView.setVisibility(View.GONE);
            recyclerRelease.setVisibility(View.VISIBLE);
        }
        initRecycler();
    }

    private void havePower() {
        showLoaddingDialog("正在初始化...");
        Bundle bundle = new Bundle();
        bundle.putString("type", 3 + "");
        CckHttpClient.get(GuessApi.HAVE_POWER, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessCreateModel createModel = JSON.parseObject(result, GuessCreateModel.class);
                dealCreateData(createModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineJoinActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                newCreateBtn.setClickable(true);
            }
        });
    }

    //购买新建盘口权限
    private GuessBuyServiceUtils serviceUtils;
    //输入支付密码购买
    private GuessBuyShoppingUtils shoppingUtils;
    //选择图库还是相册的对话框
    private GuessSelectUtils selectUtils;
    //盘口购买产品适配
    private GuessBuyCreateSerciveAdapter buyCreateSerciveAdapter;
    //用户选择的购买项
    private int buy_id;

    /**
     * 处理新建盘口数据
     *
     * @param createModel
     * @param result
     */
    private void dealCreateData(GuessCreateModel createModel, String result) {
        if (createModel.getErrorVo().getCode().equals("1000")) {
            //判断是否有权限
            if (createModel.getData().getEnable() == 0) {
                //没有权限，弹出购买弹框
                if (serviceUtils == null) {
                    serviceUtils = new GuessBuyServiceUtils(this, this);
                    serviceUtils.setCenterText("每日免费发布题目数量上限");
                }
                serviceUtils.setTitle("你当前每日可免费发布的题目数量上限为" + createModel.getData().getCurrentNum() + "个,已用完，提升至");
                //添加购买数据
                List<GuessCreateModel.DataBean.ListBean> listBeen = createModel.getData().getList();
                buyCreateSerciveAdapter = new GuessBuyCreateSerciveAdapter(this, listBeen);
                buyCreateSerciveAdapter.setHave("个");
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
            } else {
                //有权限，弹出选择图片的弹框
                if (selectUtils == null) {
                    selectUtils = new GuessSelectUtils(this, this);
                }
                selectUtils.show();
            }
        } else {
            getErrorMessage(result);
        }
    }

    //是否支付成功
    private boolean isPaySuccess = false;
    //提示用户充值对话框
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
                    ToastUtils.showToast(GuessMineJoinActivity.this, "购买成功");
                    isPaySuccess = true;
                    shoppingUtils.dismiss();
                    //购买成功后，弹出图片选择对话框
                    if (selectUtils == null) {
                        selectUtils = new GuessSelectUtils(GuessMineJoinActivity.this, GuessMineJoinActivity.this);
                    }
                    selectUtils.show();
                } else {
                    //1003,余额不足，引导用户去充值
                    if (simPleModel.getErrorVo().getCode().equals("1003")) {
                        shoppingUtils.dismiss();
                        if (lessMoneyUtils == null) {
                            lessMoneyUtils = new GuessLessMoneyUtils(GuessMineJoinActivity.this, GuessMineJoinActivity.this);
                        }
                        lessMoneyUtils.show();
                    } else {
                        ToastUtils.showToast(GuessMineJoinActivity.this, simPleModel.getErrorVo().getMsg());
                        showSoftInputView();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(GuessMineJoinActivity.this, ex.getMessage());
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

    private int share_type=0;//区分分享的类别，1为分享我参与的，2为分享我发布的
    private int currentSharePotion=-1;
    @Override
    public void shareJoin(int position) {
        //分享我参与的
        share_type=1;
        currentSharePotion=position;
        openShareBoard(shareBoardlistener);
    }

    @Override
    public void shareRelease(int position) {
        //分享我发布的
        share_type=2;
        currentSharePotion=position;
        openShareBoard(shareBoardlistener);
    }

    private String imageUrl;//分享图片地址
    private String title;//分享标题
    private String description;//分享描述
    private int id;//盘口id


    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            isCreate=false;
            if(share_type==1){
                imageUrl=adapter.getDataList().get(currentSharePotion).getPicUrl();
                description=adapter.getDataList().get(currentSharePotion).getDescription();
                id=adapter.getDataList().get(currentSharePotion).getId();
            }else if(share_type==2){
                imageUrl=rAdapter.getDataList().get(currentSharePotion).getPicUrl();
                description=rAdapter.getDataList().get(currentSharePotion).getDescription();
                id=rAdapter.getDataList().get(currentSharePotion).getId();
            }
            title = getResources().getString(R.string.share_title);
            //分享链接
            String url = "";
            if (share_media == SHARE_MEDIA.WEIXIN||share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {//微信
                url = Common.initModel.getData().getWeixinTopiShare();
            } else if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {//qq或qq空间
                url = Common.initModel.getData().getQqTopicShare();
            } else if (share_media == SHARE_MEDIA.SINA) {//新浪
                url = Common.initModel.getData().getSinaTopicShare();
            }
            if (TextUtils.isEmpty(url)) {
                ToastUtils.showToast(GuessMineJoinActivity.this, "分享链接不能为空，请检查您的网络设置");
                return;
            } else {
                //拼接上盘口id和用户userId
                url = url + "?topicId=" + id+ "&userId=" + SPUtils.get(GuessMineJoinActivity.this, "userId", 0);
            }
            share(imageUrl, url, title, description, share_media);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isCreate) {
            //新建
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            //分享回调
            umShareAPI.onActivityResult(requestCode, resultCode, data);
        }
    }
}
