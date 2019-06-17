package com.example.financasdroid.VIEW;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.R;

import java.util.ArrayList;

public class CriarCategoria extends AppCompatActivity {

    private static class ViewHolder{
        EditText editDescricao;

        Button buttonSalvar;
        Button buttonCancelar;
    }

    public ViewHolder viewHolder = new ViewHolder();

    private Categoria categoria;
    private CategoriaDAO categoriaDAO;
    //private ArrayList<Categoria> listCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_categoria);

        criarComponentes();

        viewHolder.buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSalvarClicado();
            }
        });
        viewHolder.buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void buttonSalvarClicado(){
        String mensagem = "";
        String descricao = "";


        mensagem = validarCamposVazios();

        if(mensagem.equals("SUCESSO")){
            descricao = viewHolder.editDescricao.getText().toString();

            categoriaDAO = new CategoriaDAO(this);


            if(categoriaDAO.validaDescricaoExiste(descricao)){
                mensagem = "ERRO - CATEGORIA JA CADASTRADA !!!";
            }else{


                categoria = new Categoria(descricao);

                categoriaDAO.inserirCategoria(categoria);

                mensagem = "CATEGORIA ADICIONADA COM SUCESSO !!!";

                finish();
            }
        }else{
            mostrarMensagem(mensagem);
        }
        mostrarMensagem(mensagem);

    }

    private String validarCamposVazios(){
        String mensagem = "";

        if(viewHolder.editDescricao.getText().length() == 0){
            mensagem = "NECESSÁRIO PREENCHER O CAMPO DESCRICAO !!!";
        }else{
            mensagem = "SUCESSO";
        }

        return mensagem;
    }

    private void criarComponentes(){
        this.viewHolder.editDescricao = (EditText) findViewById(R.id.editDescricao);

        this.viewHolder.buttonSalvar = (Button) findViewById(R.id.btnSalvar);
        this.viewHolder.buttonCancelar = (Button) findViewById(R.id.btnCancelar);
    }

    private void mostrarMensagem(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}