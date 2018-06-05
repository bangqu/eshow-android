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
import org.eshow.demo.adapter.RecyclerAdapter;
import org.eshow.demo.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    List<Person> mList = new ArrayList<>();
    RecyclerAdapter mAdapter;
    private PersonDao personDao;

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
        personDao = DbManager.getDaoSession(this).getPersonDao();
        mAdapter = new RecyclerAdapter(this, mList);
        slipRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slipRecyclerView.setAdapter(mAdapter);
        slipRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 4f, Color.TRANSPARENT));
        slipLoadLayout.setLoadingMore(true);
        getListData(true);
    }


    private void showList() {
        slipLoadingView.setLoadingState(LoadingView.SHOW_DATA);
        slipLoadLayout.onLoadingComplete(true);
        if (begin == 0) mList.clear();
        mList.addAll(personDao.loadAll());
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
        mAdapter.setRecyclerViewItemClickListener(new RecyclerViewItemClickListener<Person>() {
            @Override
            public void onItemClick(int position, Person value) {

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

    @OnClick({R.id.person_add, R.id.person_del, R.id.person_edit, R.id.person_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_add:
                goToActivity(PersonActivity.class);
                break;
            case R.id.person_del:
                if (mAdapter.getChoiceSet().size() > 0) {
                    personDao.deleteByKeyInTx(mAdapter.getChoiceSet());
                    getListData(false);
                } else {
                    showToast("未选择删除行");
                }
                break;
            case R.id.person_edit:
                break;
            case R.id.person_search:
                break;
        }
    }
}
