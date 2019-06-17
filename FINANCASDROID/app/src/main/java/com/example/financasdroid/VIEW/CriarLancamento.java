package com.example.financasdroid.VIEW;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CriarLancamento extends AppCompatActivity {

    private static class ViewHolder{
        EditText editDescricao;
        EditText editValor;
        EditText editData;

        ImageButton imageButtonData;

        Spinner spinnerTipo;
        Spinner spinnerCategoria;

        Button buttonSalvar;
        Button buttonCancelar;
    }

    public ViewHolder viewHolder = new ViewHolder();


    private LancamentoDAO lancamentoDAO;
    private Lancamento lancamento;

    private ArrayAdapter adapter;
    private int posicaoSpinnerCategoria;
    private int posicaoSpinnerTipo;
    private ArrayList<Categoria> listCategoria;
    private CategoriaDAO categoriaDAO;

    private String[] listTipo = {"C", "D"};
    private Categoria categoria;
    private String tipo;

    private String dataSelecionada;
    private long dataLongSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_lancamentos);

        criarComponentes();

        categoriaDAO = new CategoriaDAO(this);
        listCategoria = categoriaDAO.getTodasCategorias();

        adapter = new ArrayAdapter<>(CriarLancamento.this, android.R.layout.simple_list_item_1, listCategoria);
        viewHolder.spinnerCategoria.setAdapter(adapter);

        adapter = new ArrayAdapter<>(CriarLancamento.this, android.R.layout.simple_list_item_1, listTipo);
        viewHolder.spinnerTipo.setAdapter(adapter);

        viewHolder.spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerCategoria = (Integer) viewHolder.spinnerCategoria.getSelectedItemPosition();
                categoria = listCategoria.get(posicaoSpinnerCategoria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.spinnerTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                posicaoSpinnerTipo = (Integer) viewHolder.spinnerTipo.getSelectedItemPosition();
                tipo = listTipo[posicaoSpinnerTipo];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        viewHolder.imageButtonData.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CriarLancamento.this);
                View view = getLayoutInflater().inflate(R.layout.calendario, null);

                final CalendarView widget = (CalendarView) view.findViewById(R.id.calendarView);

                builder.setView(view);
                final AlertDialog dialog = builder.create();
                dialog.show();

                widget.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {


                        Calendar c = Calendar.getInstance();
                        c.set(year,month,dayOfMonth); // dia de hoje
                        dataLongSelecionada = c.getTimeInMillis();


                        int ano = year;
                        int mes = month+1;//Inicia de 0
                        int dia = dayOfMonth;

                        dataSelecionada = dia + "-" + mes + "-" + ano;
                        viewHolder.editData.setText(dataSelecionada);
                        dialog.cancel();
                    }
                });



            }
        });



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
        String mensagem = validarCamposVazios();
        String descricao = "";
        String data = "";
        //String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        double valor = 0.0;


        if(mensagem.equals("SUCESSO")){
            if(categoria != null){
                descricao = viewHolder.editDescricao.getText().toString();
                valor = Double.parseDouble(viewHolder.editValor.getText().toString());
                //data = viewHolder.dataTeste.getText().toString();


                lancamento = new Lancamento(descricao, dataSelecionada, valor, tipo, categoria.getIdCategoria());
                lancamentoDAO = new LancamentoDAO(this);
                lancamentoDAO.inserirLancamento(lancamento);


                mensagem = "CADASTRADO COM SUCESSO !!!";

                finish();

            }else{
                mensagem = "ERRO - NECESSÁRIO PELO MENOS UMA CATEGORIA !!!";
            }

        }else{
            mostrarMensagem(mensagem);

        }
        mostrarMensagem(mensagem);
    }

    private String validarCamposVazios(){
        String mensagem = "";

        if(viewHolder.editDescricao.getText().length() == 0){
            mensagem = "NECESSÁRIO PREENCHER O CAMPO DESCRIÇÃO !!!";
        }else if(viewHolder.editValor.getText().length() == 0){
            mensagem = "NECESSÁRIO PREENCHER O CAMPO VALOR !!!";
        }
        else{
            mensagem = "SUCESSO";
        }

        return mensagem;
    }

    private void mostrarMensagem(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }

    private void criarComponentes(){
        //this.viewHolder.dataTeste = (TextView) findViewById(R.id.textViewTeste);
        this.viewHolder.editDescricao = (EditText) findViewById(R.id.editDescricao);
        this.viewHolder.editValor = (EditText) findViewById(R.id.editValor);
        this.viewHolder.editData = (EditText) findViewById(R.id.editData);

        this.viewHolder.spinnerTipo = (Spinner) findViewById(R.id.spinnerTipo);
        this.viewHolder.spinnerCategoria = (Spinner) findViewById(R.id.spinnerCategoria);


        this.viewHolder.imageButtonData = (ImageButton) findViewById(R.id.imageButtonData);
        this.viewHolder.buttonSalvar = (Button) findViewById(R.id.btnSalvar);
        this.viewHolder.buttonCancelar = (Button) findViewById(R.id.btnCancelar);
    }
}