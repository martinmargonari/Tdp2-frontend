package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Margonari on 23/09/2016.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Professor implements java.io.Serializable {

    private String id;
    private String name;
    private String last_name;
    private String type;
    private String role;

    private int photo_id;
    private String file_extension;

    private static String PROFESOR = "PROFESOR";
    private static String ASISTENTE = "ASISTENTE";

    public Professor(String name, boolean isProfessor) {
        this.name = name;
        if (isProfessor) {
            type = PROFESOR;
        } else {
            type = ASISTENTE;
        }
    }
    public Professor(){

    }

    public boolean isProfessor(){
        return this.role.equals("teacher");
    }

    public boolean isAssistent(){
        return this.role.equals("assistant");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {

        if(this.isAssistent())
            return ASISTENTE;
        if(this.isProfessor())
            return PROFESOR;
        return "";

    }

    public String getFullName(){
        StringBuffer fullname= new StringBuffer();
        if(!name.isEmpty()) fullname.append(name);
        fullname.append(" ");
        if(!last_name.isEmpty()) fullname.append(last_name);
        return fullname.toString();
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }
}