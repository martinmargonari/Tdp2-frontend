package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 12/09/16.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Course implements java.io.Serializable {

    private String id;
    private String session_id;
    private String name;
    private String description;
    private String file_extension;
    private List<SessionCourse> next_sessions =new ArrayList<SessionCourse>();
    private List<Unit> unities= new ArrayList<Unit>();
    private List<Professor> users=new ArrayList<Professor>();
    private int photo_id;
    private String full_image;


    public Course(String name, String description, int photo_id) {
        this.name = name;
        this.description = description;
        this.photo_id = photo_id;
    }
    public Course(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<SessionCourse> getNext_sessions() {
        return next_sessions;
    }

    public void setNext_sessions(List<SessionCourse> next_sessions) {
        this.next_sessions = next_sessions;
    }

    public List<Unit> getUnities() {
        return unities;
    }

    public void setUnities(List<Unit> unities) {
        this.unities = unities;
    }

    public List<Professor> getUsers() {
        return users;
    }

    public void setUsers(List<Professor> users) {
        this.users = users;
    }

    public boolean hasCurrentSessions(){
        return getNext_sessions()!=null & !getNext_sessions().isEmpty();
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getFull_image() {
        return full_image;
    }

    public void setFull_image(String full_image) {
        this.full_image = full_image;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
               // ", description='" + description + '\'' +
                ", file_extension='" + file_extension + '\'' +
                ", current_sessions=" + next_sessions +
                ", unities=" + unities +
                ", users=" + users +
                ", photo_id=" + photo_id +
                '}';
    }


}
