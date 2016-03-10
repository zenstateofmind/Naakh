package com.example.nikhiljoshi.naakh;

import android.app.Application;

import com.example.nikhiljoshi.naakh.Dependencies.Components.ApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.BaseComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerApiComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.DaggerSignInComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Components.SignInComponent;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.NaakhModule;
import com.example.nikhiljoshi.naakh.Dependencies.Modules.SignInModule;
import com.example.nikhiljoshi.naakh.UI.signin.SignIn;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class ProdApplication extends Application {

    private BaseComponent baseComponent;
    private SignIn signIn;

    public BaseComponent createBaseComponent() {
        baseComponent = DaggerApiComponent.builder().naakhModule(new NaakhModule())
                .signInModule(new SignInModule(signIn)).build();
        return baseComponent;
    }

    public void setSignIn(SignIn signIn) {
        this.signIn = signIn;
    }

    public BaseComponent baseComponent() {
        return baseComponent;
    }
}
