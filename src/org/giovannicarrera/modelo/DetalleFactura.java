/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

/**
 *
 * @author Dell G7
 */
public class DetalleFactura {
    private int detalleFacturaId;
    private String factura;
    private String cliente;
    private int facturaId;
    private int clienteId;

    public DetalleFactura() {
        
    }

    public DetalleFactura(int detalleFacturaId, String factura, String cliente) {
        this.detalleFacturaId = detalleFacturaId;
        this.factura = factura;
        this.cliente = cliente;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" + "detalleFacturaId=" + detalleFacturaId + ", factura=" + factura + ", cliente=" + cliente + ", facturaId=" + facturaId + ", clienteId=" + clienteId + '}';
    }
    
    
}
