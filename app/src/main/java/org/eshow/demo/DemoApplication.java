package org.eshow.demo;

import android.content.Context;
import android.os.StrictMode;

import com.bangqu.lib.EshowApplication;

public class DemoApplication extends EshowApplication {

    private static DemoApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        /*7.0以上版本调用系统文件异常FileUriExposedException*/
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
    }

    public static DemoApplication getInstance() {
        return mApplication;
    }
}
