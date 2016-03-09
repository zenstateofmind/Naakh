package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class PostUntranslatedTranslateTextTask extends AsyncTask<Object, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final String translatedText;
    private final String translatedTextUuid;
    private final String accessToken;

    public PostUntranslatedTranslateTextTask(NaakhApi api, String translatedText, String translatedTextUuid,
                                             String accessToken) {

        this.api = api;
        this.translatedText = translatedText;
        this.translatedTextUuid = translatedTextUuid;
        this.accessToken = accessToken;
    }


    @Override
    protected TranslationInfoPojo doInBackground(Object... params) {
        return api.postTranslatorTranslatedText(translatedTextUuid, translatedText, accessToken);
    }
}
