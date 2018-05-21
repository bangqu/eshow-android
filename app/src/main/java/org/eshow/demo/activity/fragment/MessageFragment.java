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
import android.widget.TextView;

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
public class MessageFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_message, container,
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
        title.setText("消息");
    }

}
