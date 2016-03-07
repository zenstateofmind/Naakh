package com.example.nikhiljoshi.naakh.Dependencies.Modules;

import com.example.nikhiljoshi.naakh.network.NaakhApi;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikhiljoshi on 3/6/16.
 */

@Module
public class MockNaakhApiModule {

    @Provides
    @Singleton
    NaakhApi provideNaakhApi() {
        return Mockito.mock(NaakhApi.class);
    }
}
