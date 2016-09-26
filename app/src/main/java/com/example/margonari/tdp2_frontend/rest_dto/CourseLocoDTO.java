package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.Course_Loco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 26/09/16.
 */

public class CourseLocoDTO extends AbstractDTO {

    public ArrayList<Course_Loco>[] getData() { return (ArrayList<Course_Loco>[]) data; }

    public void setData(ArrayList<Course_Loco>[] data) {
        this.data = data;
    }
}
