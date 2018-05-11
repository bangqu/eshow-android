package com.bangqu.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 蓝牙状态监听
 */

public class BluetoothReceiver extends BroadcastReceiver {

    public BluetoothReceiver(BluetoothStateChangeListener listener) {
        super();
        this.bluetoothStateChangeListener = listener;
    }

    private BluetoothStateChangeListener bluetoothStateChangeListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BluetoothDevice.ACTION_FOUND:
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int deviceClassType = device.getBluetoothClass().getDeviceClass();
                //找到指定的蓝牙设备
                if ((deviceClassType == BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET
                        || deviceClassType == BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES)
                        && device.getName() != null && device.getName().startsWith("")) {
                    //判断蓝牙类型和蓝牙名称
                }
                break;
            case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
                int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                switch (bondState) {
                    case BluetoothDevice.BOND_BONDED:  //配对成功
                        break;
                    case BluetoothDevice.BOND_BONDING:
                        break;
                    case BluetoothDevice.BOND_NONE:
                        //配对不成功的话，重新尝试配对
                        break;
                    default:
                        break;
                }
                break;
            case BluetoothAdapter.ACTION_STATE_CHANGED:
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                switch (state) {
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        break;
                    case BluetoothAdapter.STATE_OFF:
                        break;
                }
                break;
        }
    }

    public interface BluetoothStateChangeListener {
        void onBluetoothStateChange(String tag);
    }
}
