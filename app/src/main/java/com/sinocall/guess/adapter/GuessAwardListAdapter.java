package com.sinocall.guess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.BaseListAdapter;
import com.sinocall.guess.model.GuessAwardListModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 开奖条件
 * Created by Administrator on 2017/2/15.
 */

public class GuessAwardListAdapter extends BaseListAdapter<GuessAwardListModel.DataBean.ListBean> {

    private Context context;

    private LayoutInflater mInflater;


    public GuessAwardListAdapter(Context context, List<GuessAwardListModel.DataBean.ListBean> list) {
        super(context, list);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.guess_list_item_award, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GuessAwardListModel.DataBean.ListBean buyType = getList().get(position);
        viewHolder.awardName.setText(buyType.getNum()+"");//额度
        if (buyType.getSelect() == 0) {
            //未选中
            viewHolder.selectImage.setImageResource(R.mipmap.unchoose_rechargecenter);
        } else {
            //选中
            viewHolder.selectImage.setImageResource(R.mipmap.choose_rechargecenter);
        }
        if (position == getList().size() - 1) {
            viewHolder.payBottomLine.setVisibility(View.GONE);
        } else {
            viewHolder.payBottomLine.setVisibility(View.VISIBLE);
        }
        //将位置赋值给text,做点击事件时用
        viewHolder.positionText.setText(position + "");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.select_image)
        ImageView selectImage;
        @BindView(R.id.award_name)
        TextView awardName;
        @BindView(R.id.pay_bottom_line)
        View payBottomLine;
        @BindView(R.id.position_text)
        TextView positionText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
