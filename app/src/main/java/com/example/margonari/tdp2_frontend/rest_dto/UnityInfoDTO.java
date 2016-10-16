package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.UnityInfo;

/**
 * Created by luis on 15/10/16.
 */

public class UnityInfoDTO extends AbstractDTO {


    public UnityInfo getData() { return (UnityInfo) data; }

    public void setData(UnityInfo data) {
        this.data = data;
    }
}
