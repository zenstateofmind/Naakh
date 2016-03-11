package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class TranslationRequestPojo implements Parcelable {

    private String text;
    private String uuid;

    public String getTranslationRequestText() {
        return text;
    }

    public String getTranslationRequestUuid() {
        return uuid;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.uuid);
    }

    public TranslationRequestPojo() {
    }

    private TranslationRequestPojo(Parcel in) {
        this.text = in.readString();
        this.uuid = in.readString();
    }

    public static final Parcelable.Creator<TranslationRequestPojo> CREATOR = new Parcelable.Creator<TranslationRequestPojo>() {
        public TranslationRequestPojo createFromParcel(Parcel source) {
            return new TranslationRequestPojo(source);
        }

        public TranslationRequestPojo[] newArray(int size) {
            return new TranslationRequestPojo[size];
        }
    };
}
