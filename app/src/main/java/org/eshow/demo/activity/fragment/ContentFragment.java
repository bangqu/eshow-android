package org.eshow.demo.activity.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bangqu.download.presenter.DownLoadImpl;
import com.bangqu.download.service.DownloadService;
import com.bangqu.download.widget.DownloadDialog;
import com.bangqu.lib.banner.BannerView;
import com.bangqu.lib.utils.AppUtils;
import com.bangqu.lib.widget.UpdateAppDialog;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;
import org.eshow.demo.activity.BluetoothActivity;
import org.eshow.demo.activity.LoadingListActivity;
import org.eshow.demo.activity.MainActivity;
import org.eshow.demo.activity.PhotosPickActivity;
import org.eshow.demo.activity.WebActivity;
import org.eshow.demo.base.BaseFragment;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.WebBundle;
import org.eshow.demo.util.LogInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class ContentFragment extends BaseFragment implements DownLoadImpl {

    @BindView(R.id.content_banner)
    BannerView contentBanner;

    private boolean isBindService;
    private List<View> bannerList = new ArrayList<>();
    private int[] bannerRes = new int[]{R.mipmap.home_banner_01, R.mipmap.home_banner_02, R.mipmap.home_banner_03};
    DownloadDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_content, container,
                    false);
            unbinder = ButterKnife.bind(this, rootView);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    private void initView() {
        int w = AppUtils.getDisplayMetrics(getContext()).widthPixels;
        int h = w * 10 / 23;
        contentBanner.setLayoutParams(new LinearLayout.LayoutParams(w, h));
        getBannerList();
    }

    public void getBannerList() {
        for (int res : bannerRes) {
            ImageView image = new ImageView(getActivity());
            image.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //设置显示格式
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this).load(res).into(image);
            bannerList.add(image);
        }
        contentBanner.setViewList(bannerList);
        contentBanner.startLoop(true);
    }

    @OnClick({R.id.menu_normal, R.id.menu_list, R.id.menu_photos, R.id.menu_webview, R.id.menu_ble
            , R.id.menu_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_normal:
                goToActivity(MainActivity.class);
                break;
            case R.id.menu_list:
                goToActivity(LoadingListActivity.class);
                break;
            case R.id.menu_photos:
                goToActivity(PhotosPickActivity.class);
                break;
            case R.id.menu_webview:
                Bundle bundle = new Bundle();
                WebBundle webBundle = new WebBundle();
                webBundle.title = "百度一下";
                webBundle.url = "https://www.baidu.com";
                bundle.putParcelable(Constants.INTENT_OBJECT, webBundle);
                goToActivity(WebActivity.class, bundle);
                break;
            case R.id.menu_ble:
                goToActivity(BluetoothActivity.class);
                break;
            case R.id.menu_download:
                showUpdateDialog();
                break;
        }
    }

    private void showUpdateDialog() {
        String update = "1、修复BUG；\n2、界面优化；\n3、功能完善。";
        new UpdateAppDialog(getContext(), "2.0", update, new UpdateAppDialog.OnUpdateListener() {
            @Override
            public void onUpdate(boolean value) {
                if (value) {
                    downLoadApk("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");
                }
            }
        }).show();
    }

    private void downLoadApk(String url) {
        this.bindService(url, "demo.apk");
        progressDialog = new DownloadDialog(getContext(), "下载中", new DownloadDialog.DialogConfirmListener() {
            @Override
            public void onDialogConfirm(boolean result, Object value) {

            }
        });
        progressDialog.show();
//        DownloadUtils down = new DownloadUtils();
//        down.init(getContext());
//        down.downloadFile(url, "demo.apk");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (contentBanner != null)
            contentBanner.startLoop(true);
    }

    @Override
    public void onDestroyView() {
        if (contentBanner != null)
            contentBanner.startLoop(false);
        super.onDestroyView();
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            DownloadService downloadService = binder.getService();
            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    LogInfo.e("下载进度：" + fraction);
                    if (progressDialog != null) {
                        progressDialog.setProgress((int) (fraction * 100));
                    }
                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (fraction == DownloadService.UNBIND_SERVICE && isBindService) {
                        getActivity().getApplicationContext().unbindService(conn);
                        isBindService = false;
                        showToast("下载完成！");
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void bindService(String apkUrl, String fileName) {
        Intent intent = new Intent(getContext(), DownloadService.class);
        intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, apkUrl);
        intent.putExtra(DownloadService.BUNDLE_KEY_FILENAME, fileName);
        isBindService = getActivity().getApplicationContext().bindService(intent, conn, Activity.BIND_AUTO_CREATE);
    }
}
