package com.example.nikhiljoshi.naakh.UI.SignIn;

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

import com.example.nikhiljoshi.naakh.GCM.RegistrationIntentService;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSignInTaskCompleted;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;

import com.example.nikhiljoshi.naakh.network.POJO.SignIn.AccessToken;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import javax.inject.Inject;


public class SignIn extends AppCompatActivity implements OnSignInTaskCompleted {

    private static final String LOG_TAG = SignIn.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Inject NaakhApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_in);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((ProdApplication) getApplication()).component().inject(this);
    }

    public void signInValidation(View view) {

        final String username = ((EditText) findViewById(R.id.username)).getText().toString();
        final String password = ((EditText) findViewById(R.id.password)).getText().toString();
        final String oauth_client_secret = "1f022ef12e35f86c2f02f1b9988b899a9cd7de02";
        final String oauth_client_id = "b73fa9950d135f4cdf21";

        if (!fieldsFilled(username, password)) {
            Toast.makeText(this, "Please enter phone number and password!", Toast.LENGTH_SHORT).show();
        } else {
            new LoginTask(api, this).execute(oauth_client_id, oauth_client_secret, username, password);
        }
    }

    private boolean fieldsFilled(String username, String password) {
        return !username.trim().isEmpty() && !password.trim().isEmpty();
    }

    @Override
    public void runOnPostTaskCompleted(AccessToken accessToken) {
        if (accessToken == null) {
            Toast.makeText(getApplicationContext(), "Phone Number/Password seems incorrect :( ", Toast.LENGTH_SHORT).show();
            Log.w(LOG_TAG, "Didn't get an access token... seems like username/password is incorrect");
        } else {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(getString(R.string.token), accessToken.getAccessToken());
            editor.commit();

            if (checkPlayServices()) {
                // Start IntentService to register this application with GCM.
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }

            Intent intent = new Intent(SignIn.this, Profile.class);
            startActivity(intent);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(LOG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
