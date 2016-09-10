package com.example.margonari.tdp2_frontend.rest_dto;

import java.util.List;

/**
 * Created by luis on 10/09/16.
 */
public class RestCommunicationDTO {

    private LoginDTO data;
    private List<String> errors;


    public LoginDTO getData() {
        return data;
    }

    public void setData(LoginDTO data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
