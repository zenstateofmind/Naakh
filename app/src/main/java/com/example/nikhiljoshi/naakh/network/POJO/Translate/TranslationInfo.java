package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Each translateObject contains TranslationInfo Objects within it
 *
 * <br> Note: Don't change the name of the variables unless there are changes made in the
 * NaakhAPI.
 */
public class TranslationInfo implements Parcelable {

    private TranslationRequest translation_request;
    private String uuid;
    private String translation_text;
    private Tone tone;
    private List<String> topics = new ArrayList<>();

    public TranslationRequest getTranslationRequest() {
        return translation_request;
    }

    public void setTranslationRequest(TranslationRequest translation_request) {
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

    public Tone getTone() {
        return tone == null ? new Tone() : tone;
    }

    public void setTone(Tone tone) {
        this.tone = tone;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.translation_request, 0);
        dest.writeString(this.uuid);
        dest.writeString(this.translation_text);
        dest.writeParcelable(this.tone, 0);
        dest.writeStringList(this.topics);
    }

    public TranslationInfo() {
    }

    private TranslationInfo(Parcel in) {
        this.translation_request = in.readParcelable(TranslationRequest.class.getClassLoader());
        this.uuid = in.readString();
        this.translation_text = in.readString();
        this.tone = in.readParcelable(Tone.class.getClassLoader());
        this.topics = new ArrayList<String>();
        in.readStringList(this.topics);
    }

    public static final Parcelable.Creator<TranslationInfo> CREATOR = new Parcelable.Creator<TranslationInfo>() {
        public TranslationInfo createFromParcel(Parcel source) {
            return new TranslationInfo(source);
        }

        public TranslationInfo[] newArray(int size) {
            return new TranslationInfo[size];
        }
    };
}
