package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.UI.signin.SignIn;
import com.example.nikhiljoshi.naakh.UI.signin.SignInFragment;
import com.example.nikhiljoshi.naakh.UI.welcome.WelcomeFragment;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public interface BaseComponent {

    void inject(SignIn signIn);

}
