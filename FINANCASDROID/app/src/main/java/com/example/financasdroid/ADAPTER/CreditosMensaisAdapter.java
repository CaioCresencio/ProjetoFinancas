package com.example.financasdroid.ADAPTER;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;
import com.example.financasdroid.VIEW.CreditosMensais;

import java.util.ArrayList;

public class CreditosMensaisAdapter extends RecyclerView.Adapter<CreditosMensaisAdapter.WordViewHolder> {

    private ArrayList<Lancamento> lista_lancamentos;
    private LayoutInflater mInflater;
    private Context mContext;

    private CreditosMensais contextoCreditosMensais;


    public CreditosMensaisAdapter(Context context, ArrayList<Lancamento> lista_lancamentos) {
        mInflater = LayoutInflater.from(context);
        contextoCreditosMensais =  (CreditosMensais) context;
        this.lista_lancamentos = lista_lancamentos;
    }



    class WordViewHolder extends RecyclerView.ViewHolder{

        public final TextView wordItemView;
        public final TextView viewValor;
        final CreditosMensaisAdapter mAdapter;

        public WordViewHolder(View itemView, CreditosMensaisAdapter adapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.word);
            this.viewValor = itemView.findViewById(R.id.textViewValor);
            this.mAdapter = adapter;
        }

    }




    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.creditosmensaisadapter, parent, false);
        mContext = parent.getContext();
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
        final Lancamento mCurrent = lista_lancamentos.get(position);

        mostrarDados(holder, mCurrent);

    }

    private void mostrarDados(WordViewHolder holder, Lancamento mCurrent){
        CategoriaDAO categoriaDAO = new CategoriaDAO(contextoCreditosMensais);
        Categoria categoria = categoriaDAO.getCategoriaPorID(mCurrent.getIdCategoria());

            holder.wordItemView.setText(
                    "Descrição: "+ mCurrent.getDescricao() +
                    "\n"+ mCurrent.getData());
            holder.viewValor.setText("R$: "+mCurrent.getValor());

    }


    @Override
    public int getItemCount() {
        return lista_lancamentos.size();
    }


}
