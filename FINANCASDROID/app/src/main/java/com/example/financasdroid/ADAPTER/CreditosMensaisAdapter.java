package com.example.financasdroid.ADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;
import com.example.financasdroid.VIEW.CreditosMensais;
import com.example.financasdroid.VIEW.Lancamentos;

import java.util.ArrayList;

public class CreditosMensaisAdapter extends RecyclerView.Adapter<CreditosMensaisAdapter.WordViewHolder> {

    private ArrayList<Lancamento> mWordList;
    private LayoutInflater mInflater;
    private Context mContext;

    private CreditosMensais contextoCreditosMensais;


    public CreditosMensaisAdapter(Context context, ArrayList<Lancamento> mWordList) {
        mInflater = LayoutInflater.from(context);
        contextoCreditosMensais =  (CreditosMensais) context;
        this.mWordList = mWordList;
    }



    class WordViewHolder extends RecyclerView.ViewHolder{

        public final TextView wordItemView;
        final CreditosMensaisAdapter mAdapter;

        public WordViewHolder(View itemView, CreditosMensaisAdapter adapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.word);
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
        final Lancamento mCurrent = mWordList.get(position);

        mostrarDados(holder, mCurrent);

    }

    private void mostrarDados(WordViewHolder holder, Lancamento mCurrent){
        CategoriaDAO categoriaDAO = new CategoriaDAO(contextoCreditosMensais);
        Categoria categoria = categoriaDAO.getCategoriaPorID(mCurrent.getIdCategoria());

            holder.wordItemView.setText("LANÇAMENTO " +
                    "\n\t\t\tDescrição: "+ mCurrent.getDescricao() +
                    "\n\t\t\tData: "+ mCurrent.getData() +
                    "\n\t\t\tValor: "+ mCurrent.getValor() +
                    "\n\t\t\tTipo: Crédito" +
                    "\n\t\t\tCategoria: "+ categoria.getDescricaoCategoria() +
                    "\n-------------------------------------------");

    }


    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}
