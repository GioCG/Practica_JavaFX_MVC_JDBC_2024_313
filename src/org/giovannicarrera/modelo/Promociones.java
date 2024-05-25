/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

import java.sql.Date;

/**
 *
 * @author Dell G7
 */
public class Promociones {
    private int promocionId;
    private Double precioPromocio;
    private String descripcionPromocion;
    private Date fechaInicio;
    private Date horaEntrada;
    private int productoId;

    public Promociones() {
        
    }

    public Promociones(int promocionId, Double precioPromocio, String descripcionPromocion, Date fechaInicio, Date horaEntrada, int productoId) {
        this.promocionId = promocionId;
        this.precioPromocio = precioPromocio;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.horaEntrada = horaEntrada;
        this.productoId = productoId;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public Double getPrecioPromocio() {
        return precioPromocio;
    }

    public void setPrecioPromocio(Double precioPromocio) {
        this.precioPromocio = precioPromocio;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
    
    
}
