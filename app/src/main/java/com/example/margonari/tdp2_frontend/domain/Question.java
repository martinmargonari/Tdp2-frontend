package com.example.margonari.tdp2_frontend.domain;

/**
 * Created by Margonari on 02/10/2016.
 */

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


    @Override
    public String toString() {
        //TODO
        return "";
    }
}