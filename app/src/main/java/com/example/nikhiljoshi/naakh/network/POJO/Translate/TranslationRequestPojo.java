package com.example.nikhiljoshi.naakh.network.POJO.Translate;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class TranslationRequestPojo {

    private String text;
    private String uuid;

    public String getOriginalText() {
        return text;
    }

    public void setOriginalText(String text) {
        this.text = text;
    }

    public String getOriginalPhraseUuid() {
        return uuid;
    }

    public void setOriginalPhraseUuid(String uuid) {
        this.uuid = uuid;
    }
}
