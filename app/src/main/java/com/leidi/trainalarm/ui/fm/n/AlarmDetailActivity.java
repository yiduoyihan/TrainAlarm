package com.leidi.trainalarm.ui.fm.n;


import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.adapter.NotificationListAdapter;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.CommonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author yan
 * @description 消息详情
 */
public class AlarmDetailActivity extends BaseActivity implements AlarmDetailView.Result {
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private AlarmDetailPresenter presenter;
    private List<NotificationBean.DataBean> beanList = new ArrayList<>();
    private NotificationListAdapter adapter;
    private int pageSize = 0;
    private int pageNum = 10;
    private int regId = 1;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.alarm_detail;
    }

    @Override
    protected void initView() {
        setToolbar("历史告警");
        presenter = new AlarmDetailPresenter(this);
        presenter.getListData(pageSize, pageNum, regId, this);
        initRecycleView();

    }

    /**
     * 初始化通知列表的数据
     */
    private void initRecycleView() {
        adapter = new NotificationListAdapter(R.layout.item_notify, beanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);

        swipeRefreshLayout.setColorSchemeColors(Color.rgb(15, 80, 153));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            pageSize = 0;
            presenter.getListData(pageSize, pageNum, regId, AlarmDetailActivity.this);
        });

        adapter.setOnLoadMoreListener(() -> {
            pageSize++;
            presenter.getListData(pageSize * pageNum, pageNum, regId, AlarmDetailActivity.this);
        }, recyclerView);

        adapter.disableLoadMoreIfNotFullPage();
//        adapter.setPreLoadNumber(0);

        adapter.setOnItemClickListener((adapter, view, position) ->
                AppUtil.print("点击了第" + position + "条")
        );

        adapter.setOnItemChildClickListener((adapter, view, position) -> showDeleteDialog(position));

    }

    @Override
    public void getListSuccess(NotificationBean bean) {
        swipeRefreshLayout.setRefreshing(false);
        if (pageSize == 0) {
            beanList.clear();
            adapter.setEnableLoadMore(true);
        } else if (bean.getData().size() == pageNum) {
            adapter.loadMoreComplete();
        } else if (bean.getData().size() < pageNum) {
            adapter.loadMoreEnd();
        }

        beanList.addAll(bean.getData());
        if (pageSize == 0) {
            adapter.setNewData(beanList);
        } else {
            adapter.notifyDataSetChanged();
        }

        if (beanList.size() == 0) {
            adapter.setEmptyView(R.layout.empty_view);
        }
    }

    /**
     * 获取地图下面的列表数据的结果回调（失败）
     */
    @Override
    public void getListFailed(String string) {
        swipeRefreshLayout.setRefreshing(false);
        AppUtil.print(string);
    }

    /**
     * 删除一条Item 的结果回调（成功）
     */
    @Override
    public void deleteSuccess(BaseBean bean, int deletePosition) {
        ToastUtils.showShort("删除成功");
        beanList.remove(deletePosition);
        if (beanList.size() > 1) {
            adapter.notifyItemRemoved(deletePosition);
            adapter.notifyItemRangeChanged(deletePosition, beanList.size() - deletePosition);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除一条Item 的结果回调（失败）
     */
    @Override
    public void deleteFailed(String string) {
        AppUtil.print(string);
    }


    /**
     * 删除某一条item的对话框
     */
    private void showDeleteDialog(int position) {
        final CommonDialog dialog = new CommonDialog(this, R.layout.delete_notification_dialog);
        dialog.setTitle("您确定要删除此条报警信息吗？")
                .setWight(true)
                .setNegative("取消").setPositive("确定")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
//                        int aaa = beanList.get(position).getAlarmInfoId();
                        presenter.deleteItem(beanList.get(position).getAlarmInfoId(), position, AlarmDetailActivity.this);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.setCanceledOnTouchOutside(true);
    }
}
