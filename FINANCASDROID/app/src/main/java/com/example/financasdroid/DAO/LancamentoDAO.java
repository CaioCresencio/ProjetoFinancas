package com.example.financasdroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.animation.LayoutAnimationController;

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
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_IDLANCAMENTO,
                        ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }


    public ArrayList<Lancamento> getLancamentoPorTipo(String tipo){
        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_IDLANCAMENTO,
                        ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                ConexaoDB.COLUNA_LANCAMENTO_TIPO + " = ?", new String[]{tipo}, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }

    public ArrayList<Lancamento> getLancamentoPorPeriodo(String dataInicial){
        System.out.println("DataIncial: " + dataInicial);


        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_IDLANCAMENTO,
                        ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,
                        ConexaoDB.COLUNA_LANCAMENTO_DATA,
                        ConexaoDB.COLUNA_LANCAMENTO_VALOR,
                        ConexaoDB.COLUNA_LANCAMENTO_TIPO,
                        ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                ConexaoDB.COLUNA_LANCAMENTO_DATA + " >= ?", new String[]{dataInicial}, null, null, null);

        while(cursor.moveToNext()){
            Lancamento novoDespesa = new Lancamento(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4),
                    cursor.getInt(5));
            listDespesas.add(novoDespesa);
        }

        return listDespesas;
    }


    public double getValorPorCategoria(int idCategoria){
        double valor=0;
        ArrayList<Lancamento> listDespesas = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_VALOR, ConexaoDB.COLUNA_LANCAMENTO_TIPO,},
                ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA + " = "+idCategoria,null, null, null, null);

        while(cursor.moveToNext()) {
            if (cursor.getString(1).equals("D")) {
                valor += cursor.getDouble(0);

            }
        }
        return valor;
    }



    public void excluirLancamento(Lancamento lancamento){
        banco.delete(ConexaoDB.TABELA_LANCAMENTO ,ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO + " = ? AND " + ConexaoDB.COLUNA_LANCAMENTO_TIPO + " = ?", new String[]{lancamento.getDescricao(), lancamento.getTipo()});
    }
    public void atualizarLancamento(Lancamento lancamento){
        ContentValues values = pegarValores(lancamento);
        
        banco.update(ConexaoDB.TABELA_LANCAMENTO,values,"idlancamento = ?",toArgs(lancamento));
    }
    private String[] toArgs(Lancamento lancamento) {
        String[] args = {String.valueOf(lancamento.getIdLancamento())};

        return args;
    }

    private ContentValues pegarValores(Lancamento lancamento){
        ContentValues values = new ContentValues();
        values.put(ConexaoDB.COLUNA_LANCAMENTO_IDLANCAMENTO,lancamento.getIdLancamento());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_DESCRICAO,lancamento.getDescricao());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_DATA,lancamento.getData());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_VALOR,lancamento.getValor());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_TIPO,lancamento.getTipo());
        values.put(ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA, lancamento.getIdCategoria());
        return values;
    }
}
