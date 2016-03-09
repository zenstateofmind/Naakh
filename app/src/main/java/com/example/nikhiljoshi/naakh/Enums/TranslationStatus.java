package com.example.nikhiljoshi.naakh.Enums;

/**
 * Created by nikhiljoshi on 3/4/16.
 */
public enum TranslationStatus {

    UNTRANSLATED("untranslated"),
    UNVERIFIED("unverified");

    private final String translationStatus;

    TranslationStatus(String translationStatus) {
        this.translationStatus = translationStatus;
    }

    public String getTranslationStatus() {
        return translationStatus;
    }
}
