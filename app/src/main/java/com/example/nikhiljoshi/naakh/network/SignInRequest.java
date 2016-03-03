package com.example.nikhiljoshi.naakh.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by nikhiljoshi on 2/29/16.
 */
public class SignInRequest extends StringRequest {


    private final String username;
    private final String password;
    private final String oauth_client_id;
    private final String oauth_client_secret;

    private SignInRequest(Response.Listener<String> listener, Response.ErrorListener errorListener,
                          String username, String password, String oauth_client_id,
                          String oauth_client_secret) {
        super(Request.Method.POST, NaakhApiBaseUrls.LOGIN_BASE_URL, listener, errorListener);
        this.username = username;
        this.password = password;
        this.oauth_client_id = oauth_client_id;
        this.oauth_client_secret = oauth_client_secret;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(NaakhApiQueryKeys.PHONE_NUMBER, username);
        params.put(NaakhApiQueryKeys.PASSWORD, password);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_ID, oauth_client_id);
        params.put(NaakhApiQueryKeys.OAUTH_CLIENT_SECRET, oauth_client_secret);
        return params;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String username;
        private String password;
        private String oauth_client_id;
        private String oauth_client_secret;
        private Response.Listener<String> listener;
        private Response.ErrorListener errorListener;

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder oauth_client_id(String oauth_client_id) {
            this.oauth_client_id = oauth_client_id;
            return this;
        }

        public Builder oauth_client_secret(String oauth_client_secret) {
            this.oauth_client_secret = oauth_client_secret;
            return this;
        }

        public Builder listener(Response.Listener<String> listener) {
            this.listener = listener;
            return this;
        }

        public Builder errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public SignInRequest build() {
            return new SignInRequest(listener, errorListener, username, password, oauth_client_id,
                    oauth_client_secret);
        }
    }
}
