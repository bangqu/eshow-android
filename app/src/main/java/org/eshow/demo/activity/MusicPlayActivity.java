package org.eshow.demo.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bangqu.lib.utils.TimerThread;
import com.bumptech.glide.Glide;

import org.eshow.demo.R;
import org.eshow.demo.base.BaseActivity;
import org.eshow.demo.comm.Constants;
import org.eshow.demo.model.MusicModel;
import org.eshow.demo.util.AlarmNotice;
import org.eshow.demo.util.LogInfo;
import org.eshow.demo.util.MusicUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class MusicPlayActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.music_cover)
    ImageView cover;
    @BindView(R.id.music_song)
    TextView song;
    @BindView(R.id.music_singer)
    TextView singer;
    @BindView(R.id.music_play)
    CheckBox musicPlay;
    @BindView(R.id.music_seekbar)
    AppCompatSeekBar musicSeekbar;
    @BindView(R.id.music_time)
    TextView musicTime;
    @BindView(R.id.music_long)
    TextView musicLong;

    private MusicModel musicModel;

    private final int message_what = 1;
    private final int sleep_time = 1000;
    private TimerThread timerThread;
    private long playTimes;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        super.setLayoutView(savedInstanceState);
        setContentView(R.layout.activity_musicplay);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        musicModel = getIntent().getParcelableExtra(Constants.INTENT_OBJECT);
        if (musicModel != null) {
            getSupportActionBar().setTitle(musicModel.song.substring(0, musicModel.song.lastIndexOf(".")));
            getSupportActionBar().setSubtitle(musicModel.singer);
            song.setText(musicModel.song.substring(0, musicModel.song.lastIndexOf(".")));
            singer.setText(musicModel.singer);
            Glide.with(this).load(MusicUtils.getAlbumArt(this, musicModel.album_id))
                    .into(cover);
            musicTime.setText("00:00");
            musicLong.setText(MusicUtils.formatTime(musicModel.duration));
            musicSeekbar.setMax((int) musicModel.duration);
        } else {
            showToast("未获取到音乐数据");
            finish();
        }
    }

    @OnClick({R.id.music_pre, R.id.music_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.music_pre:
                break;
            case R.id.music_next:
                break;
        }
    }

    @Override
    protected void addViewListener() {
        musicPlay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    if (isChecked) {
                        playMusic();
                    } else {
                        pauseMusic();
                    }
                }
            }
        });
        musicSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            }
        });
    }

    private void startPlayerTime() {
        if (timerThread == null) {
            timerThread = new TimerThread(handler, message_what, sleep_time);
            timerThread.start();
        } else {
            timerThread.resumeThread();
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == message_what) {
                showPlayTime();
            }
            super.handleMessage(msg);
        }
    };

    private void showPlayTime() {
        musicTime.setText(MusicUtils.formatTime(mediaPlayer.getCurrentPosition()));
        musicSeekbar.setProgress(mediaPlayer.getCurrentPosition());
    }

    private MediaPlayer mediaPlayer;

    private void playMusic() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setNextMediaPlayer(mediaPlayer);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    startPlayerTime();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playDone();
                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
            try {
                mediaPlayer.setDataSource(musicModel.path);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                LogInfo.e(e);
            }
        } else {
            mediaPlayer.start();
            startPlayerTime();
        }
    }

    private void playDone() {
        musicPlay.setChecked(false);
        if (timerThread != null) {
            timerThread.pauseThread();
        }
        musicTime.setText("00:00");
        musicSeekbar.setProgress(0);
    }

    private void pauseMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        if (timerThread != null) {
            timerThread.pauseThread();
        }
    }

    //来电处理
    protected void onDestroy() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
        if (timerThread != null) {
            timerThread.pauseThread();
            timerThread = null;
        }
        super.onDestroy();
    }

    protected void onPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        }
        if (timerThread != null) {
            timerThread.pauseThread();
        }
        super.onPause();
    }

    protected void onResume() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        }
        if (timerThread != null) {
            timerThread.resumeThread();
        }
        super.onResume();
    }

}
