package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;

import java.io.UnsupportedEncodingException;

/**
 * Created by luis on 31/10/16.
 */

public class ForumMakePostServices extends AbstractServices{
    private static final String service_name = "forum/post/store/";


    public Boolean makePostBy(String thread_id, String content) throws UnsupportedEncodingException {
        String coursesQuery = this.getQueryBy(thread_id,content);


        EmptyDTO emptyDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);
        return true;
    }

    @Override
    protected String getQueryBy(String... params) throws UnsupportedEncodingException {


        String thread_id = params[0];
        String content=params[1];
        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&thread_id=");
        urlStringBuffer.append(thread_id);
        urlStringBuffer.append("&content=");

        urlStringBuffer.append(content);

       Log.d("MakePostinTheadForum",urlStringBuffer.toString());
        return urlStringBuffer.toString();

    }

}
