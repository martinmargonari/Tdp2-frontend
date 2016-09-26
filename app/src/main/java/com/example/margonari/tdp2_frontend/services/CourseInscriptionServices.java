package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.rest_dto.CoursesDTO;
import com.example.margonari.tdp2_frontend.rest_dto.InscriptionDTO;

/**
 * Created by luis on 26/09/16.
 */

public class CourseInscriptionServices extends AbstractServices {
    private static final String service_name = "courses/registration";

    public boolean ifExistsErrors(String course_id) {
        String coursesQuery = this.getQueryBy(course_id);
        System.out.println("Course query" +coursesQuery);
        InscriptionDTO coursesDTO = (InscriptionDTO) geDataOftDTO(coursesQuery, InscriptionDTO.class);
        return coursesDTO.getErrors().get( 0 ).length()>0;
    }


    @Override
    protected String getQueryBy(String... params) {
        String session_id = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&session_id=");
        urlStringBuffer.append(session_id);

        return urlStringBuffer.toString();
    }
}
