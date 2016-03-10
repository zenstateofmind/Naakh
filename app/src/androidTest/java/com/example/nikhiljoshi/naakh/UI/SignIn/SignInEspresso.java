package com.example.nikhiljoshi.naakh.UI.SignIn;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.signin.SignIn;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/9/16.
 */
@RunWith(AndroidJUnit4.class)
public class SignInEspresso {

    @Inject
    NaakhApi api;
    @Inject
    LoginTask loginTask;

    @Rule
    public ActivityTestRule<SignIn> signInActivityRule = new ActivityTestRule<SignIn>(
            SignIn.class,
            false,
            true);

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication applicationContext = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        final TestApiComponent testApiComponent = (TestApiComponent) applicationContext.createBaseComponent();
        testApiComponent.inject(this);
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
//        Mockito.when(loginTask.execute(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
//                .thenAnswer()

        onView(withId(R.id.username)).perform(typeText("12345"));
        onView(withId(R.id.password)).perform(typeText("password"));
        onView(withId(R.id.sign_in_validation)).perform(click());

        onView(withId(R.id.translator_name)).check(matches(isDisplayed()));
    }

    private SignInPojo getSignInPojo(boolean loginSuccess) {
        if (loginSuccess) {
            SignInPojo signInPojo = new SignInPojo();
            signInPojo.setAccessToken("Access");
            return signInPojo;
        } else {
            return null;
        }
    }

}
