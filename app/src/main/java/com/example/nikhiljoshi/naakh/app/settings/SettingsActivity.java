package com.example.nikhiljoshi.naakh.app.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.welcome.Welcome;

/**
 * Created by nikhiljoshi on 2/16/16.
 */
public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_translate);

        final Preference logOutPreference = findPreference(getString(R.string.logoutKey));
        logOutPreference.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        if (preference.getKey().equals(getString(R.string.logoutKey))) {
            final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(getString(R.string.token));
            editor.commit();

            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        }

        return true;
    }
}
