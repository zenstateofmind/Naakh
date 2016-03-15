package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Api Component
 */

@Component(modules = {NaakhModule.class})
@Singleton
public interface ApiComponent extends BaseComponent {}
