package org.eshow.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangqu.lib.listener.RecyclerViewItemClickListener;
import com.bangqu.lib.widget.DividerItemDecoration;

import org.eshow.demo.R;
import org.eshow.demo.adapter.DirChoiceAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.model.DirModel;
import org.eshow.demo.util.LogInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DirChoiceActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.slip_recycler_view)
    RecyclerView slipRecyclerView;
    @BindView(R.id.directory_root)
    TextView directoryRoot;
    @BindView(R.id.directory_navi)
    LinearLayout directoryNavi;
    @BindView(R.id.directory_scroll)
    HorizontalScrollView directoryScroll;

    List<DirModel> mList = new ArrayList<>();
    DirChoiceAdapter mAdapter;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_dirchoice);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("目录选择");
        rightTv.setText(R.string.ok);
        rightTv.setVisibility(View.VISIBLE);
        mAdapter = new DirChoiceAdapter(this, mList);
        slipRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        slipRecyclerView.setAdapter(mAdapter);
        slipRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST, 1f, Color.GRAY));
        showList();
    }

    @OnClick({R.id.right_tv, R.id.directory_root})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                break;
            case R.id.directory_root:
                loadDataFrompATH(Environment.getExternalStorageDirectory());
                directoryNavi.removeAllViews();
                break;
        }
    }

    private void loadDataFrompATH(final File file) {
        mList.clear();//data为RecyclerView中要显示的数据
        new Thread() {
            public void run() {
                super.run();
                File[] listFiles = file.listFiles();//获取子文件
                if (listFiles != null) {
                    for (File f : listFiles) {
                        if (!f.isDirectory() || f.getName().startsWith(".")) {//如果不是路径或者以 . 开头的文件夹 则直接跳过
                            continue;
                        }
                        //往集合中添加符合条件的数据
                        DirModel dirModel = new DirModel();
                        dirModel.name = f.getName();
                        dirModel.path = f.getAbsolutePath();
                        mList.add(dirModel);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Collections.sort(mList);
                        mAdapter.notifyDataSetChanged();//将数据载入适配器当中
                    }
                });
            }
        }.start();

    }

    private void showList() {
        directoryRoot.setText("内部存储>");
        loadDataFrompATH(Environment.getExternalStorageDirectory());
    }

    @Override
    protected void addViewListener() {
        mAdapter.setRecyclerViewItemClickListener(new RecyclerViewItemClickListener<DirModel>() {
            @Override
            public void onItemClick(int position, DirModel value) {
                addDirectoryNavi(value);
                loadDataFrompATH(new File(value.path));
            }
        });
    }

    private void addDirectoryNavi(DirModel value) {
        TextView navi = new TextView(this);
        navi.setText(value.name + ">");
        navi.setTag(value.path);
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = (String) v.getTag();
                loadDataFrompATH(new File(path));
                int count = directoryNavi.getChildCount();
                int begin = directoryNavi.indexOfChild(v) + 1;
                if (begin < count) {
                    directoryNavi.removeViews(begin, count - begin);
                }
            }
        });
        directoryNavi.addView(navi);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                directoryScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 500);
    }
}
