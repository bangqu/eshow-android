package org.eshow.demo.activity.fragment;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangqu.download.presenter.DownLoadImpl;
import com.bangqu.download.service.DownloadService;
import com.bangqu.download.utils.FileUtil;
import com.bangqu.download.widget.DownloadDialog;
import com.bangqu.lib.listener.DialogConfirmListener;
import com.bangqu.lib.utils.BadgeUtils;
import com.bangqu.lib.widget.ConfirmDialog;
import com.bangqu.lib.widget.UpdateAppDialog;
import com.google.zxing.activity.CaptureActivity;

import org.eshow.demo.BuildConfig;
import org.eshow.demo.R;
import org.eshow.demo.activity.BadgeActivity;
import org.eshow.demo.activity.BluetoothActivity;
import org.eshow.demo.activity.ImageViewActivity;
import org.eshow.demo.activity.LeftMenuActivity;
import org.eshow.demo.activity.LoadingListActivity;
import org.eshow.demo.activity.MusicListActivity;
import org.eshow.demo.activity.PhotosPickActivity;
import org.eshow.demo.activity.RecyclerViewActivity;
import org.eshow.demo.activity.SwipeListActivity;
import org.eshow.demo.activity.WebActivity;
import org.eshow.demo.adapter.FunctionAdapter;
import org.eshow.demo.base.BaseFragment;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.WebBundle;
import org.eshow.demo.util.LogInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class FunctionFragment extends BaseFragment implements DownLoadImpl {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.function_grid)
    GridView gridView;

    private boolean isBindService;
    DownloadDialog progressDialog;
    DownloadService downloadService;
    private final int REQUEST_QRCODE_SCANNING = 1;
    private final int REQUEST_CONTACTS = 2;
    private FunctionAdapter functionAdapter;
    private ArrayList<String> functions = new ArrayList<String>() {
        {
            add("左侧菜单");
            add("列表刷新");
            add("多图选择");
            add("网址加载");
            add("蓝牙连接");
            add("下载更新");
            add("扫一扫");
            add("联系人");
            add("本地数据库");
            add("图片加载");
            add("桌面角标");
            add("列表侧滑");
            add("音乐列表");
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_function, container,
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
        title.setText("功能");
        functionAdapter = new FunctionAdapter(getContext(), functions);
        gridView.setAdapter(functionAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (functions.get(position)) {
                    case "左侧菜单":
                        goToActivity(LeftMenuActivity.class);
                        break;
                    case "列表刷新":
                        goToActivity(LoadingListActivity.class);
                        break;
                    case "多图选择":
                        goToActivity(PhotosPickActivity.class);
                        break;
                    case "网址加载":
                        Bundle bundle = new Bundle();
                        WebBundle webBundle = new WebBundle();
                        webBundle.title = "百度一下";
                        webBundle.url = BuildConfig.HTTP_BASE;
                        bundle.putParcelable(Constants.INTENT_OBJECT, webBundle);
                        goToActivity(WebActivity.class, bundle);
                        break;
                    case "蓝牙连接":
                        goToActivity(BluetoothActivity.class);
                        break;
                    case "下载更新":
                        showUpdateDialog();
                        break;
                    case "扫一扫":
                        goToActivityForResult(CaptureActivity.class, REQUEST_QRCODE_SCANNING);
                        break;
                    case "联系人":
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, REQUEST_CONTACTS);
                        break;
                    case "本地数据库":
                        goToActivity(RecyclerViewActivity.class);
                        break;
                    case "图片加载":
                        goToActivity(ImageViewActivity.class);
                        break;
                    case "桌面角标":
                        goToActivity(BadgeActivity.class);
                        break;
                    case "列表侧滑":
                        goToActivity(SwipeListActivity.class);
                        break;
                    case "音乐列表":
                        goToActivity(MusicListActivity.class);
                        break;
                }
            }
        });
    }

    private void showUpdateDialog() {
        String update = "1、修复BUG；\n2、界面优化；\n3、功能完善。";
        new UpdateAppDialog(getContext(), "2.0", update, new DialogConfirmListener() {
            @Override
            public void onDialogConfirm(boolean result, Object value) {
                if (result) {
                    downLoadApk("http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk");
                }
            }
        }).show();
    }

    private void downLoadApk(String url) {
        this.bindService(url, "demo.apk");
        progressDialog = new DownloadDialog(getContext(), "下载中", "后台下载", "取消", new DownloadDialog.DialogConfirmListener() {
            @Override
            public void onDialogConfirm(boolean result, Object value) {
                if (result) {
                    FileUtil.openFile(getContext(), (File) value);
                } else {
                    if (downloadService != null)
                        downloadService.cancelService();
                }
            }
        });
        progressDialog.show();
    }

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            downloadService = binder.getService();
            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    if (progressDialog != null) {
                        progressDialog.setProgress((int) (fraction * 100));
                    }
                }

                @Override
                public void onProgressDone(File downloadFile) {
                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (isBindService) {
                        getActivity().getApplicationContext().unbindService(conn);
                        isBindService = false;
                    }
                    if (progressDialog.isShowing()) {
                        progressDialog.setProgress(100);
                        progressDialog.setDownloadFile(downloadFile);
                    } else {
                        FileUtil.openFile(getContext(), downloadFile);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_QRCODE_SCANNING:
                    String result = data.getStringExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN);
                    if (!TextUtils.isEmpty(result)) {
                        new ConfirmDialog(getContext(), "扫描结果", result, null).show();
                    }
                    break;
                case REQUEST_CONTACTS:
                    Uri contactData = data.getData();
                    Cursor cursor = getActivity().managedQuery(contactData, null, null, null, null);
                    cursor.moveToFirst();
                    String num = this.getContactPhone(cursor);
                    LogInfo.e("所选手机号为：" + num);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getContactPhone(Cursor cursor) {
        // TODO Auto-generated method stub
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = getContext().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int typeindex = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
                    int phone_type = phone.getInt(typeindex);
                    String phoneNumber = phone.getString(index);
                    result = phoneNumber;
//                  switch (phone_type) {//此处请看下方注释
//                  case 2:
//                      result = phoneNumber;
//                      break;
//
//                  default:
//                      break;
//                  }
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }
}
