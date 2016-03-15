package com.example.nikhiljoshi.naakh.Enums;

/**
 * Translation Statuses. Different types of statuses that will be used while trying to fetch
 * translations/verifications for the translator to work on.
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
