package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;

/**
 * Created by luis on 30/10/16.
 */
public class ForumDeletePostServices extends AbstractServices{

    private static final String service_name = "forum/post/delete/";

    public Boolean deletePostBy(String post_id) {
        String coursesQuery = this.getQueryBy(post_id);
        Log.d("Query "+this.getClass().getName(),coursesQuery);

        EmptyDTO emptyDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);
        return emptyDTO.getErrors().isEmpty();
    }

    @Override
    protected String getQueryBy(String... params) {
        String post_id = params[0];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append(post_id);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
       Log.d("QueryDelPost",urlStringBuffer.toString());
        return urlStringBuffer.toString();
    }
}
