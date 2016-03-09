package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.VerificationParameter;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

import java.util.Map;

/**
 * Created by nikhiljoshi on 3/9/16.
 */
public class PostReviewerVerificationInfoTask extends AsyncTask<Object, Object, TranslationInfoPojo> {

    private final NaakhApi api;
    private final Map<VerificationParameter, Boolean> verificationInfo;
    private final String translatedTextUuid;
    private final String accessToken;

    public PostReviewerVerificationInfoTask(NaakhApi api, Map<VerificationParameter, Boolean> verificationInfo,
                                            String translatedTextUuid, String accessToken) {
        this.api = api;

        this.verificationInfo = verificationInfo;
        this.translatedTextUuid = translatedTextUuid;
        this.accessToken = accessToken;
    }

    @Override
    protected TranslationInfoPojo doInBackground(Object... params) {
        return api.postReviewerVerificationInformation(translatedTextUuid, verificationInfo, accessToken);
    }
}
