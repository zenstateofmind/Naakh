package com.example.nikhiljoshi.naakh.network;

import com.example.nikhiljoshi.naakh.network.POJO.Profile.ProfileObject;
import com.example.nikhiljoshi.naakh.network.POJO.SignIn.AccessToken;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslateObject;
import com.example.nikhiljoshi.naakh.network.POJO.Translate.TranslationInfo;

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
 * Using retrofit, set up the calls to all the endpoints that are present in Naakh API
 */
public interface NaakhClient {

    //sign in
    @FormUrlEncoded
    @POST("/api/v1/oauth2/access_token")
    Call<AccessToken> login (@FieldMap Map<String, String> params);

    //get translate
    @GET("/api/v1/incomplete/translations/")
    Call<TranslateObject> getTranslationJob(@QueryMap Map<String, String> options,
                                             @Header("authorization") String authorization);

    //post translate
    @FormUrlEncoded
    @POST("/api/v1/translatedtext/{uuid}")
    Call<TranslationInfo> postTranslatedTextInfo(@FieldMap Map<String, String> options,
                                                     @Header("authorization") String authorization,
                                                     @Path("uuid") String translatedTextUuid);

    @FormUrlEncoded
    @POST("/api/v1/translatetext/")
    Call<TranslationInfo> postReviewersTranslateTextInfo(@FieldMap Map<String, String> options,
                                                             @Header("authorization") String authorization);


    //get profile
    @GET("/api/v1/translator/profile/")
    Call<ProfileObject> getProfile(@Header("authorization") String authorization);
}
