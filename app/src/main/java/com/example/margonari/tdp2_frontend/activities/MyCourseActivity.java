package com.example.margonari.tdp2_frontend.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.adapters.MyCourseUnitAdapter;
import com.example.margonari.tdp2_frontend.adapters.UnitAdapter;
import com.example.margonari.tdp2_frontend.domain.Unit;

import java.util.ArrayList;

public class MyCourseActivity extends AppCompatActivity {

    private RecyclerView unitsRecyclerView;
    private RecyclerView.LayoutManager unitsLayoutManager;
    private RecyclerView.Adapter unitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_course);

        unitsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_course_units);
        unitsRecyclerView.setHasFixedSize(true);
        unitsLayoutManager = new LinearLayoutManager(this);
        unitsRecyclerView.setLayoutManager(unitsLayoutManager);
        unitsRecyclerView.setFocusable(false);
        unitsAdapter = new MyCourseUnitAdapter(getDataSetUnits());
        unitsRecyclerView.setAdapter(unitsAdapter);

    }

    private ArrayList<Unit> getDataSetUnits() {
        //TODO
        ArrayList results = new ArrayList<Unit>();
        /*if (courseFullData.getUnities().size() != 0){
            for (int index = 0; index < courseFullData.getUnities().size(); index++) {
                Unit obj = new Unit(courseFullData.getUnities().get( index ).getName(), R.drawable.arte);
                results.add(obj);
            }
        }*/

        return results;
    }
}
