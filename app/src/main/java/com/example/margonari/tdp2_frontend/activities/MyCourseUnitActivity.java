package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.adapters.MaterialAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Material;
import com.example.margonari.tdp2_frontend.domain.Question;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.domain.Video;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseUnitActivity extends AppCompatActivity {

    private RecyclerView materialRecyclerView;
    private RecyclerView.LayoutManager materialLayoutManager;
    private RecyclerView.Adapter materialAdapter;
    private String api_token;
    private UnityInfo unityInfo;
    private ArrayList<Material> materialList;
    private ArrayList<Video> videosList;
    private static String LOG_TAG = "MyCourseUnitActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_unit);
        Intent intent = getIntent();

        api_token = getIntent().getStringExtra("API_TOKEN");
        unityInfo = (UnityInfo)intent.getSerializableExtra("UNITY");
       // unityInfo.getMaterials()[unityInfo.getMaterials().length].setFull_path("http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/course_videos/4/1.mp4");
        /*
        //TODO HARDCODE
        Material[] materialHardCode = new Material[10];
        for (int i=0;i<unityInfo.getMaterials().length;i++){
            materialHardCode[i]=unityInfo.getMaterials()[i];
        }
        Material m =new Material();
        m.setFull_path("http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/course_videos/4/1.mp4");

        materialHardCode[unityInfo.getMaterials().length] =m;
        unityInfo.setMaterials(materialHardCode);
        */

        materialRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_material);
        materialRecyclerView.setHasFixedSize(true);
        materialLayoutManager = new LinearLayoutManager(this);
        materialRecyclerView.setLayoutManager(materialLayoutManager);
        materialRecyclerView.setFocusable(false);
        materialAdapter = new MaterialAdapter(getDataSetMaterial());
        materialRecyclerView.setAdapter(materialAdapter);
        materialList = getDataSetMaterial();
        videosList = getDataSetVideos();

        final Button button = (Button) findViewById(R.id.buttonActividades);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ArrayList questions = new ArrayList<Question>();
                for (int i = 0; i < unityInfo.getQuestions().length; i++) {
                    questions.add(unityInfo.getQuestions()[i]);
                }
                Intent intent = new Intent(MyCourseUnitActivity.this,EvaluationActivity.class);
                intent.putExtra("API_TOKEN",api_token);
                intent.putExtra("QUESTIONS",questions);
                startActivity(intent);
            }
        });
    }

    private ArrayList<Material> getDataSetMaterial() {
        //TODO
        ArrayList results = new ArrayList<Material>();
        for (int i = 0; i < unityInfo.getMaterials().length; i++) {
            results.add(unityInfo.getMaterials()[i]);
        }
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

                System.out.println("LLEGO ACA!!!");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(material.getFull_path()));
                startActivity(browserIntent);
                /*
                if (type == Material.DOCUMENTO) {
                    //Abrir Documento

                } else if (type == Material.VIDEO) {
                    Video video = videosList.get(position);
                    Intent intent = new Intent(MyCourseUnitActivity.this, VideoViewActivity.class);
                    intent.putExtra("VIDEO_URL",video.getFull_path());
                    startActivity(intent);
                } else {
                    //Abrir Examen
                    System.out.println("GOT HERE");
                    ArrayList questions = new ArrayList<Question>();
                    for (int i = 0; i < unityInfo.getQuestions().length; i++) {
                        questions.add(unityInfo.getQuestions()[i]);
                    }
                    Intent intent = new Intent(MyCourseUnitActivity.this,EvaluationActivity.class);
                    intent.putExtra("API_TOKEN",api_token);
                    intent.putExtra("QUESTIONS",questions);
                    startActivity(intent);
                }*/

                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }
}
