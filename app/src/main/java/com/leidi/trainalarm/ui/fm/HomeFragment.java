package com.leidi.trainalarm.ui.fm;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.leidi.trainalarm.R;
import com.leidi.trainalarm.adapter.HomeListAdapter;
import com.leidi.trainalarm.base.BaseBean;
import com.leidi.trainalarm.base.BaseFragment;
import com.leidi.trainalarm.bean.HomeEvent;
import com.leidi.trainalarm.bean.HomeListBean;
import com.leidi.trainalarm.ui.fm.h.MsgDetailActivity;
import com.leidi.trainalarm.util.AppUtil;
import com.leidi.trainalarm.util.CommonDialog;
import com.leidi.trainalarm.util.Constant;
import com.leidi.trainalarm.util.ToastUtil;
import com.leidi.trainalarm.util.Url;
import com.leidi.trainalarm.v.HomePresenter;
import com.leidi.trainalarm.v.HomeView;
import com.rxjava.rxlife.RxLife;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import rxhttp.wrapper.param.RxHttp;

/**
 * @author 阎
 * @date 2020/5/7
 */
public class HomeFragment extends BaseFragment implements HomeView.Result {
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private List<HomeListBean.DataBean> beanList = new ArrayList<>();
    private HomePresenter presenter;
    private int pageSize = 0;
    private int pageNum = 10;
    HomeListAdapter adapter;
    String dateString;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected int getLayoutId() {
        return R.layout.fm_home;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView(@Nullable Bundle savedInstanceState, View view) {
        setToolbar(View.INVISIBLE, "列车接近告警系统", View.VISIBLE);
        EventBus.getDefault().register(this);

        tvTitleRightButton.setImageResource(R.mipmap.calendar);
        tvTitleRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog();
            }
        });

        dateString = formatter.format(new Date());
        presenter = new HomePresenter(this);
        presenter.getListData(pageSize, pageNum, dateString, this);
        initRecyclerView();

    }

    /**
     * 初始化通知列表的数据
     */
    private void initRecyclerView() {
        beanList.clear();
        adapter = new HomeListAdapter(R.layout.item_home, beanList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);
        adapter.bindToRecyclerView(recyclerView);

        swipeRefreshLayout.setColorSchemeColors(Color.rgb(15, 80, 153));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.setEnableLoadMore(false);
            pageSize = 0;
            AppUtil.print("下拉刷新");
            dateString = formatter.format(new Date());
            presenter.getListData(pageSize, pageNum, dateString, this);
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                AppUtil.print("加载更多");
                pageSize++;
                presenter.getListData(pageSize * pageNum, pageNum, dateString, HomeFragment.this);

            }
        }, recyclerView);

        adapter.disableLoadMoreIfNotFullPage();
//        adapter.setPreLoadNumber(1);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("detail", beanList.get(position).getNoticeContent());
                intent.setClass(Objects.requireNonNull(getContext()), MsgDetailActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final CommonDialog dialog = new CommonDialog(HomeFragment.this.getActivity(), R.layout.delete_notification_dialog);

                dialog.setTitle("您确定要删除此条通知消息吗？")
                        .setWight(true)
                        .setNegative("取消").setPositive("确定")
                        .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                            @Override
                            public void onPositiveClick() {
                                dialog.dismiss();
                                ToastUtil.showToast(getActivity(), "确定:" + position);
                            }

                            @Override
                            public void onNegativeClick() {
                                dialog.dismiss();
                                ToastUtil.showToast(getActivity(), "取消:" + position);
                            }
                        })
                        .show();
                dialog.setCanceledOnTouchOutside(true);
//                    dialog.setCancelable(true);
            }
        });
    }

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void getListSuccess(HomeListBean bean) {
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

    @Override
    public void getListFailed(String string) {
        swipeRefreshLayout.setRefreshing(false);
        AppUtil.print(string);
    }

    @Override
    public void deleteSuccess(BaseBean bean) {

    }

    @Override
    public void deleteFailed(String string) {

    }

    /**
     * 展示选择日期的dialog
     */
    private void showCalendarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeFragment.this.getActivity());
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
                presenter.getListData(pageSize, pageNum, dateString, HomeFragment.this);
            }
        });
        builder.create().show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(HomeEvent message) {
        //收到推送延迟1S刷新数据
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageSize = 0;
                dateString = formatter.format(new Date());
                presenter.getListData(pageSize, pageNum, dateString, HomeFragment.this);
            }
        }, 1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
        EventBus.getDefault().unregister(this);
    }
}
