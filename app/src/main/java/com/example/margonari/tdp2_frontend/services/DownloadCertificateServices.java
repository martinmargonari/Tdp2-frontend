package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.AbstractDTO;
import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;
import com.example.margonari.tdp2_frontend.rest_dto.UnitiesDTO;
import com.example.margonari.tdp2_frontend.rest_dto.UnitiesElementDTO;

import java.util.List;

import static com.example.margonari.tdp2_frontend.services.AbstractServices.urlBase;
import static com.example.margonari.tdp2_frontend.services.LoginServices.api_security;

/**
 * Created by luis on 14/11/16.
 */

public class DownloadCertificateServices extends AbstractServices {

    private static final String service_name = "course/certificate-pdf";

    public void download(String courseId) {
        String coursesQuery = this.getQueryBy(courseId);
        Log.d("Query "+this.getClass().getName(),coursesQuery);

        EmptyDTO unitiesDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);

    }

    @Override
    public String getQueryBy(String... params) {
        String session_id = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&session_id=");
        urlStringBuffer.append(session_id);
        System.out.println(urlStringBuffer.toString());
        return urlStringBuffer.toString();
    }
}
