package org.eshow.demo.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseFragment;
import org.eshow.demo.listener.LeftMenuItemSelectedListener;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class LeftMenuFragment extends BaseFragment {

    private LeftMenuItemSelectedListener leftMenuItemSelectedListener;

    public void setLeftMenuItemSelectedListener(LeftMenuItemSelectedListener leftMenuItemSelectedListener) {
        this.leftMenuItemSelectedListener = leftMenuItemSelectedListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_leftmenu, container,
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
    }

    @OnClick({R.id.mine_header, R.id.mine_nickname, R.id.leftmenu_setting, R.id.leftmenu_aboutus, R.id.leftmenu_exit
            , R.id.leftmenu_changepass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_header:
            case R.id.mine_nickname:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("user", null);
                }
                break;
            case R.id.leftmenu_setting:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("set", null);
                }
                break;
            case R.id.leftmenu_aboutus:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("about", null);
                }
                break;
            case R.id.leftmenu_exit:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("exit", null);
                }
                break;
            case R.id.leftmenu_changepass:
                if (leftMenuItemSelectedListener != null) {
                    leftMenuItemSelectedListener.onMenuItemSelected("change", null);
                }
                break;
        }
    }

}