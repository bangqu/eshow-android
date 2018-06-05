package org.eshow.demo.activity;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.WebBundle;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.web_progress)
    ProgressBar webProgress;
    @BindView(R.id.web_errorMsg)
    TextView webErrorMsg;
    @BindView(R.id.web_error)
    RelativeLayout webError;

    private WebBundle webBundle;
    private boolean isError;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        webBundle = getIntent().getParcelableExtra(Constants.INTENT_OBJECT);
        title.setText(webBundle.title);
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();
        //设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String webTitle) {
                title.setText(webTitle);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    webProgress.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    webProgress.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    webProgress.setProgress(newProgress);//设置进度值
                }
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.loadUrl(webBundle.url);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();    //表示等待证书响应
            // handler.cancel();      //表示挂起连接，为默认方式
            // handler.handleMessage(null);    //可做其他处理
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isError = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (isError) {
                webView.setVisibility(View.GONE);
                webError.setVisibility(View.VISIBLE);
            } else {
                webView.setVisibility(View.VISIBLE);
                webError.setVisibility(View.GONE);
            }
        }

        // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return;
            }
            // 在这里显示自定义错误页
            isError = true;
            webErrorMsg.setText("加载网址失败，错误码：" + errorCode);
        }

        // 新版本，只会在Android6及以上调用
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                // 在这里显示自定义错误页
                isError = true;
                webErrorMsg.setText("加载网址失败，错误码：" + error.getErrorCode());
            }
        }
    }

    @OnClick({R.id.web_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_reload:
                webView.reload();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
