package com.example.margonari.tdp2_frontend.services;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.rest_dto.LoginDTO;

/**
 * Created by luis on 19/09/16.
 */
public class LoginServices extends AbstractServices{
    private static final String service_name="login";
    public static final String api_security="85d8b4ccd607dde1753fa9293d694c03";

    public Login getLoginBy(String user, String token,String image_url,String name) {
        String loginQuery = this.getQueryBy(user,token,image_url,name);
        Log.d("Query "+this.getClass().getName(),loginQuery);
        LoginDTO loginDTO = (LoginDTO) geDataOftDTO(loginQuery, LoginDTO.class);
        return loginDTO.getData();
    }

    @NonNull
    protected String getQueryBy(String... params) {
        String user=params[0];
        String push_id=params[1];
        String image_url=params[2];
        String name=params[3];

        Log.d("LoginServices_PushId", push_id);

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_security=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&email=");
        urlStringBuffer.append(user);
        urlStringBuffer.append("&push_id=");
        urlStringBuffer.append(push_id);
        urlStringBuffer.append("&image_url=");
        urlStringBuffer.append(image_url);
        urlStringBuffer.append("&name=");
        urlStringBuffer.append(name);



        return urlStringBuffer.toString();
    }



}
