package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSendingTranslations;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class PostUntranslatedTranslateTextTask extends AsyncTask<String, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final OnSendingTranslations listener;

    public PostUntranslatedTranslateTextTask(NaakhApi api, OnSendingTranslations listener) {
        this.api = api;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
        listener.runOnSendingTranslations(translationInfoPojo);
    }

    @Override
    protected TranslationInfoPojo doInBackground(String... params) {
        String translatedText = params[0];
        String translatedTextUuid = params[1];
        String accessToken = params[2];
        return api.postTranslatorTranslatedText(translatedTextUuid, translatedText, accessToken);
    }
}
