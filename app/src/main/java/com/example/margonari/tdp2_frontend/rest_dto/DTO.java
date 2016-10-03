package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Course;

import java.util.List;

/**
 * Created by luis on 03/10/16.
 */

public class DTO<T> {

    protected T data;
    protected List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public T getData() { return (T) data; }

    public void setData(T data) {
        this.data = data;
    }

}
