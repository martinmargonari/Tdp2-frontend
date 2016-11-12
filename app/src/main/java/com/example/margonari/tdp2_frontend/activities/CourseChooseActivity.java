package com.example.margonari.tdp2_frontend.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.ProfessorAdapter;
import com.example.margonari.tdp2_frontend.adapters.UnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Professor;
import com.example.margonari.tdp2_frontend.domain.Unit;
import com.example.margonari.tdp2_frontend.services.CourseInscriptionServices;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
        //final Button inscribirse = (Button) findViewById(R.id.button_inscribirse);

        Intent intent = getIntent();
        api_token = getIntent().getStringExtra("API_TOKEN");
        courseFullData= (Course)intent.getSerializableExtra("COURSE_FULL_DATA");

        this.setTitle(courseFullData.getName());

        ImageView imageCourse = (ImageView) findViewById(R.id.image_course_choose);
        String urlImage = getResources().getString(R.string.imagesURL) + courseFullData.getId() + "." + courseFullData.getFile_extension();
        Picasso.with(this).load(urlImage).into(imageCourse);


        TextView nameProfessorTextView = (TextView)findViewById(R.id.professor_course_choose);

        for (Professor professor:courseFullData.getUsers()) {
            if(professor.isProfessor()) {
                nameProfessorTextView.setText(StringUtils.capitalize (professor.getFullName()));
            }
            System.out.println(professor.getType() + ": " + StringUtils.capitalize(professor.getFullName()));
        }

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
        professorsAdapter = new ProfessorAdapter((ArrayList)courseFullData.getUsers());
        professorsRecyclerView.setAdapter(professorsAdapter);
    }

    private ArrayList<Unit> getDataSetUnits() {
        ArrayList results = new ArrayList<Unit>(courseFullData.getUnities());

        return results;
    }

    private ArrayList<Professor> getDataSetProfessors() {
        ArrayList results = new ArrayList<Professor>();

        for (Professor professor:courseFullData.getUsers()) {
            if(professor.isProfessor())results.add(professor);
        }
        for (Professor professor:courseFullData.getUsers()) {
            if(professor.isAssistent())results.add(professor);
        }

        return results;
    }

    public void inscribirse(View view){
        HttpRequestTask httpRequestTask= new HttpRequestTask();
        //TODO devolvemos la primera sesion de la lista, deberiamos devolver la mas proxima.

        httpRequestTask.execute( courseFullData.getNext_sessions().get(0).getId());
        Log.d("SESSION ID : ",courseFullData.getNext_sessions().get(0).getId().toString());
        try {
            Boolean ifExistsErrors= (Boolean) httpRequestTask.get();
            if(ifExistsErrors!=null && ifExistsErrors==true) {Toast.makeText( CourseChooseActivity.this,"Hay un error en la inscripcion , intente mas tarde",Toast.LENGTH_SHORT).show();}
            else{Toast.makeText( CourseChooseActivity.this,"Inscripcion realizada con exito",Toast.LENGTH_SHORT).show();}
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class HttpRequestTask extends AsyncTask<String, Void, Boolean> {
        Boolean result;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                String sessionId = params[0];


                CourseInscriptionServices courseInscriptionServices=new CourseInscriptionServices();
                courseInscriptionServices.setApi_security(api_token);
                 boolean ifExistsErrors=  courseInscriptionServices.ifExistsErrors(sessionId);
                 result= new Boolean( ifExistsErrors );

            } catch (Exception e) {
                Log.e("CourseChooseActivity", e.getMessage(), e);
            }

            return result;
        }

    }
}
