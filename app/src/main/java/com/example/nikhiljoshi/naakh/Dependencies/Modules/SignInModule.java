package com.example.nikhiljoshi.naakh.Dependencies.Modules;

import com.example.nikhiljoshi.naakh.UI.signin.SignIn;
import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
@Module
public class SignInModule {

    private final SignIn signIn;

    public SignInModule(SignIn signIn) {
        this.signIn = signIn;
    }

    @Provides
    LoginTask provideLoginTask(NaakhApi api) {
        return new LoginTask(api, signIn);
    }

}
