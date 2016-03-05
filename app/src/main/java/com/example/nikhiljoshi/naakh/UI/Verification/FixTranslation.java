package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;

public class FixTranslation extends AppCompatActivity {

    private NaakhApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_translation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void postFixedTranslation(View view) {
        api = new NaakhApi();

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
                            //post create to the naakh servers
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
}
