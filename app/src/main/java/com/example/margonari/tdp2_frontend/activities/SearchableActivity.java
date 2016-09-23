package com.example.margonari.tdp2_frontend.activities;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class SearchableActivity extends ListActivity {

    private String api_token;

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

        HttpRequestTask httpRequestTask = new HttpRequestTask();
        httpRequestTask.execute(query, api_token);

        ArrayList<String>  values = null;
        try {
            List<Course> courseList= httpRequestTask.get();
            values = getValuesFromListCourse(courseList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /*String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2" };*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    private ArrayList<String> getValuesFromListCourse(List<Course> courseList) {
        ArrayList<String> coursesNames = new ArrayList<>();
        for (Course course: courseList) {
            coursesNames.add(course.getName());

        }

        return coursesNames;
    }

    private class HttpRequestTask extends AsyncTask<String, Void, List<Course>> {
        @Override
        protected List<Course> doInBackground(String... params) {
            try {
                String user = params[0];
                String coursesQuery = getCoursesQuery(user);
                CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
                return coursesDTO.getData();
            } catch (Exception e) {
                Log.e("SearcheableAcivity", e.getMessage(), e);
            }

            return null;
        }

        @NonNull
        private String getCoursesQuery(String courseName) {
            String url = getResources().getString(R.string.courses_services_address);
            StringBuffer urlStringBuffer = new StringBuffer(url);
            urlStringBuffer.append("?");
            urlStringBuffer.append("api_token=");
            urlStringBuffer.append(api_token);
            urlStringBuffer.append("&name=");
            urlStringBuffer.append(courseName);

            return urlStringBuffer.toString();
        }


        private Object geDataOftDTO(String url, Class object) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            return restTemplate.getForObject(url, object);
        }
    }


}
