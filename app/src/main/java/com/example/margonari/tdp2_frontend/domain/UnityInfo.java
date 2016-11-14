package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/**
 * Created by luis on 15/10/16.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnityInfo implements Serializable{
    private Unit unity;
    private Material[] materials;
    private Video[] videos;
    private Question[] questions;
    private String exam_is_approved;
    private String exam_score;

    public Unit getUnity() {
        return unity;
    }

    public void setUnity(Unit unity) {
        this.unity = unity;
    }

    public Material[] getMaterials() {
        return materials;
    }

    public void setMaterials(Material[] materials) {
        this.materials = materials;
    }

    public Video[] getVideos() {
        return videos;
    }

    public void setVideos(Video[] videos) {
        this.videos = videos;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public String getExam_is_approved() {
        return exam_is_approved;
    }

    public void setExam_is_approved(String exam_is_approved) {
        this.exam_is_approved = exam_is_approved;
    }

    public String getExam_score() {
        return exam_score;
    }

    public void setExam_score(String exam_score) {
        this.exam_score = exam_score;
    }
}
