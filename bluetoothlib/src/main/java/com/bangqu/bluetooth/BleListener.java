package com.bangqu.bluetooth;

/**
 * Created by Administrator on 2017-12-11 0011.
 */

public interface BleListener {
    /*开始扫描*/
    void onStartLeScan();

    /*结束扫描*/
    void onStopLeScan();

    /*连接设备*/
    void onConnect();

    /*连接成功*/
    void onConnected();

    /*连接断开*/
    void onDisConnect();
}
