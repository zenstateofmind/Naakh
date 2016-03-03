package com.example.nikhiljoshi.naakh.network;

import android.util.Log;

import com.example.nikhiljoshi.naakh.network.POJO.SignInPojo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class NaakhApi {

    private static String LOG_TAG = NaakhApi.class.getSimpleName();

    public SignInPojo login(String oauth_client_secret, String oauth_client_id,
                            String phone_number, String password) {
        final NaakhClient client = ServiceGenerator.createService(NaakhClient.class);
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
}
