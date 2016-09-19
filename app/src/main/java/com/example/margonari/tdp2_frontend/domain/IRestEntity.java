package com.example.margonari.tdp2_frontend.domain;

/**
 * Created by luis on 18/09/16.
 */
public interface IRestEntity {
    public  String getQueryByParams(String baseUrl, String nameService, String api_security, String... params);

}
