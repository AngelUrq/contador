package com.horas.az.horasdetrabajo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.horas.az.horasdetrabajo.adaptadores.AdaptadorTareas;
import com.horas.az.horasdetrabajo.bbdd.Estructura;
import com.horas.az.horasdetrabajo.bbdd.Helper;
import com.horas.az.horasdetrabajo.entidades.Tarea;

import java.util.ArrayList;

public class ListaHorasActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_horas);

        ArrayList<Tarea> tareas = new ArrayList<>();

        final Helper helper = new Helper(this);

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                Estructura.TareasTabla.NOMBRE_COLUMNA1,
                Estructura.TareasTabla.NOMBRE_COLUMNA2,
                Estructura.TareasTabla.NOMBRE_COLUMNA3,
                Estructura.TareasTabla.NOMBRE_COLUMNA4,
                Estructura.TareasTabla.NOMBRE_COLUMNA5
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
            String id = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA1));
            String actividad = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA2));
            String horas = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA3));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA4));
            String supervisor = cursor.getString(cursor.getColumnIndexOrThrow(Estructura.TareasTabla.NOMBRE_COLUMNA5));

            tareas.add(new Tarea(id,actividad,horas,fecha,supervisor));
        }

        ListView lista =  findViewById(R.id.lista);
        AdaptadorTareas adapter = new AdaptadorTareas(this, tareas);
        lista.setAdapter(adapter);


    }
}
