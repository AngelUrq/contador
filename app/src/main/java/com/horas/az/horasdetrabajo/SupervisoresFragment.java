package com.horas.az.horasdetrabajo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.horas.az.horasdetrabajo.bbdd.Estructura;
import com.horas.az.horasdetrabajo.bbdd.Helper;
import com.horas.az.horasdetrabajo.generador.Generador;

public class SupervisoresFragment extends Fragment {

    private Button boton_registrar;

    private EditText et_nombres, et_apellidopaterno, et_apellidomaterno, et_telefono, et_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supervisores, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        et_nombres = getView().findViewById(R.id.et_nombres);
        et_apellidopaterno = getView().findViewById(R.id.et_apellidopaterno);
        et_apellidomaterno = getView().findViewById(R.id.et_apellidomaterno);
        et_telefono = getView().findViewById(R.id.et_telefono);
        et_email = getView().findViewById(R.id.et_email);

        boton_registrar = getView().findViewById(R.id.boton_registrar);
        boton_registrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registrar();
            }
        });
    }

    public void registrar(){
        String nombres = et_nombres.getText().toString();
        String apellidopaterno = et_apellidopaterno.getText().toString();
        String apellidomaterno = et_apellidomaterno.getText().toString();
        String telefono = et_telefono.getText().toString();
        String email = et_email.getText().toString();

        final Helper helper = new Helper(getView().getContext());

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA1, Generador.generarCodigo());
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA2, nombres);
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA3, apellidopaterno);
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA4, apellidomaterno);
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA5, email);
        values.put(Estructura.SupervisoresTabla.NOMBRE_COLUMNA6, telefono);

        db.insert(Estructura.SupervisoresTabla.NOMBRE_TABLA, null, values);

        Toast.makeText(getView().getContext(), "¡Registro insertado correctamente!", Toast.LENGTH_LONG).show();

        getActivity().recreate();
    }

}