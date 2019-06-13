package com.example.financasdroid.VIEW;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.financasdroid.ADAPTER.LancamentosAdapter;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;

import java.util.ArrayList;

public class Lancamentos extends AppCompatActivity {

    private ArrayList<Lancamento> mWordList;

    private RecyclerView mRecyclerView;
    LancamentosAdapter mAdapter;

    LancamentoDAO lancamentoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamentos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), CriarLancamento.class);
                startActivity(it);
            }
        });
    }

    private void atualizarLista(){
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        lancamentoDAO = new LancamentoDAO(this);

        mWordList = lancamentoDAO.getTodosLancamentos();

        // Create an adapter and supply the data to be displayed.
        mAdapter = new LancamentosAdapter(this,mWordList);
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
