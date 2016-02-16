package com.example.nikhiljoshi.naakh;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nikhiljoshi on 2/13/16.
 */
public class VolleyInstance {

    private static VolleyInstance volleyInstance;
    private RequestQueue requestQueue;
    private static Context context;

    public VolleyInstance(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized VolleyInstance getInstance(Context context) {
        if (volleyInstance == null) {
            volleyInstance = new VolleyInstance(context);
        }
        return volleyInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
