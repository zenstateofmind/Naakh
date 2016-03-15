package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Translation Request Object. Contains the original text that needs to be translated. Also
 * contains all the languages that the phrase needs to be translated into
 *
 * <br> Note: Don't change the name of the variables unless there are changes made in the
 * NaakhAPI.
 */
public class TranslationRequest implements Parcelable {

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

    public TranslationRequest() {
    }

    private TranslationRequest(Parcel in) {
        this.text = in.readString();
        this.uuid = in.readString();
    }

    public static final Parcelable.Creator<TranslationRequest> CREATOR = new Parcelable.Creator<TranslationRequest>() {
        public TranslationRequest createFromParcel(Parcel source) {
            return new TranslationRequest(source);
        }

        public TranslationRequest[] newArray(int size) {
            return new TranslationRequest[size];
        }
    };
}
