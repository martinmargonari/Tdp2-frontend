package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.ArrayList;

public class MyCoursesActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private Spinner spinnerCoursesType;
    private Intent intent;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "MyCoursesActivity";
    private  ArrayList<Course> coursesList;
    private String api_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);

        spinnerCoursesType = (Spinner) findViewById(R.id.spinner_type_of_courses);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_courses_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCoursesType.setAdapter(adapter);
        spinnerCoursesType.setOnItemSelectedListener(this);


        intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        System.out.println("APITOKEN EN MY COURSES: "+ api_token);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_courses);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        switch (position) {
            case 0:
                // CURSOS ACTUALES
                coursesList= (ArrayList<Course>)intent.getSerializableExtra("LIST_CATEGORIES");
                //TODO Pedir la info según lo que corresponda, por el momento en esta solapa poner
                // unicamente lo que devuelva la request de mis cursos, luego agregaremos mas logica
                mAdapter = new CoursesAdapter(coursesList);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case 1:
                // CURSOS FUTUROS
                break;
            case 2:
                // CURSOS PASADOS
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
