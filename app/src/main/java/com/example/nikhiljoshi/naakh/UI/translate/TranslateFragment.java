package com.example.nikhiljoshi.naakh.UI.translate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;


/**
 * A placeholder fragment containing a simple view.
 */
public class TranslateFragment extends Fragment{

    private static final String LOG_TAG = TranslateFragment.class.getSimpleName();
    private View rootView;

    @Inject NaakhApi api;

    public TranslateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translate, container, false);

        displayTranslationsIfAvailable();

        return rootView;
    }

    private void displayTranslationsIfAvailable() {
        final Intent intent = getActivity().getIntent();
        final TranslationInfoPojo translationInfoPojo = intent.getParcelableExtra(Profile.TRANSLATION_INFO_POJO);

        if (translationInfoPojo == null) {
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
            alertDialog.show();
        } else {
            TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
            toTranslateView.setText(translationInfoPojo.getTranslationRequest().getTranslationRequestText());
            ((Translate) getActivity()).setTranslatedTextUuid(translationInfoPojo.getTranslatedTextUuid());
            Log.i(LOG_TAG, "Got translation task with uuid: " + translationInfoPojo.getTranslatedTextUuid());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);

    }

}
