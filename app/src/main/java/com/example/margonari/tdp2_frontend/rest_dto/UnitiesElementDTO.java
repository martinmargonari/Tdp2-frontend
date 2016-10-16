package com.example.margonari.tdp2_frontend.rest_dto;

/**
 * Created by luis on 15/10/16.
 */
public class UnitiesElementDTO extends AbstractDTO{

    private String   id;
    private String   number;
    private String   name;
    private String   exam_deadline;
    private String   file_extension;
    private String   full_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExam_deadline() {
        return exam_deadline;
    }

    public void setExam_deadline(String exam_deadline) {
        this.exam_deadline = exam_deadline;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }

    public String getFull_image() {
        return full_image;
    }

    public void setFull_image(String full_image) {
        this.full_image = full_image;
    }
}
