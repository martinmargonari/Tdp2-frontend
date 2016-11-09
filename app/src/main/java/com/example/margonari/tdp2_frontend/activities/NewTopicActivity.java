package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.services.ForumPostServices;

import java.util.ArrayList;

public class NewTopicActivity extends AppCompatActivity {

    EditText textNewTopicTitle;
    EditText textNewTopicContent;
    Button   buttonCreateNewTopic;

    String api_token;
    String categoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_topic);

        Intent intent = getIntent();
        api_token = intent.getStringExtra("API_TOKEN");
        categoryID = intent.getStringExtra("CATEGORY_ID");

        this.setTitle("Nuevo Tópico");

        textNewTopicTitle = (EditText) findViewById(R.id.new_topic_title);
        textNewTopicContent = (EditText) findViewById(R.id.new_topic_content);
        buttonCreateNewTopic = (Button) findViewById(R.id.button_create_topic);
        buttonCreateNewTopic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String title = textNewTopicTitle.getText().toString();
                String content = textNewTopicContent.getText().toString();

                //TODO Make communication to post new thread

                finish();
            }
        });
    }

}
