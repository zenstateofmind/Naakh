package com.example.nikhiljoshi.naakh.UI.SignIn;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.test.InstrumentationTestCase;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.UI.signin.SignIn;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert.*;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.*;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class SignInTest {

    @Inject
    NaakhApi api;

    Instrumentation instrumentation;

    @Rule
    public ActivityTestRule<SignIn> signInActivityTestRule = new ActivityTestRule<>(
            SignIn.class,
            true,
            false);

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        final MockApplication application = (MockApplication) instrumentation.getTargetContext().getApplicationContext();
        final TestApiComponent component = (TestApiComponent) application.component();
        component.inject(this);
    }

    @Test
    public void testWhenSignInSucceeds() {
        final SignInPojo signInPojo = getSignInPojo("access");
        Instrumentation.ActivityMonitor monitor = instrumentation.addMonitor(Profile.class.getName(), null, true);

        Mockito.when(api.login(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
                .thenReturn(signInPojo);


        signInActivityTestRule.launchActivity(new Intent());


        onView(withId(R.id.sign_in_validation)).perform(click());
        Assert.assertEquals(1, monitor.getHits());

        instrumentation.removeMonitor(monitor);
    }

    private SignInPojo getSignInPojo(String access_token) {
        SignInPojo signInPojo = new SignInPojo();
        signInPojo.setAccessToken(access_token);
        return signInPojo;
    }


}
