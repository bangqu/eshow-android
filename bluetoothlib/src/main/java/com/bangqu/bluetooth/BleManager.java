package com.bangqu.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017-11-30 0030.
 */

public class BleManager {

    public static enum BLE_STATE {
        BLE_ENABLE, BLE_DISABLE, BLE_SCANNING, BLE_SCAN_COMPLETE, BLE_CONNECTING, BLE_CONNECTED, BLE_DISCONNECT
    }

    private BLE_STATE bleState;
    /*BLE蓝牙设备服务UUID*/
    private static final String SERVICE_UUID = "0000fff0-0000-1000-8000-00805f9b34fb";
    //蓝牙扫描持续时间
    private int SCAN_DURATION = 10000;
    private static BleManager bleManager;
    private Context mContext;
    //适配器与蓝牙管理器的成员变量。
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    //蓝牙搜索设备列表
    private List<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    //手机链接蓝牙设备，就需要获取与之相关的GATT链接，首先声明gatt
    private BluetoothGatt mBluetoothGatt;
    //蓝牙回调接口
    private BleListener bleListener;
    //在UI主线程中进行接口回调
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String autoContentDevice;//自动连接设备名称

    public void setAutoContentDevice(String autoContentDevice) {
        this.autoContentDevice = autoContentDevice;
    }

    public static BleManager getInstance(Context mContext) {
        if (bleManager == null) {
            bleManager = new BleManager(mContext);
        }
        return bleManager;
    }

    public BleManager(Context mContext) {
        this.mContext = mContext;
        initManager(mContext);
    }

    //如果设备支持BLE，那么就可以获取蓝牙适配器。
    private void initManager(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            mBluetoothAdapter = mBluetoothManager.getAdapter();
        } else {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        }
    }

    /*连接蓝牙列表position设备*/
    public void connectBleDevice(int position) {
        if (position < bluetoothDevices.size() && position >= 0) {
            connectBleDevice(bluetoothDevices.get(position));
        }
    }

    public void connectBleDevice(BluetoothDevice mTargetDevice) {
        if (bleListener != null) bleListener.onConnect();
        bleState = BLE_STATE.BLE_CONNECTING;
        mBluetoothGatt = mTargetDevice.connectGatt(mContext, true, bluetoothGattCallback);
    }

    public void startBleScan() {
        if (bleState == BLE_STATE.BLE_SCANNING) return;
        if (bleListener != null) bleListener.onStartLeScan();
        bluetoothDevices.clear();
        bleState = BLE_STATE.BLE_SCANNING;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            List<ScanFilter> bleScanFilters = new ArrayList<>();
            bleScanFilters.add(
                    new ScanFilter.Builder().setServiceUuid(ParcelUuid.fromString(SERVICE_UUID)).build()
            );
            ScanSettings settings = new ScanSettings.Builder().build();
            ScanCallback scanCallback = new ScanCallback() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    if (!bluetoothDevices.contains(result.getDevice())) {
                        bluetoothDevices.add(result.getDevice());
                        if (result.getDevice().getName() != null
                                && result.getDevice().getName().equals(autoContentDevice)) {
                            stopBleScan(false);
                            connectBleDevice(result.getDevice());
                        }
                    }
                    bleState = BLE_STATE.BLE_SCAN_COMPLETE;
                }

                @Override
                public void onBatchScanResults(List<ScanResult> results) {
                    super.onBatchScanResults(results);
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                }
            };
            mBluetoothAdapter.getBluetoothLeScanner().startScan(bleScanFilters, settings, scanCallback);
        } else {
//            mBluetoothAdapter.startLeScan(new UUID[]{UUID.fromString(SERVICE_UUID)}, leScanCallback);
            mBluetoothAdapter.startLeScan(leScanCallback);
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bleState == BLE_STATE.BLE_SCANNING) {
                    stopBleScan(true);
                }
            }
        }, SCAN_DURATION);
    }

    private void stopBleScan(boolean isComplete) {
        if (bleState == BLE_STATE.BLE_SCANNING) {
            bleState = BLE_STATE.BLE_SCAN_COMPLETE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ScanCallback scanCallback = new ScanCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                        if (!bluetoothDevices.contains(result.getDevice())) {
                            bluetoothDevices.add(result.getDevice());
                            if (result.getDevice().getName() != null
                                    && result.getDevice().getName().equals(autoContentDevice)) {
                                stopBleScan(false);
                                connectBleDevice(result.getDevice());
                            }
                        }
                        bleState = BLE_STATE.BLE_SCAN_COMPLETE;
                    }

                    @Override
                    public void onBatchScanResults(List<ScanResult> results) {
                        super.onBatchScanResults(results);
                    }

                    @Override
                    public void onScanFailed(int errorCode) {
                        super.onScanFailed(errorCode);
                    }
                };
                mBluetoothAdapter.getBluetoothLeScanner().stopScan(scanCallback);
            } else {
                mBluetoothAdapter.stopLeScan(leScanCallback);
            }
            if (isComplete && bleListener != null) {
                bleListener.onStopLeScan();
            }
        }
    }

    private BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            switch (newState) {//newState顾名思义，表示当前最新状态。status可以获取之前的状态。
                case BluetoothProfile.STATE_CONNECTED:
                    bleState = BLE_STATE.BLE_CONNECTED;
                    //这里表示已经成功连接，如果成功连接，我们就会执行discoverServices()方法去发现设备所包含的服务
                    mBluetoothGatt = gatt;
                    if (bleListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                bleListener.onConnected();
                            }
                        });
                    }
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    bleState = BLE_STATE.BLE_DISCONNECT;
                    //表示gatt连接已经断开。
                    if (bleListener != null) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                bleListener.onDisConnect();
                            }
                        });
                    }
                    gatt.close();
                    break;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            for (BluetoothGattService service : gatt.getServices()) {
//                LogInfo.e("S:" + service.getUuid());
                //每发现一个服务，我们再次遍历服务当中所包含的特征，service.getCharacteristics()可以获得当前服务所包含的所有特征
                for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
//                    LogInfo.e("C:" + characteristic.getUuid());
                }
            }
        }
    };

    private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (!bluetoothDevices.contains(device)) {
                bluetoothDevices.add(device);
                if (device.getName() != null
                        && device.getName().equals(autoContentDevice)) {
                    stopBleScan(false);
                    connectBleDevice(device);
                }
            }
        }
    };

    //写入特征，也相当简单，一句话带过，读取结果会回调到mGattCallback中的onCharacteristicWrite
    public void writeCharacteristic(byte[] value) {
        for (BluetoothGattService service : mBluetoothGatt.getServices()) {
            if (!SERVICE_UUID.equals(service.getUuid().toString())) continue;
            //每发现一个服务，我们再次遍历服务当中所包含的特征，service.getCharacteristics()可以获得当前服务所包含的所有特征
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                characteristic.setValue(value);//参数可以是byte数组，字符串等。
                mBluetoothGatt.writeCharacteristic(characteristic);
            }
        }

    }

    //获取完适配器后，需要检测是否已经打开蓝牙功能，如果没有，就需要开启。
    //开启蓝牙功能需要一小段时间，具体涉及的线程操作或同步对象不在此讨论，视实际情况按需编写。
    public boolean enableBluetooth() {
        return (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled());
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    //此方法用于获取在手机中已经获取并绑定了的设备
    private void getBoundDevices() {
        Set<BluetoothDevice> boundDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : boundDevices) {
            //对device进行其他操作，比如连接等。
        }
    }

    public void setBleListener(BleListener bleListener) {
        this.bleListener = bleListener;
    }

    public List<BluetoothDevice> getBluetoothDevices() {
        return bluetoothDevices;
    }
}
