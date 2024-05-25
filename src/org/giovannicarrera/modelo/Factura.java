/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author informatica
 */
public class Factura {
    private int facturaId;
    private Date fecha;
    private Time hora;
    private int clienteId;
    private int empleadoId;
    private Double total;

    public Factura() {
        
    }

    public Factura(int facturaId, Date fecha, Time hora, int clienteId, int empleadoId, Double total) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.total = total;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Id: "+ facturaId +"|" + fecha +"|" + hora +"|" + total;
    }
    
    
}
