package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.MockNaakhApiModule;
import com.example.nikhiljoshi.naakh.UI.SignIn.SignInTest;
import com.example.nikhiljoshi.naakh.UI.SignIn.SignInTestInstrumentation;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/6/16.
 */

@Singleton
@Component(modules = MockNaakhApiModule.class)
public interface TestApiComponent extends BaseComponent {
    void inject(SignInTest signInTest);
    void inject(SignInTestInstrumentation signInTestInstrumentation);
}
