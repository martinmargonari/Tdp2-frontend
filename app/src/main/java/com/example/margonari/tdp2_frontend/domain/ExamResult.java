package com.example.margonari.tdp2_frontend.domain;

import android.icu.text.StringPrepParseException;
import android.icu.text.SymbolTable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by luis on 07/11/16.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamResult {


    private String score;
    private String is_approved;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(String is_approved) {
        this.is_approved = is_approved;
    }
}
