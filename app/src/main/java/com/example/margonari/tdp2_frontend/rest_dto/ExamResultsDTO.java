package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.ExamResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 07/11/16.
 */

public class ExamResultsDTO extends AbstractDTO {


    public ExamResult getData() {
        try {
            return (ExamResult) data;


        }catch (Exception e){
            return null;
        }
    }

    public void setData(ExamResult data) {
        this.data = data;
    }
}
