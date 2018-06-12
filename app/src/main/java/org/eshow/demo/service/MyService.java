package org.eshow.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.eshow.demo.aidl.IMyAidlInterface;

/**
 * Created by Administrator on 2018-6-7 0007.
 */

public class MyService extends Service {

    public final static String TAG = "AIDL";

    private IBinder binder = new IMyAidlInterface.Stub() {
        @Override
        public String getInfor(String s) throws RemoteException {
            Log.e(TAG, s);
            return "我是 Service 返回的字符串";
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreat");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
