package com.example.financasdroid.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.financasdroid.MODEL.Categoria;

import java.util.ArrayList;

public class CategoriaDAO {

    private ConexaoDB conexaoDB;
    private SQLiteDatabase banco;

    public CategoriaDAO(Context context){
        conexaoDB = new ConexaoDB(context);
        banco = conexaoDB.getWritableDatabase();
    }

    public long inserirCategoria(Categoria categoria){
        ContentValues values = new ContentValues();
        values.put(ConexaoDB.COLUNA_CATEGORIA_DESCRICAO, categoria.getDescricaoCategoria());

        return banco.insert(ConexaoDB.TABELA_CATEGORIA, null, values);
    }

    public ArrayList<Categoria> getTodasCategorias(){
        ArrayList<Categoria> listCategoria = new ArrayList<>();
        Cursor cursor = banco.query(ConexaoDB.TABELA_CATEGORIA,
                new String[]{ConexaoDB.COLUNA_CATEGORIA_IDCATEGORIA,
                        ConexaoDB.COLUNA_CATEGORIA_DESCRICAO},
                null, null, null, null, null);

        while(cursor.moveToNext()){
            Categoria novoCategoria = new Categoria(cursor.getInt(0),
                    cursor.getString(1));
            listCategoria.add(novoCategoria);
        }

        return listCategoria;
    }

    public Categoria getCategoriaPorID(int id){
        Categoria novoCategoria = null;
        Cursor cursor = banco.query(ConexaoDB.TABELA_CATEGORIA,
                new String[]{ConexaoDB.COLUNA_CATEGORIA_IDCATEGORIA,
                        ConexaoDB.COLUNA_CATEGORIA_DESCRICAO},
                ConexaoDB.COLUNA_CATEGORIA_IDCATEGORIA + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        while(cursor.moveToNext()){
            novoCategoria = new Categoria(cursor.getInt(0),
                    cursor.getString(1));
        }

        return novoCategoria;
    }


    public boolean validaDescricaoExiste(String descricao){
        boolean retorno = false;

        Cursor cursor = banco.query(ConexaoDB.TABELA_CATEGORIA,
                new String[]{ConexaoDB.COLUNA_CATEGORIA_DESCRICAO},
                ConexaoDB.COLUNA_CATEGORIA_DESCRICAO + " = ?", new String[]{descricao}, null, null, null);

        if(cursor.moveToNext()){
            retorno = true;
        }
        return retorno;
    }


    public boolean excluirCategoria(Categoria categoria){
        boolean retorno = false;

        if(!verificaLancamentoCategoria(categoria.getIdCategoria())){
            banco.delete(ConexaoDB.TABELA_CATEGORIA ,ConexaoDB.COLUNA_CATEGORIA_IDCATEGORIA + " = ?", new String[]{String.valueOf(categoria.getIdCategoria())});
            retorno = true;
        }

        return retorno;
    }


    private boolean verificaLancamentoCategoria(int idCategoria){
        boolean retorno = false;

        Cursor cursor = banco.query(ConexaoDB.TABELA_LANCAMENTO,
                new String[]{ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA},
                ConexaoDB.COLUNA_LANCAMENTO_CATEGORIA + " = "+idCategoria,null, null, null, null);

        if(cursor.moveToNext()){
            retorno = true;
        }
        return retorno;
    }

}