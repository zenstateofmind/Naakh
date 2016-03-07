package com.example.nikhiljoshi.naakh.UI.SignIn;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.R;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.UI.signin.SignIn;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;

import junit.framework.Assert;

import org.mockito.Matchers;
import org.mockito.Mockito;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by nikhiljoshi on 3/7/16.
 */
public class SignInTestInstrumentation extends ActivityInstrumentationTestCase2<SignIn> {

    @Inject
    NaakhApi api;

    private static int TIMEOUT_IN_MS = 10000;

    public SignInTestInstrumentation() {
        super(SignIn.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        final MockApplication mockApplication = (MockApplication) getInstrumentation()
                .getTargetContext().getApplicationContext();
        final TestApiComponent component = (TestApiComponent) mockApplication.component();
        component.inject(this);

        getActivity();
    }

    public void testLoginSuccess() {
        final SignInPojo signInPojo = getSignInPojo("access");
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(Profile.class.getName(), null, true);

        Mockito.when(api.login(Matchers.anyString(), Matchers.anyString(), Matchers.anyString(), Matchers.anyString()))
                .thenReturn(signInPojo);

        onView(withId(R.id.sign_in_validation)).perform(click());

        monitor.waitForActivityWithTimeout(TIMEOUT_IN_MS);

        Assert.assertEquals(1, monitor.getHits());

        getInstrumentation().removeMonitor(monitor);
    }

    private SignInPojo getSignInPojo(String access_token) {
        SignInPojo signInPojo = new SignInPojo();
        signInPojo.setAccessToken(access_token);
        return signInPojo;
    }


}
