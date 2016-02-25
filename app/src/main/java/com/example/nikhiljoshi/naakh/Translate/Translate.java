package com.example.nikhiljoshi.naakh.translate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.app.settings.SettingsActivity;
import com.example.nikhiljoshi.naakh.network.calls.NaakhApiBaseUrls;
import com.example.nikhiljoshi.naakh.network.calls.VolleyInstance;

import java.util.HashMap;
import java.util.Map;

public class Translate extends AppCompatActivity {

    private String uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_translate, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setTranslatedTextUuid(String uuid) {
        this.uuid = uuid;
    }

    public void postTranslations(View view) {
        final String translatedText = ((EditText) findViewById(R.id.translated_text)).getText().toString();
        if (translatedText.trim().length() == 0 || uuid.trim().length() == 0) {
            Toast.makeText(this, getString(R.string.please_translate), Toast.LENGTH_SHORT).show();
        }

        String baseUrl = "https://naakh.herokuapp.com/api/v1/translatedtext/" + uuid + "?";
        final String uri = Uri.parse(baseUrl)
                .buildUpon()
                .appendQueryParameter("oauth_consumer_key", "ab89611abed189ce0f9f13f5f9ec818442ed44e7")
                .appendQueryParameter("translation_text", translatedText)
                .build().toString();

        StringRequest response = new StringRequest(Request.Method.POST, NaakhApiBaseUrls.getPostTranslatedTextUrl(uuid), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "successfully posted back", Toast.LENGTH_SHORT).show();
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
                params.put("oauth_consumer_key", "ab89611abed189ce0f9f13f5f9ec818442ed44e7");
                params.put("translation_text", translatedText);
                return params;
            }
        };

        VolleyInstance.getInstance(getApplicationContext()).getRequestQueue().add(response);
    }
}
