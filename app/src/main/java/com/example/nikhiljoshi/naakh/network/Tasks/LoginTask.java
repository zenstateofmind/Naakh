package com.example.nikhiljoshi.naakh.network.Tasks;

import android.os.AsyncTask;

import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public class LoginTask extends AsyncTask<Object, Object, SignInPojo> {

    private NaakhApi api;
    private final String oauth_client_id;
    private final String oauth_client_secret;
    private final String phone_number;
    private final String password;

    public LoginTask(NaakhApi api, String oauth_client_id, String oauth_client_secret,
                     String phone_number, String password) {
        this.api = api;
        this.oauth_client_id = oauth_client_id;
        this.oauth_client_secret = oauth_client_secret;
        this.phone_number = phone_number;
        this.password = password;
    }
    @Override
    protected SignInPojo doInBackground(Object... params) {
        return api.login(oauth_client_secret, oauth_client_id, phone_number, password);
    }
}
