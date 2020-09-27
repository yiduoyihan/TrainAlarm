package com.leidi.trainalarm.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.util.AppUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 首页中间通知页适配器
 *
 * @author 阎
 */
public class NotificationListAdapter extends BaseQuickAdapter<NotificationBean.DataBean, BaseViewHolder> {


    public NotificationListAdapter(@LayoutRes int layoutResId, @Nullable List<NotificationBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, NotificationBean.DataBean bean) {
        holder.setText(R.id.tv_title_content, "紧急告警");
        holder.setText(R.id.tv_home_description, "" + bean.getAlarmContent());
        holder.setText(R.id.tv_home_detail, "位置：" + AppUtil.formatDate(bean.getAlarmMileage()) + "Km");
        holder.setText(R.id.tv_notify_car_id, "车次：" + bean.getTrainNo());
        holder.setText(R.id.tv_notify_orientation, "方向：" + bean.getDirectionName());
        holder.setText(R.id.tv_notify_road, "轨道：" + bean.getRailwaylineNo());
        holder.setText(R.id.tv_date, bean.getCreateTime());
        holder.addOnClickListener(R.id.home_item_delete);
    }
}
