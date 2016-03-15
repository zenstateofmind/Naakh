package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSendingTranslations;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

/**
 * AsyncTask that returns the verifier's translation fixes
 */
public class PostReviewerTranslateTextTask extends AsyncTask<Object, Object, TranslationInfo> {

    private final NaakhApi api;
    private final OnSendingTranslations listener;
    private final String translatedText;
    private final String translationRequestUuid;
    private final Language language;
    private final String accessToken;

    public PostReviewerTranslateTextTask(NaakhApi api, OnSendingTranslations listener, String translatedText, String translationRequestUuid,
                                         Language language, String accessToken) {
        this.api = api;
        this.listener = listener;
        this.translatedText = translatedText;
        this.translationRequestUuid = translationRequestUuid;
        this.language = language;
        this.accessToken = accessToken;
    }

    @Override
    protected void onPostExecute(TranslationInfo translationInfo) {
        listener.runOnSendingTranslations(translationInfo);
    }

    @Override
    protected TranslationInfo doInBackground(Object... params) {
        return api.postReviewerTranslatedText(translatedText, translationRequestUuid, language, accessToken);
    }
}
