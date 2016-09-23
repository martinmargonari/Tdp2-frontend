package com.example.margonari.tdp2_frontend.services;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luis on 19/09/16.
 */
public abstract class AbstractServices {

    public static final String urlBase="http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/";
    protected String api_security;

    protected Object geDataOftDTO(String url, Class object) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(url, object);
    }

    protected  abstract String getQueryBy(String... params);

    public String getApi_security() {
        return api_security;
    }

    public void setApi_security(String api_security) {
        this.api_security = api_security;
    }
}
