package com.example.nikhiljoshi.naakh.UI.Verification;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.nikhiljoshi.naakh.Dependencies.Components.TestApiComponent;
import com.example.nikhiljoshi.naakh.MockApplication;
import com.example.nikhiljoshi.naakh.network.NaakhApi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 * Created by nikhiljoshi on 3/11/16.
 */
@RunWith(AndroidJUnit4.class)
public class VerificationTest {

    @Inject
    NaakhApi api;

    @Rule
    final ActivityTestRule<Verification> verificationActivityTestRule = new ActivityTestRule<Verification>(
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

    // if click on correct, make sure dialog pops up. Two tests for each path

    // If not sure, make sure dialog pops up, two tests for each path

    // If incorrect, make sure dialog pops up. Two tests- one to make sure something is checked
    // and the other to see what happens if nothing is clicked

    // If something is incorrect and choose options, ensure fix verification page shows up

    //


}
