package com.horas.az.horasdetrabajo.entidades;

public class Tarea {

    private String id;
    private String actividad;
    private String horas;
    private String fecha;
    private String supervisor;

    public Tarea(String actividad, String horas, String fecha, String supervisor) {
        this.actividad = actividad;
        this.horas = horas;
        this.fecha = fecha;
        this.supervisor = supervisor;
    }

    public Tarea(String id, String actividad, String horas, String fecha, String supervisor) {
        this.id = id;
        this.actividad = actividad;
        this.horas = horas;
        this.fecha = fecha;
        this.supervisor = supervisor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

}
