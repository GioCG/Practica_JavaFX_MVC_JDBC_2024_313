/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.modelo;

/**
 *
 * @author informatica
 */
public class LvlAcces {
    private int lvlAccesoId;
    private String lvlAcceso;

    public LvlAcces() {
        
    }

    public LvlAcces(int lvlAccesoId, String lvlAcceso) {
        this.lvlAccesoId = lvlAccesoId;
        this.lvlAcceso = lvlAcceso;
    }

    public int getLvlAccesoId() {
        return lvlAccesoId;
    }

    public void setLvlAccesoId(int lvlAccesoId) {
        this.lvlAccesoId = lvlAccesoId;
    }

    public String getLvlAcceso() {
        return lvlAcceso;
    }

    public void setLvlAcceso(String lvlAcceso) {
        this.lvlAcceso = lvlAcceso;
    }
    
    @Override
    public String toString(){
        return "Nivel de Acceso: " + lvlAcceso;
    }
}
