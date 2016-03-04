package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
                        //save incorrectTranslationReasons
                        String list = "";
                        for (String incorrect: incorrectTranslationReasons) {
                            list += incorrect + " ";
                        }
                        Toast.makeText(Verification.this, "Time to translate!" + list , Toast.LENGTH_SHORT).show();
                    }
                });

        final AlertDialog incorrectDialog = builder.create();
        incorrectDialog.show();
    }
}
