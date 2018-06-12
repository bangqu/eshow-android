package org.eshow.demo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-5-8 0008.
 */

public class MusicModel implements Parcelable {
    public String singer;
    public String song;
    public String path;
    public long duration;
    public long size;
    public long album_id;
    public long song_id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.singer);
        dest.writeString(this.song);
        dest.writeString(this.path);
        dest.writeLong(this.duration);
        dest.writeLong(this.size);
        dest.writeLong(this.album_id);
        dest.writeLong(this.song_id);
    }

    public MusicModel() {
    }

    protected MusicModel(Parcel in) {
        this.singer = in.readString();
        this.song = in.readString();
        this.path = in.readString();
        this.duration = in.readLong();
        this.size = in.readLong();
        this.album_id = in.readLong();
        this.song_id = in.readLong();
    }

    public static final Parcelable.Creator<MusicModel> CREATOR = new Parcelable.Creator<MusicModel>() {
        @Override
        public MusicModel createFromParcel(Parcel source) {
            return new MusicModel(source);
        }

        @Override
        public MusicModel[] newArray(int size) {
            return new MusicModel[size];
        }
    };
}
