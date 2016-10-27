package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.ForumCategory;
import com.example.margonari.tdp2_frontend.rest_dto.ForumCategoriesDTO;

import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

public class ForumCategoriesServices extends AbstractServices {
    private static final String service_name = "forum/category";
    public List<ForumCategory> getListCategoriesBy(String course_id) {

        String coursesQuery = this.getQueryBy(course_id);
        ForumCategoriesDTO coursesDTO = (ForumCategoriesDTO) geDataOftDTO(coursesQuery, ForumCategoriesDTO.class);
        return coursesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {


        String courseCategory = params[0];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&course_id=");
        urlStringBuffer.append(courseCategory);

        Log.d("Categoriesinforum",urlStringBuffer.toString());

        return urlStringBuffer.toString();
    }
}
