package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ForumPostAdapter;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.services.ForumPostServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseForumThreadPostsActivity extends AppCompatActivity {

    private static final String API_TOKEN = "API_TOKEN";
    private static final String THREAD_ID = "THREAD_ID";

    private String api_token;
    private String thread_id;

    private RecyclerView forumPostsRecyclerView;
    private RecyclerView.LayoutManager forumPostsLayoutManager;
    private RecyclerView.Adapter forumPostsAdapter;
    private ArrayList<ForumPost> forumPostArrayList;
    private Button buttonNewPost;
    
    private EditText textPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_forum_thread_posts);

        api_token = getIntent().getStringExtra(API_TOKEN);
        thread_id = getIntent().getStringExtra(THREAD_ID);

        textPost = (EditText) findViewById(R.id.text_forum_post);
        forumPostsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_course_forum_posts);
        forumPostsRecyclerView.setHasFixedSize(true);
        forumPostsLayoutManager = new LinearLayoutManager(this);
        forumPostsRecyclerView.setLayoutManager(forumPostsLayoutManager);
        forumPostsRecyclerView.setFocusable(false);
        forumPostArrayList = getDataSetForumPosts();
        forumPostsAdapter = new ForumPostAdapter(forumPostArrayList);
        forumPostsRecyclerView.setAdapter(forumPostsAdapter);
        buttonNewPost = (Button) findViewById(R.id.button_new_post);
        buttonNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Communication to make POST
                String content = textPost.getText().toString();

            }
        });
    }

    private ArrayList<ForumPost> getDataSetForumPosts() {
        HttpRequestTaskForumPost httpRequestTaskForumPosts = new HttpRequestTaskForumPost();
        httpRequestTaskForumPosts.execute();
        ArrayList<ForumPost> listPosts = new ArrayList<>();
        try {
            listPosts = httpRequestTaskForumPosts.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return listPosts;
    }
    
    private class HttpRequestTaskForumPost extends AsyncTask<String, Void, ArrayList<ForumPost>> {

        ArrayList<ForumPost> listPost;
        @Override
        protected ArrayList<ForumPost> doInBackground(String... params) {
            try {
                ForumPostServices forumPostServices= new ForumPostServices();
                forumPostServices.setApi_security(api_token);
                listPost = (ArrayList<ForumPost>) forumPostServices.getListPostBy(thread_id);
                return listPost;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
