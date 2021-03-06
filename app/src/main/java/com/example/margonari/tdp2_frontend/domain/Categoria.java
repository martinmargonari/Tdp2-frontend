package com.example.margonari.tdp2_frontend.domain;

import com.example.margonari.tdp2_frontend.R;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Margonari on 05/09/2016.
 */


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Categoria {
    private String name;
    private int id;

    public Categoria(){}

    public Categoria(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getIdHash() {
        return name.hashCode();
    }

    public static Categoria[] ITEMS = {
            new Categoria("Informática", R.drawable.computacion),
            new Categoria("Idiomas", R.drawable.literatura),
            new Categoria("Matemáticas", R.drawable.matematica),
            new Categoria("Ciencias Biologicas", R.drawable.salud)
    };

    /**
     * Obtiene item basado en su identificador
     *
     * @param id identificador
     * @return com.example.margonari.tdp2_frontend.domain.Categoria
     */
    public static Categoria getItem(int id) {
        for (Categoria item : ITEMS) {
            if (item.getIdHash() == id) {
                return item;
            }
        }
        return null;
    }

    public static int getCategoryByIdView(int id_view) {

                switch (id_view) {
                    case R.drawable.computacion:
                        return 4;
                    case R.drawable.salud:
                        return 6;
                    case R.drawable.matematica:
                        return 5;

                    case R.drawable.literatura:
                        return 3;
                    default:
                        System.out.println("error");
                        break;
                }
        return 0;

    }
}