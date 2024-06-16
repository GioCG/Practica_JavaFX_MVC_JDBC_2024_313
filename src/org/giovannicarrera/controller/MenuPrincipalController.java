
package org.giovannicarrera.controller;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.report.GenerarReporte;


public class MenuPrincipalController implements Initializable{
    private Main stage;
    @FXML
    MenuItem btnMenuClientes,btnMenuTiketSop,btnMenuProductos,btnMenuCargos,
            btnMenuCategoriaProductos,btnMenuEmpleados,btnMenuDistribuidores,
            btnMenuPromociones,btnMenuFacturas,btnRepoCliente,btnRepoProducto,
            btnRepoFactura,btnRepoDistribuidores,btnRepoEmpleados;
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
        }else if(event.getSource() == btnRepoCliente){
            GenerarReporte.getInstance().generarClientes();
        }else if(event.getSource() == btnRepoProducto){
            GenerarReporte.getInstance().generarProductos();
        }else if(event.getSource() == btnRepoFactura){
            stage.menuFacturaView();
        }else if(event.getSource() == btnRepoDistribuidores){
            GenerarReporte.getInstance().generarDistribuidores();
        }else if(event.getSource() == btnRepoEmpleados){
            GenerarReporte.getInstance().generarEmpleado();
        }
    }
    
    
}
