package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Margonari on 02/10/2016.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Material implements java.io.Serializable {

    private String id;
    private String name;
    private int type;
    private String file_extension;
    private String full_path;

    public static final int VIDEO = 1;
    public static final int DOCUMENTO = 2;
    public static final int EXAMEN = 3;

    public Material(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public Material(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        //TODO
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public String getFull_path() {
        return full_path;
    }

    public void setFull_path(String full_path) {
        this.full_path = full_path;
    }


}
