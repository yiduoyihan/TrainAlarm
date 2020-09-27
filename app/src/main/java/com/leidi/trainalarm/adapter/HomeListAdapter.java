package com.leidi.trainalarm.adapter;

import android.text.Html;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.HomeListBean;

import java.util.List;

/**
 * @author yan
 * @description 首页列表适配器
 */
public class HomeListAdapter extends BaseQuickAdapter<HomeListBean.DataBean, BaseViewHolder> {

    public HomeListAdapter(@LayoutRes int layoutResId, @Nullable List<HomeListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeListBean.DataBean bean) {
        holder.setText(R.id.tv_title_content, "普通公告");
        holder.setText(R.id.tv_home_description, bean.getNoticeTitle());
        holder.setText(R.id.tv_home_detail, Html.fromHtml(bean.getNoticeContent()));
        holder.setText(R.id.tv_date,bean.getCreateTime());
        holder.addOnClickListener(R.id.home_item_delete);
    }
}
