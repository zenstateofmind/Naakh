package com.example.nikhiljoshi.naakh.network.POJO.Translate;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class TranslationRequestPojo {

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
}
