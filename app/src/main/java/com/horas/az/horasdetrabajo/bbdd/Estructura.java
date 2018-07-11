package com.horas.az.horasdetrabajo.bbdd;

import android.provider.BaseColumns;

public class Estructura {

    public static final String SQL_CREAR_TABLA_TAREAS =
            "CREATE TABLE " + TareasTabla.NOMBRE_TABLA + " (" +
                    TareasTabla.NOMBRE_COLUMNA1 + " TEXT PRIMARY KEY," +
                    TareasTabla.NOMBRE_COLUMNA2 + " TEXT," +
                    TareasTabla.NOMBRE_COLUMNA3 + " TEXT," +
                    TareasTabla.NOMBRE_COLUMNA4 + " TEXT," +
                    TareasTabla.NOMBRE_COLUMNA5 + " TEXT)";

    public static final String SQL_CREAR_TABLA_SUPERVISORES =
            "CREATE TABLE " + SupervisoresTabla.NOMBRE_TABLA + " (" +
                    SupervisoresTabla.NOMBRE_COLUMNA1 + " TEXT PRIMARY KEY," +
                    SupervisoresTabla.NOMBRE_COLUMNA2 + " TEXT," +
                    SupervisoresTabla.NOMBRE_COLUMNA3 + " TEXT," +
                    SupervisoresTabla.NOMBRE_COLUMNA4 + " TEXT," +
                    SupervisoresTabla.NOMBRE_COLUMNA5 + " TEXT," +
                    SupervisoresTabla.NOMBRE_COLUMNA6 + " TEXT)";

    public static final String SQL_BORRAR_TABLA_TAREAS =
            "DROP TABLE IF EXISTS " + TareasTabla.NOMBRE_TABLA;
    public static final String SQL_BORRAR_TABLA_SUPERVISORES =
            "DROP TABLE IF EXISTS " + SupervisoresTabla.NOMBRE_TABLA;

    // To prevent someone from accidentally instantiating the contract class, make the constructor private.
    private Estructura() {}

    /* Inner class that defines the table contents */
    public static class TareasTabla implements BaseColumns {
        public static final String NOMBRE_TABLA = "TAREAS";
        public static final String NOMBRE_COLUMNA1 = "ID_TAREA";
        public static final String NOMBRE_COLUMNA2 = "ACTIVIDAD";
        public static final String NOMBRE_COLUMNA3 = "HORAS";
        public static final String NOMBRE_COLUMNA4 = "FECHA";
        public static final String NOMBRE_COLUMNA5 = "SUPERVISOR";
    }

    public static class SupervisoresTabla implements BaseColumns {
        public static final String NOMBRE_TABLA = "SUPERVISORES";
        public static final String NOMBRE_COLUMNA1 = "ID_SUPERVISOR";
        public static final String NOMBRE_COLUMNA2 = "NOMBRES";
        public static final String NOMBRE_COLUMNA3 = "APELLIDO_PATERNO";
        public static final String NOMBRE_COLUMNA4 = "APELLIDO_MATERNO";
        public static final String NOMBRE_COLUMNA5 = "EMAIL";
        public static final String NOMBRE_COLUMNA6 = "TELEFONO";
    }

}
