package com.example.margonari.tdp2_frontend.domain;

import com.example.margonari.tdp2_frontend.util.RestServiceName;

/**
 * Created by aranc on 21/8/2016.
 */
public class Login implements IRestEntity {
    public String id;
    public String api_token;
    public String email;
    public String password;
    public String rest_services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public  String getQueryByParams(String baseUrl,String nameService, String api_security, String... params) {

        String userEmail = params[0];

        StringBuffer urlStringBuffer = new StringBuffer(baseUrl);
        urlStringBuffer.append(nameService);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_security=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&email=");
        urlStringBuffer.append(userEmail);
        return urlStringBuffer.toString();
    }
}
