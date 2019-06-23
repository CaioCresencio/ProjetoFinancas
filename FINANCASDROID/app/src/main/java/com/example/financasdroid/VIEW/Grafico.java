package com.example.financasdroid.VIEW;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.financasdroid.DAO.CategoriaDAO;
import com.example.financasdroid.DAO.LancamentoDAO;
import com.example.financasdroid.MODEL.Categoria;
import com.example.financasdroid.MODEL.Lancamento;
import com.example.financasdroid.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Grafico extends AppCompatActivity {

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        pieChart = findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(95f);

        ArrayList<PieEntry> valores = new ArrayList<>();
        CategoriaDAO categoriaDAO = new CategoriaDAO(this);
        LancamentoDAO lancamentoDAO = new LancamentoDAO(this);

        ArrayList<Categoria> categorias = categoriaDAO.getTodasCategorias();


        for(Categoria c: categorias){
            if((float) lancamentoDAO.getValorPorCategoria(c.getIdCategoria()) != 0.0){
                valores.add(new PieEntry((float) lancamentoDAO.getValorPorCategoria(c.getIdCategoria()), c.getDescricaoCategoria()));
            }
        }

        PieDataSet dataSet = new PieDataSet(valores,"CATEGORIAS:");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData(dataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        pieChart.setData(pieData);

    }
}