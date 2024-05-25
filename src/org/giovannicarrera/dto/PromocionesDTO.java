/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.dto;

import org.giovannicarrera.modelo.Promociones;

/**
 *
 * @author Dell G7
 */
public class PromocionesDTO {
    private static PromocionesDTO instance;
    private Promociones promocion;

    public Promociones getPromociones() {
        return promocion;
    }

    public void setPromociones(Promociones promocion) {
        this.promocion = promocion;
    }
    
    
    
    private PromocionesDTO (){
        
    }
    
    public static PromocionesDTO getPromocionesDTO(){
        if(instance == null){
            instance = new PromocionesDTO();
        }
        return instance;
    }
    
}
