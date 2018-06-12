package org.eshow.demo.util;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;

import com.bangqu.lib.utils.TimerThread;

import org.eshow.demo.R;

/**
 * long milliseconds ：震动的时长，单位是毫秒
 * long[] pattern  ：自定义震动模式 。数组中数字的含义依次是[静止时长，震动时长，静止时长，震动时长。。。]
 * 时长的单位是毫秒
 * boolean isRepeat ： 是否反复震动，如果是true，反复震动，如果是false，只震动一次
 */
public class AlarmNotice {

    private SoundPool soundPool;
    private Vibrator vib;
    private static AlarmNotice alarmManager;
    private long DEFAULT_KEEPTIME = 3000;
    private int streamId = -1;
    private long[] PATTERN = {0, 1000, 1000, 1000, 1000, 1000, 1000};
    private boolean isNotice = false;
    private TimerThread timerThread;
    private final int message_what = 1;
    private final int sleep_time = 1000;
    private long residueTime = 0;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == message_what) {
                checkResidueTime();
            }
            super.handleMessage(msg);
        }
    };

    private void checkResidueTime() {
        residueTime -= 1000;
        if (residueTime <= 0) {
            stopNotice();
        }
    }

    public static AlarmNotice getInstance(Context context) {
        if (alarmManager == null) {
            alarmManager = new AlarmNotice(context);
        }
        return alarmManager;
    }

    public AlarmNotice(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        //加载deep 音频文件
        soundPool.load(context, R.raw.beep, 1);
        vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }

    public void startNotice(boolean isSound, boolean isVibrator, long keepTime) {
        stopNotice();
        if (isSound)
            playSound();
        if (isVibrator)
            startVibrate(PATTERN, true);
        residueTime = keepTime > 0 ? keepTime : DEFAULT_KEEPTIME;
        if (timerThread == null) {
            timerThread = new TimerThread(handler, message_what, sleep_time);
            timerThread.start();
        } else {
            timerThread.resumeThread();
        }
    }

    private void stopNotice() {
        if (timerThread != null)
            timerThread.pauseThread();
        if (streamId > 0 && soundPool != null)
            soundPool.stop(streamId);
        if (vib != null && vib.hasVibrator()) {
            vib.cancel();
        }
    }

    private void playSound() {
        //播放deep
        streamId = soundPool.play(1, 1, 1, 0, -1, 1);
    }

    private void startVibrate(long milliseconds) {
        vib.vibrate(milliseconds);
    }

    private void startVibrate(long[] pattern, boolean isRepeat) {
        vib.vibrate(pattern, isRepeat ? 1 : -1);
    }
}
