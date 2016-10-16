package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Margonari on 02/10/2016.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements java.io.Serializable {

    private String question;
    private String[] options;
    private int correct_option;

    public Question() {
        this.options = new String[4];
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption(int number) {
        return options[number];
    }

    public void setOption(String option, int number, boolean isCorrect) {
        this.options[number] = option;
        if (isCorrect)
            correct_option = number;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getCorrect_option() {
        return correct_option;
    }

    public void setCorrect_option(int correct_option) {
        this.correct_option = correct_option;
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }
}