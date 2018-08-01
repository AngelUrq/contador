package com.horas.az.horasdetrabajo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.horas.az.horasdetrabajo.bbdd.Estructura;
import com.horas.az.horasdetrabajo.bbdd.Helper;
import com.horas.az.horasdetrabajo.entidades.Tarea;

import java.util.ArrayList;

public class AnalisisFragment extends Fragment {

    private String horasTotales;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        horasTotales = "50:00";
        return inflater.inflate(R.layout.fragment_analisis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Helper helper = new Helper(getContext());

        SQLiteDatabase db = helper.getReadableDatabase();

        ArrayList<String> horas = new ArrayList<>();

        String[] projection = {
                Estructura.TareasTabla.NOMBRE_COLUMNA3
        };

        Cursor cursor = db.query(
                Estructura.TareasTabla.NOMBRE_TABLA,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        while(cursor.moveToNext()){
            String hora = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA3));
            horas.add(hora);
        }

        String horasAcumuladas = "0:00";
        for(int i = 0; i < horas.size(); i++){
            horasAcumuladas = sumar(horasAcumuladas, horas.get(i));
        }

        verificarProgreso(view, horasTotales, horasAcumuladas);
    }

    private void verificarProgreso(View view, String horasTotales, String horasAcumuladas){
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView texto = view.findViewById(R.id.textView5);

        String[] horasTotalesSeparadas = horasTotales.split(":");
        String[] horasAcumuladasSeparadas = horasAcumuladas.split(":");

        int minutosTotales = Integer.parseInt(horasTotalesSeparadas[0]) * 60 + Integer.parseInt(horasTotalesSeparadas[1]);
        int minutosAcumulados = Integer.parseInt(horasAcumuladasSeparadas[0]) * 60 + Integer.parseInt(horasAcumuladasSeparadas[1]);

        double progreso = ((float) minutosAcumulados / minutosTotales) * 100;

        String horasRestantes = restar(horasTotales, horasAcumuladas);
        texto.setText("Has completado un " + (int)progreso + "% de tus horas de trabajo.\nTe faltan " + horasRestantes + " horas por cumplir.");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress((int) progreso, true);
        } else{
            progressBar.setProgress((int) progreso);
        }
    }

    public String sumar(String hora1, String hora2) {
        String[] horaSeparada1 = hora1.split(":");
        String[] horaSeparada2 = hora2.split(":");

        int horas = Integer.parseInt(horaSeparada1[0]) + Integer.parseInt(horaSeparada2[0]);
        int minutos = Integer.parseInt(horaSeparada1[1]) + Integer.parseInt(horaSeparada2[1]);

        while(minutos >= 60) {
            minutos -= 60;
            horas++;
        }

        String minutos_formato = String.valueOf(minutos);
        if(minutos < 10) {
            minutos_formato += "0";
        }

        return horas + ":" + minutos_formato;
    }

    public String restar(String hora1, String hora2) {
        String[] horaSeparada1 = hora1.split(":");
        String[] horaSeparada2 = hora2.split(":");

        if(Integer.parseInt(horaSeparada2[0]) > Integer.parseInt(horaSeparada1[0])) {
            String[] aux = horaSeparada1;
            horaSeparada1 = horaSeparada2;
            horaSeparada2 = aux;
        }

        int horas = Integer.parseInt(horaSeparada1[0]) - Integer.parseInt(horaSeparada2[0]);
        int minutos = Integer.parseInt(horaSeparada1[1]) - Integer.parseInt(horaSeparada2[1]);

        if (minutos < 0) {
            minutos = 60 - minutos;
            horas--;
        }

        while(minutos >= 60) {
            minutos -= 60;
            horas++;
        }

        String minutos_formato = String.valueOf(minutos);
        if(minutos < 10) {
            minutos_formato += "0";
        }

        return horas + ":" + minutos_formato;
    }

}
