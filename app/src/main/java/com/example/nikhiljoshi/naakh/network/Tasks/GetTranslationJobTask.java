package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class GetTranslationJobTask extends AsyncTask<String, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final OnGettingIncompleteTranslatedText onGettingIncompleteTranslatedText;
    private final Language language;
    private final TranslationStatus translation_status;
    private final String access_token;

    public GetTranslationJobTask(NaakhApi api, OnGettingIncompleteTranslatedText onGettingIncompleteTranslatedText,
                                 Language language, TranslationStatus translation_status,
                                 String access_token) {
        this.api = api;
        this.onGettingIncompleteTranslatedText = onGettingIncompleteTranslatedText;
        this.language = language;
        this.translation_status = translation_status;
        this.access_token = access_token;
    }

    @Override
    protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
        onGettingIncompleteTranslatedText.updateViewWithIncompleteTranslatedTextObject(translationInfoPojo);
    }

    @Override
    protected TranslationInfoPojo doInBackground(String... params) {
        return api.getTranslateJob(language, translation_status, access_token);
    }
}
