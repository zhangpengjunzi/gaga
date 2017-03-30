package com.sinocall.guess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinocall.guess.R;
import com.sinocall.guess.base.ListBaseAdapter;
import com.sinocall.guess.base.SuperViewHolder;
import com.sinocall.guess.model.GuessJoinModel;
import com.sinocall.guess.utils.RelativeDateFormat;

/**
 * Created by Administrator on 2017/2/20.
 */

public class GuessMineJoinAdapter extends ListBaseAdapter<GuessJoinModel.DataBean.HistoryTopicBean> {

    private ShareJoin join;

    public GuessMineJoinAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.guess_list_item_mine_release_do;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final GuessJoinModel.DataBean.HistoryTopicBean historyTopicBean=mDataList.get(position);
        //投注图片
        ImageView imageView=holder.getView(R.id.release_image);
        Glide.with(mContext)
        .load(historyTopicBean.getPicUrl())
        .into(imageView);
        //盘口名称
        TextView name=holder.getView(R.id.release_name);
        name.setText(historyTopicBean.getName());
        //下注时间
        TextView time=holder.getView(R.id.release_time);
        if(!TextUtils.isEmpty(historyTopicBean.getBuyTime()))time.setText(RelativeDateFormat.getTimeRange(historyTopicBean.getBuyTime()));
        //盘口状态
        TextView state=holder.getView(R.id.release_state);
        TextView share_btn=holder.getView(R.id.share_btn);//分享按钮
        TextView cancle_text=holder.getView(R.id.cancle_text);//若已开奖，则显示
        if(historyTopicBean.getStatus()==0){
            state.setText(historyTopicBean.getBuyNum());
            share_btn.setVisibility(View.VISIBLE);
            cancle_text.setVisibility(View.GONE);
            share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(join!=null){
                        join.shareJoin(position);
                    }
                }
            });
        } else{
            String number=null;
            if(historyTopicBean.getReward()>=0){
                number="+"+historyTopicBean.getReward();
            }else{
                number=historyTopicBean.getReward()+"";
            }
            state.setText(setFontColorSize(historyTopicBean.getBuyNum()+"  盈亏:"+number));
            share_btn.setVisibility(View.GONE);
            cancle_text.setVisibility(View.VISIBLE);
        }
    }

    private SpannableStringBuilder setFontColorSize(String label) {
        int fstart=label.indexOf("盈亏:")+3;
        SpannableStringBuilder style=new SpannableStringBuilder(label);
        style.setSpan(new ForegroundColorSpan(Color.RED),fstart,label.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return style;
    }

    public void setShareJoin(ShareJoin shareJoin){
        this.join=shareJoin;
    }


    public interface ShareJoin{
        void shareJoin(int postion);
    }

}
