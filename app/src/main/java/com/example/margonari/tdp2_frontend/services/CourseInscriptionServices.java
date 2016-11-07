package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;

/**
 * Created by luis on 26/09/16.
 */

public class CourseInscriptionServices extends AbstractServices {
    private static final String service_name = "course/register";

    public boolean ifExistsErrors(String course_id) {
        String coursesQuery = this.getQueryBy(course_id);
        Log.d("Query "+this.getClass().getName(),coursesQuery);
        EmptyDTO coursesDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);
        return  !coursesDTO.getErrors()[0].isEmpty() ;
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
