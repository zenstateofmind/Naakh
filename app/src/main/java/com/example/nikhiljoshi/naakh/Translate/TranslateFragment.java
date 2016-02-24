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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.language.Language;
import com.example.nikhiljoshi.naakh.network.calls.NaakhApiQueryKeys;
import com.example.nikhiljoshi.naakh.network.calls.VolleyInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class TranslateFragment extends Fragment {

    private static final String LOG_TAG = "TranslateFragment";

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
        String incompleteTranslationUrl = getTranslationUrl(token, Language.HINDI.getDbValue(), "untranslated", 1);

        StringRequest request = new StringRequest(Request.Method.GET, incompleteTranslationUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                populateView(rootView, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Unable to gather translations", Toast.LENGTH_SHORT).show();
            }
        });

        VolleyInstance.getInstance(getActivity().getApplicationContext()).addToRequestQueue(request);
    }

    private String getTranslationUrl(String token, String language, String untranslated, int limit) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("naakh.herokuapp.com")
                .appendPath("api")
                .appendPath("v1")
                .appendPath("incomplete")
                .appendPath("translations")
                .appendPath("")
                .appendQueryParameter(NaakhApiQueryKeys.OAUTH_CONSUMER_KEY, token)
                .appendQueryParameter(NaakhApiQueryKeys.LANGUAGE, language)
                .appendQueryParameter(NaakhApiQueryKeys.TRANSLATION_STATUS, untranslated)
                .appendQueryParameter(NaakhApiQueryKeys.LIMIT, limit + "");

        return builder.build().toString();
    }

    private void populateView(View rootView, String response) {
        TextView toTranslateView = (TextView) rootView.findViewById(R.id.to_translate);
        try {
            JSONObject jsonResonse = new JSONObject(response);
            final JSONArray objectsArray = jsonResonse.getJSONArray(NaakhApiQueryKeys.OBJECTS);
            final JSONObject incompleteTranslationObject = objectsArray.getJSONObject(0);
            final String uuid = incompleteTranslationObject.getString("uuid");

            final JSONObject translationRequestObject = incompleteTranslationObject.getJSONObject(NaakhApiQueryKeys.TRANSLATION_REQUEST);
            final String to_translate_text = translationRequestObject.getString(NaakhApiQueryKeys.TRANSLATION_TEXT);

            ((Translate)getActivity()).setTranslatedTextUuid(uuid);
            Log.i(LOG_TAG, "Uiud of translate text: " + uuid);
            ((TextView) rootView.findViewById(R.id.to_translate)).setText(to_translate_text);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to response into json: " + e.getMessage());
        }

    }
}
