package com.example.nikhiljoshi.naakh.network;

import com.example.nikhiljoshi.naakh.network.POJO.SignInPojo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public interface NaakhClient {

    //sign in
    @FormUrlEncoded
    @POST("/api/v1/oauth2/access_token")
    Call<SignInPojo> login (@FieldMap Map<String, String> params);

    //get translate

    //post translate

    //get profile
}
