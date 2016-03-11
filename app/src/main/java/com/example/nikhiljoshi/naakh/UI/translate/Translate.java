package com.example.nikhiljoshi.naakh.UI.translate;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnSendingTranslations;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.app.settings.SettingsActivity;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;
import com.example.nikhiljoshi.naakh.network.Tasks.PostUntranslatedTranslateTextTask;

import javax.inject.Inject;

public class Translate extends AppCompatActivity implements OnSendingTranslations, OnGettingIncompleteTranslatedText {

    @Inject NaakhApi api;
    private SharedPreferences preferences;
    private String translated_text_uuid;
    private static final String LOG_TAG = Translate.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((ProdApplication) getApplication()).component().inject(this);

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

    public void sendTranslations(View view) {

        final String translatedText = ((EditText) findViewById(R.id.translated_text)).getText().toString();

        if (translatedText.trim().length() == 0 || translated_text_uuid.trim().length() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.please_translate)
                    .setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.are_you_sure)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            postTranslations(translatedText);
                            askIfTranslatorWantsToDoMoreTranslations();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    }

    private void askIfTranslatorWantsToDoMoreTranslations() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.do_another_translation)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        final String token = sharedPreferences.getString(getString(R.string.token), "");

                        new GetTranslationJobTask(api, Translate.this, Language.MALAYALAM, TranslationStatus.UNTRANSLATED, token).execute();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Translate.this, Profile.class);
                        startActivity(intent);
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void postTranslations(String translatedText) {
        preferences = PreferenceManager.getDefaultSharedPreferences(Translate.this.getApplicationContext());
        final String access_token = preferences.getString(getString(R.string.token), "");

        new PostUntranslatedTranslateTextTask(api, this).execute(translatedText, translated_text_uuid, access_token);
    }

    @Override
    public void runOnSendingTranslations(TranslationInfoPojo translationInfoPojo) {
        if (translationInfoPojo != null) {
            Toast.makeText(Translate.this, "Thank you!", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(LOG_TAG, "Problems in posting the results back to the Naakh API server");
        }
    }

    @Override
    public void takeActionWithIncompleteTranslatedTextObject(TranslationInfoPojo translationInfoPojo) {
        Intent intent = new Intent(this, Translate.class);
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfoPojo);
        startActivity(intent);
    }
}
