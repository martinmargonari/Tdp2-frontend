package com.example.margonari.tdp2_frontend.domain;

import java.util.List;

/**
 * Created by Margonari on 02/10/2016.
 */

public class Material implements java.io.Serializable {

    private String name;
    private int type;

    public static final int VIDEO = 1;
    public static final int DOCUMENTO = 2;

    public Material(String name, int type) {
        this.name = name;
        this.type = type;
    }

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
}
