package com.example.margonari.tdp2_frontend.domain;

import java.io.Serializable;

/**
 * Created by luis on 25/09/16.
 */

public class User implements Serializable {

    private String course_id;
    private String name;
    private String last_name;
    private Pivot  pivot;

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
