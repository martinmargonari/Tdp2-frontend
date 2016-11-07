package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.MyCourseBox;
import com.example.margonari.tdp2_frontend.rest_dto.CourseBoxDTO;

import java.util.ArrayList;

/**
 * Created by luis on 26/09/16.
 */

public class ListMyCoursesServices extends AbstractServices{

    private static final String service_name = "courses/currents";

    public ArrayList<Course> getListCoursesBy() {
        String coursesQuery = this.getQueryBy();
        Log.d("Query "+this.getClass().getName(),coursesQuery);
        CourseBoxDTO coursesDTO = (CourseBoxDTO) geDataOftDTO(coursesQuery, CourseBoxDTO.class);
        ArrayList<Course> listCourses= new ArrayList<>( );

        ArrayList<MyCourseBox> listCourseLoco= coursesDTO.getData()[0];
        for(  MyCourseBox myCourse_box : listCourseLoco){
            myCourse_box.getCourse().setSession_id(myCourse_box.getId());
            listCourses.add( myCourse_box.getCourse());
        }
        return listCourses;
    }

    @Override
    protected String getQueryBy(String... params) {
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        return urlStringBuffer.toString();
    }
}
