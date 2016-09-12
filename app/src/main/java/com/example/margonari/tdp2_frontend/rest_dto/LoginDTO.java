package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Login;

import java.util.List;

/**
 * Created by luis on 10/09/16.
 */
public class LoginDTO extends AbstractDTO {

    public Login getData() { return (Login) data; }

    public void setData(Login data) {
        this.data = data;
    }


}
