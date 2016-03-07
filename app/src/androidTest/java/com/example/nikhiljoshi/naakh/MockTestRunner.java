package com.example.nikhiljoshi.naakh;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;
import android.test.AndroidTestRunner;

/**
 * Created by nikhiljoshi on 3/6/16.
 */
public class MockTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockApplication.class.getName(), context);
    }
}
