package org.eshow.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.EdittextBundle;

import butterknife.BindView;
import butterknife.OnClick;

public class EdittextActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_tv)
    TextView rightTv;
    @BindView(R.id.edittext_et)
    TextInputEditText edittextEt;
    @BindView(R.id.edittext_ly)
    TextInputLayout edittextLy;

    private EdittextBundle edittextBundle;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_edittext);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        rightTv.setText(R.string.save);
        rightTv.setVisibility(View.VISIBLE);
        edittextBundle = getIntent().getParcelableExtra(Constants.INTENT_OBJECT);
        title.setText(edittextBundle.title);
        setTextValue(edittextEt, edittextBundle.value);
        edittextLy.setHint(edittextBundle.hint);
    }

    @OnClick({R.id.right_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_tv:
                if (!TextUtils.isEmpty(edittextEt.getText())) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.INTENT_OBJECT, edittextEt.getText().toString());
                    setResult(RESULT_OK, intent);
                    onBackPressed();
                }
                break;
        }
    }
}
