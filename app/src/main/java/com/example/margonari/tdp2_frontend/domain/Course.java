package com.example.margonari.tdp2_frontend.domain;

import java.util.List;

/**
 * Created by luis on 12/09/16.
 */
public class Course {

    private String id;
    private String name;
    private String description;
    private int photo_id;
    private String file_extension;
    private List<SessionCourse> sessions;

    public Course(String name, String description, int photo_id) {
        this.name = name;
        this.description = description;
        this.photo_id = photo_id;
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

    public List<SessionCourse> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionCourse> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", file_extension='" + file_extension + '\'' +
                ", sessionCourseList=" + sessions +
                '}';
    }


}
