package com.example.nikhiljoshi.naakh.UI.Profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.ProdApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.CallbackInterfaces.OnGettingIncompleteTranslatedText;
import com.example.nikhiljoshi.naakh.UI.Verification.Verification;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.example.nikhiljoshi.naakh.UI.welcome.Welcome;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;
import com.example.nikhiljoshi.naakh.network.Tasks.GetTranslationJobTask;

import javax.inject.Inject;

/**
 * A profile Activity. When the user logs in, this is the first activity that is visible to the user.
 * This activity contains a navigation drawer as well as basic user information.
 */
public class Profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnGettingIncompleteTranslatedText {

    public static final String TRANSLATION_INFO_POJO = "TranslationInfoPojo";

    @Inject
    NaakhApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ((ProdApplication) getApplication()).component().inject(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String token = sharedPreferences.getString(getString(R.string.token), "");
        final Language language = Language.chooseFluentLanguage(sharedPreferences.getString(getString(R.string.languages), ""));

        if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_translate) {
            new GetTranslationJobTask(api, this, language, TranslationStatus.UNTRANSLATED, token).execute();
        } else if (id == R.id.nav_review) {
            new GetTranslationJobTask(api, this, language, TranslationStatus.UNVERIFIED, token).execute();
        } else if (id == R.id.nav_logout) {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(getString(R.string.token));
            editor.commit();

            Intent intent = new Intent(this, Welcome.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void takeActionWithIncompleteTranslatedTextObject(TranslationInfo translationInfo,
                                                             TranslationStatus translationStatus) {
        if (translationStatus == TranslationStatus.UNTRANSLATED) {
            Intent intent = new Intent(this, Translate.class);
            intent.putExtra(TRANSLATION_INFO_POJO, translationInfo);
            startActivity(intent);
        } else if (translationStatus == TranslationStatus.UNVERIFIED) {
            Intent intent = new Intent(this, Verification.class);
            intent.putExtra(TRANSLATION_INFO_POJO, translationInfo);
            startActivity(intent);
        }
    }
}
