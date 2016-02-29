package com.example.nikhiljoshi.naakh.network.calls;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.example.nikhiljoshi.naakh.R;

import org.json.JSONObject;

/**
 * Grab all the base urls
 */
public class NaakhApiBaseUrls {

    public static final String LOGIN_BASE_URL = "https://naakh.herokuapp.com/api/v1/oauth2/access_token";

    public static String getPostTranslatedTextUrl(String uuid) {
        return "https://naakh.herokuapp.com/api/v1/translatedtext/" + uuid;
    }

    /**
     * Get the URL to do a GET translation request to get a translation query that the translator
     * can translate
     */
    public static String getTranslationUrl(String language, String untranslated, int limit) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("naakh.herokuapp.com")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("incomplete")
                .appendPath("translations")
                .appendPath("")
                .appendQueryParameter(NaakhApiQueryKeys.LANGUAGE, language)
                .appendQueryParameter(NaakhApiQueryKeys.TRANSLATION_STATUS, untranslated)
                .appendQueryParameter(NaakhApiQueryKeys.LIMIT, limit + "");

        return builder.build().toString();
    }

    /**
     * URL that can be used to carry out a GET request to get all the profile information
     */
    public static String getProfileInformationUrl(String phoneNumber) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("naakh.herokuapp.com")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("translator")
                .appendPath("profile")
                .appendPath("");

        return builder.build().toString();
    }
}
