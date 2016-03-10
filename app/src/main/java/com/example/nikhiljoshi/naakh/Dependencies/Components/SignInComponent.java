package com.example.nikhiljoshi.naakh.Dependencies.Components;

import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.SignInModule;
import com.example.nikhiljoshi.naakh.network.Tasks.LoginTask;

import dagger.Component;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
@Component(modules = {NaakhModule.class, SignInModule.class})
public interface SignInComponent {
    LoginTask loginTask();
}
