package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.domain.CourseLocoIntern;
import com.example.margonari.tdp2_frontend.domain.Course_Loco;
import com.example.margonari.tdp2_frontend.rest_dto.CourseLocoDTO;

import java.util.ArrayList;

/**
 * Created by luis on 26/09/16.
 */

public class ListMyCoursesServices extends AbstractServices{

    private static final String service_name = "courses/currents";


    public ArrayList<CourseLocoIntern> getListCoursesBy() {
        String coursesQuery = this.getQueryBy();
        System.out.println("QUERY " + coursesQuery.toString());
        CourseLocoDTO coursesDTO = (CourseLocoDTO) geDataOftDTO(coursesQuery, CourseLocoDTO.class);
        ArrayList<CourseLocoIntern> listCourses= new ArrayList<>( );

        ArrayList<Course_Loco> listCourseLoco= coursesDTO.getData()[0];
        for(  Course_Loco course_loco: listCourseLoco){
            listCourses.add( course_loco.getCourse());
        }
        return listCourses;
    }

    @Override
    protected String getQueryBy(String... params) {


        String url = urlBase;
        StringBuffer urlStringBuffer = new StringBuffer(url);
        urlStringBuffer.append(service_name);
        urlStringBuffer.append("?");
        urlStringBuffer.append("api_token=");
        urlStringBuffer.append(api_security);


        return urlStringBuffer.toString();
    }
}
