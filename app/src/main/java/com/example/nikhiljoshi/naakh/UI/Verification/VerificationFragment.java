package com.example.nikhiljoshi.naakh.UI.Verification;

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
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequestPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;

/**
 * A placeholder fragment containing a simple view.
 */
public class VerificationFragment extends Fragment {

    private static final String LOG_TAG = VerificationFragment.class.getSimpleName();
    private View rootView;

    @Inject NaakhApi api;

    public VerificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_verification, container, false);
        getVerificationJob();
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);
    }

    private void getVerificationJob() {
        final Intent intent = getActivity().getIntent();
        final TranslationInfoPojo translationInfoPojo = intent.getParcelableExtra(Profile.TRANSLATION_INFO_POJO);

        if (translationInfoPojo == null) {
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
            alertDialog.show();
        } else {
            final TranslationRequestPojo translationRequestPojo = translationInfoPojo.getTranslationRequest();
            final Verification verificationActivity = (Verification) getActivity();

            verificationActivity.setTranslationRequestUuid(translationRequestPojo.getTranslationRequestUuid());
            verificationActivity.setTranslatedTextUuid(translationInfoPojo.getTranslatedTextUuid());
            verificationActivity.setTranslatedText(translationInfoPojo.getTranslationText());
            verificationActivity.setTranslationRequestTest(translationRequestPojo.getTranslationRequestText());

            ((TextView) rootView.findViewById(R.id.original_text)).setText(translationRequestPojo.getTranslationRequestText());
            ((TextView) rootView.findViewById(R.id.translated_text)).setText(translationInfoPojo.getTranslationText());

        }
    }
}
