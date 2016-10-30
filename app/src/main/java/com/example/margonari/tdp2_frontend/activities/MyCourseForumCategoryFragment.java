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
import com.example.margonari.tdp2_frontend.adapters.ForumCategoryAdapter;
import com.example.margonari.tdp2_frontend.domain.ForumCategory;
import com.example.margonari.tdp2_frontend.services.ForumCategoriesServices;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MyCourseForumCategoryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String API_TOKEN = "API_TOKEN";
    private static final String COURSE_ID = "COURSE_ID";

    private String api_token;
    private String courseID;

    private RecyclerView forumCategoriesRecyclerView;
    private RecyclerView.LayoutManager forumCategoriesLayoutManager;
    private RecyclerView.Adapter forumCategoriesAdapter;
    private ArrayList<ForumCategory> forumCategoryArrayList;


    public MyCourseForumCategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param api_token Parameter 1.
     * @param courseID Parameter 2.
     * @return A new instance of fragment MyCourseForumCategoryFragment.
     */
    public static MyCourseForumCategoryFragment newInstance(String api_token, String courseID) {
        MyCourseForumCategoryFragment fragment = new MyCourseForumCategoryFragment();
        Bundle args = new Bundle();
        args.putString(API_TOKEN, api_token);
        args.putString(COURSE_ID, courseID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            api_token = getArguments().getString(API_TOKEN);
            courseID = getArguments().getString(COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_my_course_forum_category, container, false);

        forumCategoriesRecyclerView = (RecyclerView) fragmentView.findViewById(R.id.recycler_view_my_course_forum_categories);
        forumCategoriesRecyclerView.setHasFixedSize(true);
        forumCategoriesLayoutManager = new LinearLayoutManager(getContext());
        forumCategoriesRecyclerView.setLayoutManager(forumCategoriesLayoutManager);
        forumCategoriesRecyclerView.setFocusable(false);
        forumCategoryArrayList = getDataSetForumCategories();
        Log.d("Size de categorias", String.valueOf(forumCategoryArrayList.size()));

        forumCategoriesAdapter = new ForumCategoryAdapter(forumCategoryArrayList);
        forumCategoriesRecyclerView.setAdapter(forumCategoriesAdapter);

        return fragmentView;
    }

    private ArrayList<ForumCategory> getDataSetForumCategories() {
        HttpRequestTaskForumCategories httpRequestTaskForumCategories= new HttpRequestTaskForumCategories();
        httpRequestTaskForumCategories.execute();
        ArrayList<ForumCategory> listCategories= new ArrayList<>();
        try {
            listCategories= httpRequestTaskForumCategories.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return listCategories;
    }


    private class HttpRequestTaskForumCategories extends AsyncTask<String, Void, ArrayList<ForumCategory>> {

        ArrayList<ForumCategory> listCategories;
        @Override
        protected ArrayList<ForumCategory> doInBackground(String... params) {
            try {
                ForumCategoriesServices forumCategoriesServices = new ForumCategoriesServices();
                forumCategoriesServices.setApi_security(api_token);
                listCategories = (ArrayList<ForumCategory>) forumCategoriesServices.getListCategoriesBy(courseID);
                return listCategories;
            } catch (Exception e) {
                Log.e("LoginActivity", e.getMessage(), e);
            }

            return null;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        ((ForumCategoryAdapter) forumCategoriesAdapter).setOnItemClickListener(new ForumCategoryAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                ForumCategory forumCategory = forumCategoryArrayList.get(position);
                String categoryID = forumCategory.getCategory_id();
                Intent intent = new Intent(getContext(), MyCourseForumThreadActivity.class);
                intent.putExtra("API_TOKEN", api_token);
                intent.putExtra("CATEGORY_ID", categoryID);
                startActivity(intent);

                Log.i("LOG_TAG", " Clicked on Item " + position);
            }
        });
    }

}
