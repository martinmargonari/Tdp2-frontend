package com.example.margonari.tdp2_frontend.services;

import com.example.margonari.tdp2_frontend.domain.Course;
import com.example.margonari.tdp2_frontend.domain.MyCourseBox;
import com.example.margonari.tdp2_frontend.rest_dto.CourseBoxDTO;

import java.util.ArrayList;

/**
 * Created by luis on 26/09/16.
 */

public class ListMyCoursesServices extends AbstractServices{

    private static final String service_name = "courses/currents";

    private static final String hardodequery= "http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/courses/currents?api_token=Je2fEMma8U60rGaZOKYF1TE4aQs5BPtN0cPlD6PmuDWYeBE1pFh1jH7Ji5Q7";
    public ArrayList<Course> getListCoursesBy() {
        String coursesQuery = hardodequery;//this.getQueryBy();
        System.out.println("QUERY " + coursesQuery.toString());
        CourseBoxDTO coursesDTO = (CourseBoxDTO) geDataOftDTO(coursesQuery, CourseBoxDTO.class);
        ArrayList<Course> listCourses= new ArrayList<>( );

        ArrayList<MyCourseBox> listCourseLoco= coursesDTO.getData()[0];
        for(  MyCourseBox myCourse_box : listCourseLoco){
            listCourses.add( myCourse_box.getCourse());
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
