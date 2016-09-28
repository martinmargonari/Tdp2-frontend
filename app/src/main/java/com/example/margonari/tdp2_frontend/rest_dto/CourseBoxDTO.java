package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.MyCourseBox;

import java.util.ArrayList;

/**
 * Created by luis on 26/09/16.
 */

public class CourseBoxDTO extends AbstractDTO {

    public ArrayList<MyCourseBox>[] getData() { return (ArrayList<MyCourseBox>[]) data; }

    public void setData(ArrayList<MyCourseBox>[] data) {
        this.data = data;
    }
}
