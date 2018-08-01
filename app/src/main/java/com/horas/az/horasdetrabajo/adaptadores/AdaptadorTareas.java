package com.horas.az.horasdetrabajo.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.horas.az.horasdetrabajo.R;
import com.horas.az.horasdetrabajo.bbdd.Estructura;
import com.horas.az.horasdetrabajo.bbdd.Helper;
import com.horas.az.horasdetrabajo.entidades.Tarea;

import java.util.ArrayList;

public class AdaptadorTareas extends BaseAdapter{

    protected Activity activity;
    protected ArrayList<Tarea> items;

    public AdaptadorTareas(Activity activity, ArrayList<Tarea> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Tarea> category) {
        for (int i = 0; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.entrada_hora, null);
        }

        final Tarea dir = items.get(position);

        final EditText actividad = view.findViewById(R.id.entrada_actividad);
        actividad.setText(dir.getActividad());

        final EditText fecha = view.findViewById(R.id.entrada_fecha);
        fecha.setText(dir.getFecha());

        final EditText horas = view.findViewById(R.id.entrada_horas);
        horas.setText(dir.getHoras());

        final EditText supervisor = view.findViewById(R.id.entrada_supervisor);
        supervisor.setText(dir.getSupervisor());

        Button boton_guardar = view.findViewById(R.id.boton_guardar);
        boton_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button boton_borrar = view.findViewById(R.id.boton_borrar);
        boton_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Helper helper = new Helper(v.getContext());
                SQLiteDatabase db = helper.getReadableDatabase();

                String selection = Estructura.TareasTabla.NOMBRE_COLUMNA1 + " = ?;";

                String[] selectionArgs = { dir.getId() };

                int deletedRows = db.delete(Estructura.TareasTabla.NOMBRE_TABLA, selection, selectionArgs);

                Toast.makeText(v.getContext(), "Borrado con éxito: ID: " + deletedRows, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

}