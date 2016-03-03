package com.example.nikhiljoshi.naakh.Profile;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.nikhiljoshi.naakh.network.NaakhApiBaseUrls;
import com.example.nikhiljoshi.naakh.network.VolleyInstance;
import com.example.nikhiljoshi.naakh.user.UserProfile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikhiljoshi on 2/28/16.
 * TODO: Current problem is that we do a network call each time this page loads.
 * This will be unecessary data costs for the user. We probably should have a settings
 * page that contains the number of words translated and the money earned eventually. This page
 * just contains the name and what languages you are good at and many other static information
 */
public class ProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        populateView(rootView);
        return rootView;
    }

    private void populateView(View rootView) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        getUserProfile("", rootView);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private UserProfile getUserProfile(String phone_number, final View rootView) {

        final UserProfile profile = new UserProfile();

        final String url = NaakhApiBaseUrls.getProfileInformationUrl(phone_number);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    final String name = jsonObject.getString("name");
                    final String words_translated = jsonObject.getString("words_translated");
                    final String money_earned = jsonObject.getString("total_money_earned");
                    final JSONArray fluent_languages_array = jsonObject.getJSONArray("fluent_langauges");
                    String[] fluent_languages = new String[fluent_languages_array.length()];
                    for (int i = 0; i < fluent_languages_array.length(); i++) {
                        fluent_languages[i] = fluent_languages_array.getString(i);
                    }

                    //TODO: Tie up fluent languages into shared preferences?
                    profile.setName(name);
                    profile.setLanguages(fluent_languages);
                    profile.setMoneyEarned(money_earned);
                    profile.setNumWordsTranslated(words_translated);

                    ((TextView)rootView.findViewById(R.id.translator_name)).setText(profile.getName());
                    ((TextView)rootView.findViewById(R.id.languages)).setText(profile.getTranslatorLanguages());
                    ((TextView)rootView.findViewById(R.id.money_earned)).setText(profile.getMoneyEarned());
                    ((TextView)rootView.findViewById(R.id.words_translated)).setText(profile.getNumWordsTranslated());

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error in JSON parsing", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Unable to get profile information", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("authorization", "ab89611abed189ce0f9f13f5f9ec818442ed44e7");
                return headers;
            }
        };

        VolleyInstance.getInstance(getContext().getApplicationContext()).addToRequestQueue(request);

        return profile;
    }


}
