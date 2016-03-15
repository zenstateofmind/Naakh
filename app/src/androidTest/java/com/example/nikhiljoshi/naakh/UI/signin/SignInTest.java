package com.example.nikhiljoshi.naakh.UI.SignIn;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.AccessToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Inject
    NaakhApi api;

    @Rule
    public ActivityTestRule<SignIn> signInActivityRule = new ActivityTestRule<SignIn>(
            SignIn.class,
            false,
            true);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication applicationContext = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        ((TestApiComponent) applicationContext.component()).inject(this);
    }

    @Test
    public void signInFailsWhenPasswordEmpty() {
        onView(withId(R.id.username)).perform(typeText("12345678"));
        onView(withId(R.id.sign_in_validation)).perform(click());
        onView(withId(R.id.username)).check(matches(isDisplayed()));
    }

    @Test
    public void signInFailsWhenPhoneNumberEmpty() {
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.sign_in_validation)).perform(click());
        onView(withId(R.id.username)).check(matches(isDisplayed()));

    }

    @Test
    public void profileActivityOpensWhenLoginSucceeds() {
        Mockito.when(api.login(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
                .thenReturn(getSignInPojo(true));

        onView(withId(R.id.username)).perform(typeText("12345"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.sign_in_validation)).perform(click());
        onView(withId(R.id.translator_name)).check(matches(isDisplayed()));
    }

    @Test
    public void profileActivityDoesntOpenWhenLoginFails() {
        Mockito.when(api.login(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
                .thenReturn(getSignInPojo(false));

        onView(withId(R.id.username)).perform(typeText("12345"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.sign_in_validation)).perform(click());
        onView(withId(R.id.username)).check(matches(isDisplayed()));
    }

    private AccessToken getSignInPojo(boolean loginSuccess) {
        if (loginSuccess) {
            AccessToken accessToken = new AccessToken();
            accessToken.setAccessToken("Access");
            return accessToken;
        } else {
            return null;
        }
    }

}
