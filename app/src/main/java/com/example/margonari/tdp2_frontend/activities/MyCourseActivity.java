package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseActivity extends AppCompatActivity {

    private RecyclerView unitsRecyclerView;
    private RecyclerView.LayoutManager unitsLayoutManager;
    private RecyclerView.Adapter unitsAdapter;
    private String api_token;
    private Course courseFullData;
    private  ArrayList<Unit> unitArrayList;



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
                httpRequestTask.execute(unit.getId(),courseFullData.getSession_id());
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
                String session_id= params[1];
                UnitServices unitServices= new UnitServices();
                unitServices.setApi_security(api_token);
                UnityInfo unityInfo=  unitServices.getUnityInfo(course_id,session_id);

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
                String session_id= params[1];
                UnitServices unitServices= new UnitServices();
                unitServices.setApi_security(api_token);
                UnityInfo unityInfo=  unitServices.getUnityInfo(course_id,session_id);

                return unityInfo;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }



}