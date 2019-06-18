package com.example.financasdroid.MODEL;

import java.io.Serializable;

public class Lancamento implements Serializable {
    private String descricao;
    private String data;
    private double valor;
    private String tipo;
    private int idCategoria;
    private int idLancamento;

    public Lancamento(int idLancamento,String descricao, String data, double valor, String tipo, int idCategoria) {
        this.setDescricao(descricao);
        this.setId(idLancamento);
        this.setData(data);
        this.setValor(valor);
        this.setTipo(tipo);
        this.setIdCategoria(idCategoria);
    }
    public Lancamento(String descricao, String data, double valor, String tipo, int idCategoria) {
        this.setDescricao(descricao);
        this.setData(data);
        this.setValor(valor);
        this.setTipo(tipo);
        this.setIdCategoria(idCategoria);
    }

    public int getIdLancamento(){return idLancamento;}
    public void setId(int id){this.idLancamento = id;}
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}