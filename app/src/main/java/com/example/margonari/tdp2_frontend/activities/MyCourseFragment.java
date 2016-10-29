package com.example.margonari.tdp2_frontend.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.example.margonari.tdp2_frontend.adapters.MyCourseUnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.services.UnitServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_TOKEN = "API_TOKEN";
    private static final String COURSE_FULL_DATA = "COURSE_FULL_DATA";

    private String api_token;
    private Course courseFullData;

    private RecyclerView unitsRecyclerView;
    private RecyclerView.LayoutManager unitsLayoutManager;
    private RecyclerView.Adapter unitsAdapter;
    private ArrayList<Unit> unitArrayList;

    public MyCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param api_token Parameter 1.
     * @param courseFullData Parameter 2.
     * @return A new instance of fragment MyCourseFragment.
     */
    public static MyCourseFragment newInstance(String api_token, Course courseFullData) {
        MyCourseFragment fragment = new MyCourseFragment();
        Bundle args = new Bundle();
        args.putString(API_TOKEN, api_token);
        args.putSerializable(COURSE_FULL_DATA, courseFullData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            api_token = getArguments().getString(API_TOKEN);
            courseFullData = (Course) getArguments().getSerializable(COURSE_FULL_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_my_course, container, false);

        unitsRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_my_course_units);
        unitsRecyclerView.setHasFixedSize(true);
        unitsLayoutManager = new LinearLayoutManager(getContext());
        unitsRecyclerView.setLayoutManager(unitsLayoutManager);
        unitsRecyclerView.setFocusable(false);
        unitsAdapter = new MyCourseUnitAdapter(getDataSetUnits());
        unitsRecyclerView.setAdapter(unitsAdapter);
        unitArrayList= (ArrayList)courseFullData.getUnities();

        return fragmentView;
    }

    private ArrayList<Unit> getDataSetUnits() {
        return (ArrayList<Unit>) courseFullData.getUnities();

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyCourseUnitAdapter) unitsAdapter).setOnItemClickListener(new MyCourseUnitAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Unit unit = unitArrayList.get(position);
                MyCourseFragment.HttpRequestTaskUnity httpRequestTask = new MyCourseFragment.HttpRequestTaskUnity();
                httpRequestTask.execute(unit.getId());
                try {
                    UnityInfo unityInfo = (UnityInfo) httpRequestTask.get();
                    Intent intent = new Intent(getContext(), MyCourseUnitActivity.class);
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


}
