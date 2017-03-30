package com.sinocall.guess.activitys;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jungly.gridpasswordview.GridPasswordView;
import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.GuessApi;
import com.sinocall.guess.R;
import com.sinocall.guess.adapter.GuessBuyCreateSerciveAdapter;
import com.sinocall.guess.adapter.GuessMainListAdapter;
import com.sinocall.guess.dialog.GuessBuyServiceUtils;
import com.sinocall.guess.dialog.GuessBuyShoppingUtils;
import com.sinocall.guess.dialog.GuessCreateNoticeDialog;
import com.sinocall.guess.dialog.GuessLessMoneyUtils;
import com.sinocall.guess.dialog.GuessMainDialog;
import com.sinocall.guess.dialog.GuessNewGuideDialog;
import com.sinocall.guess.dialog.GuessReportUtils;
import com.sinocall.guess.dialog.GuessSelectUtils;
import com.sinocall.guess.fragment.GuessMainFragment;
import com.sinocall.guess.http.CckHttpClient;
import com.sinocall.guess.model.GuessCreateModel;
import com.sinocall.guess.model.GuessDiscoverModel;
import com.sinocall.guess.model.GuessMainListModel;
import com.sinocall.guess.model.GuessReportModel;
import com.sinocall.guess.model.GuessUserModel;
import com.sinocall.guess.model.SimPleModel;
import com.sinocall.guess.utils.Common;
import com.sinocall.guess.utils.DESUtils;
import com.sinocall.guess.utils.DensityUtil;
import com.sinocall.guess.utils.MD5Utils;
import com.sinocall.guess.utils.SPUtils;
import com.sinocall.guess.utils.ScreenUtils;
import com.sinocall.guess.utils.SystemUtils;
import com.sinocall.guess.utils.ToastUtils;
import com.sinocall.guess.view.CircleImageView;
import com.sinocall.guess.view.MyViewPager;
import com.sinocall.guess.view.PullScrollView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.wx.goodview.GoodView;
import com.zhy.android.percent.support.PercentFrameLayout;
import com.zhy.android.percent.support.PercentLinearLayout;

import org.xutils.common.Callback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.user_headimage)
    CircleImageView userHeadimage;
    @BindView(R.id.user_nickname)
    TextView userNickname;
    /*@BindView(R.id.user_rank)
    TextView userRank;*/
    @BindView(R.id.join_number)
    TextView joinNumber;
    @BindView(R.id.mine_join_linear)
    LinearLayout mineJoinLinear;
    @BindView(R.id.water_number)
    TextView waterNumber;
    @BindView(R.id.mine_water_linear)
    LinearLayout mineWaterLinear;
    @BindView(R.id.release_number)
    TextView releaseNumber;
    @BindView(R.id.mine_release_linear)
    LinearLayout mineReleaseLinear;
    @BindView(R.id.slide_top)
    PercentLinearLayout slideTop;
    @BindView(R.id.pay_center_linear)
    PercentLinearLayout payCenterLinear;
    @BindView(R.id.exchange_center_linear)
    PercentLinearLayout exchangeCenterLinear;
    @BindView(R.id.help_center_linear)
    PercentLinearLayout helpCenterLinear;
    @BindView(R.id.settings_linear)
    PercentLinearLayout settingsLinear;
    @BindView(R.id.image_rank)
    ImageView imageRank;
    @BindView(R.id.vip_linear)
    PercentLinearLayout vipLinear;
    @BindView(R.id.main_head_image)
    CircleImageView mainHeadImage;
    @BindView(R.id.main_beans_number)
    TextView mainBeansNumber;
    @BindView(R.id.main_help)
    LinearLayout mainHelp;
    @BindView(R.id.viewpager_view)
    MyViewPager viewpagerView;
    @BindView(R.id.main_data_linear)
    PercentLinearLayout mainDataLinear;
    @BindView(R.id.main_pullscroll)
    PullScrollView mainPullscroll;
    @BindView(R.id.main_add_money)
    LinearLayout mainAddMoney;
    @BindView(R.id.main_red_btn)
    ImageView mainRedBtn;
    @BindView(R.id.main_blue_btn)
    ImageView mainBlueBtn;
    @BindView(R.id.main_bottom_frame)
    PercentLinearLayout mainBottomFrame;
    @BindView(R.id.main_title)
    PercentFrameLayout mainTitle;
    @BindView(R.id.common_text)
    TextView commonText;
    @BindView(R.id.common_linear)
    LinearLayout commonLinear;
    @BindView(R.id.help_center_share)
    PercentLinearLayout helpCenterShare;
    @BindView(R.id.rank_sort)
    PercentLinearLayout rankSort;

    private long new_id = 0;//数据集合中最新的id
    private long old_id = 0;//数据集合中最旧的id
    private final int COUNT = 5;
    private GuessMainDialog dialog;//对话框，分享，新建，举报功能
    private GuessReportUtils reportUtils;//举报对话框
    private GuessCreateNoticeDialog noticeDialog;//新建提示对话框
    private GoodView goodView;//点击飘心效果
    private boolean isCreate = true;//是否正在新建盘口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        //侧滑
        showLoaddingDialog("正在加载...");
        //请求列表数据
        mainDataLinear.setVisibility(View.GONE);
        requestListData("0", "1");
        //初始化pullscroview
        mainPullscroll.setHeaderViewColor(R.color.actionbar_color, R.color.login_btn, android.R.color.white);
        mainPullscroll.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        mainPullscroll.setRefreshListener(new PullScrollView.RefreshListener() {
            @Override
            public void onRefresh() {//首页下拉刷新
                requestListData("0", "1");
            }
        });
        viewpagerView.setLayoutParams(new PercentLinearLayout.LayoutParams(ScreenUtils.getWindowWidth(this), (ScreenUtils.getWindowHeigh(this) - DensityUtil.dip2px(this, 80)) / 5 * 4));
        mainBottomFrame.setLayoutParams(new PercentLinearLayout.LayoutParams(ScreenUtils.getWindowWidth(this), (ScreenUtils.getWindowHeigh(this) - DensityUtil.dip2px(this, 80)) / 5));
        commonLinear.setLayoutParams(new PercentLinearLayout.LayoutParams(ScreenUtils.getWindowWidth(this), ScreenUtils.getWindowHeigh(this) - DensityUtil.dip2px(this, 80)));
        goodView = new GoodView(this);
        goodView.setDuration(500);
        //设置裁剪类型
        cropType = 1;
        //监听侧滑打开状态，打开加载数据
        setDrawerLeftEdgeSize(this, drawerLayout, 0);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                initData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    /**
     * 请求侧滑数据
     */
    private void initData() {
        CckHttpClient.get(GuessApi.USER_INFO, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessUserModel userModel = JSON.parseObject(result, GuessUserModel.class);
                dealData(userModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
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
     * 请求列表数据
     */
    private void requestListData(String id, String type) {//第一次请求
        Bundle bundle = new Bundle();
        bundle.putString("id", id);//id代表要请求的某条数据id,第一次为1
        bundle.putString("type", type);//type为1代表请求最新的，2代表请求相对旧的数据
        bundle.putString("ps", COUNT + "");//数据量
        CckHttpClient.get(GuessApi.MAIN_LIST, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessMainListModel listModel = JSON.parseObject(result, GuessMainListModel.class);
                dealListData(result, listModel);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
                commonLinear.setVisibility(View.VISIBLE);
                commonText.setText("加载失败，下拉可重新加载");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                mainPullscroll.setRefreshCompleted();//下拉刷新完成
            }
        });
    }

    int covertIndex = 0;//将要跳转到的页面

    /**
     * 请求最新的数据
     *
     * @param id
     * @param type
     * @param state    上一页数据的状态
     * @param position 上一页数据的位置
     */
    private void requestNewListData(String id, final String type, final int state, final int position) {//第一次请求
        mainDataLinear.setVisibility(View.GONE);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);//id代表要请求的某条数据id,第一次为1
        bundle.putString("type", type);//type为1代表请求最新的，2代表请求相对旧的数据
        bundle.putString("ps", COUNT + "");//数据量
        CckHttpClient.get(GuessApi.MAIN_LIST, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessMainListModel listModel = JSON.parseObject(result, GuessMainListModel.class);
                if (listModel.getErrorVo().getCode().equals("1000")) {
                    List<GuessMainListModel.DataBean.ListBean> addList = listModel.getData().getList();
                    if (addList.size() > 0) {
                        if (type.equals("1")) {//新数据
                            //将新更新的数据覆盖到原来的页面
                            int count = COUNT;
                            for (int i = addList.size(); i > 0; i--) {
                                for (int j = count; j > 0; j--) {
                                    adapter.getListFragments().get(count).setAddModel(addList.get(i - 1));
                                    adapter.getListFragments().get(count).setState(1);
                                    count--;
                                    break;
                                }
                            }
                            //若更新的数据不足，则将有数据页面的前一页设置为无数据页面
                            if (COUNT > addList.size()) {
                                if (COUNT - addList.size() == 1) {
                                    adapter.getListFragments().get(COUNT - addList.size()).setState(2);
                                    adapter.getListFragments().get(COUNT - addList.size()).setAddModel(new GuessMainListModel.DataBean.ListBean());
                                } else {
                                    for (int i = COUNT - addList.size(); i > 0; i--) {
                                        if (i == COUNT - addList.size()) {
                                            adapter.getListFragments().get(COUNT - addList.size()).setState(2);
                                            adapter.getListFragments().get(COUNT - addList.size()).setAddModel(new GuessMainListModel.DataBean.ListBean());
                                        } else {
                                            adapter.getListFragments().get(i).setState(2);
                                            adapter.getListFragments().get(i).setAddModel(null);
                                        }
                                    }
                                }
                            }
                            covertIndex = COUNT;
                        } else {//旧数据
                            //将新更新的数据覆盖到原来的页面
                            for (int i = 0; i < addList.size(); i++) {
                                adapter.getListFragments().get(i + 1).setAddModel(addList.get(i));
                                adapter.getListFragments().get(i + 1).setState(1);
                            }
                            //若更新的数据不足，则将有数据页面的后一页设置为无数据页面
                            if (COUNT > addList.size()) {
                                if (COUNT - addList.size() == 1) {
                                    adapter.getListFragments().get(COUNT).setState(2);
                                    adapter.getListFragments().get(COUNT).setAddModel(new GuessMainListModel.DataBean.ListBean());
                                } else {
                                    for (int i = addList.size() + 1; i <= COUNT; i++) {
                                        if (i == addList.size() + 1) {
                                            adapter.getListFragments().get(i).setState(2);
                                            adapter.getListFragments().get(i).setAddModel(new GuessMainListModel.DataBean.ListBean());
                                        } else {
                                            adapter.getListFragments().get(i).setState(2);
                                            adapter.getListFragments().get(i).setAddModel(null);
                                        }
                                    }
                                }
                            }
                            covertIndex = 1;
                        }
                        new_id = addList.get(0).getId();//更新新的id
                        old_id = addList.get(addList.size() - 1).getId();//更新旧的id
                    } else {
                        covertIndex = position;
                        if (state == 2 || state == 3) {
                            //若无数据，且上个页面不是正常状态，则更新成无数据状态
                            adapter.getListFragments().get(position).setState(2);
                        } else {
                            ToastUtils.showToast(MainActivity.this, "没有更多的题目了");
                        }
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            positionItem = covertIndex;
                            viewpagerView.setCurrentItem(covertIndex, false);
                        }
                    }, 200);
                } else {
                    getErrorMessage(result);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            positionItem = position;
                            viewpagerView.setCurrentItem(position, false);
                        }
                    }, 200);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
                if (state != 1) {
                    //若无数据，且上个页面不是正常状态，则更新成无网络状态
                    adapter.getListFragments().get(position).setState(3);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        positionItem = position;
                        viewpagerView.setCurrentItem(position, false);
                    }
                }, 200);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                dismissLoaddingDialog();
                mainDataLinear.setVisibility(View.VISIBLE);
            }
        });
    }


    GuessMainListAdapter adapter;
    private int positionItem = 1;//记录上一页的页码
    private int direction = 0;//记录用户滑动方向
    private boolean isRegular = true;//本页面是否正常

    /**
     * 处理列表数据
     *
     * @param result
     * @param listModel
     */
    private void dealListData(String result, GuessMainListModel listModel) {
        mainDataLinear.setVisibility(View.VISIBLE);
        if (listModel.getErrorVo().getCode().equals("1000")) {
            commonLinear.setVisibility(View.GONE);
            mainDataLinear.setVisibility(View.VISIBLE);
            //剩余金豆数
            mainBeansNumber.setText(listModel.getData().getRemain() + "");
            List<GuessMainListModel.DataBean.ListBean> listBean = listModel.getData().getList();
            if (listBean != null && listBean.size() > 0) {
                List<GuessMainFragment> fragments = new ArrayList<>();
                //做无限循环的viewpager,需要两个额外的页面做跳板
                for (int i = 0; i < COUNT + 2; i++) {
                    if (i == 0 || i == COUNT + 1) {  //将第一个和最后一个最为跳板
                        //添加空的fragment
                        GuessMainFragment guessFragment = new GuessMainFragment();
                        guessFragment.setState(2);
                        fragments.add(guessFragment);
                    } else {
                        if (i <= listBean.size()) {
                            //添加有数据的fragment
                            int index = i - 1;
                            fragments.add(GuessMainFragment.newInstance(listBean.get(index)));
                        } else {
                            if (COUNT - listBean.size() > 1) {
                                //数据量远少于需要的页面,将数据页面之后的一个页面填充为无数据页面
                                if (i - listBean.size() == 1) {
                                    GuessMainFragment guessFragment = new GuessMainFragment();
                                    guessFragment.setState(2);
                                    guessFragment.setAddModel(new GuessMainListModel.DataBean.ListBean());
                                    fragments.add(guessFragment);
                                } else {
                                    //多余的页面，添加空的fragment
                                    GuessMainFragment guessFragment = new GuessMainFragment();
                                    guessFragment.setState(2);
                                    fragments.add(guessFragment);
                                }
                            } else {//将最后一个页面填充为无数据页面
                                GuessMainFragment guessFragment = new GuessMainFragment();
                                guessFragment.setState(2);
                                guessFragment.setAddModel(new GuessMainListModel.DataBean.ListBean());
                                fragments.add(guessFragment);
                            }
                        }
                    }
                }
                new_id = listBean.get(0).getId();
                old_id = listBean.get(listBean.size() - 1).getId();
                final int size = fragments.size();//大小
                viewpagerView.removeAllViewsInLayout();
                adapter = new GuessMainListAdapter(getSupportFragmentManager());
                adapter.setListFragments(fragments);
                viewpagerView.setAdapter(adapter);
                viewpagerView.setCurrentItem(1);//第2页为第一条数据
                viewpagerView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == positionItem) {
                            direction = 0;//不产生任何事件
                        }

                        if (position > positionItem) {//用户向左滑了
                            positionItem = position;
                            direction = 1;
                        }
                        if (position < positionItem) {//用户向右滑了
                            positionItem = position;
                            direction = 2;
                        }

                        if (adapter.getListFragments().get(position).getState() == 1) {
                            isRegular = true;
                        } else {
                            isRegular = false;
                        }

                        if (direction == 2) {
                            if (position == 0) {//当前页面是首位的扩展页，则加载数据
                                showLoaddingDialog("正在加载...");
                                requestNewListData(new_id + "", "1", adapter.getListFragments().get(1).getState(), 1);
                            } else {
                                //不是首位扩展页，判断上个页面是不是正常状态，不是则加载新数据
                                if (adapter.getListFragments().get(position + 1).getState() != 1 && !isRegular) {
                                    showLoaddingDialog("正在加载...");
                                    requestNewListData(new_id + "", "1", adapter.getListFragments().get(position + 1).getState(), position + 1);
                                }
                            }
                        } else if (direction == 1) {
                            if (position == size - 1) {
                                //当前页面是不是最后一个扩展页，是的话加载旧的数据
                                showLoaddingDialog("正在加载...");
                                requestNewListData(old_id + "", "2", adapter.getListFragments().get(size - 2).getState(), size - 2);
                            } else {
                                //不是最后一个扩展页，查看上一个页面是否是正常状态，不是则加载数据
                                if (adapter.getListFragments().get(position - 1).getState() != 1 && !isRegular) {
                                    showLoaddingDialog("正在加载...");
                                    requestNewListData(old_id + "", "2", adapter.getListFragments().get(position - 1).getState(), position - 1);
                                }
                            }
                        }
                        //跳转页面不显示按钮
                        if (adapter.getListFragments().get(position).getModel() == null) {
                            mainBottomFrame.setVisibility(View.GONE);
                        } else {
                            mainBottomFrame.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            } else {
                //首页无数据
                commonLinear.setVisibility(View.VISIBLE);
                mainDataLinear.setVisibility(View.GONE);
                commonText.setText("暂无题目哦");
                if (viewpagerView != null) {
                    viewpagerView.removeAllViewsInLayout();
                }
                if (adapter != null) adapter = null;
            }
        } else {
            commonLinear.setVisibility(View.VISIBLE);
            mainDataLinear.setVisibility(View.GONE);
            commonText.setText(listModel.getErrorVo().getMsg());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //更新头像
        if (!SPUtils.get(this, "headImage", "").equals("")) {
            Glide.with(this).load(SPUtils.get(this, "headImage", "")).into(userHeadimage);
            Glide.with(this).load(SPUtils.get(this, "headImage", "")).into(mainHeadImage);
        } else {
            Glide.with(this).load(R.mipmap.avatar_recharge).into(userHeadimage);
            Glide.with(this).load(R.mipmap.avatar_homwpage).into(mainHeadImage);
        }
        //用户昵称
        if (!SPUtils.get(this, "nickName", "").equals("")) {
            userNickname.setText((String) SPUtils.get(this, "nickName", ""));
        }
        //开启新手引导
        if ((Boolean) SPUtils.get(this, "new_guide", true)) {
            GuessNewGuideDialog newGuideDialog = new GuessNewGuideDialog(this);
            newGuideDialog.show();
            SPUtils.put(this, "new_guide", false);//只显示一次
        }
        /*更新余额*/
        moneyLess();
    }

    /**
     * 处理首页数据
     *
     * @param userModel
     * @param result
     */
    private void dealData(GuessUserModel userModel, String result) {
        if (userModel.getErrorVo().getCode().equals("1000")) {
            String level = userModel.getData().getLevel();
            if (level.equals("举人")) {
                imageRank.setImageResource(R.mipmap.vip0_mine);
            } else if (level.equals("进士")) {
                imageRank.setImageResource(R.mipmap.vip1_mine);
            } else if (level.equals("探花")) {
                imageRank.setImageResource(R.mipmap.vip2_mine);
            } else if (level.equals("榜眼")) {
                imageRank.setImageResource(R.mipmap.vip3_mine);
            } else if (level.equals("状元")) {
                imageRank.setImageResource(R.mipmap.vip4_mine);
            }
            /*参加的活动数量*/
            joinNumber.setText(userModel.getData().getDayTakePartNum() + "");
            /*发布的活动数量*/
            releaseNumber.setText(userModel.getData().getDayCreateNum() + "");
            /*我的流水数量*/
            waterNumber.setText(userModel.getData().getDayBuyCoin() + "");
        } else {
            getErrorMessage(result);
            dismissLoaddingDialog();
            mainPullscroll.setRefreshCompleted();//下拉刷新完成
        }
    }

    /**
     * 设置侧滑在主页面拉取宽度
     *
     * @param activity
     * @param drawerLayout
     * @param displayWidthPercentage
     */
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick({R.id.vip_linear, R.id.mine_join_linear, R.id.mine_water_linear, R.id.mine_release_linear, R.id.pay_center_linear, R.id.exchange_center_linear, R.id.help_center_linear, R.id.settings_linear, R.id.main_head_image, R.id.main_add_money, R.id.main_red_btn, R.id.main_blue_btn, R.id.main_help, R.id.help_center_share,R.id.rank_sort})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vip_linear:
                startActivity(new Intent(this, GuessVipCenterActivity.class));
                break;
            case R.id.mine_join_linear:
                //我参与的
                Bundle bundle = new Bundle();
                bundle.putInt("page_position", 0);
                SystemUtils.StartByParams(this, GuessMineJoinActivity.class, bundle);
                break;
            case R.id.mine_water_linear:
                //我的流水
                startActivity(new Intent(this, GuessMiniWaterActivity.class));
                break;
            case R.id.pay_center_linear:
                //进入充值中心页面
                startActivity(new Intent(this, GuessPayCenterActivity.class));
                break;
            case R.id.exchange_center_linear:
                //兑换中心
                startActivity(new Intent(this, GuessConvertCenterActivity.class));
                break;
            case R.id.help_center_linear:
                //帮助中心
                Bundle help = new Bundle();
                help.putString("name", "帮助中心");
                help.putString("url", Common.initModel.getData().getUserHelp());
                SystemUtils.StartByParams(this, GuessWebViewActivity.class, help);
                break;
            case R.id.help_center_share:
                //让朋友赚钱
                Bundle friend = new Bundle();
                friend.putString("name", "合伙人");
                friend.putInt("type", 1);//代表右上角是分享合伙人
                friend.putString("url", Common.initModel.getData().getPartnerHelp());
                SystemUtils.StartByParams(this, GuessWebViewActivity.class, friend);
                break;
            case R.id.settings_linear:
                //设置
                startActivity(new Intent(this, GuessMineSettingsActivity.class));
                break;
            case R.id.main_head_image:
                //打开侧滑
                drawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.main_help:
                //更多
                if (dialog == null) {
                    dialog = new GuessMainDialog(this, this);
                }
                dialog.showPopupWindow(mainHelp);
                break;
            case R.id.main_add_money:
                //跳转到支付界面
                startActivity(new Intent(this, GuessPayCenterActivity.class));
                break;
            case R.id.main_red_btn:
                //点击红色方
                if (adapter.getListFragments().get(viewpagerView.getCurrentItem()).getState() != 1) {
                    return;//非正常状态下点击没有效果
                }
                discover(1);
                mainRedBtn.setClickable(false);
                break;
            case R.id.main_blue_btn:
                //点击蓝色方
                if (adapter.getListFragments().get(viewpagerView.getCurrentItem()).getState() != 1) {
                    return;
                }
                discover(2);
                mainBlueBtn.setClickable(false);
                break;
            case R.id.buy_btn:
                //购买
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
            case R.id.new_pop_btn:
                dialog.dismiss();
                //新建时需要弹出新建提示对话框，只弹一次
                if (!(Boolean) SPUtils.get(this, "createNotice", false)) {
                    noticeDialog = new GuessCreateNoticeDialog(this);
                    noticeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialogInterface) {
                            //隐藏监听
                            havePower();
                            SPUtils.put(MainActivity.this, "createNotice", true);
                        }
                    });
                    noticeDialog.show();
                } else {
                    havePower();
                }
                break;
            case R.id.mine_release_linear:
                //我发布的
                Bundle release = new Bundle();
                release.putInt("page_position", 1);
                SystemUtils.StartByParams(this, GuessMineJoinActivity.class, release);
                break;
            case R.id.report_pop_btn:
                //举报投诉
                dialog.dismiss();
                if (adapter == null) return;
                if (adapter.getListFragments().size() < 3) return;
                if (adapter.getListFragments().get(viewpagerView.getCurrentItem()).getState() != 1)
                    return;
                if (Common.reportJSon != null) {
                    //缓存
                    GuessReportModel reportModel = JSON.parseObject(Common.reportJSon, GuessReportModel.class);
                    dealReportData(reportModel, Common.reportJSon);
                } else {
                    //请求举报列表数据
                    requestReportData();
                }
                break;
            case R.id.select_one:
                //拍照
                selectUtils.dismiss();
                isCreate = true;
                selectPhoto(this);
                break;
            case R.id.select_two:
                //图库选择
                selectUtils.dismiss();
                isCreate = true;
                pickFromGallery(this);
                break;
            case R.id.share_pop_btn:
                //分享
                dialog.dismiss();
                if (adapter == null) return;
                if (adapter.getListFragments().size() < 3) return;
                if (adapter.getListFragments().get(viewpagerView.getCurrentItem()).getState() != 1) {
                    ToastUtils.showToast(this, "请选择正确的分享内容");
                    return;
                }
                isCreate = false;
                //打开分享面板
                openShareBoard(shareBoardlistener);
                break;
            case R.id.rank_sort:
                Bundle rank = new Bundle();
                rank.putString("name", "排行榜");
                rank.putString("url", Common.initModel.getData().getTopH5()+ "?userId=" + SPUtils.get(this, "userId", 0) + "&lId=" + SPUtils.get(this, "lId", ""));
                SystemUtils.StartByParams(this, GuessWebViewActivity.class, rank);
                break;
        }
    }

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            String imageUrl = adapter.getListFragments().get(viewpagerView.getCurrentItem()).getModel().getPicUrl();
            String title = getResources().getString(R.string.share_title);
            String description = adapter.getListFragments().get(viewpagerView.getCurrentItem()).getModel().getDescription();
            //分享链接
            String url = "";
            if (share_media == SHARE_MEDIA.WEIXIN || share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {//微信
                url = Common.initModel.getData().getWeixinTopiShare();
            } else if (share_media == SHARE_MEDIA.QQ || share_media == SHARE_MEDIA.QZONE) {//qq或qq空间
                url = Common.initModel.getData().getQqTopicShare();
            } else if (share_media == SHARE_MEDIA.SINA) {//新浪
                url = Common.initModel.getData().getSinaTopicShare();
            }
            if (TextUtils.isEmpty(url)) {
                ToastUtils.showToast(MainActivity.this, "分享链接不能为空，请检查您的网络设置");
                return;
            } else {
                //拼接上盘口id和用户userId
                url = url + "?topicId=" + adapter.getListFragments().get(viewpagerView.getCurrentItem()).getModel().getId() + "&userId=" + SPUtils.get(MainActivity.this, "userId", 0);
            }
            share(imageUrl, url, title, description, share_media);
        }
    };


    @Override
    public void cropAfter() {
        super.cropAfter();
        //处理裁剪图片之后的逻辑
        SystemUtils.StartByParams(this, GuessSendInfoActivity.class, null);
    }

    private void havePower() {
        showLoaddingDialog("正在初始化...");
        Bundle bundle = new Bundle();
        bundle.putString("type", 3 + "");
        CckHttpClient.get(GuessApi.HAVE_POWER, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessCreateModel createModel = JSON.parseObject(result, GuessCreateModel.class);
                dealNewCreate(createModel, result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
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
     * 请求举报列表
     */
    private void requestReportData() {
        showLoaddingDialog("正在初始化举报信息...");
        CckHttpClient.get(GuessApi.REPORT_LIST, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessReportModel reportModel = JSON.parseObject(result, GuessReportModel.class);
                if (reportModel.getErrorVo().getCode().equals("1000")) {
                    dealReportData(reportModel, result);
                } else {
                    getErrorMessage(result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
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
     * 处理举报列表数据
     *
     * @param reportModel
     * @param result
     */
    private void dealReportData(GuessReportModel reportModel, String result) {
        if (Common.reportJSon == null) Common.reportJSon = result;//缓存举报信息
        if (reportModel.getData().getList().size() > 0) {
            //举报对话框
            if (reportUtils == null) {
                reportUtils = new GuessReportUtils(MainActivity.this);
            }
            reportUtils.setData(reportModel.getData().getList(), adapter.getListFragments().get(viewpagerView.getCurrentItem()).getModel().getId());
            reportUtils.show();
        }
    }

    /**
     * 点击玩游戏
     */
    private void discover(final int choice) {
        Bundle bundle = new Bundle();
        //获取当前id
        final int index = viewpagerView.getCurrentItem();
        bundle.putString("topicId", adapter.getListFragments().get(index).getModel().getId() + "");
        bundle.putString("choice", choice + "");
        CckHttpClient.get(GuessApi.MAIN_CACHE, bundle, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                GuessDiscoverModel discoverModel = JSON.parseObject(result, GuessDiscoverModel.class);
                dealDiscoverData(discoverModel, result, choice, index);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mainRedBtn.setClickable(true);
                mainBlueBtn.setClickable(true);
            }
        });
    }


    /**
     * 处理下注数据
     *
     * @param discoverModel
     * @param result
     */
    private void dealDiscoverData(final GuessDiscoverModel discoverModel, String result, final int choice, final int id) {
        if (discoverModel.getErrorVo().getCode().equals("1000")) {
            if (choice == 1) {
                //飘红心
                goodView.setImage(R.mipmap.heart_red);
                goodView.show(mainRedBtn);
            } else {
                //飘蓝心
                goodView.setImage(R.mipmap.heart_blue);
                goodView.show(mainBlueBtn);
            }
            //动画结束后执行逻辑
            goodView.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //修改已下注数据
                    adapter.getListFragments().get(id).setSupport(choice, discoverModel.getData().getSupport());
                    //根据已下注金额改变进度条百分比
                    adapter.getListFragments().get(id).updateProcess(discoverModel.getData().getRecordCoin());
                    //改变金豆余额显示
                    mainBeansNumber.setText(discoverModel.getData().getRemain() + "");
                }
            });
        } else {
            if (discoverModel.getErrorVo().getCode().equals("1004")) {
                //单个题目投注金额达上限，需要购买
                GuessCreateModel createModel = JSON.parseObject(result, GuessCreateModel.class);
                dealCreateData(createModel, 1004);
            } else if (discoverModel.getErrorVo().getCode().equals("1003")) {
                //每日可参与竞猜题目个数
                GuessCreateModel createModel = JSON.parseObject(result, GuessCreateModel.class);
                dealCreateData(createModel, 1003);
            } else if (discoverModel.getErrorVo().getCode().equals("1002")) {
                //余额不足
                if (lessMoneyUtils == null) {
                    lessMoneyUtils = new GuessLessMoneyUtils(MainActivity.this, MainActivity.this);
                }
                lessMoneyUtils.show();
            } else {
                getErrorMessage(result);
            }
        }
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
     * @param code
     */
    private void dealCreateData(GuessCreateModel createModel, int code) {
        //弹出购买弹框
        if (serviceUtils == null) {
            serviceUtils = new GuessBuyServiceUtils(this, this);
        }
        //添加购买数据
        List<GuessCreateModel.DataBean.ListBean> listBeen = createModel.getData().getList();
        buyCreateSerciveAdapter = new GuessBuyCreateSerciveAdapter(this, listBeen);
        if (code == 1004) {
            serviceUtils.setCenterText("每日单个题目参与次数上限");
            serviceUtils.setTitle("你当前每个题目可免费参与的次数上限为" + createModel.getData().getCurrentNum() + "次,已用完，提升至");
            buyCreateSerciveAdapter.setHave("次");
        } else {
            serviceUtils.setCenterText("每日题目参与个数上限");
            serviceUtils.setTitle("你当前每日可免费参与的题目数量上限为" + createModel.getData().getCurrentNum() + "个,已用完，提升至");
            buyCreateSerciveAdapter.setHave("个");
        }
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


    /**
     * 处理新建盘口数据
     *
     * @param createModel
     * @param result
     */
    private void dealNewCreate(GuessCreateModel createModel, String result) {
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
                    ToastUtils.showToast(MainActivity.this, "购买成功");
                    mainBeansNumber.setText(simPleModel.getData().getRemain() + "");
                    isPaySuccess = true;
                    shoppingUtils.dismiss();
                } else {
                    //1003,余额不足，引导用户去充值
                    if (simPleModel.getErrorVo().getCode().equals("1003")) {
                        shoppingUtils.dismiss();
                        if (lessMoneyUtils == null) {
                            lessMoneyUtils = new GuessLessMoneyUtils(MainActivity.this, MainActivity.this);
                        }
                        lessMoneyUtils.show();
                    } else {
                        ToastUtils.showToast(MainActivity.this, simPleModel.getErrorVo().getMsg());
                        showSoftInputView();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
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

    /**
     * 请求最新余额
     */
    private void moneyLess() {
        CckHttpClient.get(GuessApi.MONEY_LESS, null, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                SimPleModel simPleModel = JSON.parseObject(result, SimPleModel.class);
                if (simPleModel.getErrorVo().getCode().equals("1000")) {
                    mainBeansNumber.setText(simPleModel.getData().getRemain() + "");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                CckHttpClient.error(MainActivity.this, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再点一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
