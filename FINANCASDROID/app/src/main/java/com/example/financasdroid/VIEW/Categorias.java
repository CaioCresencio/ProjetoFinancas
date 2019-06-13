package com.example.financasdroid.VIEW;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.financasdroid.ADAPTER.CategoriaAdapter;
import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.R;

import java.util.ArrayList;

public class Categorias extends AppCompatActivity {


    private ArrayList<Categoria> mWordList;

    private RecyclerView mRecyclerView;
    CategoriaAdapter mAdapter;

    CategoriaDAO categoriaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), CriarCategoria.class);
                startActivity(it);
            }
        });
    }

    private void atualizarLista(){
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        categoriaDAO = new CategoriaDAO(this);


        mWordList = categoriaDAO.getTodasCategorias();

        // Create an adapter and supply the data to be displayed.
        mAdapter = new CategoriaAdapter(this,mWordList);
        //mAdapter = MercadoListAdapter.getInstance(this,mWordList);
        // Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }
}
