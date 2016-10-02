package com.example.margonari.tdp2_frontend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ProfessorAdapter;
import com.example.margonari.tdp2_frontend.domain.Professor;

import java.util.ArrayList;

public class CourseInformationActivity extends AppCompatActivity {

    private RecyclerView professorsRecyclerView;
    private RecyclerView.LayoutManager professorsLayoutManager;
    private RecyclerView.Adapter professorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_information);

        professorsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_professors_course);
        professorsRecyclerView.setHasFixedSize(true);
        professorsLayoutManager = new LinearLayoutManager(this);
        professorsRecyclerView.setLayoutManager(professorsLayoutManager);
        professorsRecyclerView.setFocusable(false);
        professorsAdapter = new ProfessorAdapter(getDataSetProfessors());
        professorsRecyclerView.setAdapter(professorsAdapter);
    }

    private ArrayList<Professor> getDataSetProfessors() {
        //TODO
        ArrayList results = new ArrayList<Professor>();
        /*
        results.add(new Professor(courseFullData.getUsers().get( 0 ).getName()+courseFullData.getUsers().get( 0 ).getLast_name(), true));
        for (int index = 0; index < 2; index++) {
            Professor obj = new Professor("Nombre Asistente", false);
            results.add(obj);
        }*/
        return results;
    }

}
