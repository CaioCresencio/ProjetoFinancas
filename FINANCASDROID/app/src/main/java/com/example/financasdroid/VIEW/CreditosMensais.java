package com.example.financasdroid.VIEW;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.financasdroid.ADAPTER.CreditosMensaisAdapter;
import com.example.financasdroid.ADAPTER.LancamentosAdapter;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;

import java.util.ArrayList;

public class CreditosMensais extends AppCompatActivity {

    private ArrayList<Lancamento> mWordList;

    private RecyclerView mRecyclerView;
    CreditosMensaisAdapter mAdapter;

    LancamentoDAO lancamentoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos_mensais);
    }

    private void atualizarLista(){
        // Get a handle to the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerview);

        lancamentoDAO = new LancamentoDAO(this);

        mWordList = lancamentoDAO.getLancamentoPorTipo("C");

        // Create an adapter and supply the data to be displayed.
        mAdapter = new CreditosMensaisAdapter(this,mWordList);
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