package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.UI.welcome.Welcome;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequest;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class VerificationFragment extends Fragment implements OnGettingIncompleteTranslatedText {

    private static final String LOG_TAG = VerificationFragment.class.getSimpleName();
    private View rootView;

    @Inject NaakhApi api;

    public VerificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_verification, container, false);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        if (token == null || token.isEmpty()) {
            Intent intent = new Intent(getActivity(), Welcome.class);
            startActivity(intent);
        } else {
            getAndDisplayVerificationsIfAvailable();
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);
    }

    public void getAndDisplayVerificationsIfAvailable() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        final Language language = Language.chooseFluentLanguage(sharedPreferences.getString(getString(R.string.languages), ""));

        new GetTranslationJobTask(api, this, language, TranslationStatus.UNVERIFIED, token).execute();
    }

    @Override
    public void takeActionWithIncompleteTranslatedTextObject(TranslationInfo translationInfo, TranslationStatus translationStatus) {
        if (translationInfo == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.no_more_verifications)
                    .setMessage("Sorry, we have no more phrases for you to verify :(")
                    .setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent profileIntent = new Intent(getActivity(), Profile.class);
                            startActivity(profileIntent);
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        } else {
            final TranslationRequest translationRequest = translationInfo.getTranslationRequest();
            final Verification verificationActivity = (Verification) getActivity();
            final String translatedText = translationInfo.getTranslationText();
            final String originalText = translationRequest.getTranslationRequestText();
            final String topics = TextUtils.join(",", translationInfo.getTopics());
            final String tone = translationInfo.getTone().getName();

            verificationActivity.setTranslationRequestUuid(translationRequest.getTranslationRequestUuid());
            verificationActivity.setTranslatedTextUuid(translationInfo.getTranslatedTextUuid());
            verificationActivity.setTranslatedText(translatedText);
            verificationActivity.setTranslationRequestTest(originalText);
            verificationActivity.setTopics(topics);
            verificationActivity.setTone(tone);

            if (!topics.isEmpty()) {
                ((CardView) rootView.findViewById(R.id.card_topics)).setVisibility(View.VISIBLE);
                ((TextView) rootView.findViewById(R.id.topics_verification)).setText(topics);
            }

            if (!tone.isEmpty()) {
                ((CardView) rootView.findViewById(R.id.card_tone)).setVisibility(View.VISIBLE);
                ((TextView) rootView.findViewById(R.id.tone_verification)).setText(tone);
            }

            ((CardView) rootView.findViewById(R.id.card_original_phrase)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.original_text)).setText(originalText);

            ((CardView) rootView.findViewById(R.id.card_translation_phrase)).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.translated_text)).setText(translatedText);

        }
    }
}
