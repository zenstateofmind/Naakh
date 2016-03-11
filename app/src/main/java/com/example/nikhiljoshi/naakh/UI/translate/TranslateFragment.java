package com.example.nikhiljoshi.naakh.UI.translate;

import android.content.SharedPreferences;
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
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;


/**
 * A placeholder fragment containing a simple view.
 */
public class TranslateFragment extends Fragment implements OnGettingIncompleteTranslatedText {

    private static final String LOG_TAG = TranslateFragment.class.getSimpleName();
    private View rootView;

    @Inject NaakhApi api;

    public TranslateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_translate, container, false);
        getIncompleteTranslation(rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);
    }

    public void getIncompleteTranslation(final View rootView) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");

        new GetTranslationJobTask(api, this, Language.MALAYALAM, TranslationStatus.UNTRANSLATED, token).execute();
    }

    @Override
    public void updateViewWithIncompleteTranslatedTextObject(TranslationInfoPojo translationInfoPojo) {
        if (translationInfoPojo == null) {
            Log.i(LOG_TAG, "Did not get any translation jobs");
            final Button sendButton = (Button) rootView.findViewById(R.id.send_translations);
            sendButton.setEnabled(false);
        } else {
            TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
            toTranslateView.setText(translationInfoPojo.getTranslationRequest().getTranslationRequestText());
            ((Translate) getActivity()).setTranslatedTextUuid(translationInfoPojo.getTranslatedTextUuid());
            Log.i(LOG_TAG, "Got translation task with uuid: " + translationInfoPojo.getTranslatedTextUuid());
        }
    }

}
