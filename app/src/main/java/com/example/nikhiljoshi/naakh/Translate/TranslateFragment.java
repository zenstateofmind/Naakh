package com.example.nikhiljoshi.naakh.translate;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.language.Language;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.NaakhApiBaseUrls;
import com.example.nikhiljoshi.naakh.network.NaakhApiQueryKeys;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequestPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslateJobTask;
import com.example.nikhiljoshi.naakh.network.VolleyInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
            protected void onPostExecute(TranslationRequestPojo translationRequestPojo) {
                if (translationRequestPojo == null) {
                    Log.i(LOG_TAG, "Did not get any translation jobs");
                } else {
                    TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
                    toTranslateView.setText(translationRequestPojo.getText());
                    ((Translate) getActivity()).setTranslatedTextUuid(translationRequestPojo.getUuid());
                    Log.i(LOG_TAG, "Got translation task with uuid: " + translationRequestPojo.getUuid());
                }
            }
        }.execute();

    }

}
