package com.leidi.trainalarm.ui.fm.n;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.adapter.NotificationListAdapter;
import com.leidi.trainalarm.base.BaseActivity;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.bean.NotificationBean;
import com.leidi.trainalarm.ui.fm.HomeFragment;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.CommonDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author yan
 * @description 历史告警
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

    String dateString;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected int getLayoutId() {
        return R.layout.alarm_detail;
    }

    @Override
    protected void initView() {
        setToolbar("历史告警");
        dateString = formatter.format(new Date());
        presenter = new AlarmDetailPresenter(this);
        presenter.getListData(pageSize, pageNum, regId,dateString, this);
        initRecycleView();

        tvTitleRightButton.setImageResource(R.mipmap.calendar);
        tvTitleRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog();
            }
        });

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
            dateString = formatter.format(new Date());
            presenter.getListData(pageSize, pageNum, regId,dateString, AlarmDetailActivity.this);
        });

        adapter.setOnLoadMoreListener(() -> {
            pageSize++;
            presenter.getListData(pageSize * pageNum, pageNum, regId, dateString,AlarmDetailActivity.this);
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
            adapter.setEmptyView(R.layout.empty_view);
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
                }).show();
        dialog.setCanceledOnTouchOutside(true);
    }


    /**
     * 展示选择日期的dialog
     */
    private void showCalendarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.data_pick_layout, null);//这个布局在下边,可参考
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
        //设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        Calendar calendar = Calendar.getInstance();
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        //初始化当前日期
        calendar.setTimeInMillis(System.currentTimeMillis());
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);
        /**
         * 下面这行代吗 设置的是只显示年月
         */
        ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);

        //设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth() + 1;//我勒个去,系统获取的日期居然不准
                int day = datePicker.getDayOfMonth();
                dateString = year + "-" + month + "-" + day;
                pageSize = 0;
                //TODO
                presenter.getListData(pageSize, pageNum, regId,dateString,AlarmDetailActivity.this);
            }
        });
        builder.create().show();
    }
}
