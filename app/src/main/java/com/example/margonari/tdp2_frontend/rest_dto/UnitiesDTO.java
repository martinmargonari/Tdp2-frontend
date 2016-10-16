package com.example.margonari.tdp2_frontend.rest_dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis on 15/10/16.
 */

public class UnitiesDTO extends AbstractDTO{

    public ArrayList<UnitiesElementDTO> getData() { return (ArrayList<UnitiesElementDTO>) data; }

    public void setData(List<UnitiesElementDTO> data) {
        this.data = data;
    }
}
