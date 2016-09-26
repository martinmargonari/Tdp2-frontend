package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.CoursesAdapter;
import com.example.margonari.tdp2_frontend.adapters.ProfessorAdapter;
import com.example.margonari.tdp2_frontend.adapters.UnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.domain.Professor;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.example.margonari.tdp2_frontend.services.LoginServices;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class CourseChooseActivity extends AppCompatActivity {

    private RecyclerView unitsRecyclerView;
    private RecyclerView.Adapter unitsAdapter;
    private RecyclerView.LayoutManager unitsLayoutManager;

    private RecyclerView professorsRecyclerView;
    private RecyclerView.Adapter professorsAdapter;
    private RecyclerView.LayoutManager professorsLayoutManager;
    
    private static String LOG_TAG = "CourseChooseActivity";
    private  ArrayList<Unit> unitsList;
    private String api_token;
    private Course courseFullData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_choose);

        Intent intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        courseFullData= (Course)intent.getSerializableExtra("COURSE_FULL_DATA");



        TextView nameCourseTextView = (TextView)findViewById(R.id.name_course_choose);
        nameCourseTextView.setText(courseFullData.getName());

        TextView descriptionTextView = (TextView)findViewById(R.id.course_choose_description);
        descriptionTextView.setText(courseFullData.getDescription());


        unitsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_units);
        unitsRecyclerView.setHasFixedSize(true);
        unitsLayoutManager = new LinearLayoutManager(this);
        unitsRecyclerView.setLayoutManager(unitsLayoutManager);
        unitsRecyclerView.setFocusable(false);
        unitsAdapter = new UnitAdapter(getDataSetUnits());
        unitsRecyclerView.setAdapter(unitsAdapter);

        professorsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_professors);
        professorsRecyclerView.setHasFixedSize(true);
        professorsLayoutManager = new LinearLayoutManager(this);
        professorsRecyclerView.setLayoutManager(professorsLayoutManager);
        professorsRecyclerView.setFocusable(false);
        professorsAdapter = new ProfessorAdapter(getDataSetProfessors());
        professorsRecyclerView.setAdapter(professorsAdapter);
    }

    private ArrayList<Unit> getDataSetUnits() {
        ArrayList results = new ArrayList<Unit>();
        if (courseFullData.getUnities().size() != 0){
            for (int index = 0; index < courseFullData.getUnities().size(); index++) {
                Unit obj = new Unit(courseFullData.getUnities().get( index ).getName(), R.drawable.arte);
                results.add(obj);
            }
        }
        return results;
    }

    private ArrayList<Professor> getDataSetProfessors() {
        ArrayList results = new ArrayList<Professor>();
        results.add(new Professor("Nombre Profesor", true));
        for (int index = 0; index < 5; index++) {
            Professor obj = new Professor("Nombre Asistente", false);
            results.add(obj);
        }
        return results;
    }

    public void inscribirse(View view){
        HttpRequestTask httpRequestTask= new HttpRequestTask();
        //TODO Ver el tema de la consulta al server para la inscripcion
        httpRequestTask.execute( "ACA VA EL SESSION ID QUE NOS DIGAN" );
    }

    //TODO arreglar con el session id que nos pase leandro.
    private class HttpRequestTask extends AsyncTask<String, Void, Login> {
        @Override
        protected Login doInBackground(String... params) {
            try {
                String sessionId = params[0];
                 Login login=  new LoginServices().getLoginBy(sessionId);
                return login;
            } catch (Exception e) {
                Log.e("CourseChooseActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
