package com.leidi.trainalarm.ui.fm.h;


import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.leidi.trainalarm.R;
import com.leidi.trainalarm.base.BaseActivity;

import butterknife.BindView;

/**
 * @author yan
 * @description 消息详情
 */
public class MsgDetailActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_msg_detail;
    }

    @Override
    protected void initView() {
        setToolbar("公告详情");
        String str = getIntent().getStringExtra("detail");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        //webSettings.setBuiltInZoomControls(true);
        //webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(false);
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setDefaultFontSize(10); //设置显示字体的大小
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //控制webview不可点击
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        StringBuilder sb = new StringBuilder();
        String linkCss = "<link rel=\"stylesheet\" href=\"file:///android_asset/1.css\">";
        sb.append("<!DOCTYPE HTML><html><HEAD>");
        sb.append(linkCss).append("</HEAD><body>");
        sb.append("<div class=\"ql-editor\">");
        sb.append(str);
        sb.append("</div>");
        sb.append("</body></html>");
        webView.loadDataWithBaseURL(linkCss, sb.toString(), "text/html", "UTF-8", null);

    }

}
