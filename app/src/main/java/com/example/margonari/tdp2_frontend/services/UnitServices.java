package com.example.margonari.tdp2_frontend.services;

import android.util.Log;

import com.example.margonari.tdp2_frontend.domain.UnityInfo;
import com.example.margonari.tdp2_frontend.rest_dto.UnityInfoDTO;

/**
 * Created by luis on 16/10/16.
 */

public class UnitServices extends AbstractServices {


        private static final String service_name = "course/unity";
        public UnityInfo getUnityInfo(String courseId, String sessionid) {
            String unityQuery = this.getQueryBy(courseId,sessionid);
            Log.d("UnitServices", unityQuery);
            UnityInfoDTO unitDTO = (UnityInfoDTO) geDataOftDTO(unityQuery, UnityInfoDTO.class);
            return unitDTO.getData();
        }

        @Override
        protected String getQueryBy(String... params) {
            String unity_id = params[0];
            String session_id= params[1];

            String url = urlBase;
            StringBuffer urlStringBuffer = new StringBuffer(url);
            urlStringBuffer.append(service_name);
            urlStringBuffer.append("?");
            urlStringBuffer.append("api_token=");
            urlStringBuffer.append(api_security);
            urlStringBuffer.append("&unity_id=");
            urlStringBuffer.append(unity_id);
            urlStringBuffer.append("&session_id=");
            urlStringBuffer.append(session_id);
            System.out.println(urlStringBuffer.toString());
            return urlStringBuffer.toString();
        }

}
