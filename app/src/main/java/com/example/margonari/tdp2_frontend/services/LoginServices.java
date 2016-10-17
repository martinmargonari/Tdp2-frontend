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

    public Login getLoginBy(String user, String token) {
        String loginQuery = this.getQueryBy(user,token);
        LoginDTO loginDTO = (LoginDTO) geDataOftDTO(loginQuery, LoginDTO.class);
        return loginDTO.getData();
    }

    @NonNull
    protected String getQueryBy(String... params) {
        String user=params[0];
        String push_id=params[1];

        Log.d("LoginServices_PushId", push_id);

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_security=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&email=");
        urlStringBuffer.append(user);
        urlStringBuffer.append("push_id");
        urlStringBuffer.append(push_id);


        return urlStringBuffer.toString();
    }



}
