package com.example.margonari.tdp2_frontend.activities;

import android.content.Context;
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
import com.example.margonari.tdp2_frontend.adapters.ForumThreadAdapter;
import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.example.margonari.tdp2_frontend.services.ForumTheadsServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MyCourseForumThreadFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_TOKEN = "API_TOKEN";
    private static final String CATEGORY_ID = "CATEGORY_ID";

    private String api_token;
    private String categoryID;

    private RecyclerView forumThreadsRecyclerView;
    private RecyclerView.LayoutManager forumThreadsLayoutManager;
    private RecyclerView.Adapter forumThreadsAdapter;
    private ArrayList<ForumThread> forumThreadArrayList;
    
    public MyCourseForumThreadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param api_token Parameter 1.
     * @param categoryID Parameter 2.
     * @return A new instance of fragment MyCourseForumThreadFragment.
     */
    public static MyCourseForumThreadFragment newInstance(String api_token, String categoryID) {
        MyCourseForumThreadFragment fragment = new MyCourseForumThreadFragment();
        Bundle args = new Bundle();
        args.putString(API_TOKEN, api_token);
        args.putString(CATEGORY_ID, categoryID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            api_token = getArguments().getString(API_TOKEN);
            categoryID = getArguments().getString(CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_my_course_forum_thread, container, false);

        forumThreadsRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_my_course_forum_threads);
        forumThreadsRecyclerView.setHasFixedSize(true);
        forumThreadsLayoutManager = new LinearLayoutManager(getContext());
        forumThreadsRecyclerView.setLayoutManager(forumThreadsLayoutManager);
        forumThreadsRecyclerView.setFocusable(false);
        forumThreadArrayList = getDataSetForumThreads();
        forumThreadsAdapter = new ForumThreadAdapter(forumThreadArrayList);
        forumThreadsRecyclerView.setAdapter(forumThreadsAdapter);

        return fragmentView;
    }

    private ArrayList<ForumThread> getDataSetForumThreads() {
        HttpRequestTaskForumThreads httpRequestTaskForumThreads= new HttpRequestTaskForumThreads();
        httpRequestTaskForumThreads.execute();
        ArrayList<ForumThread> listThreads = new ArrayList<>();
        try {
            listThreads = httpRequestTaskForumThreads.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return listThreads;
    }

    private class HttpRequestTaskForumThreads extends AsyncTask<String, Void, ArrayList<ForumThread>> {

        ArrayList<ForumThread> listThreads;
        @Override
        protected ArrayList<ForumThread> doInBackground(String... params) {
            try {
                ForumTheadsServices forumCategoriesServices= new ForumTheadsServices();
                forumCategoriesServices.setApi_security(api_token);
                listThreads = (ArrayList<ForumThread>) forumCategoriesServices.getListTheadsBy(categoryID);
                return listThreads;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }
 
}
