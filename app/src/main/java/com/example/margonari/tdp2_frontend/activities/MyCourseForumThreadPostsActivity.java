package com.example.margonari.tdp2_frontend.activities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ForumPostAdapter;
import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.services.ForumDeletePostServices;
import com.example.margonari.tdp2_frontend.services.ForumMakePostServices;
import com.example.margonari.tdp2_frontend.services.ForumPostServices;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
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
    private ArrayList<ForumPost> forum_list_attached_files;
    private EditText textPost;
private DownloadManager downloadManager;
    public String filenameManager;
    private long q;

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
        forumPostsAdapter = new ForumPostAdapter(forumPostArrayList,MyCourseForumThreadPostsActivity.this);
        forumPostsRecyclerView.setAdapter(forumPostsAdapter);
        buttonNewPost = (Button) findViewById(R.id.button_new_post);
        buttonNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Communication to make POST
                String content = textPost.getText().toString();
                HttpRequestTaskForumMakePost httpRequestTaskForumMakePost= new HttpRequestTaskForumMakePost();
                httpRequestTaskForumMakePost.execute(thread_id,content);
                finish();
                startActivity(getIntent());
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

    public void downloadFile(String urlFile, final String filename){

        Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();
        filenameManager= String.valueOf(filename);
        Log.d("FILENAME", filenameManager);

        this.downloadManager = (DownloadManager) getApplication().getSystemService(Context.DOWNLOAD_SERVICE);
        String url = urlFile ;
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri)
                .setTitle(filename )
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                        filename)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Log.i("Download1", String.valueOf(request));
        final long q= this.downloadManager.enqueue(request);


        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(q);
                    downloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator +
                                    filenameManager);
                            Log.d("FILENAME", filenameManager);

                            Uri path = Uri.fromFile(file);
                            Log.i("Fragment2", String.valueOf(file.getAbsolutePath()));
                            Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                            pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            String ext1 = FilenameUtils.getExtension(filenameManager); // returns "txt"

                            pdfOpenintent.setDataAndType(path, "application/"+ext1);
                            try {
                                context.startActivity(pdfOpenintent);
                            } catch (ActivityNotFoundException e) {

                            }
                        }
                    }
                }
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public Boolean deletePost(String post_id){
        HttpRequestTaskForumDeletePost httpRequestTaskForumDeletePost= new HttpRequestTaskForumDeletePost();
        httpRequestTaskForumDeletePost.execute(post_id);
        try {
            forumPostsAdapter.notifyDataSetChanged();
            return httpRequestTaskForumDeletePost.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }return null;

    }

    private class HttpRequestTaskForumDeletePost extends AsyncTask<String, Void, Boolean> {

        ArrayList<ForumPost> listPost;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String post_id = params[0];

                ForumDeletePostServices forumPostServices= new ForumDeletePostServices();
                forumPostServices.setApi_security(CourselandApp.getApi_token());
                Boolean is_deleted=   forumPostServices.deletePostBy(post_id);
                Log.d("ResultadoEliminacion", is_deleted.toString());
            } catch (Exception e) {
                Log.e("MyCoourseForumThread", e.getMessage(), e);
            }

            return null;
        }

    }


    private class HttpRequestTaskForumMakePost extends AsyncTask<String, Void, Boolean> {

        ArrayList<ForumPost> listPost;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String thread_id = params[0];
                String content= params[1];

                ForumMakePostServices forumPostServices= new ForumMakePostServices();
                forumPostServices.setApi_security(CourselandApp.getApi_token());
                Boolean is_make=   forumPostServices.makePostBy(thread_id,content);
                Log.d("ResultadoMakePost", is_make.toString());
            } catch (Exception e) {
                Log.e("MyCoourseForumThread", e.getMessage(), e);
            }

            return null;
        }

    }




}
