package com.example.nikhiljoshi.naakh;

import android.app.Application;

import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhApiModule;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class ProdApplication extends Application {

    private final BaseComponent component = createComponent();

    protected BaseComponent createComponent() {
        return DaggerApiComponent.builder().naakhApiModule(new NaakhApiModule()).build();
    }

    public BaseComponent component() {
        return component;
    }
}
