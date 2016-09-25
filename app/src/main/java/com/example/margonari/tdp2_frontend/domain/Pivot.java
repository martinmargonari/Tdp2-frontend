package com.example.margonari.tdp2_frontend.domain;

import java.io.Serializable;

/**
 * Created by luis on 25/09/16.
 */
public class Pivot implements Serializable {

    private String course_id;
    private String user_id;
    private String created_at;
    private String updated_at;



    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
