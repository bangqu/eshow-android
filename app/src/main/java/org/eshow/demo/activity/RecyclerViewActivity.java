package org.eshow.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.bangqu.lib.listener.RecyclerViewItemClickListener;
import com.bangqu.lib.slipload.widget.SlipLoadLayout;
import com.bangqu.lib.volley.ResponseCallBack;
import com.bangqu.lib.widget.DividerItemDecoration;
import com.bangqu.lib.widget.LoadingView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.eshow.demo.R;
import org.eshow.demo.adapter.MsgSysAdapter;
import org.eshow.demo.adapter.RecyclerAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.model.MsgModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class RecyclerViewActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.slip_recycler_view)
    RecyclerView slipRecyclerView;
    @BindView(R.id.slip_load_layout)
    SlipLoadLayout slipLoadLayout;
    @BindView(R.id.slip_loading_view)
    LoadingView slipLoadingView;

    private int begin = 0;
    List<String> mList = new ArrayList<>();
    RecyclerAdapter mAdapter;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("RecycleView");
        mAdapter = new RecyclerAdapter(this, mList);
        slipRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slipRecyclerView.setAdapter(mAdapter);
        slipRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 0.5f, Color.parseColor("#191919")));
        slipLoadLayout.setLoadingMore(true);
        getListData(true);
    }

    private void showList() {
        slipLoadingView.setLoadingState(LoadingView.SHOW_DATA);
        slipLoadLayout.onLoadingComplete(true);
        if (begin == 0) mList.clear();
        for (int i = 0; i < 10; i++) {
            mList.add("item----" + i);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void addViewListener() {
        slipLoadLayout.setOnRefreshListener(new SlipLoadLayout.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                begin = 0;
                getListData(false);
            }

            @Override
            public void onLoadingMore() {
                begin++;
                getListData(false);
            }
        });
        mAdapter.setRecyclerViewItemClickListener(new RecyclerViewItemClickListener<String>() {
            @Override
            public void onItemClick(int position, String value) {
                showToast(value);
            }
        });
    }

    void getListData(boolean load) {
        if (load)
            slipLoadingView.setLoadingState(LoadingView.LOADING);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                showList();
            }
        }, 1500);
        HashMap<String, String> params = new HashMap<>();
        params.put("username", "13771199261");
        params.put("appKey", "rm9ko0t225sbs3tf");
        params.put("appSecret", "30mp0j71wi9h5hx8");
        params.put("sort", "addTime,desc");
        params.put("page", begin + "");
//        getData(HttpConfig.MSG_SYS, params, new VolleyCallBack("msglist", responseCallBack));
    }

}
