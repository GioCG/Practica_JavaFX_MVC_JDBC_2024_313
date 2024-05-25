/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

import java.sql.Time;

/**
 *
 * @author Dell G7
 */
public class Empleado {
    private int empleadoId;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private Double sueldo;
    private Time horaEntrada;
    private Time horaSalida;
    private int cargoId;
    private int encargadoId;

    public Empleado() {
        
    }

    public Empleado(int empleadoId, String nombreEmpleado, String apellidoEmpleado, Double sueldo, Time horaEntrada, Time horaSalida,int cargoId, int encargadoId) {
        this.empleadoId = empleadoId;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.sueldo = sueldo;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.cargoId = cargoId;
        this.encargadoId = encargadoId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public int getCargoId() {
        return cargoId;
    }

    public void setCargoId(int cargoId) {
        this.cargoId = cargoId;
    }

    public int getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(int encargadoId) {
        this.encargadoId = encargadoId;
    }

    @Override
    public String toString() {
        return "Empleado{" + "empleadoId=" + empleadoId + ", nombreEmpleado=" + nombreEmpleado + ", apellidoEmpleado=" + apellidoEmpleado + ", sueldo=" + sueldo + ", horaEntrada=" + horaEntrada + ", horaSalida=" + horaSalida + ", cargoId=" + cargoId + ", encargadoId=" + encargadoId + '}';
    } 
    

}
