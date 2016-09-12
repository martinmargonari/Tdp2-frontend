package com.example.margonari.tdp2_frontend.rest_dto;

import com.example.margonari.tdp2_frontend.domain.Categoria;
import com.example.margonari.tdp2_frontend.domain.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 10/09/16.
 */
public class CategoriesDTO extends AbstractDTO{

    public ArrayList<Categoria> getData() { return (ArrayList<Categoria>) data; }

    public void setData(List<Categoria> data) {
        this.data = data;
    }
}
