package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.MyCourseSessionBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 21/11/16.
 */

public class CourseSessionDTO extends AbstractDTO {
    public MyCourseSessionBox getData() { return (MyCourseSessionBox) data; }

    public void setData(MyCourseSessionBox data) {
        this.data = data;
    }
}
