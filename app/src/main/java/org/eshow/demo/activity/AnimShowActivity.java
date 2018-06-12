package org.eshow.demo.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimShowActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.anim_image)
    ImageView animImage;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_animshow);
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        title.setText("动画演示");
    }

    @Override
    protected void addViewListener() {
        Animation setAnim = AnimationUtils.loadAnimation(this, R.anim.set_anim);
        setAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @OnClick({R.id.anim_translate, R.id.anim_scale, R.id.anim_rotate, R.id.anim_alpha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.anim_translate:
                Animation tanimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim);
                animImage.startAnimation(tanimation);
                break;
            case R.id.anim_scale:
                Animation sanimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim);
                animImage.startAnimation(sanimation);
                break;
            case R.id.anim_rotate:
                Animation ranimation = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
                animImage.startAnimation(ranimation);
                break;
            case R.id.anim_alpha:
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
                animImage.startAnimation(animation);
                break;
        }
    }
}
