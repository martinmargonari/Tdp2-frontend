package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Margonari on 23/09/2016.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Unit implements java.io.Serializable {

    private String id;
    private String course_id;
    private String number;
    private String name;
    private int photo_id;
    private String questions_amount;
    private String exam_deadline;
    private String file_extension;
    private String is_final_exam;
    private String full_image;

    public Unit() {
    }

    public Unit(String name, int photo_id) {
        this.name = name;
        this.photo_id = photo_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestions_amount() {
        return questions_amount;
    }

    public void setQuestions_amount(String questions_amount) {
        this.questions_amount = questions_amount;
    }

    public String getExam_deadline() {
        return exam_deadline;
    }

    public void setExam_deadline(String exam_deadline) {
        this.exam_deadline = exam_deadline;
    }

    public String getIs_final_exam() {
        return is_final_exam;
    }

    public void setIs_final_exam(String is_final_exam) {
        this.is_final_exam = is_final_exam;
    }

    public String getFull_image() {
        return full_image;
    }

    public void setFull_image(String full_image) {
        this.full_image = full_image;
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }
}