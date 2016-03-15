package com.example.nikhiljoshi.naakh.network.POJO.Profile;

import java.util.List;

/**
 * Contains the json keys that are going to be returned when hitting the profile endpoint for the
 * NaakhAPI.
 *
 * <br> Note: Don't change the name of the variables unless there are changes made in the
 * NaakhAPI.
 */
public class ProfileObject {

    private List<String> fluent_languages;
    private String total_money_earned;
    private int words_translated;
    private String name;

    public List<String> getFluentLanguages() {
        return fluent_languages;
    }

    public void setFluentLanguages(List<String> fluent_languages) {
        this.fluent_languages = fluent_languages;
    }

    public String getTotalMoneyEarned() {
        return total_money_earned;
    }

    public void setTotalMoneyEarned(String total_money_earned) {
        this.total_money_earned = total_money_earned;
    }

    public int getWordsTranslated() {
        return words_translated;
    }

    public void setWordsTranslated(int words_translated) {
        this.words_translated = words_translated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
