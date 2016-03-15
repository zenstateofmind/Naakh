package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.Enums.VerificationParameter;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

import java.util.Map;

/**
 * AsyncTask that updates the verifier's take on the context/spelling/grammar of a specific
 * translation
 */
public class PostReviewerVerificationInfoTask extends AsyncTask<Object, Object, TranslationInfo> {

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
    protected TranslationInfo doInBackground(Object... params) {
        return api.postReviewerVerificationInformation(translatedTextUuid, verificationInfo, accessToken);
    }
}
