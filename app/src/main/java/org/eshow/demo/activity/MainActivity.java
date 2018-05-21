package org.eshow.demo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.eshow.demo.DemoApplication;
import org.eshow.demo.R;
import org.eshow.demo.activity.fragment.ContentFragment;
import org.eshow.demo.activity.fragment.FunctionFragment;
import org.eshow.demo.activity.fragment.HomeFragment;
import org.eshow.demo.activity.fragment.MessageFragment;
import org.eshow.demo.activity.fragment.MineFragment;
import org.eshow.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tabLayout)
    TabLayout mainTabLayout;

    private int[] images = new int[]{
            R.drawable.tabbar_home, R.drawable.tabbar_car, R.drawable.tabbar_message, R.drawable.tabbar_mine
    };

    private String[] tabTitles;
    private HomeFragment homeFragment;
    private FunctionFragment functionFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        super.initView();
        tabTitles = getResources().getStringArray(R.array.main_tab_array);
        for (int i = 0; i < tabTitles.length; i++) {
            mainTabLayout.addTab(mainTabLayout.newTab().setCustomView(getTabView(i)));
        }
        replaceFragment(0);
        setMessageCount(99);
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_main_tab, null);
        TextView tv = v.findViewById(R.id.main_tab_text);
        tv.setText(tabTitles[position]);
        ImageView img = v.findViewById(R.id.main_tab_image);
        img.setImageResource(images[position]);
        return v;
    }

    private void setMessageCount(int count) {
        View v = mainTabLayout.getTabAt(2).getCustomView();
        TextView msg = v.findViewById(R.id.main_tab_msg);
        if (count > 0) {
            msg.setVisibility(View.VISIBLE);
            msg.setText(count > 99 ? "99+" : count + "");
        } else {
            msg.setVisibility(View.GONE);
        }
    }

    @Override
    protected void addViewListener() {
        mainTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                replaceFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(int n) {
        FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
        hideFragment(fts);
        switch (n) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fts.add(R.id.main_content, homeFragment, tabTitles[n]);
                }
                fts.show(homeFragment).commit();
                break;
            case 1:
                if (functionFragment == null) {
                    functionFragment = new FunctionFragment();
                    fts.add(R.id.main_content, functionFragment, tabTitles[n]);
                }
                fts.show(functionFragment).commit();
                break;
            case 2:
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    fts.add(R.id.main_content, messageFragment, tabTitles[n]);
                }
                fts.show(messageFragment).commit();
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fts.add(R.id.main_content, mineFragment, tabTitles[n]);
                }
                fts.show(mineFragment).commit();
                break;
        }
    }

    private void hideFragment(FragmentTransaction fts) {
        if (homeFragment != null)
            fts.hide(homeFragment);
        if (functionFragment != null)
            fts.hide(functionFragment);
        if (messageFragment != null)
            fts.hide(messageFragment);
        if (mineFragment != null)
            fts.hide(mineFragment);
    }

    //获取权限相关操作
    private List<String> needPermission;
    private final int REQUEST_CODE_PERMISSION = 0;
    private String[] permissionArray = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @Override
    protected void requestData() {
        super.requestData();
        askMultiplePermission();
    }

    public void askMultiplePermission() {
        needPermission = new ArrayList<>();
        for (String permissionName :
                permissionArray) {
            if (!checkIsAskPermission(this, permissionName)) {
                needPermission.add(permissionName);
            }
        }
        if (needPermission.size() > 0) {
            //开始申请权限
            ActivityCompat.requestPermissions(this, needPermission.toArray(new String[needPermission.size()]), REQUEST_CODE_PERMISSION);
        }
    }

    public boolean checkIsAskPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean checkIsAskPermissionState(Map<String, Integer> maps, String[] list) {
        for (int i = 0; i < list.length; i++) {
            if (maps.get(list[i]) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                Map<String, Integer> permissionMap = new HashMap<>();
                for (String name : needPermission) {
                    permissionMap.put(name, PackageManager.PERMISSION_GRANTED);
                }
                for (int i = 0; i < permissions.length; i++) {
                    permissionMap.put(permissions[i], grantResults[i]);
                }
                if (checkIsAskPermissionState(permissionMap, permissions)) {
                    //获取数据
                } else {
                    //提示权限获取不完成，可能有的功能不能使用
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*双击返回键退出*/
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                showToast(R.string.press_again_to_exit);
                mExitTime = System.currentTimeMillis();
            } else {
                DemoApplication.getInstance().exitApplication();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
