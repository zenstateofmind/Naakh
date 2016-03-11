package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.UI.signin.OnSignInTaskCompleted;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class LoginTask extends AsyncTask<String, Object, SignInPojo> {

    private final NaakhApi api;
    private final OnSignInTaskCompleted onSignInTaskCompleted;

    public LoginTask(NaakhApi api, OnSignInTaskCompleted onSignInTaskCompleted) {
        this.api = api;
        this.onSignInTaskCompleted = onSignInTaskCompleted;
    }

    @Override
    public void onPostExecute(SignInPojo signInPojo) {
        onSignInTaskCompleted.runOnPostTaskCompleted(signInPojo);
    }

    @Override
    public SignInPojo doInBackground(String... params) {
        String oauth_client_id = params[0];
        String oauth_client_secret = params[1];
        String phone_number = params[2];
        String password = params[3];
        final SignInPojo signInPojo = api.login(oauth_client_secret, oauth_client_id, phone_number, password);
        return signInPojo;
    }
}
