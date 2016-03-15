package com.example.nikhiljoshi.naakh.network.POJO.SignIn;

/**
 * Contains keys for JSON returned when hitting a NaakhAPI sign in end point.
 *
 * <br> Note: Don't change the name of the variables unless there are changes made in the
 * NaakhAPI.
 */
public class AccessToken {

    private String access_token;

    public String getAccessToken() {
        return access_token;
    }

    public void setAccessToken(String access_token) {
        this.access_token = access_token;
    }
}
