package org.eshow.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bangqu.greendao.dao.PersonDao;
import com.bangqu.greendao.db.DbManager;
import com.bangqu.greendao.entity.Person;
import com.bangqu.lib.listener.RecyclerViewItemClickListener;
import com.bangqu.lib.slipload.widget.SlipLoadLayout;
import com.bangqu.lib.widget.DividerItemDecoration;
import com.bangqu.lib.widget.LoadingView;

import org.eshow.demo.R;
import org.eshow.demo.adapter.MusicAdapter;
import org.eshow.demo.adapter.RecyclerAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.model.MusicModel;
import org.eshow.demo.util.LogInfo;
import org.eshow.demo.util.MusicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicListActivity extends BaseActivity {

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

    List<MusicModel> mList = new ArrayList<>();
    MusicAdapter mAdapter;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_musiclist);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("音乐列表");
        mAdapter = new MusicAdapter(this, mList);
        slipRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slipRecyclerView.setAdapter(mAdapter);
        slipRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 8f, Color.TRANSPARENT));
        showList();
    }

    private void showList() {
        mList.clear();
        mList.addAll(MusicUtils.getMusicData(this));
        mAdapter.notifyDataSetChanged();
        slipLoadingView.setLoadingState(LoadingView.SHOW_DATA);
    }

    @Override
    protected void addViewListener() {
        mAdapter.setRecyclerViewItemClickListener(new RecyclerViewItemClickListener<MusicModel>() {
            @Override
            public void onItemClick(int position, MusicModel value) {
                LogInfo.e(value.song);
                LogInfo.e(value.path);
                LogInfo.e(MusicUtils.formatTime(value.duration));
                LogInfo.e(value.size);
            }
        });
        slipLoadLayout.setOnRefreshListener(new SlipLoadLayout.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                showList();
                slipLoadLayout.onLoadingComplete(true);
            }

            @Override
            public void onLoadingMore() {

            }
        });
    }

}
