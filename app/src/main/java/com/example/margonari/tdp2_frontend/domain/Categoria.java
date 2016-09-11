package com.example.margonari.tdp2_frontend.domain;

import com.example.margonari.tdp2_frontend.R;

/**
 * Created by Margonari on 05/09/2016.
 */
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
            new Categoria("Arte", R.drawable.arte),
            new Categoria("Informática", R.drawable.computacion),
            new Categoria("Literatura", R.drawable.literatura),
            new Categoria("Matemáticas", R.drawable.matematica),
            new Categoria("Química", R.drawable.quimica),
            new Categoria("Medicina", R.drawable.salud)
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
}