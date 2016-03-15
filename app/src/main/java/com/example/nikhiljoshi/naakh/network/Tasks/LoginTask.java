package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSignInTaskCompleted;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.AccessToken;

/**
 * AsyncTask that calls the SignIn endpoint and gets the Access Token for the user
 */
public class LoginTask extends AsyncTask<String, Object, AccessToken> {

    private final NaakhApi api;
    private final OnSignInTaskCompleted onSignInTaskCompleted;

    public LoginTask(NaakhApi api, OnSignInTaskCompleted onSignInTaskCompleted) {
        this.api = api;
        this.onSignInTaskCompleted = onSignInTaskCompleted;
    }

    @Override
    public void onPostExecute(AccessToken accessToken) {
        onSignInTaskCompleted.runOnPostTaskCompleted(accessToken);
    }

    @Override
    public AccessToken doInBackground(String... params) {
        String oauth_client_id = params[0];
        String oauth_client_secret = params[1];
        String phone_number = params[2];
        String password = params[3];
        final AccessToken accessToken = api.login(oauth_client_secret, oauth_client_id, phone_number, password);
        return accessToken;
    }
}
