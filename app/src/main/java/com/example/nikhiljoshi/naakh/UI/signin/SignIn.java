package com.example.nikhiljoshi.naakh.UI.signin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;

import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;


public class SignIn extends AppCompatActivity {

    private static final String LOG_TAG = "naakh.SignIn";
    //TODO: Will eventually be injecting this
    private NaakhApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void signInValidation(View view) {
        api = new NaakhApi();

        final String username = ((EditText) findViewById(R.id.username)).getText().toString();
        final String password = ((EditText) findViewById(R.id.password)).getText().toString();
        final String oauth_client_secret = "1f022ef12e35f86c2f02f1b9988b899a9cd7de02";
        final String oauth_client_id = "b73fa9950d135f4cdf21";

        new LoginTask(api, oauth_client_id, oauth_client_secret, username, password) {
            @Override
            protected void onPostExecute(SignInPojo signInPojo) {
                if (signInPojo == null) {
                    Toast.makeText(getApplicationContext(), "Phone Number/Password seems incorrect :( ", Toast.LENGTH_SHORT).show();
                    Log.w(LOG_TAG, "Didn't get an access token... seems like username/password is incorrect");
                } else {
                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString(getString(R.string.token), signInPojo.getAccess_token());
                    editor.commit();

                    Intent intent = new Intent(SignIn.this, Profile.class);
                    startActivity(intent);
                }
            }
        }.execute();

    }

}
