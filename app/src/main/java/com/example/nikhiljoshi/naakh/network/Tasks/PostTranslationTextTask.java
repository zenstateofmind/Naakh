package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class PostTranslationTextTask extends AsyncTask<Object, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final String translatedText;
    private final String uuid;

    public PostTranslationTextTask(NaakhApi api, String translatedText, String uuid) {

        this.api = api;
        this.translatedText = translatedText;
        this.uuid = uuid;
    }

    @Override
    protected TranslationInfoPojo doInBackground(Object... params) {
        return api.postTranslateJob(uuid, translatedText);
    }
}
