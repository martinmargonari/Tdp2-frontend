package com.example.margonari.tdp2_frontend.rest_dto;

import java.util.List;

/**
 * Created by luis on 10/09/16.
 */
public class JsonDataDTO {

    private DummyDTO data;
    private List<String> errors;


    public DummyDTO getData() {
        return data;
    }

    public void setData(DummyDTO data) {
        this.data = data;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
