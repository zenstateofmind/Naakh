package com.example.nikhiljoshi.naakh.UI.translate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.app.settings.SettingsActivity;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.PostUntranslatedTranslateTextTask;

public class Translate extends AppCompatActivity {

    private NaakhApi api;
    private SharedPreferences preferences;
    private String translated_text_uuid;
    private static final String LOG_TAG = Translate.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "State: creating");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    //TODO: Make sure that the back button goes to the profile page

    public void setTranslatedTextUuid(String uuid) {
        this.translated_text_uuid = uuid;
    }

    public void postTranslations(View view) {
        final String translatedText = ((EditText) findViewById(R.id.translated_text)).getText().toString();

        if (translatedText.trim().length() == 0 || translated_text_uuid.trim().length() == 0) {
            Toast.makeText(this, getString(R.string.please_translate), Toast.LENGTH_SHORT).show();
        }

        api = new NaakhApi();
        preferences = PreferenceManager.getDefaultSharedPreferences(Translate.this.getApplicationContext());
        final String access_token = preferences.getString(getString(R.string.token), "");

        new PostUntranslatedTranslateTextTask(api, translatedText, translated_text_uuid, access_token) {
            @Override
            protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
                if (translationInfoPojo != null) {
                    Toast.makeText(Translate.this, "Thank you!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Translate.this, Translate.class);
                    startActivity(intent);
                } else {
                    Log.e(LOG_TAG, "Problems in posting the results back to the Naakh API server");
                }
            }
        }.execute();

    }
}
