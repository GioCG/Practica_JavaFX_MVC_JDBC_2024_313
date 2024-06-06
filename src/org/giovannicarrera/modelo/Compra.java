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
public class Compra {
    private int compraId;
    private Date fechaCompra;
    private Double totalCompra;

    public Compra() {
        
    }

    public Compra(int compraId, Date fechaCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "Id: "+ compraId +"|" + fechaCompra;
    }

    
}
