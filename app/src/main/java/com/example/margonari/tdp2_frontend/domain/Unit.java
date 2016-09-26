package com.example.margonari.tdp2_frontend.domain;

/**
 * Created by Margonari on 23/09/2016.
 */
public class Unit implements java.io.Serializable {

    private String course_id;
    private String name;
    private String number;
    private int photo_id;
    private String file_extension;

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

    @Override
    public String toString() {
        //TODO
        return "";
    }
}