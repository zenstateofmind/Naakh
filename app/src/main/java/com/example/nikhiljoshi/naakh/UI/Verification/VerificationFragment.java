package com.example.nikhiljoshi.naakh.UI.Verification;

import android.content.SharedPreferences;
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
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequestPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class VerificationFragment extends Fragment {

    private static final String LOG_TAG = VerificationFragment.class.getSimpleName();
    private NaakhApi api;

    public VerificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_verification, container, false);
        getVerificationJob(rootView);
        return rootView;
    }


    private void getVerificationJob(final View rootView) {
        api = new NaakhApi();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");

        new GetTranslationJobTask(api, Language.HINDI, TranslationStatus.UNVERIFIED, token) {
            @Override
            protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
                if (translationInfoPojo == null) {
                    Log.e(LOG_TAG, "Did not get any translations back");
                    //Todo: Figure out what to do here
                } else {
                    populateView(rootView, translationInfoPojo);

                }
            }
        }.execute();
    }

    private void populateView(final View rootView, TranslationInfoPojo translationInfoPojo) {
        final TranslationRequestPojo translationRequestPojo = translationInfoPojo.getTranslation_request();
        final Verification verificationActivity = (Verification) getActivity();

        verificationActivity.setTranslationRequestUuid(translationRequestPojo.getTranslationRequestUuid());
        verificationActivity.setTranslatedTextUuid(translationInfoPojo.getTranslatedTextUuid());
        verificationActivity.setTranslatedText(translationInfoPojo.getTranslationText());
        verificationActivity.setTranslationRequestTest(translationRequestPojo.getTranslationRequestTest());

        ((TextView) rootView.findViewById(R.id.original_text)).setText(translationRequestPojo.getTranslationRequestTest());
        ((TextView) rootView.findViewById(R.id.translated_text)).setText(translationInfoPojo.getTranslationText());
        //TODO: Add context and tone
    }
}
