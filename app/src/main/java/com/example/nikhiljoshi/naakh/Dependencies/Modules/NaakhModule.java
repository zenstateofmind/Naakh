package com.example.nikhiljoshi.naakh.Dependencies.Modules;

import com.example.nikhiljoshi.naakh.network.NaakhApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provides Naakh API as of now
 */

@Module
public class NaakhModule {

    @Provides
    @Singleton
    NaakhApi provideNaakhApi() {
        return new NaakhApi();
    }

}
