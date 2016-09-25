package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 25/09/16.
 */

public class CourseDTO extends AbstractDTO{
    public Course getData() { return (Course) data; }

    public void setData(Course data) {
        this.data = data;
    }
}
