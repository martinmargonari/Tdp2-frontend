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
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapterFinished;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.services.CourseFullDataServices;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCoursesFinishedFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_TOKEN = "API_TOKEN";
    private static final String LIST_FINISHEDS = "LIST_FINISHEDS";

    private String api_token;
    private ArrayList<Course> coursesList;


    private RecyclerView coursesRecyclerView;
    private RecyclerView.Adapter coursesAdapter;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private static String LOG_TAG = "MyCoursesFinished";

    public MyCoursesFinishedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param api_token Parameter 1.
     * @param coursesList Parameter 2.
     * @return A new instance of fragment MyCoursesFinishedFragment.
     */
    public static MyCoursesFinishedFragment newInstance(String api_token, ArrayList<Course> coursesList) {
        MyCoursesFinishedFragment fragment = new MyCoursesFinishedFragment();
        Bundle args = new Bundle();
        args.putString(API_TOKEN, api_token);
        args.putSerializable(LIST_FINISHEDS, coursesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            api_token = getArguments().getString(API_TOKEN);
            coursesList = (ArrayList<Course>) getArguments().getSerializable(LIST_FINISHEDS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_courses_finished, container, false);
        coursesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_my_courses_finished);
        coursesRecyclerView.setHasFixedSize(true);
        coursesLayoutManager = new LinearLayoutManager(getContext());
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerView.setFocusable(false);
        if (coursesList.size() > 0) {
            coursesAdapter = new CoursesAdapterFinished(coursesList);
            coursesRecyclerView.setAdapter(coursesAdapter);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (coursesAdapter != null)
            ((CoursesAdapterFinished) coursesAdapter).setOnItemClickListener(new CoursesAdapterFinished
                    .MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Course course = coursesList.get(position);


                }
        });
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
                Log.e("MyCoursesFinished", e.getMessage(), e);
            }

            return null;
        }


    }



}
