package org.eshow.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangqu.lib.volley.ResponseCallBack;
import com.bangqu.lib.volley.VolleyCallBack;
import com.bangqu.photos.PhotosActivity;
import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.HttpConfig;
import org.eshow.demo.util.LogInfo;
import org.eshow.demo.util.QiniuUpload;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageViewActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.image_original)
    ImageView imageOriginal;
    @BindView(R.id.image_thumbnail)
    ImageView imageThumbnail;

    private String imageUrl = "http://p33rsiln2.bkt.clouddn.com/2018/05/28/1527493736848";
    private String suffix = "?imageView2/0/w/300";
    private String token = "MIhP6Vyx0ozuZlH3X3_P9ZoY0irIilhikXc2F2Jw:9H_PphC4FFX_RcJbzh6H72EYPX4=:eyJzY29wZSI6InhpbmdwYWkiLCJkZWFkbGluZSI6MTUyNzQ5NzIzNX0=";
    final int REQUEST_PHOTO = 1;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_imageview);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("图片加载");
        getQiNiuToken();
    }

    @OnClick({R.id.qiniu_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qiniu_upload:
                pickPhotos();
                break;
        }
    }

    private void getQiNiuToken() {
        getData(HttpConfig.GET_QN_KEY, null, new VolleyCallBack("key", responseCallBack));
        getData(HttpConfig.GET_QN_TOKEN, null, new VolleyCallBack("token", responseCallBack));
    }

    private ResponseCallBack responseCallBack = new ResponseCallBack() {
        @Override
        public void onResponseSuccess(String tag, JsonObject response, String code, String msg) {
            //请求成功
        }

        @Override
        public void onResponseNoData(String tag, String code, String msg) {
            //无数据
        }

        @Override
        public void onResponseError(String tag, String code, String msg) {
            //请求失败
        }

        @Override
        public void onResponseOverDue(String tag, String code, String msg) {
            //用户信息过期
        }
    };

    private void pickPhotos() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("single", true);
        goToActivityForResult(PhotosActivity.class, bundle, REQUEST_PHOTO);
    }

    private void uploadHeader(String path) {
        QiniuUpload.getInstance().UploadImageDelay(path, "2018/05/28/1527493736848", token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                //res包含hash、key等信息，具体字段取决于上传策略的设置
                if (info.isOK()) {
                    String header_url = HttpConfig.QINIU_URL + key;
                    LogInfo.e(header_url);
                    loadImage(header_url);
                } else {
                    showToast("上传图片失败");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                    LogInfo.e(key + ",\r\n " + info + ",\r\n " + response);
                }
            }
        });
    }

    private void loadImage(String header_url) {
        Glide.with(this).load(header_url).into(imageOriginal);
        Glide.with(this).load(header_url + suffix).into(imageThumbnail);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO:
                    Bundle bundle = data.getExtras();
                    String path = bundle.getString("photo");
                    uploadHeader(path);
                    break;
            }
        }
    }

}
