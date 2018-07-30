package com.horas.az.horasdetrabajo.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.horas.az.horasdetrabajo.R;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.entrada_hora, null);
        }

        Tarea dir = items.get(position);

        EditText actividad = v.findViewById(R.id.entrada_actividad);
        actividad.setText(dir.getActividad());

        EditText fecha = v.findViewById(R.id.entrada_fecha);
        fecha.setText(dir.getFecha());

        EditText horas = v.findViewById(R.id.entrada_horas);
        horas.setText(dir.getHoras());

        EditText supervisor = v.findViewById(R.id.entrada_supervisor);
        supervisor.setText(dir.getSupervisor());

        return v;
    }

}