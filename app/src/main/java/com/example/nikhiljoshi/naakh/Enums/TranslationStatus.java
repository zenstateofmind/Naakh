package com.example.nikhiljoshi.naakh.Enums;

/**
 * Created by nikhiljoshi on 3/4/16.
 */
public enum TranslationStatus {

    UNTRANSLATED("untranslated"),
    UNVERIFIED("unverified");

    private final String translation_status;

    TranslationStatus(String translation_status) {
        this.translation_status = translation_status;
    }

    public String get_translation_status() {
        return translation_status;
    }
}
