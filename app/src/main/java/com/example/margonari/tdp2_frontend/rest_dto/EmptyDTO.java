package com.example.margonari.tdp2_frontend.rest_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by luis on 26/09/16.
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmptyDTO extends AbstractDTO {

   //Esta clase esta vacia porque no se espera en el response de la inscripcion que vengan datos
    //la ausencia de errores es la que da el ok de que la inscripcion fue realizada con exito, y eso esta implementado en AbstractDTO



}
