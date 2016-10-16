package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.rest_dto.UnityInfoDTO;

/**
 * Created by luis on 16/10/16.
 */

public class UnitServices extends AbstractServices {


        private static final String service_name = "course/unity";
        public UnityInfo getUnityInfo(String courseId) {
            //TODO HARDCODEADO unityQuery

            String unityQuery = "http://ec2-54-68-222-103.us-west-2.compute.amazonaws.com/api/course/unity?api_token=70rSdy8qjufNl6edymVac0uuMCuGa9ZeenISjkSnjizoxhZ1bEXZZG5cUjdf&unity_id=10";//this.getQueryBy(courseId);
            Log.d("UnitServices", unityQuery);
            UnityInfoDTO unitDTO = (UnityInfoDTO) geDataOftDTO(unityQuery, UnityInfoDTO.class);
            return unitDTO.getData();
        }

        @Override
        protected String getQueryBy(String... params) {
            String unity_id = params[0];

            String url = urlBase;
            StringBuffer urlStringBuffer = new StringBuffer(url);
            urlStringBuffer.append(service_name);
            urlStringBuffer.append("?");
            urlStringBuffer.append("api_token=");
            urlStringBuffer.append(api_security);
            urlStringBuffer.append("&unity_id=");
            urlStringBuffer.append(unity_id);
            System.out.println(urlStringBuffer.toString());
            return urlStringBuffer.toString();
        }

}
