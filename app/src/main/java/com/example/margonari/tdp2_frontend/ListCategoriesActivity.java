package com.example.margonari.tdp2_frontend;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.rest_dto.CategoriesDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ListCategoriesActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        new HttpRequestTask().execute();
    }

        private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Categoria> > {
            CategoriesDTO categoriesDTO;
            @Override
            protected ArrayList<Categoria> doInBackground(Void... params) {
                try {
                    final String url = getResources().getString(R.string.category_services_address);;
                     categoriesDTO = (CategoriesDTO) geDataOftDTO(url, CategoriesDTO.class);
                    return categoriesDTO.getData();
                } catch (Exception e) {
                    Log.e("ListCategories", e.getMessage(), e);
                }

                return null;
            }


            private Object geDataOftDTO(String url, Class object) {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, object);
            }

            @Override
            protected void onPostExecute(ArrayList<Categoria> greeting) {
                // specify an adapter (see also next example)
                mAdapter = new CategoryAdapter(greeting);
                mRecyclerView.setAdapter(mAdapter);
            }

        }


    }

