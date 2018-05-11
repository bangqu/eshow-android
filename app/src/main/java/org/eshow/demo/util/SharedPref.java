package org.eshow.demo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.eshow.demo.comm.Constants;

/**
 * Created 任瑞刚
 * <p>
 * Android SharedPreferences数据存储和读取方法
 */
public class SharedPref {
    private SharedPreferences preferences;

    public SharedPref(Context context) {
        preferences = context.getSharedPreferences(Constants.APP, Context.MODE_PRIVATE);
    }

    public void setJsonInfo(String key, Object o) {
        if (o != null)
            preferences.edit().putString(key, new Gson().toJson(o)).commit();
        else
            preferences.edit().remove(key).commit();
    }

    public <T> T getJsonInfo(String key, Class<T> classOfT) {
        String value = preferences.getString(key, "");
        if (!TextUtils.isEmpty(value)) {
            return new Gson().fromJson(value, classOfT);
        }
        return null;
    }

    public void setInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void setString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void setBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean value) {
        return preferences.getBoolean(key, value);
    }

}
