/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

import java.sql.Blob;

public class Productos {
    private int productoId;
    private String nombreProducto;
    private String descripcionProducto;
    private int cantidadStock;
    private Double precioVentaUnitario;
    private Double precioVentaMayor;
    private Double precioCompra;
    private Blob image;
    private String distribuidor;
    private String categoria;
    private int distribuidorId;
    private int categoriaProductosId;

    public Productos() {
    }

    public Productos(int productoId, String nombreProducto, String descripcionProducto, int cantidadStock, Double precioVentaUnitario, Double precioVentaMayor, Double precioCompra, Blob image, String distribuidor, String categoria) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.descripcionProducto = descripcionProducto;
        this.cantidadStock = cantidadStock;
        this.precioVentaUnitario = precioVentaUnitario;
        this.precioVentaMayor = precioVentaMayor;
        this.precioCompra = precioCompra;
        this.image = image;
        this.distribuidor = distribuidor;
        this.categoria = categoria;
    }

    

    
    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public Double getPrecioVentaUnitario() {
        return precioVentaUnitario;
    }

    public void setPrecioVentaUnitario(Double precioVentaUnitario) {
        this.precioVentaUnitario = precioVentaUnitario;
    }

    public Double getPrecioVentaMayor() {
        return precioVentaMayor;
    }

    public void setPrecioVentaMayor(Double precioVentaMayor) {
        this.precioVentaMayor = precioVentaMayor;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public String getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(String distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getDistribuidorId() {
        return distribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        this.distribuidorId = distribuidorId;
    }

    public int getCategoriaProductosId() {
        return categoriaProductosId;
    }

    public void setCategoriaProductosId(int categoriaProductosId) {
        this.categoriaProductosId = categoriaProductosId;
    }

    @Override
    public String toString() {
        return "Productos{" + "productoId=" + productoId + ", nombreProducto=" + nombreProducto + ", descripcionProducto=" + descripcionProducto + ", cantidadStock=" + cantidadStock + ", precioVentaUnitario=" + precioVentaUnitario + ", precioVentaMayor=" + precioVentaMayor + ", precioCompra=" + precioCompra + ", image=" + image + ", distribuidor=" + distribuidor + ", categoria=" + categoria + ", distribuidorId=" + distribuidorId + ", categoriaProductosId=" + categoriaProductosId + '}';
    }

    


}
