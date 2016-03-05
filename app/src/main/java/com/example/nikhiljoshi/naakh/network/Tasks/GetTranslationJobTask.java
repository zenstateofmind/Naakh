package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class GetTranslationJobTask extends AsyncTask<Object, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final Language language;
    private final TranslationStatus translation_status;

    public GetTranslationJobTask(NaakhApi api, Language language, TranslationStatus translation_status) {
        this.api = api;
        this.language = language;
        this.translation_status = translation_status;
    }

    @Override
    protected TranslationInfoPojo doInBackground(Object... params) {
        return api.getTranslateJob(language, translation_status);
    }
}
