package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ForumThreadAdapter;
import com.example.margonari.tdp2_frontend.domain.ForumCategory;
import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.example.margonari.tdp2_frontend.services.ForumThreadsServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseForumThreadActivity extends AppCompatActivity {

    public static final String API_TOKEN = "API_TOKEN";
    public static final String CATEGORY_ID = "CATEGORY_ID";
    public static final String CATEGORY_NAME = "CATEGORY_NAME";

    private String api_token;
    private String categoryId;
    private String categoryName;

    private RecyclerView forumThreadsRecyclerView;
    private RecyclerView.LayoutManager forumThreadsLayoutManager;
    private RecyclerView.Adapter forumThreadsAdapter;
    private ArrayList<ForumThread> forumThreadArrayList;
    private FloatingActionButton buttonNewTopic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_forum_thread);

        api_token = getIntent().getStringExtra(API_TOKEN);
        categoryId = getIntent().getStringExtra(CATEGORY_ID);
        categoryName = getIntent().getStringExtra(CATEGORY_NAME);

        this.setTitle(categoryName);

        forumThreadsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_course_forum_threads);
        forumThreadsRecyclerView.setHasFixedSize(true);
        forumThreadsLayoutManager = new LinearLayoutManager(this);
        forumThreadsRecyclerView.setLayoutManager(forumThreadsLayoutManager);
        forumThreadsRecyclerView.setFocusable(false);
        forumThreadArrayList = getDataSetForumThreads();
        forumThreadsAdapter = new ForumThreadAdapter(forumThreadArrayList);
        forumThreadsRecyclerView.setAdapter(forumThreadsAdapter);
        buttonNewTopic = (FloatingActionButton) findViewById(R.id.button_new_topic);
        buttonNewTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCourseForumThreadActivity.this,NewTopicActivity.class);
                intent.putExtra("API_TOKEN",api_token);
                intent.putExtra("CATEGORY_ID",categoryId);
                startActivityForResult(intent, 1);

            }
        });

    }

    private ArrayList<ForumThread> getDataSetForumThreads() {
        HttpRequestTaskForumThreads httpRequestTaskForumThreads= new HttpRequestTaskForumThreads();
        httpRequestTaskForumThreads.execute();
        ArrayList<ForumThread> listThreads = new ArrayList<>();
        try {
            listThreads = httpRequestTaskForumThreads.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return listThreads;
    }

    private class HttpRequestTaskForumThreads extends AsyncTask<String, Void, ArrayList<ForumThread>> {

        ArrayList<ForumThread> listThreads = new ArrayList<>();
        @Override
        protected ArrayList<ForumThread> doInBackground(String... params) {
            try {
                ForumThreadsServices forumThreadsServices = new ForumThreadsServices();
                forumThreadsServices.setApi_security(api_token);
                listThreads = (ArrayList<ForumThread>) forumThreadsServices.getListThreadsBy(categoryId);
                return listThreads;
            } catch (Exception e) {
                Log.e("ForumThreadActivity", e.getMessage(), e);
            }

            return null;
        }

    }



    @Override
    public void onResume() {
        super.onResume();

        ((ForumThreadAdapter) forumThreadsAdapter).setOnItemClickListener(new ForumThreadAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ForumThread forumThread = forumThreadArrayList.get(position);
                String forumThreadId = forumThread.getId();
                String title = forumThread.getTitle();
                Intent intent = new Intent(MyCourseForumThreadActivity.this, MyCourseForumThreadPostsActivity.class);
                intent.putExtra(MyCourseForumThreadPostsActivity.API_TOKEN, api_token);
                intent.putExtra(MyCourseForumThreadPostsActivity.THREAD_ID, forumThreadId);
                intent.putExtra(MyCourseForumThreadPostsActivity.THREAD_NAME, title);
                startActivity(intent);

                Log.i("LOG_TAG", " Clicked on Item " + position);
            }
        });



    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == RESULT_OK){
                finish();
                startActivity(getIntent());
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }//onActivityResult
}
