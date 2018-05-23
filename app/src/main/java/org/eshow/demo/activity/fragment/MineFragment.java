package org.eshow.demo.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bangqu.lib.widget.RoundImageView;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;
import org.eshow.demo.activity.MainActivity;
import org.eshow.demo.activity.UserInfoActivity;
import org.eshow.demo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhy on 15/4/26.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mine_avatar)
    RoundImageView mineAvatar;
    @BindView(R.id.mine_nick)
    TextView mineNick;
    @BindView(R.id.mine_intro)
    TextView mineIntro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_mine, container,
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
        title.setText("我的");
        mineNick.setText("Eshow");
        mineIntro.setText("一套简单好用的移动框架");
    }

    @OnClick({R.id.mine_avatar, R.id.mine_nick, R.id.mine_intro})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_avatar:
            case R.id.mine_nick:
            case R.id.mine_intro:
                goToActivity(UserInfoActivity.class);
                break;
        }
    }

}
