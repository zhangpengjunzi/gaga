package com.sinocall.guess.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.ListBaseAdapter;
import com.sinocall.guess.base.SuperViewHolder;

/**
 * 会员中心
 * Created by Administrator on 2017/2/20.
 */

public class GuessVipCenterAdapter extends ListBaseAdapter<String> {

    //用户目前vip等级
    private boolean isVip;

    public GuessVipCenterAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.guess_list_item_vip_center;
    }

    public void setLevelId(boolean vip){
        this.isVip=vip;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, int position) {
        final String vip=mDataList.get(position);
        //vip福利
        TextView vipText=holder.getView(R.id.vip_center_item_text);
        //vip图标
        ImageView vipImage=holder.getView(R.id.vip_center_item_image);
        switch (position){
            case 0:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.discount_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.discount_member_unclicked);
                }
                vipText.setText("会员充值时显示的折扣比例："+vip);
                break;
            case 1:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.guess_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.guess_member_unclicked);
                }
                vipText.setText("每日单个题目参与次数上限："+vip);
                break;
            case 2:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.fee_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.fee_member_unclicked);
                }
                vipText.setText("发布题目的题目制作费比例："+vip);
                break;
            case 4:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.number_participants_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.number_participants_member_unclicked);
                }
                vipText.setText("每日可参与的题目数量上限："+vip);
                break;
            case 3:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.number_publish_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.number_publish_member_unclicked);
                }
                vipText.setText("每日免费发布题目数量上限："+vip);
                break;
            case 5:
                if(isVip){
                    vipImage.setImageResource(R.mipmap.credit_limit_member_clicked);
                }else{
                    vipImage.setImageResource(R.mipmap.credit_limit_member_unclicked);
                }
                vipText.setText("发布题目可设置的开奖额度："+vip);
                break;
        }
        //底部线
        View bottom_line=holder.getView(R.id.vip_bottom_line);
        if(position==getDataList().size()-1){
            bottom_line.setVisibility(View.GONE);
        }else{
            bottom_line.setVisibility(View.VISIBLE);
        }
    }



}
