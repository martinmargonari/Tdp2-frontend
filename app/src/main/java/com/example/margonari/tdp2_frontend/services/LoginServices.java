package com.example.margonari.tdp2_frontend.services;

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
        String loginQuery = getLoginQueryBy(user);
        LoginDTO loginDTO = (LoginDTO) geDataOftDTO(loginQuery, LoginDTO.class);
        return loginDTO.getData();
    }

    private String getLoginQueryBy(String user) {
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
