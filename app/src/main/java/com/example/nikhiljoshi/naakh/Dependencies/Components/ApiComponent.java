package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/6/16.
 */

@Singleton
@Component(modules = NaakhApiModule.class)
public interface ApiComponent extends BaseComponent {}
