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

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.MyCourseUnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.services.UnitServices;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseActivity extends AppCompatActivity {

    private RecyclerView unitsRecyclerView;
    private RecyclerView.LayoutManager unitsLayoutManager;
    private RecyclerView.Adapter unitsAdapter;
    private String api_token;
    private Course courseFullData;
    private  ArrayList<Unit> unitArrayList;

    private DownloadManager downloadManager;
    public String filenameManager;
    private long q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);
        Intent intent = getIntent();

        api_token = getIntent().getStringExtra("API_TOKEN");
        courseFullData = (Course) intent.getSerializableExtra("COURSE_FULL_DATA");

        unitsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_course_units);
        unitsRecyclerView.setHasFixedSize(true);
        unitsLayoutManager = new LinearLayoutManager(this);
        unitsRecyclerView.setLayoutManager(unitsLayoutManager);
        unitsRecyclerView.setFocusable(false);
        unitsAdapter = new MyCourseUnitAdapter(getDataSetUnits());
        unitsRecyclerView.setAdapter(unitsAdapter);
        unitArrayList= (ArrayList)courseFullData.getUnities();

    }

    private ArrayList<Unit> getDataSetUnits() {
        return (ArrayList<Unit>) courseFullData.getUnities();

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyCourseUnitAdapter) unitsAdapter).setOnItemClickListener(new MyCourseUnitAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Unit unit = unitArrayList.get(position);
                HttpRequestTaskUnity httpRequestTask = new HttpRequestTaskUnity();
                httpRequestTask.execute(unit.getId());
                try {
                    UnityInfo unityInfo = (UnityInfo) httpRequestTask.get();
                    Intent intent = new Intent(MyCourseActivity.this, MyCourseUnitActivity.class);
                    intent.putExtra("API_TOKEN", api_token);
                    intent.putExtra("UNITY", unityInfo);
                    intent.putExtra("SESSION_ID", courseFullData.getSession_id());
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i("LOG_TAG", " Clicked on Item " + position);
            }
        });
    }

    private class HttpRequestTaskUnity extends AsyncTask<String, Void, UnityInfo> {
        @Override
        protected UnityInfo doInBackground(String... params) {
            try {
                String course_id = params[0];
                UnitServices unitServices= new UnitServices();
                unitServices.setApi_security(api_token);
                UnityInfo unityInfo=  unitServices.getUnityInfo(course_id);

                return unityInfo;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }


    private class HttpRequestTaskDownloadCertificate extends AsyncTask<String, Void, UnityInfo> {
        @Override
        protected UnityInfo doInBackground(String... params) {
            try {
                String course_id = params[0];
                UnitServices unitServices= new UnitServices();
                unitServices.setApi_security(api_token);
                UnityInfo unityInfo=  unitServices.getUnityInfo(course_id);

                return unityInfo;
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

}