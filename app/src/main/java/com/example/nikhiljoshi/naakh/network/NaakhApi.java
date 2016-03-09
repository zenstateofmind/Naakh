package com.example.nikhiljoshi.naakh.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.Enums.VerificationParameter;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.GetTranslatePojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class NaakhApi {

    private static String LOG_TAG = NaakhApi.class.getSimpleName();
    //TODO: Another thing that can be injected
    private NaakhClient client;


    //TODO: Inject it here maybe?
    public NaakhApi() {
        this.client = ServiceGenerator.createService(NaakhClient.class);
    }

    @Nullable
    public SignInPojo login(String oauthClientSecret, String oauthClientId,
                            String phoneNumber, String password) {

        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.PHONE_NUMBER, phoneNumber);
        params.put(NaakhApiQueryKeys.PASSWORD, password);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_SECRET, oauthClientSecret);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_ID, oauthClientId);

        final Call<SignInPojo> call = client.login(params);
        try {
            final SignInPojo access_token = call.execute().body();
            return access_token;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to sign in due to the following errors: " + e.getMessage());
            return null;
        }
    }

    @Nullable
    public TranslationInfoPojo getTranslateJob(Language language, TranslationStatus translationStatus,
                                               String accessToken) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.LANGUAGE, language.getDbValue());
        params.put(NaakhApiQueryKeys.TRANSLATION_STATUS, translationStatus.getTranslationStatus());
        params.put(NaakhApiQueryKeys.LIMIT, 1 + "");

        final Call<GetTranslatePojo> call = client.getTranslationJob(params, accessToken);
        try {
            final GetTranslatePojo getTranslatePojo = call.execute().body();
            final List<TranslationInfoPojo> translationInfoObjects = getTranslatePojo.getObjects();
            if (translationInfoObjects.isEmpty()) {
                Log.i(LOG_TAG, "Nothing to translate for the current user");
                return null;
            } else {
                final TranslationInfoPojo translationInfoPojo = translationInfoObjects.get(0);
                return translationInfoPojo;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with getting translation job " + e.getMessage());
            return null;
        }
    }

    public TranslationInfoPojo postTranslatorTranslatedText(String translatedTextUuid, String translatedText,
                                                            String accessToken) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.TRANSLATION_TEXT, translatedText);

        return postTranslatedTextInfo(translatedTextUuid, accessToken, params);
    }


    public TranslationInfoPojo postReviewerVerificationInformation(String translatedTextUiud,
                                                                   Map<VerificationParameter, Boolean> verificationInfo,
                                                                   String accessToken) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.VERIFIED_CONTEXT, String.valueOf(!verificationInfo.get(VerificationParameter.CONTEXT)));
        params.put(NaakhApiQueryKeys.VERIFIED_GRAMMAR, String.valueOf(!verificationInfo.get(VerificationParameter.GRAMMAR)));
        params.put(NaakhApiQueryKeys.VERIFIED_SPELLING, String.valueOf(!verificationInfo.get(VerificationParameter.SPELLING)));

        return postTranslatedTextInfo(translatedTextUiud, accessToken, params);
    }

    @Nullable
    private TranslationInfoPojo postTranslatedTextInfo(String translatedTextUuid, String accessToken, Map<String, String> params) {
        final Call<TranslationInfoPojo> call = client.postTranslatedTextInfo(params,
                accessToken, translatedTextUuid);
        try {
            final TranslationInfoPojo translationInfoPojo = call.execute().body();
            return translationInfoPojo;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with posting translation job " + e.getMessage());
            return null;
        }
    }

    @Nullable
    public TranslationInfoPojo postReviewerTranslatedText(String translatedText, String translationRequestUuid,
                                                          Language language, String accessToken) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.TRANSLATION_TEXT, translatedText);
        params.put(NaakhApiQueryKeys.TRANSLATION_REQUEST_UUID, translationRequestUuid);
        params.put(NaakhApiQueryKeys.LANGUAGE, language.getDbValue());

        final Call<TranslationInfoPojo> call = client.postReviewersTranslateTextInfo(params, accessToken);
        try {
            final TranslationInfoPojo translationInfoPojo = call.execute().body();
            return translationInfoPojo;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error with posting reviewer's translated text" + e.getMessage());
            return null;
        }

    }
}
