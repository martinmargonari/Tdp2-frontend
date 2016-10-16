package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.MaterialAdapter;
import com.example.margonari.tdp2_frontend.domain.Material;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;

import java.util.ArrayList;

public class MyCourseUnitActivity extends AppCompatActivity {

    private RecyclerView materialRecyclerView;
    private RecyclerView.LayoutManager materialLayoutManager;
    private RecyclerView.Adapter materialAdapter;
    private String api_token;
    private UnityInfo unityInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course_unit);

        Intent intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        unityInfo = (UnityInfo)intent.getSerializableExtra("UNITY");
       // unityInfo.getMaterials()[unityInfo.getMaterials().length].setFull_path("http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/course_videos/4/1.mp4");

        //TODO HARDCODE
        Material[] materialHardCode = new Material[10];
        for (int i=0;i<unityInfo.getMaterials().length;i++){
            materialHardCode[i]=unityInfo.getMaterials()[i];
        }
        Material m =new Material();
        m.setFull_path("http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/course_videos/4/1.mp4");

        materialHardCode[unityInfo.getMaterials().length] =m;
        unityInfo.setMaterials(materialHardCode);


        materialRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_unit_material);
        materialRecyclerView.setHasFixedSize(true);
        materialLayoutManager = new LinearLayoutManager(this);
        materialRecyclerView.setLayoutManager(materialLayoutManager);
        materialRecyclerView.setFocusable(false);
        materialAdapter = new MaterialAdapter(getDataSetMaterial());
        materialRecyclerView.setAdapter(materialAdapter);
    }

    private ArrayList<Material> getDataSetMaterial() {
        //TODO
        ArrayList results = new ArrayList<Material>();

        return results;
    }
}
