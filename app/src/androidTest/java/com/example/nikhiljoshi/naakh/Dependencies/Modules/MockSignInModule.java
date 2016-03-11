package com.example.nikhiljoshi.naakh.Dependencies.Modules;

import com.example.nikhiljoshi.naakh.network.NaakhApi;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
@Module
public class MockSignInModule {

    @Provides
    LoginTask loginTask() {
        return Mockito.mock(LoginTask.class);
    }

}
