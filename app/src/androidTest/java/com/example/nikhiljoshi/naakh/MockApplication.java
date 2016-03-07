package com.example.nikhiljoshi.naakh;


import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerTestApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.MockNaakhApiModule;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class MockApplication extends ProdApplication{

    @Override
    protected BaseComponent createComponent() {
        return DaggerTestApiComponent.builder().mockNaakhApiModule(new MockNaakhApiModule())
                .build();
    }
}
