package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.GCM.GCMRegistration;

/**
 * Created by nikhiljoshi on 3/18/16.
 */
public class PostGCMRegistrationToken extends AsyncTask<Object, Object, GCMRegistration> {

    private static final String LOG_TAG = PostGCMRegistrationToken.class.getSimpleName();

    private final NaakhApi api;
    private final String registrationToken;
    private final String deviceId;
    private final String accessToken;

    public PostGCMRegistrationToken(NaakhApi api, String registrationToken, String deviceId,
                                    String accessToken) {

        this.api = api;
        this.registrationToken = registrationToken;
        this.deviceId = deviceId;
        this.accessToken = accessToken;
    }

    @Override
    protected void onPostExecute(GCMRegistration gcmRegistration) {
        if (gcmRegistration != null) {
            Log.i(LOG_TAG, "Successfully posted the registration token for " + deviceId);
        } else {
            Log.e(LOG_TAG, "There were issues sending the registration token in for " + deviceId);
        }
    }

    @Override
    protected GCMRegistration doInBackground(Object... params) {
        final GCMRegistration gcmRegistration = api.postGCMRegistration(registrationToken, deviceId, accessToken);
        return gcmRegistration;
    }
}
