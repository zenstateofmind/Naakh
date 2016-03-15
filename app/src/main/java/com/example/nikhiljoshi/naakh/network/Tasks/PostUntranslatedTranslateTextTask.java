package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSendingTranslations;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

/**
 * AsyncTask that posts the translator's translations to the Naakh API endpoint
 */
public class PostUntranslatedTranslateTextTask extends AsyncTask<String, Object, TranslationInfo> {

    private final NaakhApi api;
    private final OnSendingTranslations listener;

    public PostUntranslatedTranslateTextTask(NaakhApi api, OnSendingTranslations listener) {
        this.api = api;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(TranslationInfo translationInfo) {
        listener.runOnSendingTranslations(translationInfo);
    }

    @Override
    protected TranslationInfo doInBackground(String... params) {
        String translatedText = params[0];
        String translatedTextUuid = params[1];
        String accessToken = params[2];
        return api.postTranslatorTranslatedText(translatedTextUuid, translatedText, accessToken);
    }
}
