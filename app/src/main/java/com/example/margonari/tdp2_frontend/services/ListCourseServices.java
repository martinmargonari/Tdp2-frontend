package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

import java.util.ArrayList;

/**
 * Created by luis on 19/09/16.
 * api/
 */
public class ListCourseServices extends AbstractServices {
    private static final String service_name = "courses";


    public ArrayList<Course> getListCoursesBy(String courseName) {
        String coursesQuery = this.getQueryBy(courseName);
        Log.d("Query "+this.getClass().getName(),coursesQuery);

        CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
        return coursesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {
        String courseName = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&name=");
        urlStringBuffer.append(courseName);

        return urlStringBuffer.toString();
    }
}

