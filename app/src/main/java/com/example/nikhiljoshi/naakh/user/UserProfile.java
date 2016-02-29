package com.example.nikhiljoshi.naakh.user;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This will a POJO for a user. This object will contain information such as the
 * languages that the user is proficient in, the name of the user, and maybe some other information
 *
 * Maybe this might be needed to be broken up and the info might be required to be stored in
 * SharedPreferences. Investigate!
 */
public class UserProfile implements Parcelable {


    private String name;
    private String[] languages;
    private String moneyEarned;
    private String numWordsTranslated;

    public UserProfile(String name, String[] languages, String moneyEarned, String numWordsTranslated) {
        this.name = name;
        this.languages = languages;
        this.moneyEarned = moneyEarned;
        this.numWordsTranslated = numWordsTranslated;
    }

    public UserProfile() {
        this.name = "";
        this.languages = new String[0];
        this.moneyEarned = "";
        this.numWordsTranslated = "";
    }

    public String getName() {
        return name;
    }

    public String[] getLanguages() {
        return languages;
    }

    public String getMoneyEarned() {
        return moneyEarned;
    }

    public String getNumWordsTranslated() {
        return numWordsTranslated;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public void setMoneyEarned(String moneyEarned) {
        this.moneyEarned = moneyEarned;
    }

    public void setNumWordsTranslated(String numWordsTranslated) {
        this.numWordsTranslated = numWordsTranslated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringArray(this.languages);
        dest.writeString(this.moneyEarned);
        dest.writeString(this.numWordsTranslated);
    }

    private UserProfile(Parcel in) {
        this.name = in.readString();
        this.languages = in.createStringArray();
        this.moneyEarned = in.readString();
        this.numWordsTranslated = in.readString();
    }

    public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
        public UserProfile createFromParcel(Parcel source) {
            return new UserProfile(source);
        }

        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };
}
