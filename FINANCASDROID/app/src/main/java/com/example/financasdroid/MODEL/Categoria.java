package com.example.financasdroid.MODEL;

public class Categoria {
    private int idCategoria;
    private String descricaoCategoria;

    public Categoria(int idCategoria, String descricaoCategoria) {
        this.setIdCategoria(idCategoria);
        this.setDescricaoCategoria(descricaoCategoria);
    }


    public Categoria(String descricaoCategoria) {
        this.setDescricaoCategoria(descricaoCategoria);
    }


    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }


    @Override
    public String toString() {
        return getDescricaoCategoria();
    }
}