package com.example.nikhiljoshi.naakh;


import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerTestApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.MockNaakhApiModule;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.MockSignInModule;
import com.example.nikhiljoshi.naakh.UI.signin.SignIn;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class MockApplication extends ProdApplication{

    @Override
    public BaseComponent createComponent() {
        return DaggerTestApiComponent.builder().mockNaakhApiModule(new MockNaakhApiModule()).build();
    }
}
