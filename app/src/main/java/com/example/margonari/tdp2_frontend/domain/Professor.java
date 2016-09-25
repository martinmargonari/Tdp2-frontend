package com.example.margonari.tdp2_frontend.domain;

/**
 * Created by Margonari on 23/09/2016.
 */
public class Professor implements java.io.Serializable {

    private String id;
    private String name;
    private String type;
    private int photo_id;
    private String file_extension;

    private static String PROFESOR = "Profesor";
    private static String ASISTENTE = "Asistente";

    public Professor(String name, boolean isProfessor) {
        this.name = name;
        if (isProfessor) {
            type = PROFESOR;
        } else {
            type = ASISTENTE;
        }
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
        return type;
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

    @Override
    public String toString() {
        //TODO
        return "";
    }
}