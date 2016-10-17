package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.MaterialAdapter;
import com.example.margonari.tdp2_frontend.adapters.VideoAdapter;
import com.example.margonari.tdp2_frontend.domain.Material;
import com.example.margonari.tdp2_frontend.domain.Question;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.domain.Video;

import java.util.ArrayList;

public class MyCourseUnitActivity extends AppCompatActivity {

    private RecyclerView materialRecyclerView;
    private RecyclerView.LayoutManager materialLayoutManager;
    private RecyclerView.Adapter materialAdapter;
    private String api_token;
    private String session_id;
    private UnityInfo unityInfo;

    private ArrayList<Material> materialList;
    private RecyclerView videoRecyclerView;
    private RecyclerView.LayoutManager videoLayoutManager;
    private RecyclerView.Adapter videoAdapter;
    private ArrayList<Video> videosList;
    private static String LOG_TAG = "MyCourseUnitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_unit);
        Intent intent = getIntent();

        api_token = getIntent().getStringExtra("API_TOKEN");
        session_id = getIntent().getStringExtra("SESSION_ID");


        unityInfo = (UnityInfo)intent.getSerializableExtra("UNITY");

        materialRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_material);
        materialRecyclerView.setHasFixedSize(true);
        materialLayoutManager = new LinearLayoutManager(this);
        materialRecyclerView.setLayoutManager(materialLayoutManager);
        materialRecyclerView.setFocusable(false);
        materialAdapter = new MaterialAdapter(getDataSetMaterial());
        materialRecyclerView.setAdapter(materialAdapter);
        materialList = getDataSetMaterial();


        videoRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_video);
        videoRecyclerView.setHasFixedSize(true);
        videoLayoutManager = new LinearLayoutManager(this);
        videoRecyclerView.setLayoutManager(videoLayoutManager);
        videoRecyclerView.setFocusable(false);
        videoAdapter = new VideoAdapter(getDataSetVideos());
        videoRecyclerView.setAdapter(videoAdapter);
        videosList = getDataSetVideos();

    }

    private ArrayList<Material> getDataSetMaterial() {
        //TODO
        ArrayList results = new ArrayList<Material>();
        for (int i = 0; i < unityInfo.getMaterials().length; i++) {
            results.add(unityInfo.getMaterials()[i]);
        }
        results.add(new Material("EXAMEN",Material.EXAMEN));
        return results;
    }

    private ArrayList<Video> getDataSetVideos() {
        //TODO
        ArrayList results = new ArrayList<Video>();
        for (int i = 0; i < unityInfo.getVideos().length; i++) {
            results.add(unityInfo.getVideos()[i]);
        }

        return results;
    }


    @Override
    protected void onResume() {
        super.onResume();
        ((MaterialAdapter) materialAdapter).setOnItemClickListener(new MaterialAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Material material = materialList.get(position);
                int type = material.getType();

                if (type == Material.EXAMEN) {
                   ArrayList questions = new ArrayList<Question>();
                    for (int i = 0; i < unityInfo.getQuestions().length; i++) {
                        questions.add(unityInfo.getQuestions()[i]);
                    }
                    Intent intent = new Intent(MyCourseUnitActivity.this,EvaluationActivity.class);
                    intent.putExtra("API_TOKEN",api_token);
                    intent.putExtra("QUESTIONS",questions);
                    intent.putExtra("UNITY_ID",unityInfo.getUnity().getId());
                    intent.putExtra("SESSION_ID",session_id);

                    startActivity(intent);
                } else {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(material.getFull_path()));
                    startActivity(browserIntent);
                }

                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });

        ((VideoAdapter) videoAdapter).setOnItemClickListener(new VideoAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Video video = videosList.get(position);
                Intent intent = new Intent(MyCourseUnitActivity.this, VideoViewActivity.class);
                intent.putExtra("VIDEO_URL",video.getFull_path());
                startActivity(intent);

                Log.i(LOG_TAG, " Clicked on Video Item " + position);
            }
        });
    }
}
