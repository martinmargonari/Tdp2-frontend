package com.example.margonari.tdp2_frontend.activities;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by luis on 11/09/16.
 */
public class CourselandApp extends Application {
    private static String api_token;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public static String getApi_token() {
        return api_token;
    }

    public static void setApi_token(String api_token) {
        api_token = api_token;
    }
}
