package com.bangqu.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.bangqu.photos.adapter.GalleryAdapter;
import com.bangqu.photos.util.ImageSelect;
import com.bangqu.photos.widget.ViewPagerFixed;

/**
 * 这个是用于进行图片浏览时的界面
 */
public class GalleryActivity extends AppCompatActivity {

    private TextView toolbarSure;
    private CheckBox picChoice;
    private TextView toolbarTitle;

    private ViewPagerFixed pager;
    private List<String> imagePaths;
    private int size;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);// 切屏到主界面
        imagePaths = getIntent().getStringArrayListExtra("path");
        size = imagePaths.size();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        initView();
        addListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        toolbarSure = findViewById(R.id.right_tv);
        picChoice = findViewById(R.id.pic_choice);
        toolbarTitle = findViewById(R.id.title);
        pager = findViewById(R.id.gallery01);
        //初始化选择框
        refreshButton();
        pager.setAdapter(new GalleryAdapter(this, imagePaths));
        toolbarTitle.setText("1/" + size);
        if (ImageSelect.mSelectedImage.contains(imagePaths.get(0))) {
            picChoice.setChecked(true);
        } else {
            picChoice.setChecked(false);
        }
        int index = getIntent().getIntExtra("index", 0);
        pager.setCurrentItem(index);
        toolbarTitle.setText((index + 1) + "/" + size);
    }

    private void addListener() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == toolbarSure) {
                    Intent intent = new Intent();
                    intent.putExtra("finish", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
        toolbarSure.setOnClickListener(onClickListener);
        picChoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    String path = imagePaths.get(pager.getCurrentItem());
                    if (isChecked) {
                        if (!ImageSelect.addSelectedImage(path)) {
                            Toast.makeText(GalleryActivity.this, "最多选择" + ImageSelect.MAX_SIZE + "张图片", Toast.LENGTH_SHORT).show();
                            buttonView.setChecked(false);
                        }
                    } else {
                        ImageSelect.mSelectedImage.remove(path);
                    }
                    refreshButton();
                }
            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                toolbarTitle.setText((position + 1) + "/" + size);
                if (ImageSelect.mSelectedImage.contains(imagePaths.get(position))) {
                    picChoice.setChecked(true);
                } else {
                    picChoice.setChecked(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void refreshButton() {
        if (ImageSelect.mSelectedImage.size() == 0) {
            toolbarSure.setEnabled(false);
            toolbarSure.setText("完成");
        } else {
            toolbarSure.setEnabled(true);
            toolbarSure.setText("完成(" + ImageSelect.mSelectedImage.size() + "/" + ImageSelect.MAX_SIZE + ")");
        }
    }
}
