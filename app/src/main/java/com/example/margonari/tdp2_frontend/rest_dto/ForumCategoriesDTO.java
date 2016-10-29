package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.ForumCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

public class ForumCategoriesDTO extends AbstractDTO{
    public ArrayList<ForumCategory> getData() { return (ArrayList<ForumCategory>) data; }

    public void setData(List<ForumCategory> data) {
        this.data = data;
    }
}
