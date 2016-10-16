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
}
