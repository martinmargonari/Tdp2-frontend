package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.ForumThread;
import com.example.margonari.tdp2_frontend.rest_dto.ForumThreadsDTO;

import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

public class ForumThreadsServices extends AbstractServices {
    private static final String service_name = "forum/thread";
    public List<ForumThread> getListThreadsBy(String category_id) {

        String coursesQuery = this.getQueryBy(category_id);
        ForumThreadsDTO coursesDTO = (ForumThreadsDTO) geDataOftDTO(coursesQuery, ForumThreadsDTO.class);
        return coursesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {


        String category_id = params[0];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&category_id=");
        urlStringBuffer.append(category_id);

        Log.d("ThreadsInCategoryForum",urlStringBuffer.toString());

        return urlStringBuffer.toString();
    }
}

