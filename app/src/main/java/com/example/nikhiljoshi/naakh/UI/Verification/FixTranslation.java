package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.PostReviewerTranslateTextTask;

public class FixTranslation extends AppCompatActivity {

    private static final String LOG_TAG = FixTranslation.class.getSimpleName();
    private NaakhApi api;
    private String translationRequestUuid;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setTranslationRequestUuid(String translationRequestUuid) {
        this.translationRequestUuid = translationRequestUuid;
    }

    public void postFixedTranslation(View view) {

        final String freshTranslation = ((TextView) findViewById(R.id.translated_text)).getText().toString();
        // if the text is empty toast that shits empty
        if (freshTranslation.isEmpty()) {
            Toast.makeText(this, "Please translate :) ", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure?")
                    .setTitle("Yay!")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            postReviewerTranslation(freshTranslation);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    private void postReviewerTranslation(String freshTranslation) {
        api = new NaakhApi();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String access_token = preferences.getString(getString(R.string.token), "");

        new PostReviewerTranslateTextTask(api, freshTranslation, translationRequestUuid, Language.HINDI, access_token) {
            @Override
            protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
                if (translationInfoPojo == null) {
                    Log.e(LOG_TAG, "There were issues in posting the reviewers translated text");
                } else {
                    Toast.makeText(FixTranslation.this, "Thank you!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FixTranslation.this, Verification.class);
                    startActivity(intent);
                }
            }
        }.execute();
    }
}
