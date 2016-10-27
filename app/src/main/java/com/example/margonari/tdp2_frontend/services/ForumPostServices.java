package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.ForumPost;
import com.example.margonari.tdp2_frontend.rest_dto.ForumPostDTO;

import java.util.List;

/**
 * Created by luis on 27/10/16.
 */

public class ForumPostServices extends AbstractServices {
    private static final String service_name = "forum/post";
    public List<ForumPost> getListPostBy(String thread_id) {

        String coursesQuery = this.getQueryBy(thread_id);
        ForumPostDTO coursesDTO = (ForumPostDTO) geDataOftDTO(coursesQuery, ForumPostDTO.class);
        return coursesDTO.getData();
    }

    @Override
    protected String getQueryBy(String... params) {


        String thread_id = params[0];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&thread_id=");
        urlStringBuffer.append(thread_id);

        Log.d("PostinTheadForum",urlStringBuffer.toString());

        return urlStringBuffer.toString();
    }
}
