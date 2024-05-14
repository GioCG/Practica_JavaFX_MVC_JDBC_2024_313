/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.dto;

import org.giovannicarrera.modelo.Empleado;

/**
 *
 * @author Dell G7
 */
public class EmpleadosDTO {
    private static EmpleadosDTO instance;
    private Empleado empleado;
    
    private EmpleadosDTO(){
        
    }
    
    public static EmpleadosDTO getEmpleadosDTO(){
        if(instance == null){
            instance = new EmpleadosDTO();
        }
        return instance;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}
