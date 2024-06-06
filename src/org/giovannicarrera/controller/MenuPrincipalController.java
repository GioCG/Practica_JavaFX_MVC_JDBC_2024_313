/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.giovannicarrera.system.Main;

public class MenuPrincipalController implements Initializable{
    private Main stage;
    @FXML
    MenuItem btnMenuClientes,btnMenuTiketSop,btnMenuProductos,btnMenuCargos,btnMenuCategoriaProductos,btnMenuEmpleados,btnMenuDistribuidores,btnMenuPromociones,btnMenuFacturas;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
  
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuClientes){
            stage.menuClientesView();
        }else if(event.getSource()== btnMenuTiketSop){
            stage.menuTicketSoporteView();
        }else if(event.getSource()== btnMenuProductos){
            stage.menuProductosView();
        }else if(event.getSource()== btnMenuCargos){
            stage.menuCargosView();
        }else if(event.getSource()== btnMenuCategoriaProductos){
            stage.menuCategoriaProductos();
        }else if(event.getSource()== btnMenuEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource()== btnMenuDistribuidores){
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnMenuPromociones){
            stage.menuPromocionesView();
        }else if(event.getSource() == btnMenuFacturas){
            stage.menuFacturaView();
        }
    }
    
    
}
