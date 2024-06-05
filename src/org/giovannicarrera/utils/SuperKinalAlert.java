/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class SuperKinalAlert {

    private static SuperKinalAlert instance;
    private SuperKinalAlert(){
    }
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    public void mostrarAlertInfo(int code){
        if(code ==400){//400 agregar registro
            Alert alert  = new Alert(Alert.AlertType.INFORMATION);
            alert. setTitle("Confirmacion Registro");
            alert. setHeaderText("Confirmacion Registro");
            alert. setContentText("Registro realizado");
            alert.showAndWait(); 
        }else if (code ==500){//500 edicion de registro
            Alert alert  = new Alert(Alert.AlertType.INFORMATION);
            alert. setTitle("Confirmacion Edición De Registro");
            alert. setHeaderText("Confirmacion Edición De Registro");
            alert. setContentText(" Edición realizado");
            alert.showAndWait(); 
        }else if (code ==600){//600 alerta de campos pendientes
            Alert alert  = new Alert(Alert.AlertType.WARNING);
            alert. setTitle("Campos pendientes");
            alert. setHeaderText("Campos pendientes");
            alert. setContentText(" Capos necesarios para el registro estan en blanco");
            alert.showAndWait(); 
        }else if(code == 800){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Contraseña Incorrecta");
           alert.setHeaderText("Contraseña Incorrecta");
           alert.setContentText("La contraseña que ingreso es incorrecta!!");
           alert.showAndWait();
        }else if(code == 900){
           Alert alert = new Alert(Alert.AlertType.WARNING);
           alert.setTitle("Usuario Inexistente o Incorrecto");
           alert.setHeaderText("Usuario Inexistente o Incorrecto");
           alert.setContentText("El usuario que intenta iniciar no existe o es incorrecto!");
           alert.showAndWait();
        }
        
    }
    public Optional<ButtonType> mostrarAlertConf(int code){
        Optional<ButtonType> action = null;
         if (code ==770){//770 para eliminar registro
            Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
            alert. setTitle("Eliminar Registro");
            alert. setHeaderText("Eliminar Registro");
            alert. setContentText(" ¿Desea eliminar el registro?");
            action = alert.showAndWait();

        }if (code ==550){//550 edicion de registro
            Alert alert  = new Alert(Alert.AlertType.CONFIRMATION);
            alert. setTitle("Edición De Registro");
            alert. setHeaderText(" Edición De Registro");
            alert. setContentText(" ¿Desea editar el registro?");
            action = alert.showAndWait();
        }
         return action;
    }
    
    public void alertBienvenida(String usuario){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("BIENVENIDO!");
           alert.setHeaderText("BIENVENIDO " +  usuario);
           alert.showAndWait();
    }
    
    public Optional <ButtonType> mostrarAlertaConfirmacion(){
        Optional<ButtonType> action = null;
        return action;
    }

    
}
