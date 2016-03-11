package com.example.nikhiljoshi.naakh;

import android.app.Application;

import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class ProdApplication extends Application {

    private final BaseComponent baseComponent = createComponent();

    protected BaseComponent createComponent() {
        return DaggerApiComponent.builder().naakhModule(new NaakhModule()).build();
    }

    public BaseComponent component() {
        return baseComponent;
    }
}
