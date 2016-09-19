package com.example.margonari.tdp2_frontend.activities;

import android.support.annotation.NonNull;

import com.example.margonari.tdp2_frontend.domain.IRestEntity;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by luis on 18/09/16.
 */
public class HttpRequestTaskImpl  {
     private static final String baseUrl= "http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/";
     private static final String api_security= "85d8b4ccd607dde1753fa9293d694c03";

    @NonNull
    public static String getQueryBy(IRestEntity restEntity, String... params ) {
        String service= params[0];
        return restEntity.getQueryByParams(baseUrl,service,api_security,params);

    }

    public static Object geDataOftDTO(String url, Class object) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return restTemplate.getForObject(url, object);
    }
}
