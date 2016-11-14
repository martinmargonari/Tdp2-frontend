package com.example.margonari.tdp2_frontend.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.example.margonari.tdp2_frontend.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Switch switchNotifications = (Switch) findViewById(R.id.switch_notifications);

        //TODO Leer desde el servidor y setear el isChecked del switch

        //Set a Click Listener for Switch Button
        switchNotifications.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean on = ((Switch) v).isChecked();
                if(on)
                {
                    //Do something when switch is on/checked
                   //TODO recibir notificaciones
                }
                else
                {
                    //Do something when switch is off/unchecked
                   //TODO Cancelar notifiaciones
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

}
