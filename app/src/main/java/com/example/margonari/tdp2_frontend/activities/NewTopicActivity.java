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
import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.example.margonari.tdp2_frontend.services.ForumMakePostServices;
import com.example.margonari.tdp2_frontend.services.ForumMakeThreadServices;
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

                HttpRequestTaskForumMakeThread httpRequestTaskForumMakeThread= new HttpRequestTaskForumMakeThread();
                httpRequestTaskForumMakeThread.execute(categoryID,title,content);

                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);
                finish();

            }
        });
    }
    private class HttpRequestTaskForumMakeThread extends AsyncTask<String, Void, Boolean> {

        ArrayList<ForumThread> listPost;
        @Override
        protected Boolean doInBackground(String... params) {
            try {

                String category_id = params[0];
                String title=params[1];
                String content=params[2];

                ForumMakeThreadServices forumMakeThreadServices= new ForumMakeThreadServices();
                forumMakeThreadServices.setApi_security(CourselandApp.getApi_token());
                Boolean is_make=   forumMakeThreadServices.makePostBy(category_id,title,content);
                Log.d("ResultadoMakePost", is_make.toString());
            } catch (Exception e) {
                Log.e("MyCoourseForumThread", e.getMessage(), e);
            }

            return null;
        }

    }

}
