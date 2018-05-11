package org.eshow.demo.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bangqu.lib.utils.CameraUtil;
import com.bangqu.lib.utils.DateFormatUtil;
import com.bangqu.lib.widget.MenuDialog;
import com.bangqu.lib.widget.RoundImageView;
import com.bangqu.lib.widget.TextViewPlus;
import com.bangqu.photos.PhotosActivity;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.EdittextBundle;
import org.eshow.demo.model.UserInfo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.user_header)
    RoundImageView userHeader;
    @BindView(R.id.user_mobile)
    TextView userMobile;
    @BindView(R.id.user_nickname)
    TextViewPlus userNickname;
    @BindView(R.id.user_sex)
    TextViewPlus userSex;
    @BindView(R.id.user_birthday)
    TextViewPlus userBirthday;
    @BindView(R.id.user_constellation)
    TextViewPlus userConstellation;
    @BindView(R.id.user_intro)
    TextViewPlus userIntro;

    final int REQUEST_PHOTO = 1;
    final int REQUEST_CROP = 2;
    final int REQUEST_CAMERA = 3;
    final int PERMISSIONS_REQUEST_STORAGE = 1001;
    final int PERMISSIONS_REQUEST_CAMERA = 1002;
    private MenuDialog menuDialog;
    private File cropFile, cameraFile;
    private UserInfo userInfo;
    final int REQUEST_NICKNAME = 8001;
    final int REQUEST_INTRO = 8002;
    private TimePickerView mTimePickerView;
    private OptionsPickerView pvOptions;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("个人中心");
        userInfo = sharedPref.getJsonInfo(Constants.USER_INFO, UserInfo.class);
        menuDialog = new MenuDialog(this);
        userMobile.setText("15866668888");
        userBirthday.setText("1990-01-01");
        if (userInfo != null) {
            showUserInfo();
        } else {
            userInfo = new UserInfo();
        }
    }

    private void showUserInfo() {
        if (!TextUtils.isEmpty(userInfo.header)) {
            Glide.with(this).load(userInfo.header)
                    .apply(new RequestOptions().placeholder(R.mipmap.ic_defaultheader)).into(userHeader);
        }
        setTextValue(userNickname, userInfo.nickname);
        setTextValue(userSex, userInfo.sex);
        setTextValue(userMobile, userInfo.mobile);
        setTextValue(userBirthday, userInfo.birthday);
        setTextValue(userConstellation, userInfo.constellation);
        setTextValue(userIntro, userInfo.intro);
    }

    @OnClick({R.id.update_header, R.id.update_nickname, R.id.update_sex, R.id.update_birthday, R.id.update_constellation, R.id.update_intro})
    public void onViewClicked(View view) {
        EdittextBundle edittextBundle = new EdittextBundle();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.update_header:
                if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSIONS_REQUEST_STORAGE)) {
                    showPickHeaderMenu();
                }
                break;
            case R.id.update_nickname:
                edittextBundle.title = "昵称";
                edittextBundle.value = userInfo.nickname;
                edittextBundle.hint = "请输入昵称";
                bundle.putParcelable(Constants.INTENT_OBJECT, edittextBundle);
                goToActivityForResult(EdittextActivity.class, bundle, REQUEST_NICKNAME);
                break;
            case R.id.update_intro:
                edittextBundle.title = "个人简介";
                edittextBundle.value = userInfo.intro;
                edittextBundle.hint = "请输入个人简介";
                bundle.putParcelable(Constants.INTENT_OBJECT, edittextBundle);
                goToActivityForResult(EdittextActivity.class, bundle, REQUEST_INTRO);
                break;
            case R.id.update_sex:
                menuDialog.setMenuItems(new String[]{"男", "女"}, new MenuDialog.OnMenuItemClickedListener() {
                    @Override
                    public void MenuItemClicked(String value,int position) {
                        switch (position) {
                            case 0:
                                userSex.setText("男");
                                break;
                            case 1:
                                userSex.setText("女");
                                break;
                        }
                        userInfo.sex = userSex.getText().toString();
                        sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
                    }
                }).show();
                break;
            case R.id.update_birthday:
                showPickBirthday();
                break;
            case R.id.update_constellation:
                showConstellationPick();
                break;
        }
    }

    private void showPickBirthday() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateFormatUtil.getString2Date(userBirthday.getText().toString(), DateFormatUtil.DATE_FORMAT));
        //时间选择器
        if (mTimePickerView == null) {
            mTimePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {//选中事件回调
                    String birth = DateFormatUtil.getDate2Str(date, DateFormatUtil.DATE_FORMAT);
                    userBirthday.setText(birth);
                    userInfo.birthday = birth;
                    sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
                }
            }).setType(new boolean[]{true, true, true, false, false, false}).build();
        }
        mTimePickerView.setDate(calendar);
        mTimePickerView.show();
    }

    private void showConstellationPick() {
        if (pvOptions == null) {
            pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    //返回的分别是三个级别的选中位置
                    userInfo.constellation = Constants.constellations.get(options1);
                    userConstellation.setText(userInfo.constellation);
                    sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
                }
            }).setSubmitText("完成")//确认按钮文字
//                .setSubmitColor(mContext.getResources().getColor(R.color.color_blue))//确定按钮文字颜色
//                .setCancelColor(mContext.getResources().getColor(R.color.color_stroke))//取消按钮文字颜色
//                .setTitleText("城市选择")
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(Constants.constellations);
        }
        pvOptions.show();
    }

    private void showPickHeaderMenu() {
        menuDialog.setMenuItems(new String[]{"拍照", "图库选择"}, new MenuDialog.OnMenuItemClickedListener() {
            @Override
            public void MenuItemClicked(String value,int position) {
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
        bundle.putBoolean("single", true);
        goToActivityForResult(PhotosActivity.class, bundle, REQUEST_PHOTO);
    }

    private void cropPhoto(String path) {
        cropFile = new File(getDiskCacheDir(this), "header.jpg");
        try {
            if (cropFile.exists()) {
                cropFile.delete();
            }
            cropFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri resouseUri = CameraUtil.getImageContentUri(this, new File(path));
        startActivityForResult(CameraUtil.getPhotoCrop(resouseUri, Uri.fromFile(cropFile)), REQUEST_CROP);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NICKNAME:
                    String nickname = data.getStringExtra(Constants.INTENT_OBJECT);
                    userNickname.setText(nickname);
                    userInfo.nickname = nickname;
                    sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
                    break;
                case REQUEST_INTRO:
                    String intro = data.getStringExtra(Constants.INTENT_OBJECT);
                    userIntro.setText(intro);
                    userInfo.intro = intro;
                    sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
                    break;
                case REQUEST_CAMERA:
                    cropPhoto(cameraFile.getPath());
                    break;
                case REQUEST_PHOTO:
                    Bundle bundle = data.getExtras();
                    String path = bundle.getString("photo");
                    cropPhoto(path);
                    break;
                case REQUEST_CROP:
                    Glide.with(this).load(Uri.fromFile(cropFile))
                            .apply(new RequestOptions().signature(new ObjectKey(cropFile.lastModified()))).into(userHeader);
                    userInfo.header = cropFile.getAbsolutePath();
                    sharedPref.setJsonInfo(Constants.USER_INFO, userInfo);
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
                    showPickHeaderMenu();
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
