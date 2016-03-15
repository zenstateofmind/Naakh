package com.example.nikhiljoshi.naakh.UI.translate;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class TranslateTest {

    @Inject
    NaakhApi api;

    @Rule
    public ActivityTestRule<Translate> translateActivityTestRule = new ActivityTestRule<Translate>(
            Translate.class,
            false,
            false);

    @Before
    public void setUp() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication applicationContext = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        ((TestApiComponent) applicationContext.component()).inject(this);
    }

    @After
    public void tearDown(){
        translateActivityTestRule.getActivity().finish();
    }

    @Test
    public void displayIncompleteTranslations() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        translateActivityTestRule.launchActivity(intent);

        onView(withId(R.id.to_translate)).check(matches(withText(translationInfo.getTranslationRequest().getTranslationRequestText())));
        onView(withId(R.id.send_translations)).check(matches(isEnabled()));
    }

    @Test
    public void goToProfilesPageOnNullTranslationInfo() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(false);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        translateActivityTestRule.launchActivity(intent);

        onView(withText(R.string.no_more_translations)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());

        onView(withId(R.id.translator_name)).check(matches(isDisplayed()));
    }

    @Test
    public void notSendEmptyTranslationsOnClickingSend() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Mockito.when(api.postTranslatorTranslatedText(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(translationInfo);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        translateActivityTestRule.launchActivity(intent);

        onView(withId(R.id.send_translations)).perform(click());
        onView(withText(R.string.please_translate)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());
        onView(withId(R.id.to_translate)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    @Test
    public void sendTranslationsAndNotDoAdditionalTranslations() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Mockito.when(api.postTranslatorTranslatedText(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(translationInfo);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        translateActivityTestRule.launchActivity(intent);

        onView(withId(R.id.translated_text)).perform(typeText("stuff"));
        onView(withId(R.id.send_translations)).perform(click());
        onView(withText(R.string.are_you_sure)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        onView(withText(R.string.do_another_translation)).check(matches(isDisplayed()));
        onView(withText(R.string.no)).perform(click());

        onView(withId(R.id.translator_name)).check(matches(isDisplayed()));
    }

    @Test
    public void sendTranslationsAndDoAnotherTranslations() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);
        Mockito.when(api.postTranslatorTranslatedText(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString())).thenReturn(translationInfo);

        final TranslationInfo nextTranslationInfo = getNewTranslationInfoPojo();
        Mockito.when(api.getTranslateJob(Mockito.any(Language.class), Mockito.any(TranslationStatus.class),
                Mockito.anyString())).thenReturn(nextTranslationInfo);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        translateActivityTestRule.launchActivity(intent);

        onView(withId(R.id.translated_text)).perform(typeText("stuff"));
        onView(withId(R.id.send_translations)).perform(click());
        onView(withText(R.string.are_you_sure)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        onView(withText(R.string.do_another_translation)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        onView(withId(R.id.to_translate)).check(matches(isDisplayed()));
        onView(withId(R.id.to_translate)).check(matches(withText(
                nextTranslationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    private TranslationInfo getTranslationInfoPojo(boolean incompleteTranslationsAvailable) {
        if (incompleteTranslationsAvailable) {
            TranslationInfo translationInfo = new TranslationInfo();
            translationInfo.setTranslationText("translationText");
            translationInfo.setUuid("translationTextUuid");

            TranslationRequest translationRequest = new TranslationRequest();
            translationRequest.setUuid("translationRequestUuid");
            translationRequest.setText("translationRequestText");

            translationInfo.setTranslationRequest(translationRequest);
            return translationInfo;
        } else {
            return null;
        }
    }

    private TranslationInfo getNewTranslationInfoPojo() {
        TranslationInfo translationInfo = new TranslationInfo();
        translationInfo.setTranslationText("translationText");
        translationInfo.setUuid("translationTextUuid");

        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setUuid("translationRequestUuid");
        translationRequest.setText("newTranslationRequest");

        translationInfo.setTranslationRequest(translationRequest);
        return translationInfo;
    }

}
