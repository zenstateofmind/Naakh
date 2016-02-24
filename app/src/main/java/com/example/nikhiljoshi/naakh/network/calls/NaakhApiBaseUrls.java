package com.example.nikhiljoshi.naakh.network.calls;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.nikhiljoshi.naakh.R;

import org.json.JSONObject;

/**
 * Grab all the base urls
 */
public class NaakhApiBaseUrls {

    public static final String INCOMPLETE_TRANSLATION_BASE_URL = "https://naakh.herokuapp.com/api/v1/incomplete/translations";
    public static final String LOGIN_BASE_URL = "https://naakh.herokuapp.com/api/v1/oauth2/access_token";

    public static String getPostTranslatedTextUrl(String uuid) {
        return "https://naakh.herokuapp.com/api/v1/translatedtext/" + uuid;
    }

}
