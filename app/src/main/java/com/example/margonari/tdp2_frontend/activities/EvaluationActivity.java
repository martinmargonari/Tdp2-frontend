package com.example.margonari.tdp2_frontend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.QuestionAdapter;
import com.example.margonari.tdp2_frontend.adapters.UnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Question;
import com.example.margonari.tdp2_frontend.domain.Unit;

import java.util.ArrayList;

public class EvaluationActivity extends AppCompatActivity {

    private RecyclerView questionsRecyclerView;
    private RecyclerView.Adapter questionsAdapter;
    private RecyclerView.LayoutManager questionsLayoutManager;

    private static String LOG_TAG = "EvaluationActivity";
    private ArrayList<Question> questionsList;
    private String api_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);

        questionsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_questions);
        questionsRecyclerView.setHasFixedSize(true);
        questionsLayoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(questionsLayoutManager);
        questionsRecyclerView.setFocusable(false);
        questionsAdapter = new QuestionAdapter(getDataSetQuestions());
        questionsRecyclerView.setAdapter(questionsAdapter);
    }

    private ArrayList<Question> getDataSetQuestions() {
        //TODO
        ArrayList results = new ArrayList<Question>();

        return results;
    }
}
