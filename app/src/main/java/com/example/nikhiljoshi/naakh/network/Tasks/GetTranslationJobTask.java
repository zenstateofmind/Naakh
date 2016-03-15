package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

/**
 * AsyncTask that calls the NaakhAPI and returns the translation objects that either need
 * to be translated or verified
 */
public class GetTranslationJobTask extends AsyncTask<String, Object, TranslationInfo> {

    private final NaakhApi api;
    private final OnGettingIncompleteTranslatedText onGettingIncompleteTranslatedText;
    private final Language language;
    private final TranslationStatus translationStatus;
    private final String accessToken;

    public GetTranslationJobTask(NaakhApi api, OnGettingIncompleteTranslatedText onGettingIncompleteTranslatedText,
                                 Language language, TranslationStatus translationStatus,
                                 String accessToken) {
        this.api = api;
        this.onGettingIncompleteTranslatedText = onGettingIncompleteTranslatedText;
        this.language = language;
        this.translationStatus = translationStatus;
        this.accessToken = accessToken;
    }

    @Override
    protected void onPostExecute(TranslationInfo translationInfo) {
        onGettingIncompleteTranslatedText.takeActionWithIncompleteTranslatedTextObject(translationInfo,
                translationStatus);
    }

    @Override
    protected TranslationInfo doInBackground(String... params) {
        return api.getTranslateJob(language, translationStatus, accessToken);
    }
}
