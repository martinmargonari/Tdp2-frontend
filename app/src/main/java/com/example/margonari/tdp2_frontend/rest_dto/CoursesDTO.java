package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 12/09/16.
 */
public class CoursesDTO extends AbstractDTO{

    public ArrayList<Course> getData() { return (ArrayList<Course>) data; }

    public void setData(List<Course> data) {
        this.data = data;
    }
}
