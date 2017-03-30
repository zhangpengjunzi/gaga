package com.sinocall.guess.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sinocall.guess.BaseActivity;
import com.sinocall.guess.R;
import com.sinocall.guess.utils.ImageUtil;
import com.sinocall.guess.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页
 */
public class GuessGuideActivity extends BaseActivity {


    @BindView(R.id.guide_viewpager)
    ViewPager guideViewpager;
    @BindView(R.id.switch_to_main)
    LinearLayout switchToMain;

    private int currentIndex;//下标索引
    private int[] pics;//图片集合
    private List<View> pagerList;//view集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guess_activity_guide);
        ButterKnife.bind(this);
        initData();
        initView();
    }


    private void initData() {
        currentIndex = 0;
        pics = new int[]{
                R.mipmap.guidepage1, R.mipmap.guidepage2, R.mipmap.guidepage3};
        pagerList = new ArrayList<View>();
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(GuessGuideActivity.this);
            iv.setLayoutParams(params);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setFitsSystemWindows(true);
            //加载图片的几种方式，占用内存对比
            //第一种，三张图片占用15多M
//            iv.setImageResource(pics[i]);
            //第二种，三张图片占用15多M，比第一种占用少
//            Bitmap bm = BitmapFactory.decodeResource(this.getResources(),pics[i]);
//            iv.setImageBitmap(bm);
            //第三种，根据压缩率不同，占用内存也不相同，400×300占用不到3M
            Bitmap bm = ImageUtil.loadImageFromResource(getResources(), pics[i], 600, 400);
            iv.setImageBitmap(bm);
            pagerList.add(iv);
        }

    }

    private void initView() {
        guideViewpager.setAdapter(new GuidePagerAdapter(pagerList));
        guideViewpager.setOnPageChangeListener(new GuidePagerListener());

        switchToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.put(GuessGuideActivity.this, "first_step", false);
                goMain();
            }
        });

    }

    private void goMain() {
        //跳转到登录页
        startActivity(new Intent(GuessGuideActivity.this, GuessLoginActivity.class));
        finish();
    }


    private class GuidePagerAdapter extends PagerAdapter {
        private List<View> pagerList;

        public GuidePagerAdapter(List<View> pagerList) {
            this.pagerList = pagerList;
        }

        @Override
        public int getCount() {
            return pagerList.size();
        }

        @Override
        public boolean isViewFromObject(View v, Object obj) {
            return (v == obj);
        }

        @Override
        public Object instantiateItem(View container, int pos) {
            ((ViewPager) container).addView(pagerList.get(pos), 0);
            return pagerList.get(pos);
        }

        @Override
        public void destroyItem(View container, int pos, Object obj) {
            ((ViewPager) container).removeView(pagerList.get(pos));
        }
    }

    private class GuidePagerListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position < 0 || position >= pics.length) {
                return;
            }
            guideViewpager.setCurrentItem(position);
           /* if (position == pics.length - 1) {
                switchToMain.setVisibility(View.VISIBLE);
            } else {
                switchToMain.setVisibility(View.GONE);
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }



}
