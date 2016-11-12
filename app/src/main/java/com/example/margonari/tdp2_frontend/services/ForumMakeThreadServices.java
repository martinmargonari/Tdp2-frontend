package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.EmptyDTO;

import java.io.UnsupportedEncodingException;

/**
 * Created by luis on 31/10/16.
 */

public class ForumMakeThreadServices extends AbstractServices{
    private static final String service_name = "forum/thread/store";


    public Boolean makePostBy(String thread_id, String title,String content) throws UnsupportedEncodingException {
        String coursesQuery = this.getQueryBy(thread_id,title,content);

        Log.d("Query "+this.getClass().getName(),coursesQuery);

        EmptyDTO emptyDTO = (EmptyDTO) geDataOftDTO(coursesQuery, EmptyDTO.class);
        return true;
    }

    @Override
    protected String getQueryBy(String... params) throws UnsupportedEncodingException {


        String category_id = params[0];
        String title=params[1];
        String content=params[2];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);
        urlStringBuffer.append("&category_id=");
        urlStringBuffer.append(category_id);

        urlStringBuffer.append("&title=");
        urlStringBuffer.append(title);

        urlStringBuffer.append("&content=");
        urlStringBuffer.append(content);

       Log.d("MakePostinTheadForum",urlStringBuffer.toString());
        return urlStringBuffer.toString();

    }

}
