package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.SignInModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/6/16.
 */

@Component(modules = {NaakhModule.class, SignInModule.class})
public interface ApiComponent extends BaseComponent {}
