package com.example.nikhiljoshi.naakh.network;

import com.example.nikhiljoshi.naakh.network.POJO.SignIn.SignInPojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.GetTranslatePojo;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfoPojo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by nikhiljoshi on 3/3/16.
 */
public interface NaakhClient {

    //sign in
    @FormUrlEncoded
    @POST("/api/v1/oauth2/access_token")
    Call<SignInPojo> login (@FieldMap Map<String, String> params);

    //get translate
    @GET("/api/v1/incomplete/translations/")
    Call<GetTranslatePojo> getTranslationJob(@QueryMap Map<String, String> options,
                                             @Header("authorization") String authorization);

    //post translate
    @FormUrlEncoded
    @POST("/api/v1/translatedtext/{uuid}")
    Call<TranslationInfoPojo> postTranslationJob(@FieldMap Map<String, String> options,
                                                 @Header("authorization") String authorization,
                                                 @Path("uuid") String uiud);




    //get profile
}
