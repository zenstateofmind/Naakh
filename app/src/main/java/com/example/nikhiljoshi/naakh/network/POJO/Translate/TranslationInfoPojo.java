package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class TranslationInfoPojo implements Parcelable {

    private TranslationRequestPojo translation_request;
    private String uuid;
    private String translation_text;

    public TranslationRequestPojo getTranslationRequest() {
        return translation_request;
    }

    public void setTranslationRequest(TranslationRequestPojo translation_request) {
        this.translation_request = translation_request;
    }

    public String getTranslatedTextUuid() {
        return uuid;
    }

    public String getTranslationText() {
        return translation_text;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setTranslationText(String translation_text) {
        this.translation_text = translation_text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.translation_request, flags);
        dest.writeString(this.uuid);
        dest.writeString(this.translation_text);
    }

    public TranslationInfoPojo() {
    }

    private TranslationInfoPojo(Parcel in) {
        this.translation_request = in.readParcelable(TranslationRequestPojo.class.getClassLoader());
        this.uuid = in.readString();
        this.translation_text = in.readString();
    }

    public static final Parcelable.Creator<TranslationInfoPojo> CREATOR = new Parcelable.Creator<TranslationInfoPojo>() {
        public TranslationInfoPojo createFromParcel(Parcel source) {
            return new TranslationInfoPojo(source);
        }

        public TranslationInfoPojo[] newArray(int size) {
            return new TranslationInfoPojo[size];
        }
    };
}
