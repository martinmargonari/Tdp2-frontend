package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 23/09/16.
 */
public class ListCoursesByCategoriesServices extends AbstractServices {
    private static final String service_name = "courses";


    public List<Course> getListCoursesBy(String courseCategory) {

        String coursesQuery = this.getQueryBy(courseCategory);
        Log.d("Query "+this.getClass().getName(),coursesQuery);

        CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
        return coursesDTO.getData();
    }

    public List<Course> getListCoursesWithActiveSessions(String courseCategory){

        List<Course> courseListWithActiveSessions= new ArrayList<>();
        List<Course> courseList= getListCoursesBy(courseCategory);
        for (Course course:courseList )
        {
                if(course.hasCurrentSessions())courseListWithActiveSessions.add(course);

        }
        Log.d("CoursesWithActiveSess",courseListWithActiveSessions.toString());

        return courseListWithActiveSessions;
    }

    @Override
    protected String getQueryBy(String... params) {


        String courseCategory = params[0];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&category_id=");
        urlStringBuffer.append(courseCategory);

        Log.d("AllCoursesInCategory",urlStringBuffer.toString());

        return urlStringBuffer.toString();
    }
}