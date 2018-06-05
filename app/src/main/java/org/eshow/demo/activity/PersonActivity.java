package org.eshow.demo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bangqu.greendao.dao.PersonDao;
import com.bangqu.greendao.db.DbManager;
import com.bangqu.greendao.entity.Person;
import com.bangqu.lib.utils.DateFormatUtil;
import com.bangqu.lib.widget.MenuDialog;
import com.bangqu.lib.widget.TextViewPlus;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.EdittextBundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.user_mobile)
    TextViewPlus userMobile;
    @BindView(R.id.user_nickname)
    TextViewPlus userNickname;
    @BindView(R.id.user_sex)
    TextViewPlus userSex;
    @BindView(R.id.user_birthday)
    TextViewPlus userBirthday;
    @BindView(R.id.user_age)
    TextViewPlus userAge;
    @BindView(R.id.user_intro)
    TextViewPlus userIntro;

    private MenuDialog menuDialog;
    final int REQUEST_NICKNAME = 8001;
    final int REQUEST_INTRO = 8002;
    final int REQUEST_MOBILE = 8003;
    private TimePickerView mTimePickerView;
    private OptionsPickerView pvOptions;
    private Person person;
    private ArrayList<Integer> ages;
    private PersonDao personDao;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_person);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("添加人员");
        rightTv.setText(R.string.save);
        rightTv.setVisibility(View.VISIBLE);
        personDao = DbManager.getDaoSession(this).getPersonDao();
        person = new Person();
        menuDialog = new MenuDialog(this);
        ages = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            ages.add(i);
        }
    }

    private void showUserInfo() {
        setTextValue(userNickname, person.getName());
        setTextValue(userSex, person.getSex() ? "男" : "女");
        setTextValue(userMobile, person.getMobile());
        setTextValue(userBirthday, DateFormatUtil.getDate2Str(person.getBirthday(), DateFormatUtil.DATE_FORMAT));
        setTextValue(userIntro, person.getIntro());
    }

    @OnClick({R.id.right_tv, R.id.person_name, R.id.person_sex, R.id.person_birthday, R.id.person_mobile, R.id.person_age, R.id.person_intro})
    public void onViewClicked(View view) {
        EdittextBundle edittextBundle = new EdittextBundle();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.right_tv:
                personDao.insertOrReplace(person);
                onBackPressed();
                break;
            case R.id.person_name:
                edittextBundle.title = "姓名";
                edittextBundle.value = person.getName();
                edittextBundle.hint = "请输入姓名";
                bundle.putParcelable(Constants.INTENT_OBJECT, edittextBundle);
                goToActivityForResult(EdittextActivity.class, bundle, REQUEST_NICKNAME);
                break;
            case R.id.person_intro:
                edittextBundle.title = "个人简介";
                edittextBundle.value = person.getIntro();
                edittextBundle.hint = "请输入个人简介";
                bundle.putParcelable(Constants.INTENT_OBJECT, edittextBundle);
                goToActivityForResult(EdittextActivity.class, bundle, REQUEST_INTRO);
                break;
            case R.id.person_mobile:
                edittextBundle.title = "手机号";
                edittextBundle.value = person.getIntro();
                edittextBundle.hint = "请输入手机号";
                bundle.putParcelable(Constants.INTENT_OBJECT, edittextBundle);
                goToActivityForResult(EdittextActivity.class, bundle, REQUEST_MOBILE);
                break;
            case R.id.person_sex:
                menuDialog.setMenuItems(new String[]{"男", "女"}, new MenuDialog.OnMenuItemClickedListener() {
                    @Override
                    public void MenuItemClicked(String value, int position) {
                        switch (position) {
                            case 0:
                                userSex.setText("男");
                                person.setSex(true);
                                break;
                            case 1:
                                userSex.setText("女");
                                person.setSex(false);
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.person_birthday:
                showPickBirthday();
                break;
            case R.id.person_age:
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
                    person.setBirthday(date);
                    String birth = DateFormatUtil.getDate2Str(date, DateFormatUtil.DATE_FORMAT);
                    userBirthday.setText(birth);
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
                    person.setAge(ages.get(options1));
                    userAge.setText(ages.get(options1) + "");
                }
            }).setSubmitText("完成")//确认按钮文字
//                .setSubmitColor(mContext.getResources().getColor(R.color.color_blue))//确定按钮文字颜色
//                .setCancelColor(mContext.getResources().getColor(R.color.color_stroke))//取消按钮文字颜色
//                .setTitleText("城市选择")
                    .setDividerColor(Color.BLACK)
                    .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                    .setContentTextSize(20)
                    .build();
            pvOptions.setPicker(ages);
        }
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_NICKNAME:
                    String nickname = data.getStringExtra(Constants.INTENT_OBJECT);
                    userNickname.setText(nickname);
                    person.setName(nickname);
                    break;
                case REQUEST_INTRO:
                    String intro = data.getStringExtra(Constants.INTENT_OBJECT);
                    userIntro.setText(intro);
                    person.setIntro(intro);
                    break;
                case REQUEST_MOBILE:
                    String mobile = data.getStringExtra(Constants.INTENT_OBJECT);
                    userMobile.setText(mobile);
                    person.setMobile(mobile);
                    break;
            }
        }
    }
}
