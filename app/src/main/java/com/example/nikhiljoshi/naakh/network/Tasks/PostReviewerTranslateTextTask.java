package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/9/16.
 */
public class PostReviewerTranslateTextTask extends AsyncTask<Object, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final String translatedText;
    private final String translationRequestUuid;
    private final Language language;
    private final String accessToken;

    public PostReviewerTranslateTextTask(NaakhApi api, String translatedText, String translationRequestUuid,
                                         Language language, String accessToken) {
        this.api = api;

        this.translatedText = translatedText;
        this.translationRequestUuid = translationRequestUuid;
        this.language = language;
        this.accessToken = accessToken;
    }

    @Override
    protected TranslationInfoPojo doInBackground(Object... params) {
        return api.postReviewerTranslatedText(translatedText, translationRequestUuid, language, accessToken);
    }
}
