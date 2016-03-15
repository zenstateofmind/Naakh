package com.example.nikhiljoshi.naakh.UI.CallbackInterfaces;

import com.example.nikhiljoshi.naakh.network.POJO.SignIn.AccessToken;

/**
 * Created by nikhiljoshi on 3/10/16.
 */
public interface OnSignInTaskCompleted {

    void runOnPostTaskCompleted(AccessToken accessToken);

}
