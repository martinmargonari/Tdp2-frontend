package com.example.margonari.tdp2_frontend.activities;

import android.support.annotation.NonNull;

import com.example.margonari.tdp2_frontend.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luis on 18/09/16.
 */
public class HttpRequestTaskImpl  {
     public static final String url= "http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/login";

    @NonNull
    public static String getLoginQueryBy(String user) {
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_security=");
        urlStringBuffer.append("85d8b4ccd607dde1753fa9293d694c03");
        urlStringBuffer.append("&email=");
        urlStringBuffer.append(user);

        return urlStringBuffer.toString();
    }

    public static Object geDataOftDTO(String url, Class object) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(url, object);
    }
}
