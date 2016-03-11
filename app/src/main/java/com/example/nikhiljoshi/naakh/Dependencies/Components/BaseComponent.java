package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.UI.Profile.Profile;
import com.example.nikhiljoshi.naakh.UI.SignIn.SignIn;
import com.example.nikhiljoshi.naakh.UI.Verification.VerificationFragment;
import com.example.nikhiljoshi.naakh.UI.translate.Translate;
import com.example.nikhiljoshi.naakh.UI.translate.TranslateFragment;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public interface BaseComponent {

    void inject(SignIn signIn);
    void inject(VerificationFragment verificationFragment);
    void inject(TranslateFragment translateFragment);
    void inject(Translate translate);
    void inject(Profile profile);

}
