package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.rest_dto.AbstractDTO;

/**
 * Created by luis on 17/10/16.
 */
public class ExamResultServices extends AbstractServices{
    private static final String service_name = "course/create-exam-result";

    public void make(String... params) {

        String myApiToken 		=  params[0];
        String mySessionId		=  params[1];
        String unit_id          =  params[2];
        String correct_answers   =  params[3];
        String questions_amount  =  params[4];

        String coursesQuery = this.getQueryBy(myApiToken, mySessionId,unit_id,correct_answers,questions_amount);
        Log.d("QueryExam", coursesQuery);
        geDataOftDTO(coursesQuery, AbstractDTO.class);

    }

    @Override
    protected String getQueryBy(String... params) {
        String myApiToken 		=  params[0];
        String mySessionId		=  params[1];
        String unit_id          =  params[2];
        String correct_answers   =  params[3];
        String questions_amount  =  params[4];

        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(myApiToken);
        urlStringBuffer.append("&session_id=");
        urlStringBuffer.append(mySessionId);
        urlStringBuffer.append("&unity_id=");
        urlStringBuffer.append(unit_id);
        urlStringBuffer.append("&questions_amount=");
        urlStringBuffer.append(questions_amount);
        urlStringBuffer.append("&correct_answers=");
        urlStringBuffer.append(correct_answers);
        System.out.println(urlStringBuffer.toString());
        return urlStringBuffer.toString();
    }
}
