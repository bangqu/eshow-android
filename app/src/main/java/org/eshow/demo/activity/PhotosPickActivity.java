package org.eshow.demo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bangqu.lib.slipload.widget.SlipLoadLayout;
import com.bangqu.lib.utils.CameraUtil;
import com.bangqu.lib.volley.ResponseCallBack;
import com.bangqu.lib.widget.LoadingView;
import com.bangqu.lib.widget.MenuDialog;
import com.bangqu.lib.widget.UnScrollGridView;
import com.bangqu.photos.GalleryActivity;
import com.bangqu.photos.PhotosActivity;
import com.bangqu.photos.util.ImageSelect;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.eshow.demo.R;
import org.eshow.demo.adapter.MsgSysAdapter;
import org.eshow.demo.adapter.PhotosAdapter;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.MsgModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class PhotosPickActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.photospick_gv)
    UnScrollGridView photosGv;

    private PhotosAdapter photosAdapter;
    private List<String> photos = new ArrayList<>();
    private File cameraFile;
    final int REQUEST_PHOTO = 1;
    final int REQUEST_CAMERA = 3;
    final int PERMISSIONS_REQUEST_STORAGE = 1001;
    final int PERMISSIONS_REQUEST_CAMERA = 1002;
    private MenuDialog menuDialog;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_photospick);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("多图选择");
        photosAdapter = new PhotosAdapter(this, photos);
        photosGv.setAdapter(photosAdapter);
        menuDialog = new MenuDialog(this);
    }

    @Override
    protected void addViewListener() {
        photosGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == photos.size()) {
                    if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSIONS_REQUEST_STORAGE)) {
                        showMenuDialog();
                    }
                } else {
                    Intent intent = new Intent(PhotosPickActivity.this, GalleryActivity.class);
                    intent.putStringArrayListExtra("path", new ArrayList<>(ImageSelect.mSelectedImage));
                    intent.putExtra("index", position);
                    startActivityForResult(intent, REQUEST_PHOTO);
                }
            }
        });
    }

    private void showMenuDialog() {
        menuDialog.setMenuItems(new String[]{"拍照", "图库选择"}, new MenuDialog.OnMenuItemClickedListener() {
            @Override
            public void MenuItemClicked(String value, int position) {
                switch (position) {
                    case 0:
                        takePhoto();
                        break;
                    case 1:
                        pickPhotos();
                        break;
                }
            }
        }).show();
    }

    private void takePhoto() {
        if (checkPermission(Manifest.permission.CAMERA, PERMISSIONS_REQUEST_CAMERA)) {
            cameraFile = new File(getDiskCacheDir(this), CameraUtil.getPhotoFileName());
            Uri fileUri = FileProvider.getUriForFile(this, "org.eshow.demo.fileprovider", cameraFile);
            startActivityForResult(CameraUtil.getTakePickIntent(fileUri), REQUEST_CAMERA);
        }
    }

    private void pickPhotos() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("single", false);
        goToActivityForResult(PhotosActivity.class, bundle, REQUEST_PHOTO);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    photos.clear();
                    ImageSelect.mSelectedImage.add(cameraFile.getPath());
                    photos.addAll(ImageSelect.mSelectedImage);
                    photosAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_PHOTO:
                    photos.clear();
                    photos.addAll(ImageSelect.mSelectedImage);
                    photosAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_STORAGE:
                    showMenuDialog();
                    break;
                case PERMISSIONS_REQUEST_CAMERA:
                    takePhoto();
                    break;
            }
        } else {
            showToast("获取相关权限失败");
        }
    }

    public String getDiskCacheDir(Context context) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

}
