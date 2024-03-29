package com.example.financasdroid.ADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;
import com.example.financasdroid.VIEW.CreditosMensais;
import com.example.financasdroid.VIEW.DebitosMensais;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DebitosMensaisAdapter extends RecyclerView.Adapter<DebitosMensaisAdapter.WordViewHolder> {

    private ArrayList<Lancamento> mWordList;
    private LayoutInflater mInflater;
    private Context mContext;

    private DebitosMensais contextoDebitossMensais;


    public DebitosMensaisAdapter(Context context, ArrayList<Lancamento> mWordList) {
        mInflater = LayoutInflater.from(context);
        contextoDebitossMensais =  (DebitosMensais) context;
        this.mWordList = mWordList;
    }



    class WordViewHolder extends RecyclerView.ViewHolder{

        public final TextView wordItemView;
        public final TextView viewValor;
        final DebitosMensaisAdapter mAdapter;

        public WordViewHolder(View itemView, DebitosMensaisAdapter adapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.word);
            this.viewValor = itemView.findViewById(R.id.textViewValor);
            this.mAdapter = adapter;
        }

    }




    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.debitosmensaisadapter, parent, false);
        mContext = parent.getContext();
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
        final Lancamento mCurrent = mWordList.get(position);

        mostrarDados(holder, mCurrent);

    }

    private void mostrarDados(WordViewHolder holder, Lancamento mCurrent){
        CategoriaDAO categoriaDAO = new CategoriaDAO(contextoDebitossMensais);
        Categoria categoria = categoriaDAO.getCategoriaPorID(mCurrent.getIdCategoria());

        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(mCurrent.getData())));


        holder.wordItemView.setText(
                mCurrent.getDescricao() +
                        "\n"+ mCurrent.getData());
        holder.viewValor.setText("R$: "+mCurrent.getValor());

    }


    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}
