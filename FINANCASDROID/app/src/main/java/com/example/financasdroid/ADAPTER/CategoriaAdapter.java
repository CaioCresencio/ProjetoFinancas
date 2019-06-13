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
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.R;
import com.example.financasdroid.VIEW.Categorias;

import java.util.ArrayList;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.WordViewHolder> {

    private ArrayList<Categoria> mWordList;
    private LayoutInflater mInflater;
    private Context mContext;

    private Categorias contextoCriarCategoria;


    public CategoriaAdapter(Context context, ArrayList<Categoria> mWordList) {
        mInflater = LayoutInflater.from(context);
        contextoCriarCategoria = (Categorias) context;
        this.mWordList = mWordList;
    }



    class WordViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener{

        public final TextView wordItemView;
        final CategoriaAdapter mAdapter;

        public WordViewHolder(View itemView, CategoriaAdapter adapter) {
            super(itemView);
            this.wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            itemView.setOnLongClickListener(this);

        }

        @Override
        public boolean onLongClick(View v) {
            PopupMenu popMenu = new PopupMenu(v.getContext(),v);
            MenuInflater menuInflater = popMenu.getMenuInflater();
            menuInflater.inflate(R.menu.menu_categoria, popMenu.getMenu());


            popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // Get the position of the item that was clicked.
                    int mPosition = getLayoutPosition();
                    // Use that to access the affected item in mWordList.
                    Categoria categoria = mWordList.get(mPosition);

                    switch (item.getItemId()){
                        case R.id.menu_remover_item:

                            apagarItem(mPosition, categoria);

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

        private void apagarItem(final int mPosition, final Categoria categoria){
            AlertDialog.Builder adb = new AlertDialog.Builder(mContext);
            adb.setTitle("REMOVER ITEM");
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    CategoriaDAO categoriaDAO = new CategoriaDAO(contextoCriarCategoria);
                    categoriaDAO.excluirCategoria(categoria);

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
        View mItemView = mInflater.inflate(R.layout.criarcategoriaadapter, parent, false);
        mContext = parent.getContext();
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, final int position) {
        final Categoria mCurrent = mWordList.get(position);

        holder.wordItemView.setText("ID CATEGORIA: " + mCurrent.getIdCategoria() +
                "\n\t\t\tDescrição: "+ mCurrent.getDescricaoCategoria() +
                "\n-------------------------------------------");

    }


    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}
