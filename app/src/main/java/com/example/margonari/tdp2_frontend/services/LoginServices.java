package com.example.margonari.tdp2_frontend.services;

import android.support.annotation.NonNull;

import com.example.margonari.tdp2_frontend.R;
import com.example.margonari.tdp2_frontend.domain.Login;
import com.example.margonari.tdp2_frontend.rest_dto.LoginDTO;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luis on 19/09/16.
 */
public class LoginServices extends AbstractServices{
    private static final String service_name="login";

    public Login getLoginBy(String user) {
        String loginQuery = this.getQueryBy(user);
        LoginDTO loginDTO = (LoginDTO) geDataOftDTO(loginQuery, LoginDTO.class);
        return loginDTO.getData();
    }

    @NonNull
    protected String getQueryBy(String... params) {
        String user=params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_security=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&email=");
        urlStringBuffer.append(user);

        return urlStringBuffer.toString();
    }



}
