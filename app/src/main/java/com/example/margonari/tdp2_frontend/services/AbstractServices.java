package com.example.margonari.tdp2_frontend.services;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luis on 19/09/16.
 */
public abstract class AbstractServices {

    public static final String urlBase="http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/";
    public static final String api_security="85d8b4ccd607dde1753fa9293d694c03";


    protected Object geDataOftDTO(String url, Class object) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(url, object);
    }

    protected  abstract String getQueryBy(String... params);
}
