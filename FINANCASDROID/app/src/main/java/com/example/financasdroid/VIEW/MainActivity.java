package com.example.financasdroid.VIEW;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.financasdroid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent it;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cadastrar_categoria) {
            it = new Intent(this, Categorias.class);
            startActivity(it);
            //mostrarMensagem("Cadastrar Categoria");
            return true;
        }

        if (id == R.id.action_cadastrar_lancamento) {
            it = new Intent(this, Lancamentos.class);
            startActivity(it);
            //mostrarMensagem("Inserir Lancamentos");
            return true;
        }

        if (id == R.id.action_listar_creditos_mensais) {
            it = new Intent(this, CreditosMensais.class);
            startActivity(it);
            //mostrarMensagem("Listar Creditos Mensais");
            return true;
        }

        if (id == R.id.action_listar_debitos_mensais) {
            it = new Intent(this, DebitosMensais.class);
            startActivity(it);
            //mostrarMensagem("Listar Débitos Mensais");
            return true;
        }

        if (id == R.id.action_listar_orcamento_final) {
            it = new Intent(this,OrcamentoFinal.class);
            startActivity(it);
            //mostrarMensagem("Listar Orçamento Final");
            return true;
        }

        if (id == R.id.action_exibir_grafico) {
            mostrarMensagem("Exibir Grafico Pizza");
            it = new Intent(this,Grafico.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void mostrarMensagem(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
