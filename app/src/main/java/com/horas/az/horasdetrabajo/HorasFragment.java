package com.horas.az.horasdetrabajo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.horas.az.horasdetrabajo.bbdd.Estructura;
import com.horas.az.horasdetrabajo.bbdd.Helper;
import com.horas.az.horasdetrabajo.generador.Generador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HorasFragment extends Fragment {

    private Button boton_registrar;
    private Button boton_listar;

    private EditText et_actividad,et_horas, et_fecha;

    private Spinner sp_supervisor;

    private String[] codigosSupervisores;
    private String[] nombresSupervisores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horas, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        et_actividad = getView().findViewById(R.id.et_actividad);
        et_horas = getView().findViewById(R.id.et_horas);
        et_fecha = getView().findViewById(R.id.et_fecha);

        listarSupervisores();

        sp_supervisor = getView().findViewById(R.id.sp_supervisor);
        sp_supervisor.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresSupervisores));

        boton_registrar = getView().findViewById(R.id.boton_registrar);
        boton_registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        boton_listar = getView().findViewById(R.id.boton_listar);
        boton_listar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent lista = new Intent(getContext(), ListaHorasActivity.class);
                startActivity(lista);
            }
        });


        iniciarFecha();
    }

    public void registrar(){
        boolean validacion = true;

        String actividad = et_actividad.getText().toString();
        String horas = et_horas.getText().toString();
        String fecha = et_fecha.getText().toString();
        String supervisor = "";

        try{
            supervisor = buscarCodigo(sp_supervisor.getSelectedItem().toString());
        } catch (Exception e) {
            e.printStackTrace();
            validacion = false;
        }

        if(!horas.matches("[0-9]+(:[0-5][0-9])?")){
            Toast.makeText(getView().getContext(), "Las horas deben estar en formato HH:MM", Toast.LENGTH_LONG).show();
            validacion = false;
        }
        if(!comprobarFecha(fecha)){
            Toast.makeText(getView().getContext(), "La fecha no es correcta. No olvides que debe estar en formato DD/MM/YYYY", Toast.LENGTH_LONG).show();
            validacion = false;
        }

        if(validacion){
            final Helper helper = new Helper(getView().getContext());

            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Estructura.TareasTabla.NOMBRE_COLUMNA1, Generador.generarCodigo());
            values.put(Estructura.TareasTabla.NOMBRE_COLUMNA2, actividad);
            values.put(Estructura.TareasTabla.NOMBRE_COLUMNA3, horas);
            values.put(Estructura.TareasTabla.NOMBRE_COLUMNA4, fecha);
            values.put(Estructura.TareasTabla.NOMBRE_COLUMNA5, supervisor);

            db.insert(Estructura.TareasTabla.NOMBRE_TABLA, null, values);

            Toast.makeText(getView().getContext(), "¡Registro insertado correctamente!", Toast.LENGTH_LONG).show();

            getActivity().recreate();
        }
    }

    private boolean comprobarFecha(String fecha) {
        if(!fecha.matches("([1-3]?[0-9]|[0-3]?[1-9])(/|-)[0-1][0-9](/|-)[0-9][0-9][0-9][0-9]")){
            return false;
        }

        String[] fechas = fecha.split("/|-");

        boolean validacion = true;

        int dia = Integer.parseInt(fechas[0]);
        int mes = Integer.parseInt(fechas[1]);
        int año = Integer.parseInt(fechas[2]);

        int maximo = 30;

        if (mes == 2) {
            if(año % 4 == 0 && (año % 100 != 0 || año % 400 == 0)) {
                maximo = 29;
            } else {
                maximo = 28;
            }
        } else {
            if((mes < 8 && mes % 2 != 0) || (mes > 8 && mes % 2 == 0 ) || mes == 8) {
                maximo = 31;
            }
        }

        if(dia > maximo || dia == 0){
            validacion = false;
        }

        return validacion;
    }


    private void iniciarFecha(){
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        et_fecha.setText(fecha);
    }

    private void listarSupervisores(){
        final Helper helper = new Helper(getView().getContext());

        SQLiteDatabase db = helper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                Estructura.SupervisoresTabla.NOMBRE_COLUMNA1,
                Estructura.SupervisoresTabla.NOMBRE_COLUMNA2,
                Estructura.SupervisoresTabla.NOMBRE_COLUMNA3
        };

        Cursor cursor = db.query(
                Estructura.SupervisoresTabla.NOMBRE_TABLA,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        ArrayList<String> listaNombres = new ArrayList<>();
        ArrayList<String> listaCodigos = new ArrayList<>();

        while(cursor.moveToNext()){
            listaNombres.add(cursor.getString(cursor.getColumnIndexOrThrow(Estructura.SupervisoresTabla.NOMBRE_COLUMNA2)) + " " +cursor.getString( cursor.getColumnIndexOrThrow(Estructura.SupervisoresTabla.NOMBRE_COLUMNA3)));
            listaCodigos.add(cursor.getString(cursor.getColumnIndexOrThrow(Estructura.SupervisoresTabla.NOMBRE_COLUMNA1)));
        }

        nombresSupervisores = new String[listaNombres.size()];
        codigosSupervisores = new String[listaCodigos.size()];
        for(int i = 0; i < listaNombres.size(); i++){
            nombresSupervisores[i] = listaNombres.get(i);
            codigosSupervisores[i] = listaCodigos.get(i);
        }
    }

    private String buscarCodigo(String nombres) {
        String codigo = "";

        for(int i = 0; i < codigosSupervisores.length; i++){
            if(nombres == nombresSupervisores[i]){
                codigo = codigosSupervisores[i];
                break;
            }
        }

        return codigo;
    }

}
