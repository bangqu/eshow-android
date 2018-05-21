package org.eshow.demo.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhy on 15/4/26.
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView title;

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
    }

}
