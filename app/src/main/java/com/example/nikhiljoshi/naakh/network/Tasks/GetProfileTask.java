package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingTranslatorProfile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Profile.ProfileObject;

/**
 * AsyncTask that calls the profile endpoint and returns the ProfileObject
 */
public class GetProfileTask extends AsyncTask<Object, Object, ProfileObject> {

    private final NaakhApi api;
    private final OnGettingTranslatorProfile listener;
    private final String accessToken;

    public GetProfileTask(NaakhApi api, OnGettingTranslatorProfile listener, String accessToken) {
        this.api = api;
        this.listener = listener;
        this.accessToken = accessToken;
    }

    @Override
    protected void onPostExecute(ProfileObject profileObject) {
        listener.carryOutActionWithUserProfile(profileObject);
    }

    @Override
    protected ProfileObject doInBackground(Object... params) {
        return api.getTranslatorProfile(accessToken);
    }
}
