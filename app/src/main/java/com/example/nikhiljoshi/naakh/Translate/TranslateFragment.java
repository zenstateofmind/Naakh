package com.example.nikhiljoshi.naakh.translate;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.language.Language;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslateJobTask;


/**
 * A placeholder fragment containing a simple view.
 */
public class TranslateFragment extends Fragment {

    private static final String LOG_TAG = "TranslateFragment";
    private NaakhApi api;

    public TranslateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_translate, container, false);
        getIncompleteTranslation(rootView);
        return rootView;
    }

    public void getIncompleteTranslation(final View rootView) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        api = new NaakhApi();

        new GetTranslateJobTask(api, Language.HINDI, "untranslated") {
            @Override
            protected void onPostExecute(TranslationInfoPojo translationInfoPojo) {
                if (translationInfoPojo == null) {
                    Log.i(LOG_TAG, "Did not get any translation jobs");
                } else {
                    TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
                    toTranslateView.setText(translationInfoPojo.getTranslation_request().getText());
                    ((Translate) getActivity()).setTranslatedTextUuid(translationInfoPojo.getUuid());
                    Log.i(LOG_TAG, "Got translation task with uuid: " + translationInfoPojo.getUuid());
                }
            }
        }.execute();

    }

}
