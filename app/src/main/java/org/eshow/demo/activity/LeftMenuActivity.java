package org.eshow.demo.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bangqu.lib.widget.ConfirmDialog;

import org.eshow.demo.DemoApplication;
import org.eshow.demo.R;
import org.eshow.demo.activity.fragment.ContentFragment;
import org.eshow.demo.activity.fragment.LeftMenuFragment;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.listener.LeftMenuItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LeftMenuActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.frame_left_menu)
    FrameLayout frameLeftMenu;
    @BindView(R.id.main_drawer)
    DrawerLayout mainDrawer;

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private LeftMenuFragment mLeftMenuFragment;
    private ContentFragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_leftmenu);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        title.setText("首页");
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mainDrawer, toolbar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        mainDrawer.addDrawerListener(mActionBarDrawerToggle);
        mFragmentManager = getSupportFragmentManager();
        mLeftMenuFragment = (LeftMenuFragment) mFragmentManager.findFragmentById(R.id.frame_left_menu);
        mCurrentFragment = (ContentFragment) mFragmentManager.findFragmentById(R.id.frame_container);
        if (mCurrentFragment == null) {
            mCurrentFragment = new ContentFragment();
            mFragmentManager.beginTransaction().add(R.id.frame_container, mCurrentFragment).commit();
        }
        if (mLeftMenuFragment == null) {
            mLeftMenuFragment = new LeftMenuFragment();
            mFragmentManager.beginTransaction().add(R.id.frame_left_menu, mLeftMenuFragment).commit();
            mLeftMenuFragment.setLeftMenuItemSelectedListener(new LeftMenuItemSelectedListener() {
                @Override
                public void onMenuItemSelected(String tag, Object obj) {
                    mainDrawer.closeDrawer(Gravity.LEFT);
                    switch (tag) {
                        case "user":
                            goToActivity(UserInfoActivity.class);
                            break;
                        case "change":
                            goToActivity(ChangePassActivity.class);
                            break;
                        case "exit":
                            showExitDialog();
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void addViewListener() {

    }

    private void showExitDialog() {
        new ConfirmDialog(this, "提示", "确认退出登录？", new ConfirmDialog.OnConfrimClickedListener() {
            @Override
            public void onConfirm(boolean value) {
                if (value) {
                    goToActivity(LoginActivity.class);
                    finish();
                }
            }
        }).show();
    }

}
