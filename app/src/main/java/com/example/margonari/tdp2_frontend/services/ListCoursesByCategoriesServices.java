package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;

import java.util.List;

/**
 * Created by luis on 23/09/16.
 */
public class ListCoursesByCategoriesServices extends AbstractServices {
    private static final String service_name = "coursesByCategories";


    public List<Course> getListCoursesBy(String courseCategorie) {
        String coursesQuery = this.getQueryBy(courseCategorie);
        CoursesDTO coursesDTO = (CoursesDTO) geDataOftDTO(coursesQuery, CoursesDTO.class);
        return coursesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {
        String courseCategory = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&category=");
        urlStringBuffer.append(courseCategory);

        return urlStringBuffer.toString();
    }
}