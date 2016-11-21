package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;

import java.io.UnsupportedEncodingException;

/**
 * Created by luis on 14/11/16.
 */


//http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/
// api/receive-notifications?
// api_token=70rSdy8qjufNl6edymVac0uuMCuGa9ZeenISjkSnjizoxhZ1bEXZZG5cUjdf&has_notifications=false
public class NotificationsOnOffServices extends AbstractServices{

    private static final String service_name = "receive-notifications";


    public Boolean setNotification(String has_notifications) throws UnsupportedEncodingException {
        String coursesQuery = this.getQueryBy(has_notifications);

        Log.d("Query "+this.getClass().getName(),coursesQuery);

        EmptyDTO emptyDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);
        return true;
    }

    @Override
    protected String getQueryBy(String... params) throws UnsupportedEncodingException {
        String has_notifications = params[0];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&has_notifications=");
        urlStringBuffer.append(has_notifications);


        Log.d("Notifications",urlStringBuffer.toString());
        return urlStringBuffer.toString();

    }
}
