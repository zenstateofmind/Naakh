package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.GCM.RegistrationIntentService;
import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.UI.Profile.ProfileFragment;
import com.example.nikhiljoshi.naakh.UI.SignIn.SignIn;
import com.example.nikhiljoshi.naakh.UI.Verification.Verification;
import com.example.nikhiljoshi.naakh.UI.Verification.VerificationFragment;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.example.nikhiljoshi.naakh.UI.translate.TranslateFragment;

/**
 * Base Component. This interface is shared by both the prod and test modules. The test component,
 * will extend this component and add additional injections to the test activities.
 */
public interface BaseComponent {

    void inject(SignIn signIn);
    void inject(VerificationFragment verificationFragment);
    void inject(TranslateFragment translateFragment);
    void inject(Translate translate);
    void inject(Profile profile);
    void inject(Verification verification);
    void inject(ProfileFragment profileFragment);
    void inject (RegistrationIntentService registrationIntentService);
}
