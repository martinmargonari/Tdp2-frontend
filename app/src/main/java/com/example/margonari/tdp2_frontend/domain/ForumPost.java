package com.example.margonari.tdp2_frontend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Arrays;

/**
 * Created by luis on 27/10/16.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumPost {

    private String id;
    private String author_name;
    private String thread_id;
    private String author_id;
    private String content;
    private String post_id;
    private String sequence;
    private String created_at;
    private String updated_at;
    private AttachFile[] attachments;
    private String author_image;
    private Author author;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
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

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    private String deleted_at;

    public AttachFile[] getAttachments() {
        return attachments;
    }

    public void setAttachments(AttachFile[] attachments) {
        this.attachments = attachments;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_image() {
        return author_image;
    }

    public void setAuthor_image(String author_image) {
        this.author_image = author_image;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ForumPost{" +
                "id='" + id + '\'' +
                ", author_name='" + author_name + '\'' +
                ", thread_id='" + thread_id + '\'' +
                ", author_id='" + author_id + '\'' +
                ", content='" + content + '\'' +
                ", post_id='" + post_id + '\'' +
                ", sequence='" + sequence + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", attachments=" + Arrays.toString(attachments) +
                ", author_image='" + author_image + '\'' +
                ", author=" + author +
                ", deleted_at='" + deleted_at + '\'' +
                '}';
    }
}
