package com.example.nikhiljoshi.naakh.UI.Verification;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;

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
        final String translationRequestText = intent.getStringExtra(Verification.TRANSLATION_REQUEST_TEXT);
        final String topics = intent.getStringExtra(Verification.TOPICS);
        final String tone = intent.getStringExtra(Verification.TONE);

        if (!topics.isEmpty()) {
            ((CardView) rootView.findViewById(R.id.card_topics)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.topics_fix_trans)).setText(topics);
        }

        if (!tone.isEmpty()) {
            ((CardView) rootView.findViewById(R.id.card_tone)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.tone_fix_trans)).setText(tone);
        }

        ((FixTranslation) getActivity()).setTranslationRequestUuid(intent.getStringExtra(Verification.TRANSLATION_REQUEST_UUID));
        ((TextView) rootView.findViewById(R.id.original_text)).setText(translationRequestText);
        ((TextView) rootView.findViewById(R.id.previous_translation_text)).setText(translatedText);

    }

}
