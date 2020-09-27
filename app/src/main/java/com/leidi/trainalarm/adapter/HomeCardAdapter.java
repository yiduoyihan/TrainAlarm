package com.leidi.trainalarm.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.bean.CardMsgBean;
import com.leidi.trainalarm.bean.HomeCardBean;
import com.leidi.trainalarm.bean.TrainLocationDetailBean;

import java.util.List;


/**
 *
 * @author YGQ
 * @date 2016/8/29 0029
 * 最新的adapter
 */

public class HomeCardAdapter extends BaseMultiItemQuickAdapter<CardMsgBean, BaseViewHolder> {

    public HomeCardAdapter(List<CardMsgBean> data) {
        super(data);
//        this.adp = adp;
        addItemType(HomeCardBean.ITEM_UP, R.layout.item_card_up);
        addItemType(HomeCardBean.ITEM_DOWN, R.layout.item_card_down);
    }

    @Override
    protected void convert(final BaseViewHolder holder,
                           final CardMsgBean bean) {
        //区分不同的itemViewType
        switch (holder.getItemViewType()) {
            case HomeCardBean.ITEM_UP:
                setItemUp(holder, bean);
                break;
            case HomeCardBean.ITEM_DOWN:
                setItemDown(holder, bean);
                break;
            default:
                break;
        }
    }

    //下行
    private void setItemDown(BaseViewHolder holder, CardMsgBean bean) {
        setLineAndPointColor(holder, bean);
    }

    //上行
    private void setItemUp(BaseViewHolder holder, CardMsgBean bean) {
        setLineAndPointColor(holder, bean);
    }

    private void setLineAndPointColor(BaseViewHolder holder, CardMsgBean bean) {
        //判断是否显示最上方标题
//        if (bean.isHasTitle()) {
//            holder.getView(R.id.tv_rv_title).setVisibility(View.VISIBLE);
//        } else {
//            holder.getView(R.id.tv_rv_title).setVisibility(View.GONE);
//        }
        //判断上行还是下行
        String str;
        if (bean.getDirection().equals("1")) {
            str = "上行";
        } else {
            str = "下行";
        }
        holder.setText(R.id.tv_date, str + bean.getNum() + "次列车据您" + bean.getDistance() + "公里");
        //显示起始车站的名字
        holder.setText(R.id.start_station, bean.getStationLast()).setText(R.id.end_station, bean.getStationNext());
        //控制火车图标在页面中显示的位置
        holder.getView(R.id.tv_train).setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, bean.getStartWeight()));
        holder.getView(R.id.viewWeight).setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.WRAP_CONTENT, bean.getEndWeight()));

        //根据不同的告警级别去处理页面不同的显示需求
        if (bean.getAlarmLv() == 1) {
            //只有一级告警
            if (null != bean.getFirstAlarm()) {
                holder.setText(R.id.tv_alarm1, bean.getFirstAlarm() + "公里");
            } else if (null != bean.getSecondAlarm()) {
                holder.setText(R.id.tv_alarm1, bean.getSecondAlarm() + "公里");
            } else {
                holder.setText(R.id.tv_alarm1, bean.getThiredAlarm() + "公里");
            }
            holder.setGone(R.id.tv_alarm2, false);
            holder.setGone(R.id.tv_alarm3, false);
            holder.setGone(R.id.point_yellow, false);
            holder.setGone(R.id.point_orange, false);
            holder.setBackgroundRes(R.id.point_red, bean.getPoint3());
            holder.setGone(R.id.line_yellow, false);
            holder.setGone(R.id.line_orange, false);
            holder.setBackgroundRes(R.id.line_red, bean.getLine3());
        }
        if (bean.getAlarmLv() == 2) {
            if (null == bean.getFirstAlarm()) {
                holder.setText(R.id.tv_alarm1, bean.getSecondAlarm() + "公里");
                holder.setText(R.id.tv_alarm2, bean.getThiredAlarm() + "公里");
            } else if (null == bean.getSecondAlarm()) {
                holder.setText(R.id.tv_alarm1, bean.getFirstAlarm() + "公里");
                holder.setText(R.id.tv_alarm2, bean.getThiredAlarm() + "公里");
            } else {
                holder.setText(R.id.tv_alarm1, bean.getFirstAlarm() + "公里");
                holder.setText(R.id.tv_alarm2, bean.getSecondAlarm() + "公里");
            }
            holder.setGone(R.id.tv_alarm3, false);
            holder.setGone(R.id.point_yellow, false);
            holder.setBackgroundRes(R.id.point_orange, bean.getPoint2());
            holder.setBackgroundRes(R.id.point_red, bean.getPoint3());
            holder.setGone(R.id.line_yellow, false);
            holder.setBackgroundRes(R.id.line_orange, bean.getLine2());
            holder.setBackgroundRes(R.id.line_red, bean.getLine3());
        }
        if (bean.getAlarmLv() == 3) {
            holder.setText(R.id.tv_alarm1, bean.getFirstAlarm() + "公里");
            holder.setText(R.id.tv_alarm2, bean.getSecondAlarm() + "公里");
            holder.setText(R.id.tv_alarm3, bean.getThiredAlarm() + "公里");
            //设置线的颜色
            holder.setBackgroundRes(R.id.point_yellow, bean.getPoint1());
            holder.setBackgroundRes(R.id.point_orange, bean.getPoint2());
            holder.setBackgroundRes(R.id.point_red, bean.getPoint3());
            holder.setBackgroundRes(R.id.line_yellow, bean.getLine1());
            holder.setBackgroundRes(R.id.line_orange, bean.getLine2());
            holder.setBackgroundRes(R.id.line_red, bean.getLine3());
        }

    }

}
