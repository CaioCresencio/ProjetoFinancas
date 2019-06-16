package com.example.financasdroid.VIEW;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class OrcamentoFinal extends AppCompatActivity {

    private static class ViewHolder{
        EditText editDataInicial;
        EditText editDataFinal;

        ImageButton imageButtonDataInicial;
        ImageButton imageButtonDataFinal;

        EditText editCredito;
        EditText editDebito;

        TextView txtResultado;

        Button buttonFiltrar;
        Button buttonCalcular;
    }

    public ViewHolder viewHolder = new ViewHolder();

    private String dataSelecionada;
    private double valorDebito;
    private double valorCredito;

    private LancamentoDAO lancamentoDAO;
    private ArrayList<Lancamento> listLancamento;

    private long dataInicialSelecionada;
    private long dataFinalSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orcamento_final);

        criarComponentes();

        viewHolder.imageButtonDataInicial.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrcamentoFinal.this);
                View view = getLayoutInflater().inflate(R.layout.calendario, null);

                final CalendarView widget = (CalendarView) view.findViewById(R.id.calendarView);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                widget.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        Calendar c = Calendar.getInstance();
                        c.set(year,month,dayOfMonth); // dia de hoje
                        dataInicialSelecionada = c.getTimeInMillis();

                        int ano = year;
                        int mes = month+1; // Ele inica de 0
                        int dia = dayOfMonth;

                        dataSelecionada = dia + "-" + mes + "-" + ano;
                        viewHolder.editDataInicial.setText(dataSelecionada);
                    }
                });


            }
        });

        viewHolder.imageButtonDataFinal.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrcamentoFinal.this);
                View view = getLayoutInflater().inflate(R.layout.calendario, null);

                final CalendarView widget = (CalendarView) view.findViewById(R.id.calendarView);

                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();

                widget.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        Calendar c = Calendar.getInstance();
                        c.set(year,month,dayOfMonth); // dia de hoje
                        dataFinalSelecionada = c.getTimeInMillis();

                        int ano = year;
                        int mes = month+1; // Ele inica de 0
                        int dia = dayOfMonth;

                        dataSelecionada = dia + "-" + mes + "-" + ano;
                        viewHolder.editDataFinal.setText(dataSelecionada);
                    }
                });


            }
        });


        viewHolder.buttonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonFiltrarClicado();
            }
        });

        viewHolder.buttonCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCalcularClicado();
            }
        });
    }



    private void buttonFiltrarClicado(){
        String mensagem = validarCamposVazios();
        valorDebito = 0.0;
        valorCredito = 0.0;

        if(mensagem.equals("SUCESSO")){
            lancamentoDAO = new LancamentoDAO(this);
            listLancamento = lancamentoDAO.getTodosLancamentos();
            System.out.println("Tamanho lista: " + listLancamento.size());
            System.out.println("Data inicial em long: " + dataInicialSelecionada);
            System.out.println("Data final em long: " + dataFinalSelecionada);

            for (Lancamento l:listLancamento) {
                System.out.println("Data Lancamento  em Long: " + l.getData());

                if ((Long.parseLong(l.getData()) > dataInicialSelecionada) && (Long.parseLong(l.getData()) < dataFinalSelecionada)){
                    System.out.println("Tipo: " + l.getTipo());
                    if(l.getTipo().equals("C")){
                        valorCredito+=l.getValor();
                    }else{
                        valorDebito+=l.getValor();
                    }
                }
            }

            viewHolder.editDebito.setText(String.valueOf(valorDebito));
            viewHolder.editCredito.setText(String.valueOf(valorCredito));
            viewHolder.buttonCalcular.setEnabled(true);

        }else{
            mostrarMensagem(mensagem);
        }

    }

    private void buttonCalcularClicado(){
        viewHolder.txtResultado.setText("Orçamento Final: " + (valorCredito - valorDebito));
    }

    private String validarCamposVazios(){
        String mensagem = "";

        if(viewHolder.editDataInicial.getText().length() == 0){
            mensagem = "NECESSÁRIO PREENCHER O CAMPO DATA INICIAL !!!";
        }else{
            mensagem = "SUCESSO";
        }

        return mensagem;
    }

    private void criarComponentes(){
        this.viewHolder.editDataInicial = (EditText) findViewById(R.id.editDataInicial);
        this.viewHolder.editDataFinal = (EditText) findViewById(R.id.editDataFinal);
        this.viewHolder.editCredito = (EditText) findViewById(R.id.editCreditos);
        this.viewHolder.editDebito = (EditText) findViewById(R.id.editDebitos);

        this.viewHolder.imageButtonDataInicial = (ImageButton) findViewById(R.id.imageButtonDataInicial);
        this.viewHolder.imageButtonDataFinal = (ImageButton) findViewById(R.id.imageButtonDataFinal);

        this.viewHolder.txtResultado= (TextView) findViewById(R.id.txtOrcamentoFinal);


        this.viewHolder.buttonFiltrar = (Button) findViewById(R.id.btnFiltrar);
        this.viewHolder.buttonCalcular = (Button) findViewById(R.id.btnCalcular);
    }

    private void mostrarMensagem(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
}
