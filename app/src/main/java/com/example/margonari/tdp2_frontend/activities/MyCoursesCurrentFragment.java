package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.services.CourseFullDataServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCoursesCurrentFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_TOKEN = "API_TOKEN";
    private static final String LIST_CURRENTS = "LIST_CURRENTS";

    private String api_token;
    private ArrayList<Course> coursesList;


    private RecyclerView coursesRecyclerView;
    private RecyclerView.Adapter coursesAdapter;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private static String LOG_TAG = "MyCoursesCurrent";

    public MyCoursesCurrentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param api_token Parameter 1.
     * @param coursesList Parameter 2.
     * @return A new instance of fragment MyCoursesCurrentFragment.
     */
    public static MyCoursesCurrentFragment newInstance(String api_token, ArrayList<Course> coursesList) {
        MyCoursesCurrentFragment fragment = new MyCoursesCurrentFragment();
        Bundle args = new Bundle();
        args.putString(API_TOKEN, api_token);
        args.putSerializable(LIST_CURRENTS, coursesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            api_token = getArguments().getString(API_TOKEN);
            coursesList = (ArrayList<Course>) getArguments().getSerializable(LIST_CURRENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_courses_current, container, false);
        coursesRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_my_courses_current);
        coursesRecyclerView.setHasFixedSize(true);
        coursesLayoutManager = new LinearLayoutManager(getContext());
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesRecyclerView.setFocusable(false);
        coursesAdapter = new CoursesAdapter(coursesList);
        coursesRecyclerView.setAdapter(coursesAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CoursesAdapter) coursesAdapter).setOnItemClickListener(new CoursesAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Course course = coursesList.get(position);

                MyCoursesCurrentFragment.HttpRequestTask httpRequestTask= new MyCoursesCurrentFragment.HttpRequestTask();
                httpRequestTask.execute(course.getId());
                try {
                    Course courseFullData= httpRequestTask.get();
                    courseFullData.setSession_id(course.getSession_id());
                    Intent intent = new Intent(getContext(), MyCourseParentActivity.class);
                    intent.putExtra("API_TOKEN", api_token);
                    intent.putExtra("COURSE_FULL_DATA", courseFullData);
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

    private class HttpRequestTask extends AsyncTask<String, Void, Course> {
        @Override
        protected Course doInBackground(String... params) {
            try {
                String course_id = params[0];
                CourseFullDataServices courseFullDataServices= new CourseFullDataServices();
                courseFullDataServices.setApi_security(api_token);
                return courseFullDataServices.getCourseBy(course_id);

            } catch (Exception e) {
                Log.e("MyCoursesCurrent", e.getMessage(), e);
            }

            return null;
        }


    }
  
}
