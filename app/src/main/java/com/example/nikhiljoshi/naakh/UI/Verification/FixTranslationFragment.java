package com.example.nikhiljoshi.naakh.UI.Verification;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class FixTranslationFragment extends Fragment {

    private NaakhApi api;

    public FixTranslationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_fix_translation, container, false);
        populateView(rootView);
        return rootView;
    }

    private void populateView(View rootView) {
        final Intent intent = getActivity().getIntent();
        final String translatedText = intent.getStringExtra(Verification.TRANSLATED_TEXT);
        final String translatedTextUuid = intent.getStringExtra(Verification.TRANSLATED_TEXT_UUID);
        final String originalText = intent.getStringExtra(Verification.ORIGINAL_TEXT);
        final String originalTextUuid = intent.getStringExtra(Verification.ORIGINAL_TEXT_UUID);
        ((TextView) rootView.findViewById(R.id.original_text)).setText(originalText);
        ((TextView) rootView.findViewById(R.id.previous_translation_text)).setText(translatedText);
    }

}
