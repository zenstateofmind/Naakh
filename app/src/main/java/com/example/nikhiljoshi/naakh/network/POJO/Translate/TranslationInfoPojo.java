package com.example.nikhiljoshi.naakh.network.POJO.Translate;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class TranslationInfoPojo {

    private TranslationRequestPojo translation_request;
    private String uuid;
    private String translation_text;

    public TranslationRequestPojo getTranslation_request() {
        return translation_request;
    }

    public void setTranslation_request(TranslationRequestPojo translation_request) {
        this.translation_request = translation_request;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTranslation_text() {
        return translation_text;
    }

    public void setTranslation_text(String translation_text) {
        this.translation_text = translation_text;
    }
}
