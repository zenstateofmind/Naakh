package com.example.nikhiljoshi.naakh.network.calls;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by nikhiljoshi on 2/16/16.
 */
public class NaakhSignInRequest extends StringRequest {

    //TODO: Add constructor and a builder

    NaakhSignInRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
