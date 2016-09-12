package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Login;

import java.util.List;

/**
 * Created by luis on 10/09/16.
 */
public abstract class AbstractDTO {

    protected Object data;
    protected List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

}
