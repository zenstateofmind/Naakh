package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/6/16.
 */

@Component(modules = {NaakhModule.class})
@Singleton
public interface ApiComponent extends BaseComponent {}
