package com.example.nikhiljoshi.naakh.Enums;

import android.text.TextUtils;

/**
 * Representation of a language. Also contains additional methods related to languages.
 * An important method in here: Given a bunch of languages that the user is fluent in,
 * we return *a* fluent language that will be used by translating and verification APIs to return
 * a translated object for the translator/reviewer to work on
 */
public enum Language {

    HINDI("Hindi", "hi"),
    MALAYALAM("Malayalam", "ml"),
    KANNADA("Kannada", "ka");

    private final String dbValue;
    private final String language;

    private Language(String language, String dbValue) {
        this.language = language;
        this.dbValue = dbValue;
    }

    @Override
    public String toString() {
        return language;
    }

    public String getDbValue() {
        return dbValue;
    }

    // TODO: What happens when the user doesnt select a fluent language
    public static Language chooseFluentLanguage(String fluentLanguages) {
        final String[] fluentLangArray = TextUtils.split(fluentLanguages, ",");
        return getLanguageEnum(fluentLangArray[0]);
    }

    public static Language getLanguageEnum(String language) {
        if (language != null) {
            for (Language languageEnum : Language.values()) {
                if (language.trim().equalsIgnoreCase(languageEnum.dbValue) ||
                        language.trim().equalsIgnoreCase(language.toString())) {
                    return languageEnum;
                }
            }
        }
        return Language.HINDI;
    }
}
