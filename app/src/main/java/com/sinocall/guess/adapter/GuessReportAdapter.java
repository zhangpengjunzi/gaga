package com.sinocall.guess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinocall.guess.R;
import com.sinocall.guess.base.BaseListAdapter;
import com.sinocall.guess.model.GuessReportModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 举报适配器
 * Created by Administrator on 2017/2/15.
 */

public class GuessReportAdapter extends BaseListAdapter<GuessReportModel.DataBean.ListBean> {

    private Context context;

    private LayoutInflater mInflater;


    public GuessReportAdapter(Context context, List<GuessReportModel.DataBean.ListBean> list) {
        super(context, list);
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public View bindView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.guess_list_item_report, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GuessReportModel.DataBean.ListBean report = getList().get(position);
        //底部线条
        if (position == getList().size() - 1) {
            viewHolder.reportLine.setVisibility(View.GONE);
        } else {
            viewHolder.reportLine.setVisibility(View.VISIBLE);
        }
        //举报理由
        viewHolder.reportText.setText(report.getReason());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.report_text)
        TextView reportText;
        @BindView(R.id.report_line)
        View reportLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
