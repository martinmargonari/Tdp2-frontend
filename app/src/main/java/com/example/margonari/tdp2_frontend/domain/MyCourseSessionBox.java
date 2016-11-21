package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by luis on 21/11/16.
 */




@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCourseSessionBox implements Serializable{

    private SessionCourse sessionCourse;
    private Course course;


    public SessionCourse getSessionCourse() {
        return sessionCourse;
    }

    public void setSessionCourse(SessionCourse sessionCourse) {
        this.sessionCourse = sessionCourse;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
