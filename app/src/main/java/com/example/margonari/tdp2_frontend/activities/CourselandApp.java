package com.example.margonari.tdp2_frontend.activities;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

/**
 * Created by luis on 11/09/16.
 */
public class CourselandApp extends Application {
    private static String api_token;
    private static String  has_notifications;
    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        try {
            FirebaseApp.getInstance();
        } catch (IllegalStateException ex) {
            FirebaseApp.initializeApp(this, FirebaseOptions.fromResource(this));
        }
        AppEventsLogger.activateApp(this);


    }

    public static String getApi_token() {
        return api_token;
    }

    public static void setApi_token(String api_token) {
        CourselandApp.api_token = api_token;
    }

    public static String getHas_notifications() {
        return has_notifications;
    }

    public static void setHas_notifications(String has_notifications) {
        CourselandApp.has_notifications = has_notifications;
    }
}
