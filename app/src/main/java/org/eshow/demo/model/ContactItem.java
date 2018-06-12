package org.eshow.demo.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Comparator;

/**
 * Created by Administrator on 2017-12-15 0015.
 */

public class ContactItem implements Comparable<ContactItem> {
    public String name;
    public String mobile;
    public String pinyin = "#";
    public char index = '#';

    public void setPinyin(String name) {
        if (!TextUtils.isEmpty(name) && name.length() > 0) {
            pinyin = Pinyin.toPinyin(name.charAt(0));
            index = pinyin.charAt(0);
        }
    }

    @Override
    public int compareTo(@NonNull ContactItem o) {
        return this.pinyin.compareToIgnoreCase(o.pinyin);
    }
}
