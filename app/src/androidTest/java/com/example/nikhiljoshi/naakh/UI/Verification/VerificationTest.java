package com.example.nikhiljoshi.naakh.UI.Verification;

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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by nikhiljoshi on 3/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class VerificationTest {

    @Inject
    NaakhApi api;

    @Rule
    public ActivityTestRule<Verification> verificationActivityTestRule = new ActivityTestRule<Verification>(
            Verification.class,
            true,
            false);

    @Before
    public void setUp() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication applicationContext = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        ((TestApiComponent) applicationContext.component()).inject(this);
    }

    // if no verification to be done, dont load anything, go back to profile page
    @Test
    public void displayOriginalAndTranslatorTextWhenTranslationInfoPojoNotNull() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        onView(withId(R.id.original_text)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
        onView(withId(R.id.translated_text)).check(matches(withText(
                translationInfo.getTranslationText())));
    }

    @Test
    public void goToProfilePageWhenNoTranslationsToVerify() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(false);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        onView(withText(R.string.no_more_verifications)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());

        onView(withId(R.id.translator_name)).check(matches(isDisplayed()));
    }

    /**
     * Click on correct -> the reviewer is sure ->  interested in another verification
     */
    @Test
    public void clickCorrectButtonWithConfidenceAndInterestedInAnotherVerification() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        final TranslationInfo newTranslationInfo = getNewTranslationInfoPojo();
        Mockito.when(api.getTranslateJob(Mockito.any(Language.class), Mockito.any(TranslationStatus.class),
                Mockito.anyString())).thenReturn(newTranslationInfo);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        // the translations are correct
        onView(withId(R.id.correct_button)).perform(click());

        // Yep, the reviewer is sure
        onView(withText(R.string.correct_translations_message)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        // Yep, interested in another set of verifications
        onView(withText(R.string.do_another_verification)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        // Load the next verification
        onView(withId(R.id.original_text)).check(matches(withText(
                newTranslationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    /**
     * Click on correct -> the reviewer is sure ->  interested in another verification
     */
    @Test
    public void clickCorrectButtonWithConfidenceAndNotInterestedInAnotherVerification() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        // the translations are correct
        onView(withId(R.id.correct_button)).perform(click());

        // Yep, the reviewer is sure
        onView(withText(R.string.correct_translations_message)).check(matches(isDisplayed()));
        onView(withText(R.string.yes)).perform(click());

        // Nope, not interested in another set of verifications
        onView(withText(R.string.do_another_verification)).check(matches(isDisplayed()));
        onView(withText(R.string.no)).perform(click());

        // same verification
        onView(withId(R.id.original_text)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    @Test
    public void clickOnCorrectButtonButNotConfident() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        // the translations are correct
        onView(withId(R.id.correct_button)).perform(click());

        // the reviewer is not sure
        onView(withText(R.string.correct_translations_message)).check(matches(isDisplayed()));
        onView(withText(R.string.no)).perform(click());

        // same verification
        onView(withId(R.id.original_text)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    @Test
    public void clickOnIncorrectButtonButNotChooseOptions() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        // the translations are correct
        onView(withId(R.id.incorrect_button)).perform(click());

        //load the incorrect dialog box and click on "Correct it" button without choosing option
        onView(withText(R.string.choose_incorrect_options)).check(matches(isDisplayed()));
        onView(withText(R.string.correct_it)).perform(click());

        //load dialog box asking chooser to choose whats incorrect
        onView(withText(R.string.select_whats_incorrect)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());

        // same verification
        onView(withId(R.id.original_text)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
    }

    /*
    Choose whats incorrect and make sure fix translation activity shows up
     */
    @Test
    public void clickOnIncorrectButtonAndChooseOptions() {
        final TranslationInfo translationInfo = getTranslationInfoPojo(true);

        Intent intent = new Intent();
        intent.putExtra(Profile.TRANSLATION_INFO_POJO, translationInfo);
        verificationActivityTestRule.launchActivity(intent);

        // the translations are correct
        onView(withId(R.id.incorrect_button)).perform(click());

        //load the incorrect dialog box and click on "Correct it" button without choosing option
        onView(withText(R.string.choose_incorrect_options)).check(matches(isDisplayed()));
        onView(withText(R.string.correct_it)).perform(click());

        //load dialog box asking chooser to choose whats incorrect
        onView(withText(R.string.select_whats_incorrect)).check(matches(isDisplayed()));
        onView(withText(R.string.OK)).perform(click());

        // same verification
        onView(withId(R.id.original_text)).check(matches(withText(
                translationInfo.getTranslationRequest().getTranslationRequestText())));
    }


    @Test
    public void clickOnNotSureButton() {

    }


    // if click on correct, make sure dialog pops up. Two tests for each path

    // If not sure, make sure dialog pops up, two tests for each path

    // If incorrect, make sure dialog pops up. Two tests- one to make sure something is checked
    // and the other to see what happens if nothing is clicked

    // If something is incorrect and choose options, ensure fix verification page shows up

    //

    private TranslationInfo getTranslationInfoPojo(boolean verificationsAvailable) {
        if (verificationsAvailable) {
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
        translationInfo.setTranslationText("newTranslationText");
        translationInfo.setUuid("newTranslationTextUuid");

        TranslationRequest translationRequest = new TranslationRequest();
        translationRequest.setUuid("translationRequestUuid");
        translationRequest.setText("newTranslationRequest");

        translationInfo.setTranslationRequest(translationRequest);
        return translationInfo;
    }

}
