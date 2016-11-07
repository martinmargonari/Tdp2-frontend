package com.example.margonari.tdp2_frontend.rest_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

/**
 * Created by luis on 10/09/16.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractDTO {

    protected Object data;
    protected String[] errors;

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }

}
