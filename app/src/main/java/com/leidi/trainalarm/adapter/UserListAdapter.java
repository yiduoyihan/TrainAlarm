package com.leidi.trainalarm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.bean.AllUserListBean;
import com.leidi.trainalarm.bean.HomeListBean;
import com.leidi.trainalarm.bean.UserListBean;

import java.util.List;

/**
 * @author yan
 * @description 首页列表适配器
 */
public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<AllUserListBean.DataBean> mList;

    public UserListAdapter(Context context, List<AllUserListBean.DataBean> mList) {
        this.mList = mList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //加载我们自己的小布局
        view = LayoutInflater.from(context).inflate(R.layout.item_userlist, null);
        TextView mName = (TextView) view.findViewById(R.id.tv_list_item_name);
        mName.setText(mList.get(i).getUserName());
        return view;
    }
}

