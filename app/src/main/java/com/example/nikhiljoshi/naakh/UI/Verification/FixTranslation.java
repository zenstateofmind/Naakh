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
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSendingTranslations;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;
import com.example.nikhiljoshi.naakh.network.Tasks.PostReviewerTranslateTextTask;

public class FixTranslation extends AppCompatActivity implements OnGettingIncompleteTranslatedText,
        OnSendingTranslations{

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
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
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        }
    }

    private void postReviewerTranslation(String freshTranslation) {
        api = new NaakhApi();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String access_token = preferences.getString(getString(R.string.token), "");
        final Language language = Language.chooseFluentLanguage(preferences.getString(getString(R.string.languages), ""));

        new PostReviewerTranslateTextTask(api, this, freshTranslation, translationRequestUuid,
                language, access_token).execute();
    }

    @Override
    public void takeActionWithIncompleteTranslatedTextObject(TranslationInfo translationInfo, TranslationStatus translationStatus) {
        Intent intent = new Intent(this, Verification.class);
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        startActivity(intent);
    }

    @Override
    public void runOnSendingTranslations(TranslationInfo translationInfo) {
        if (translationInfo == null) {
            Log.e(LOG_TAG, "There were issues in posting the reviewers translated text");
        } else {
            Toast.makeText(FixTranslation.this, "Thank you!", Toast.LENGTH_SHORT).show();
            askIfReviewerWantsToDoMoreVerifications();
        }
    }

    private void askIfReviewerWantsToDoMoreVerifications() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.do_another_verification)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final String access_token = preferences.getString(getString(R.string.token), "");
                        final Language language = Language.chooseFluentLanguage(preferences.getString(getString(R.string.languages), ""));
                        new GetTranslationJobTask(api, FixTranslation.this, language, TranslationStatus.UNVERIFIED, access_token).execute();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(FixTranslation.this, Profile.class);
                        startActivity(intent);
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
