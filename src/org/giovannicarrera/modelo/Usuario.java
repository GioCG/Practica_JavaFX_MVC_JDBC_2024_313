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
public class Usuario {
    private int usuarioId;
    private String usuario;
    private String password;
    private int nivelesAccesoId;
    private int empleadoId;
    public Usuario(){
    }
 
    public Usuario(int usuarioId, String usuario, String password, int nivelesAccesoId, int empleadoId) {
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.password = password;
        this.nivelesAccesoId = nivelesAccesoId;
        this.empleadoId = empleadoId;
    }
 
    public int getUsuarioId() {
        return usuarioId;
    }
 
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
 
    public String getUsuario() {
        return usuario;
    }
 
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public int getNivelesAccesoId() {
        return nivelesAccesoId;
    }
 
    public void setNivelesAccesoId(int nivelesAccesoId) {
        this.nivelesAccesoId = nivelesAccesoId;
    }
 
    public int getEmpleadoId() {
        return empleadoId;
    }
 
    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    @Override
    public String toString(){
        return "ID: " + usuarioId + " | " + usuario;
    }
}
