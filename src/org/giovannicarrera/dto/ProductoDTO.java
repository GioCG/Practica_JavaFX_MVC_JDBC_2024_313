/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.dto;

import org.giovannicarrera.modelo.Productos;

/**
 *
 * @author informatica
 */
public class ProductoDTO {
    private static ProductoDTO instance;
    private Productos producto;
    
    private ProductoDTO(){
        
    }
    
    public static ProductoDTO getProductoDTO(){
        if(instance == null){
            instance = new ProductoDTO();
        }
        return instance;
    }

    public Productos getProducto() {
        return producto;
    }

    public void setProducto(Productos producto) {
        this.producto = producto;
    }
}
