package com.example.financasdroid.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexaoDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "financas.db";
    private static final int DATABASE_VERSION = 1;


    //DADOS TABELA CATEGORIA
    public static final String TABELA_CATEGORIA = "categoria";
    public static final String COLUNA_CATEGORIA_IDCATEGORIA = "idcategoria";
    public static final String COLUNA_CATEGORIA_DESCRICAO = "descricao";

    //DADOS TABELA DESPESAS
    public static final String TABELA_LANCAMENTO = "lancamento";
    public static final String COLUNA_LANCAMENTO_DESCRICAO = "descricao";
    public static final String COLUNA_LANCAMENTO_DATA = "data";
    public static final String COLUNA_LANCAMENTO_VALOR = "valor";
    public static final String COLUNA_LANCAMENTO_TIPO = "tipo";
    public static final String COLUNA_LANCAMENTO_CATEGORIA = "categoria";


    public ConexaoDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE categoria(idcategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "descricao VARCHAR(50) NOT NULL)");

        db.execSQL("CREATE TABLE lancamento(descricao VARCHAR(50) NOT NULL, " +
                                            "data VARCHAR(20) NOT NULL, " +
                                            "valor DOUBLE NOT NULL, " +
                                            "tipo VARCHAR NOT NULL, " +
                                            "categoria INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
