package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.rest_dto.UnitiesDTO;
import com.example.margonari.tdp2_frontend.rest_dto.UnitiesElementDTO;

import java.util.List;

/**
 * Created by luis on 15/10/16.
 */

public class CourseUnitiesServices extends AbstractServices {
    private static final String service_name = "course/unities";

    public List<UnitiesElementDTO> getUnities(String courseId) {
        String coursesQuery = this.getQueryBy(courseId);
        UnitiesDTO unitiesDTO = (UnitiesDTO) geDataOftDTO(coursesQuery, UnitiesDTO.class);
        return unitiesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {
        String course_id = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&course_id=");
        urlStringBuffer.append(course_id);
        System.out.println(urlStringBuffer.toString());
        return urlStringBuffer.toString();
    }
}
