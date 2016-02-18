package com.example.nikhiljoshi.naakh.signin;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.translate.Translate;
import com.example.nikhiljoshi.naakh.network.calls.VolleyInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    private static final String LOG_TAG = "naakh.SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void signInValidation(View view) {
        final String username = getStringFromEditText(R.id.username);
        final String password = getStringFromEditText(R.id.password);
        final VolleyInstance volley = VolleyInstance.getInstance(this.getApplicationContext());
        final String loginUrl = "https://naakh.herokuapp.com/api/v1/oauth2/access_token";
        final String oauth_client_secret = "1f022ef12e35f86c2f02f1b9988b899a9cd7de02";
        final String oauth_client_id = "b73fa9950d135f4cdf21";

        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loginSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "failure", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_number", "123456789");
                params.put("password", "password");
                params.put("oauth_client_id", oauth_client_id);
                params.put("oauth_client_secret", oauth_client_secret);
                return params;
            }
        };

        volley.getRequestQueue().add(request);

    }

    private void loginSuccess(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            final String access_token = jsonResponse.getString("access_token");
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(getString(R.string.token), access_token);
            editor.commit();

            Intent intent = new Intent(this, Translate.class);
            startActivity(intent);

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
    }

    private String getStringFromEditText(int id) {
        EditText view = (EditText) findViewById(id);
        return view.getText().toString();
    }
}
