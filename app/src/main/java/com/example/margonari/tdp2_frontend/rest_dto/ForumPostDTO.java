package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 27/10/16.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForumPostDTO extends AbstractDTO {

    public ArrayList<ForumPost> getData() { return (ArrayList<ForumPost>) data; }

    public void setData(List<ForumPost> data) {
        this.data = data;
    }
}
