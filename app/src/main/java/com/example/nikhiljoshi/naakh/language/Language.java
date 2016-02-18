package com.example.nikhiljoshi.naakh.language;

/**
 * Representation of a language
 */
public enum Language {

    HINDI("Hindi", "hi"),
    MALAYALAM("Malayalam", "ma"),
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
}
