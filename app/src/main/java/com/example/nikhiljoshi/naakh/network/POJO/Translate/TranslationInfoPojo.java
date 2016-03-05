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

    public void setTranslationRequest(TranslationRequestPojo translation_request) {
        this.translation_request = translation_request;
    }

    public String getTranslatedTextUuid() {
        return uuid;
    }

    public void setTranslatedTextUuid(String translated_text_uuid) {
        this.uuid = translated_text_uuid;
    }

    public String getTranslationText() {
        return translation_text;
    }

    public void setTranslationText(String translation_text) {
        this.translation_text = translation_text;
    }
}
