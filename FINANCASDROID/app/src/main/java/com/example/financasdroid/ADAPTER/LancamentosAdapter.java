package com.example.financasdroid.ADAPTER;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.financasdroid.VIEW.AtualizarLancamento;
import com.example.financasdroid.VIEW.Lancamentos;
import com.example.financasdroid.VIEW.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LancamentosAdapter extends RecyclerView.Adapter<LancamentosAdapter.WordViewHolder> {

    private ArrayList<Lancamento> mWordList;
    private LayoutInflater mInflater;
    private Context mContext;

    private Lancamentos contextoDespesas;


    public LancamentosAdapter(Context context, ArrayList<Lancamento> mWordList) {
        mInflater = LayoutInflater.from(context);
        contextoDespesas =  (Lancamentos) context;
        this.mWordList = mWordList;
    }



    class WordViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        public final TextView wordItemView;
        final LancamentosAdapter mAdapter;

        public WordViewHolder(View itemView, LancamentosAdapter adapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View v) {
            PopupMenu popMenu = new PopupMenu(v.getContext(),v);
            MenuInflater menuInflater = popMenu.getMenuInflater();
            menuInflater.inflate(R.menu.menu_lancamentos, popMenu.getMenu());


            popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Get the position of the item that was clicked.
                    int mPosition = getLayoutPosition();
                    // Use that to access the affected item in mWordList.
                    Lancamento lancamento = mWordList.get(mPosition);

                    switch (item.getItemId()){
                        case R.id.menu_exibir_item:
                            atualizarItem(lancamento);
                            break;

                        case R.id.menu_remover_item:

                            apagarItem(mPosition, lancamento);

                            break;

                        default:
                            break;
                    }

                    return false;
                }
            });

            popMenu.show();

            return false;
        }

        private void atualizarItem(Lancamento lancamento){
            Intent it = new Intent(contextoDespesas, AtualizarLancamento.class);
            System.out.println(lancamento.getIdLancamento()+"OLHA AEQUIEEEEE");
            it.putExtra("lancamento",lancamento);
            contextoDespesas.startActivity(it);
        }
        private void apagarItem(final int mPosition, final Lancamento lancamento){
            AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
            adb.setTitle("REMOVER ITEM");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    LancamentoDAO lancamentoDAO = new LancamentoDAO(contextoDespesas);
                    lancamentoDAO.excluirLancamento(lancamento);

                    mWordList.remove(mPosition);
                    mAdapter.notifyDataSetChanged();

                    Toast.makeText(mContext, "EXCLUIDO COM SUCESSO !!!", Toast.LENGTH_SHORT).show();

                }
            });
            adb.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();
        }

    }




    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View mItemView = mInflater.inflate(R.layout.lancamentosaadapter, parent, false);
        mContext = parent.getContext();
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
        final Lancamento mCurrent = mWordList.get(position);

        mostrarDados(holder, mCurrent);

    }

    private void mostrarDados(WordViewHolder holder, Lancamento mCurrent){
        CategoriaDAO categoriaDAO = new CategoriaDAO(contextoDespesas);
        Categoria categoria = categoriaDAO.getCategoriaPorID(mCurrent.getIdCategoria());

        //String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(Long.parseLong(mCurrent.getData())));

        if(mCurrent.getTipo().equals("C")){
            holder.wordItemView.setText("LANÇAMENTO " +
                    "\n\t\t\tDescrição: "+ mCurrent.getDescricao() +
                    "\n\t\t\tData: "+ mCurrent.getData() +
                    "\n\t\t\tValor: "+ mCurrent.getValor() +
                    "\n\t\t\tTipo: Crédito" +
                    "\n\t\t\tCategoria: "+ categoria.getDescricaoCategoria() +
                    "\n-------------------------------------------");
        }else{
            holder.wordItemView.setText("LANÇAMENTO " +
                    "\n\t\t\tDescrição: "+ mCurrent.getDescricao() +
                    "\n\t\t\tData: "+ mCurrent.getData() +
                    "\n\t\t\tValor: "+ mCurrent.getValor() +
                    "\n\t\t\tTipo: Débito" +
                    "\n\t\t\tCategoria: "+ categoria.getDescricaoCategoria() +
                    "\n-------------------------------------------");
        }
    }



    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}