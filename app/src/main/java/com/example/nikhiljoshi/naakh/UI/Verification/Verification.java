package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nikhiljoshi.naakh.Enums.VerificationParameter;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.PostReviewerVerificationInfoTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Verification extends AppCompatActivity {

    public static final String TRANSLATION_REQUEST_UUID = "translation_request_uuid";
    public static final String TRANSLATED_TEXT_UUID = "translated_text_uuid";
    public static final String TRANSLATION_REQUEST_TEXT = "translation_request_text";
    public static final String TRANSLATED_TEXT = "translated_text";

    private static final String LOG_TAG = Verification.class.getSimpleName();

    private String translationRequestUuid;
    private String translatedTextUuid;
    private String translationRequestTest;
    private String translatedText;

    private NaakhApi api;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void setTranslationRequestUuid(String original_text_uuid) {
        this.translationRequestUuid = original_text_uuid;
    }

    public String getTranslationRequestUuid() {
        return translationRequestUuid;
    }

    public void setTranslatedTextUuid(String translated_text_uuid) {
        this.translatedTextUuid = translated_text_uuid;
    }

    public String getTranslatedTextUuid() {
        return translatedTextUuid;
    }

    public String getTranslationRequestTest() {
        return translationRequestTest;
    }

    public void setTranslationRequestTest(String translationRequestTest) {
        this.translationRequestTest = translationRequestTest;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translated_text) {
        this.translatedText = translated_text;
    }

    public void notSureDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.not_sure_translation_message)
                .setTitle("Not sure?")
                .setPositiveButton("Yea, not sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Should finish this
                        Toast.makeText(Verification.this, "Will show new verification then", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Actually, let me get back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog notSureDialog = builder.create();
        notSureDialog.show();
    }

    public void correctOptionDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.correct_translations_message)
                .setTitle("Yay!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Should finish this
                        Toast.makeText(Verification.this, "Will show new verification then", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog correctDialog = builder.create();
        correctDialog.show();

    }

    public void incorrectDialog(View view) {
        final List<VerificationParameter> incorrectTranslationReasons = new ArrayList<VerificationParameter>();
        final List<String> incorrectOptions = Arrays.asList(getResources().getStringArray(R.array.incorrect_translation_options));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.incorrect_translation_title)
                .setMultiChoiceItems(R.array.incorrect_translation_options, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        final VerificationParameter incorrectOption = VerificationParameter.getEnum(incorrectOptions.get(which));
                        if (isChecked) {
                            incorrectTranslationReasons.add(incorrectOption);
                        } else if (incorrectTranslationReasons.contains(incorrectOption)) {
                            incorrectTranslationReasons.remove(incorrectOption);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Correct it!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: where do we save this information?
                        if (incorrectTranslationReasons.isEmpty()) {
                            dialog.dismiss();
                            showChooseOptionsPleaseDialogBox();
                        } else {
                            postReviewerVerification(incorrectTranslationReasons);

                            Intent intent = new Intent(Verification.this, FixTranslation.class);
                            intent.putExtra(TRANSLATION_REQUEST_UUID, translationRequestUuid);
                            intent.putExtra(TRANSLATION_REQUEST_TEXT, translationRequestTest);
                            intent.putExtra(TRANSLATED_TEXT_UUID, translatedTextUuid);
                            intent.putExtra(TRANSLATED_TEXT, translatedText);
                            startActivity(intent);
                            Toast.makeText(Verification.this, "Time to translate!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        final AlertDialog incorrectDialog = builder.create();
        incorrectDialog.show();
    }

    private void postReviewerVerification(List<VerificationParameter> incorrectTranslationReasons) {
        api = new NaakhApi();

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String accessToken = preferences.getString(getString(R.string.token), "");

        Map<VerificationParameter, Boolean> verificationInfo = new HashMap<VerificationParameter, Boolean>();
        verificationInfo.put(VerificationParameter.SPELLING, incorrectTranslationReasons.contains(VerificationParameter.SPELLING));
        verificationInfo.put(VerificationParameter.CONTEXT, incorrectTranslationReasons.contains(VerificationParameter.CONTEXT));
        verificationInfo.put(VerificationParameter.TONE, incorrectTranslationReasons.contains(VerificationParameter.TONE));
        verificationInfo.put(VerificationParameter.GRAMMAR, incorrectTranslationReasons.contains(VerificationParameter.GRAMMAR));
        new PostReviewerVerificationInfoTask(api, verificationInfo, getTranslatedTextUuid(), accessToken) {
            @Override
            protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
                if (translationInfoPojo == null) {
                    Log.e(LOG_TAG, "Unable to update reviewer's critique for translated text uuid: " +
                    translatedTextUuid);
                }
            }
        }.execute();

    }



    private void showChooseOptionsPleaseDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Select whats incorrect")
                .setNeutralButton("OK!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
