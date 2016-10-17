package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by luis on 26/09/16.
 * Esta clase se crea con la unica finalidad de responder a la consulta de cursos del lado del server
 * Actualmente vienen muchos datos adicionales que se esconden dentro de una estructura mas grande
 * A esta estructura la llamamos MyCourseBox y lo unico que tomamos de esos datos
 * es el curso
 */



@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MyCourseBox implements Serializable {
    private String id;
    private Course course;


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
