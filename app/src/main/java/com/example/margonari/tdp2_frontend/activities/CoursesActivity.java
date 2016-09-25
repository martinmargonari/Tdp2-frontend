package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class CoursesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CoursesActivity";
    private  ArrayList<Course> coursesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Intent intent = getIntent();
        coursesList= (ArrayList<Course>)intent.getSerializableExtra("LIST_CATEGORIES");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_courses);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CoursesAdapter(coursesList);
        mRecyclerView.setAdapter(mAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
        ((CoursesAdapter) mAdapter).setOnItemClickListener(new CoursesAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Course obj = coursesList.get(position);
                //obj.
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }



    private ArrayList<Course> getDataSet() {

        return this.coursesList;
    }
}
