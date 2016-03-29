package com.example.nikhiljoshi.naakh.UI.translate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;


/**
 * A placeholder fragment containing a simple view.
 */
public class TranslateFragment extends Fragment implements OnGettingIncompleteTranslatedText {

    private static final String LOG_TAG = TranslateFragment.class.getSimpleName();
    private View rootView;
    private TranslationInfo translationInfo;

    @Inject NaakhApi api;

    public TranslateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translate, container, false);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        if (token == null || token.isEmpty()) {
            Intent intent = new Intent(getActivity(), Welcome.class);
            startActivity(intent);
        } else {
            getAndDisplayTranslationsIfAvailable();
        }
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);

    }

    public void getAndDisplayTranslationsIfAvailable() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        final Language language = Language.chooseFluentLanguage(sharedPreferences.getString(getString(R.string.languages), ""));

        new GetTranslationJobTask(api, this, language, TranslationStatus.UNTRANSLATED, token).execute();
    }

    @Override
    public void takeActionWithIncompleteTranslatedTextObject(TranslationInfo translationInfo, TranslationStatus translationStatus) {
        if (translationInfo == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.no_more_translations)
                    .setMessage("Sorry, we have no more phrases for you to translate :(")
                    .setNeutralButton(R.string.OK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final Intent profileIntent = new Intent(getActivity(), Profile.class);
                            startActivity(profileIntent);
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        } else {

            final TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
            final TextView topicsView = (TextView) rootView.findViewById(R.id.topics_translate);
            final TextView tonesView = (TextView) rootView.findViewById(R.id.tone_translate);

            final String translationRequestText = translationInfo.getTranslationRequest().getTranslationRequestText();
            final String tone = translationInfo.getTone().getName();
            final String topics = TextUtils.join(",", translationInfo.getTopics());

            if (!tone.isEmpty()) {
                ((CardView) rootView.findViewById(R.id.card_tone)).setVisibility(View.VISIBLE);
            }

            if (!topics.isEmpty()) {
                ((CardView) rootView.findViewById(R.id.card_topics)).setVisibility(View.VISIBLE);
            }

            //translation request text should never be empty
            ((CardView) rootView.findViewById(R.id.card_to_translate)).setVisibility(View.VISIBLE);

            tonesView.setText(tone);
            topicsView.setText(topics);
            toTranslateView.setText(translationRequestText);

            ((Translate) getActivity()).setTranslatedTextUuid(translationInfo.getTranslatedTextUuid());

            Log.i(LOG_TAG, "Got translation task with uuid: " + translationInfo.getTranslatedTextUuid());
        }
    }
}
