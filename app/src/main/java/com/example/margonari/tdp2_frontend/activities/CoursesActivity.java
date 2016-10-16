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
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.services.CourseFullDataServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CoursesActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CoursesActivity";
    private  ArrayList<Course> coursesList;
    private String api_token;
    private String category_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Intent intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        coursesList= (ArrayList<Course>)intent.getSerializableExtra("LIST_COURSES");
        category_name = intent.getStringExtra("CATEGORY_NAME");
        this.setTitle("Cursos de " + category_name);

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
                Course course = coursesList.get(position);
                HttpRequestTask httpRequestTask= new HttpRequestTask();
                httpRequestTask.execute(course.getId());
                try {
                    Course coursefulldata= (Course)httpRequestTask.get();
                    Intent intent = new Intent(CoursesActivity.this, CourseChooseActivity.class);
                    intent.putExtra("API_TOKEN", api_token);
                    intent.putExtra("COURSE_FULL_DATA", coursefulldata);
                    startActivity( intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }



    private ArrayList<Course> getDataSet() {

        return this.coursesList;
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Course> {
        @Override
        protected Course doInBackground(String... params) {
            try {
                String course_id = params[0];
                CourseFullDataServices courseFullDataServices= new CourseFullDataServices();
                courseFullDataServices.setApi_security(api_token);
                return courseFullDataServices.getCourseBy(course_id);

            } catch (Exception e) {
                Log.e("CourseAcivity", e.getMessage(), e);
            }

            return null;
        }


    }
}
