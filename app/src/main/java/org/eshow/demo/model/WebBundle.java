package org.eshow.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-5-8 0008.
 */

public class WebBundle implements Parcelable {
    public String title;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
    }

    public WebBundle() {
    }

    protected WebBundle(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<WebBundle> CREATOR = new Parcelable.Creator<WebBundle>() {
        @Override
        public WebBundle createFromParcel(Parcel source) {
            return new WebBundle(source);
        }

        @Override
        public WebBundle[] newArray(int size) {
            return new WebBundle[size];
        }
    };
}
