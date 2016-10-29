package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by luis on 27/10/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumCategory {
    private String id;
    private String category_id;
    private String title;
    private String description;
    private String weight;
    private String enable_threads;
    private String thread_count;
    private String post_count;
    private String created_at;
    private String updated_at;
    private String course_id;

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getEnable_threads() {
        return enable_threads;
    }

    public void setEnable_threads(String enable_threads) {
        this.enable_threads = enable_threads;
    }

    public String getThread_count() {
        return thread_count;
    }

    public void setThread_count(String thread_count) {
        this.thread_count = thread_count;
    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(String post_count) {
        this.post_count = post_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
