package com.example.margonari.tdp2_frontend.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;
import com.example.margonari.tdp2_frontend.services.CourseFullDataServices;
import com.example.margonari.tdp2_frontend.services.ListCourseServices;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class SearchableActivity extends AppCompatActivity {

    private String api_token;
    private static String LOG_TAG = "SearchableActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Course> coursesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        api_token = intent.getStringExtra("API_TOKEN");
        System.out.println("APITOKEN SEARCHABLE: " + api_token);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            search(query);
        }
    }

    protected void search(String query) {

        HttpRequestTaskListCourses httpRequestTask = new HttpRequestTaskListCourses();
        httpRequestTask.execute(query, api_token);

        coursesList = null;
        try {
            coursesList = httpRequestTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

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
                HttpRequestTaskCourseData httpRequestTask = new HttpRequestTaskCourseData();
                httpRequestTask.execute(course.getId());
                try {
                    Course coursefulldata = (Course) httpRequestTask.get();
                    Intent intent = new Intent(SearchableActivity.this, CourseChooseActivity.class);
                    intent.putExtra("API_TOKEN", api_token);
                    intent.putExtra("COURSE_FULL_DATA", coursefulldata);
                    finish();
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }
        });
    }


    private class HttpRequestTaskListCourses extends AsyncTask<String, Void, ArrayList<Course>> {
        @Override
        protected ArrayList<Course> doInBackground(String... params) {
            try {
                String user = params[0];
                ListCourseServices listCourseServices = new ListCourseServices();
                listCourseServices.setApi_security(api_token);
                return listCourseServices.getListCoursesBy(user);

            } catch (Exception e) {
                Log.e("SearcheableAcivity", e.getMessage(), e);
            }

            return null;
        }
    }

    private class HttpRequestTaskCourseData extends AsyncTask<String, Void, Course> {
        @Override
        protected Course doInBackground(String... params) {
            try {
                String course_id = params[0];
                CourseFullDataServices courseFullDataServices = new CourseFullDataServices();
                courseFullDataServices.setApi_security(api_token);
                return courseFullDataServices.getCourseBy(course_id);

            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }

            return null;
        }
    }

}
