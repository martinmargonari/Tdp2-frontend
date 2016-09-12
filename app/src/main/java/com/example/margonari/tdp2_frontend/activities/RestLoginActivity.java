package com.example.margonari.tdp2_frontend.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.rest_dto.LoginDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new HttpRequestTask().execute();

            }
        });
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Login> {
        @Override
        protected Login doInBackground(Void... params) {
            try {
                final String url = getResources().getString(R.string.login_services_address);
                LoginDTO loginDTO = (LoginDTO) geDataOftDTO(url, LoginDTO.class);
                return loginDTO.getData();
            } catch (Exception e) {
                Log.e("RestLoginActivity", e.getMessage(), e);
            }

            return null;
        }


        private Object geDataOftDTO(String url, Class object) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, object);
        }

        @Override
        protected void onPostExecute(Login greeting) {
            TextView greetingIdText = (TextView) findViewById(R.id.id_value);
            TextView greetingContentText = (TextView) findViewById(R.id.content_value);
            greetingIdText.setText(greeting.getId());
            greetingContentText.setText(greeting.getApi_token());
        }

    }

}
