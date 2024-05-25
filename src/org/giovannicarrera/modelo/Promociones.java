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
    private Double precioPromocion;
    private String descripcionPromocion;
    private Date fechaInicio;
    private Date fechaFinal;
    private String productos;
    private int productoId;

    public Promociones() {
        
    }

    public Promociones(int promocionId, Double precioPromocion, String descripcionPromocion, Date fechaInicio, Date fechaFinal, String productos) {
        this.promocionId = promocionId;
        this.precioPromocion = precioPromocion;
        this.descripcionPromocion = descripcionPromocion;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.productos = productos;
    }

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
    }

    public Double getPrecioPromocion() {
        return precioPromocion;
    }

    public void setPrecioPromocion(Double precioPromocion) {
        this.precioPromocion = precioPromocion;
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

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "Promociones{" + "promocionId=" + promocionId + ", precioPromocion=" + precioPromocion + ", descripcionPromocion=" + descripcionPromocion + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", productos=" + productos + ", productoId=" + productoId + '}';
    }
}
