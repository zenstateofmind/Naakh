package com.example.nikhiljoshi.naakh.network.POJO.Translate;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nikhiljoshi on 3/14/16.
 */
public class Tone implements Parcelable {

    private String description;
    private String examples;
    private String name;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.description);
        dest.writeString(this.examples);
        dest.writeString(this.name);
    }

    public Tone() {
    }

    private Tone(Parcel in) {
        this.description = in.readString();
        this.examples = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Tone> CREATOR = new Parcelable.Creator<Tone>() {
        public Tone createFromParcel(Parcel source) {
            return new Tone(source);
        }

        public Tone[] newArray(int size) {
            return new Tone[size];
        }
    };
}
