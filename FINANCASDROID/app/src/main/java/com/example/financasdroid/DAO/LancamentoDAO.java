package com.example.financasdroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financasdroid.MODEL.Lancamento;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class LancamentoDAO {

    private ConexaoDB conexaoDB;
    private SQLiteDatabase banco;

    public LancamentoDAO(Context context){
        conexaoDB = new ConexaoDB(context);
        banco = conexaoDB.getWritableDatabase();
    }

    public long inserirLancamento(Lancamento lancamento){
        ContentValues values = new ContentValues();
        values.put(ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO, lancamento.getDescricao());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_DATA, lancamento.getData());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_VALOR, lancamento.getValor());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_TIPO, lancamento.getTipo());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA, lancamento.getIdCategoria());


        return banco.insert(ConexaoDB.TABELA_LANCAMENTO, null, values);
    }

    public ArrayList<Lancamento> getTodosLancamentos(){
        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getInt(4));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }


    public ArrayList<Lancamento> getLancamentoPorTipo(String tipo){
        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                ConexaoDB.COLUNA_LANCAMENTO_TIPO + " = ?", new String[]{tipo}, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getInt(4));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }

    public ArrayList<Lancamento> getLancamentoPorPeriodo(String dataInicial){
        System.out.println("DataIncial: " + dataInicial);


        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                ConexaoDB.COLUNA_LANCAMENTO_DATA + " >= ?", new String[]{dataInicial}, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getString(3),
                    cursor.getInt(4));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }

    public void excluirLancamento(Lancamento lancamento){
        banco.delete(ConexaoDB.TABELA_LANCAMENTO ,ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO + " = ? AND " + ConexaoDB.COLUNA_LANCAMENTO_TIPO + " = ?", new String[]{lancamento.getDescricao(), lancamento.getTipo()});
    }

    public double getValorPorCategoria(int idCategoria){
        double valor=0;
        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_VALOR,},
                ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA + " = "+idCategoria,null, null, null, null);

        while(cursor.moveToNext()){

            valor += cursor.getDouble(0);
        }

        return valor;
    }

}

