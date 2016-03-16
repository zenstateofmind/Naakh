package com.example.nikhiljoshi.naakh.UI.Profile;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingTranslatorProfile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Profile.ProfileObject;
import com.example.nikhiljoshi.naakh.network.Tasks.GetProfileTask;

import java.util.List;

import javax.inject.Inject;

/**
 * The profile surfaces up the name, the languages that the user is fluent in, the number of words
 * that the user has translated and the amount of money that the user has earned. It also saves
 * the languages that the user is proficient in, into shared preferences
 *
 *
 * TODO: Current problem is that we do a network call each time this page loads.
 * This will be unecessary data costs for the user. We probably should have a settings
 * page that contains the number of words translated and the money earned eventually. This page
 * just contains the name and what languages you are good at and many other static information
 */
public class ProfileFragment extends Fragment implements OnGettingTranslatorProfile {

    @Inject
    NaakhApi api;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        populateView();
        return rootView;
    }

    private void populateView() {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String access_token = sharedPreferences.getString(getString(R.string.token), "");
        new GetProfileTask(api, this, access_token).execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ProdApplication) getActivity().getApplication()).component().inject(this);
    }


    @Override
    public void carryOutActionWithUserProfile(ProfileObject profileObject) {
        if (profileObject == null) {
            //TODO: FIGURE OUT WHAT TO DO HERE
        } else {
            ((TextView)rootView.findViewById(R.id.translator_name)).setText(profileObject.getName());
            ((TextView)rootView.findViewById(R.id.languages)).setText(TextUtils.join(", ", profileObject.getFluentLanguages()));
            ((TextView)rootView.findViewById(R.id.money_earned)).setText(profileObject.getTotalMoneyEarned());
            ((TextView)rootView.findViewById(R.id.words_translated)).setText(profileObject.getWordsTranslated() + "");
            saveLanguagesInSharedPreferences(profileObject.getFluentLanguages());
        }
    }

    private void saveLanguagesInSharedPreferences(List<String> fluentLanguages) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        final String languages = preference.getString(getString(R.string.languages), "");
        if (languages.trim().isEmpty()) {
            SharedPreferences.Editor editor = preference.edit();
            editor.putString(getString(R.string.languages), TextUtils.join(", ", fluentLanguages));
            editor.commit();
        }
    }
}
