package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nikhiljoshi.naakh.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Verification extends AppCompatActivity {

    public static final String ORIGINAL_TEXT_UUID = "original_text_uuid";
    public static final String TRANSLATED_TEXT_UUID = "translated_text_uuid";
    public static final String ORIGINAL_TEXT = "original_text";
    public static final String TRANSLATED_TEXT = "translated_text";

    private String original_text_uuid;
    private String translated_text_uuid;
    private String original_text;
    private String translated_text;

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

    public void setOriginalTextUuid(String original_text_uuid) {
        this.original_text_uuid = original_text_uuid;
    }

    public String getOriginalTextUuid() {
        return original_text_uuid;
    }

    public void setTranslatedTextUuid(String translated_text_uuid) {
        this.translated_text_uuid = translated_text_uuid;
    }

    public String getTranslatedTextUuid() {
        return translated_text_uuid;
    }

    public String getOriginal_text() {
        return original_text;
    }

    public void setOriginalText(String original_text) {
        this.original_text = original_text;
    }

    public String getTranslated_text() {
        return translated_text;
    }

    public void setTranslatedText(String translated_text) {
        this.translated_text = translated_text;
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
        final List<String> incorrectTranslationReasons = new ArrayList<String>();
        final List<String> incorrectOptions = Arrays.asList(getResources().getStringArray(R.array.incorrect_translation_options));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.incorrect_translation_title)
                .setMultiChoiceItems(R.array.incorrect_translation_options, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        final String incorrectOption = incorrectOptions.get(which);
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
                        String list = "";
                        for (String incorrect : incorrectTranslationReasons) {
                            list += incorrect + " ";
                        }

                        if (incorrectTranslationReasons.isEmpty()) {
                            dialog.dismiss();
                            showChooseOptionsPleaseDialogBox();
                        } else {
                            Intent intent = new Intent(Verification.this, FixTranslation.class);
                            intent.putExtra(ORIGINAL_TEXT_UUID, original_text_uuid);
                            intent.putExtra(ORIGINAL_TEXT, original_text);
                            intent.putExtra(TRANSLATED_TEXT_UUID, translated_text_uuid);
                            intent.putExtra(TRANSLATED_TEXT, translated_text);
                            startActivity(intent);
                            Toast.makeText(Verification.this, "Time to translate!" + list, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        final AlertDialog incorrectDialog = builder.create();
        incorrectDialog.show();
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
