package com.example.nikhiljoshi.naakh.network;

import android.util.Log;

import com.example.nikhiljoshi.naakh.language.Language;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.GetTranslatePojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequestPojo;

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

    public SignInPojo login(String oauth_client_secret, String oauth_client_id,
                            String phone_number, String password) {

        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.PHONE_NUMBER, phone_number);
        params.put(NaakhApiQueryKeys.PASSWORD, password);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_SECRET, oauth_client_secret);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_ID, oauth_client_id);

        final Call<SignInPojo> call = client.login(params);
        try {
            final SignInPojo access_token = call.execute().body();
            return access_token;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Unable to sign in due to the following errors: " + e.getMessage());
            return null;
        }
    }

    public TranslationRequestPojo getTranslateJob(Language language, String translationStatus) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.LANGUAGE, language.getDbValue());
        params.put(NaakhApiQueryKeys.TRANSLATION_STATUS, translationStatus);
        params.put(NaakhApiQueryKeys.LIMIT, 1 + "");

        final Call<GetTranslatePojo> call = client.getTranslateInfo(params, "ab89611abed189ce0f9f13f5f9ec818442ed44e7");
        try {
            final GetTranslatePojo getTranslatePojo = call.execute().body();
            final List<TranslationInfoPojo> translationInfoObjects = getTranslatePojo.getObjects();
            if (translationInfoObjects.isEmpty()) {
                Log.i(LOG_TAG, "Nothing to translate for the current user");
                return null;
            } else {
                final TranslationRequestPojo translationRequestPojo = translationInfoObjects.get(0).getTranslation_request();
                return translationRequestPojo;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Error with getting translation job " + e.getMessage());
            return null;
        }
    }
}
