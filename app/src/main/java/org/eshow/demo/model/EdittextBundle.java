package org.eshow.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-5-8 0008.
 */

public class EdittextBundle implements Parcelable {
    public String title;
    public String value;
    public String hint;
    public int inputType;
    public int maxLength;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.value);
        dest.writeString(this.hint);
        dest.writeInt(this.inputType);
        dest.writeInt(this.maxLength);
    }

    public EdittextBundle() {
    }

    protected EdittextBundle(Parcel in) {
        this.title = in.readString();
        this.value = in.readString();
        this.hint = in.readString();
        this.inputType = in.readInt();
        this.maxLength = in.readInt();
    }

    public static final Parcelable.Creator<EdittextBundle> CREATOR = new Parcelable.Creator<EdittextBundle>() {
        @Override
        public EdittextBundle createFromParcel(Parcel source) {
            return new EdittextBundle(source);
        }

        @Override
        public EdittextBundle[] newArray(int size) {
            return new EdittextBundle[size];
        }
    };
}
