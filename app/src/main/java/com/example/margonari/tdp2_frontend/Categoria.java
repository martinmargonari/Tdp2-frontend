package com.example.margonari.tdp2_frontend;

/**
 * Created by Margonari on 05/09/2016.
 */
public class Categoria {
    private String nombre;
    private int idDrawable;

    public Categoria(String nombre, int idDrawable) {
        this.nombre = nombre;
        this.idDrawable = idDrawable;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdDrawable() {
        return idDrawable;
    }

    public int getId() {
        return nombre.hashCode();
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
     * @return com.example.margonari.tdp2_frontend.Categoria
     */
    public static Categoria getItem(int id) {
        for (Categoria item : ITEMS) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }
}