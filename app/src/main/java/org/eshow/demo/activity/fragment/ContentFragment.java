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
public class ContentFragment extends BaseFragment {

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

    @OnClick({R.id.menu_normal, R.id.menu_list, R.id.menu_photos, R.id.menu_webview, R.id.menu_ble})
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
        }
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

}
