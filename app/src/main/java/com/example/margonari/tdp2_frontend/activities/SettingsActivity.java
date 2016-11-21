package com.example.margonari.tdp2_frontend.activities;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.services.ForumMakePostServices;
import com.example.margonari.tdp2_frontend.services.NotificationsOnOffServices;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "MyPrefsFile";
    boolean hasNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Switch switchNotifications = (Switch) findViewById(R.id.switch_notifications);

        //TODO Leer desde el servidor y setear el isChecked del switch

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
         hasNotifications = settings.getBoolean("hasNotifications", true);

        switchNotifications.setChecked(hasNotifications);

        //Set a Click Listener for Switch Button
        switchNotifications.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v){
                boolean on = ((Switch) v).isChecked();
                if(on)
                {
                    HttpRequestTaskNotificationsOnOff httpRequestTaskNotificationsOnOff= new HttpRequestTaskNotificationsOnOff();
                    httpRequestTaskNotificationsOnOff.execute("1");
                    Log.d("Notificaciones:", "ON");
                }
                else
                {

                    HttpRequestTaskNotificationsOnOff httpRequestTaskNotificationsOnOff= new HttpRequestTaskNotificationsOnOff();
                    httpRequestTaskNotificationsOnOff.execute("0");

                    Log.d("Notificaciones:", "OFF");

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class HttpRequestTaskNotificationsOnOff extends AsyncTask<String, Void, Boolean> {

        ArrayList<ForumPost> listPost;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String has_notifications = params[0];

                NotificationsOnOffServices notificationsOnOffServices= new NotificationsOnOffServices();
                notificationsOnOffServices.setApi_security(CourselandApp.getApi_token());
                Boolean is_make=   notificationsOnOffServices.setNotification(has_notifications);
                Log.d("ResultadoMakePost", is_make.toString());
            } catch (Exception e) {
                Log.e("MyCoourseForumThread", e.getMessage(), e);
            }

            return null;
        }

    }

    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hasNotifications", hasNotifications);

        // Commit the edits!
        editor.commit();
    }



}
