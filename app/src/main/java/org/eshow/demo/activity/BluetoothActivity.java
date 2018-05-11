package org.eshow.demo.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangqu.bluetooth.BleListener;
import com.bangqu.bluetooth.BleManager;
import com.bangqu.lib.widget.MenuDialog;
import com.bangqu.lib.widget.MyYAnimation;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BluetoothActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_bticon)
    ImageView homeBticon;
    @BindView(R.id.home_btstate)
    TextView homeBtstate;
    @BindView(R.id.bluetooth_btn)
    Button bluetoothBtn;

    private MyYAnimation myYAnimation;
    final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1001;
    final int REQUEST_ENABLE_BT = 2001;
    private boolean isOff = true; //指示灯状态
    private boolean isConnect = false; //蓝牙连接状态

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("蓝牙连接");
        myYAnimation = new MyYAnimation();
        myYAnimation.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
        if (BleManager.getInstance(this).enableBluetooth()) {
            homeBtstate.setText("蓝牙未开启");
            bluetoothBtn.setText("开启蓝牙");
        } else {
            homeBtstate.setText("蓝牙已开启");
            bluetoothBtn.setText("搜索蓝牙");
        }
    }

    @OnClick({R.id.bluetooth_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bluetooth_btn:
                if (isConnect) {
                    startMotor();
                } else {
                    isBlueToothOpen();
                }
                break;
        }
    }

    private void startMotor() {
        byte[] x = new byte[1];// APP链接协议
        if (isOff) {
            x[0] = (byte) 0x01; // 协议头 1BYTE
        } else {
            x[0] = (byte) 0x00;
        }
        isOff = !isOff;
        bluetoothBtn.setText(isOff ? "打开" : "关闭");
        BleManager.getInstance(this).writeCharacteristic(x);
    }

    private void isBlueToothOpen() {
        if (BleManager.getInstance(this).enableBluetooth()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            scanningBluebooth();
        }
    }

    private void scanningBluebooth() {
        BleManager.getInstance(this).setBleListener(bleListener);
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION, PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION)) {
            BleManager.getInstance(this).setAutoContentDevice(sharedPref.getString(Constants.BIND_DEVICE));
            BleManager.getInstance(this).startBleScan();
        }
    }

    private BleListener bleListener = new BleListener() {
        @Override
        public void onStartLeScan() {
            homeBtstate.setText("蓝牙搜索中");
            homeBticon.setImageResource(R.mipmap.icon_bluetooth);
            homeBticon.startAnimation(myYAnimation);
        }

        @Override
        public void onStopLeScan() {
            homeBticon.clearAnimation();
            List<BluetoothDevice> bluetoothDevices = BleManager.getInstance(BluetoothActivity.this).getBluetoothDevices();
            int i = 0;
            if (bluetoothDevices != null && bluetoothDevices.size() > 0) {
                String[] names = new String[bluetoothDevices.size()];
                for (BluetoothDevice device : bluetoothDevices) {
                    if (!TextUtils.isEmpty(device.getName())) {
                        names[i++] = device.getName();
                    } else {
                        names[i++] = device.getAddress();
                    }
                }
                new MenuDialog(BluetoothActivity.this).setMenuItems(names, new MenuDialog.OnMenuItemClickedListener() {
                    @Override
                    public void MenuItemClicked(String value, int position) {
                        sharedPref.setString(Constants.BIND_DEVICE, value);
                        BleManager.getInstance(BluetoothActivity.this).connectBleDevice(position);
                    }
                }).show();
            } else {
                homeBtstate.setText("未发现设备");
            }
        }

        @Override
        public void onConnect() {
            homeBtstate.setText("蓝牙连接中");
            homeBticon.setImageResource(R.mipmap.icon_bluetooth);
            homeBticon.startAnimation(myYAnimation);
        }

        @Override
        public void onConnected() {
            isConnect = true;
            homeBtstate.setText("蓝牙已连接");
            homeBticon.setImageResource(R.mipmap.icon_bluetooth);
            homeBticon.clearAnimation();
            bluetoothBtn.setText("打开");
        }

        @Override
        public void onDisConnect() {
            isConnect = false;
            homeBtstate.setText("蓝牙已断开");
            homeBticon.clearAnimation();
            homeBticon.setImageResource(R.mipmap.icon_bluetooth_dis);
            bluetoothBtn.setText("搜索蓝牙");
        }
    };

    @Override
    protected void addViewListener() {

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ENABLE_BT:
                    homeBtstate.setText("蓝牙已开启");
                    bluetoothBtn.setText("搜索蓝牙");
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanningBluebooth();
                } else {
                    showToast("获取相关权限失败");
                }
                break;
        }
    }
}
