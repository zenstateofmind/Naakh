package com.example.nikhiljoshi.naakh.UI.translate;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.Enums.Language;
import com.example.nikhiljoshi.naakh.Enums.TranslationStatus;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationRequestPojo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
@RunWith(AndroidJUnit4.class)
public class TranslateTest {

    /**
     * Test 1: Test when there are no more translations to be done
     * Test 2: When trying to hit send button without data, throw an error
     * Test 3: When add translations and send button, opens up a translate activity
     */
    @Inject
    NaakhApi api;

    @Rule
    public ActivityTestRule<Translate> translateActivityTestRule = new ActivityTestRule<Translate>(
            Translate.class,
            false,
            true);

    @Before
    public void setUp() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication applicationContext = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        ((TestApiComponent) applicationContext.component()).inject(this);
    }

    @Test
    public void displayIncompleteTranslations() {
        final TranslationInfoPojo translationInfoPojo = getTranslationInfoPojo(true);
        Mockito.when(api.getTranslateJob(Matchers.any(Language.class), Matchers.any(TranslationStatus.class), Matchers.anyString()))
                .thenReturn(translationInfoPojo);

        onView(withId(R.id.to_translate)).check(matches(withText(translationInfoPojo.getTranslationRequest().getTranslationRequestText())));
        onView(withId(R.id.send_translations)).check(matches(isEnabled()));
    }

    @Test
    public void notSendEmptyTranslationsOnClickingSend() {
        final TranslationInfoPojo translationInfoPojo = getTranslationInfoPojo(true);
        Mockito.when(api.getTranslateJob(Matchers.any(Language.class), Matchers.any(TranslationStatus.class), Matchers.anyString()))
                .thenReturn(translationInfoPojo);

        onView(withId(R.id.send_translations)).perform(click());
        Mockito.verify(api, Mockito.never()).postTranslatorTranslatedText(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void sendTranslationsOnClickingSend() {

    }

    private TranslationInfoPojo getTranslationInfoPojo(boolean incompleteTranslationsAvailable) {
        if (incompleteTranslationsAvailable) {
            TranslationInfoPojo translationInfoPojo = new TranslationInfoPojo();
            translationInfoPojo.setTranslationText("translationText");
            translationInfoPojo.setUuid("translationTextUuid");

            TranslationRequestPojo translationRequestPojo = new TranslationRequestPojo();
            translationRequestPojo.setUuid("translationRequestUuid");
            translationRequestPojo.setText("translationRequestText");

            translationInfoPojo.setTranslationRequest(translationRequestPojo);
            return translationInfoPojo;
        } else {
            return null;
        }
    }

}
