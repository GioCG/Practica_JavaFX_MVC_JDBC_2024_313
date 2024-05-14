/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.ClienteDTO;
import org.giovannicarrera.modelo.Cliente;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormClienteController implements Initializable {
    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    Button btnGuardar,btnCancelar;
    @FXML    
    TextField tfClienteId,tfNombre,tfApellido,tfTelefono,tfDireccion,tfNIT;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws SQLException{
        if(event.getSource()== btnCancelar){
            stage.menuClientesView();
        }if(event.getSource()== btnGuardar){
            if(op==1){
                if( !tfNombre.getText().equals("") &&  !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")){
                    agregarCliente();
                    SuperKinalAlert.getInstance().mostrarAlertInfo(400);
                    stage.menuClientesView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertInfo(600);
                    tfNombre.requestFocus();
                }
                
            }else if(op ==2){
                if( !tfNombre.getText().equals("") &&  !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertConf(550).get() == ButtonType.OK){
                        editarCliente();
                        SuperKinalAlert.getInstance().mostrarAlertInfo(500);
                        stage.menuClientesView();
                        ClienteDTO.getClienteDTO().setCliente(null);
                    }else{
                        stage.menuClientesView();
                    }     
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertInfo(600);
                    tfNombre.requestFocus();
                }
                
            }
            
        }
    }
        
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        if(ClienteDTO.getClienteDTO().getCliente()!= null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
        
    }    
    
    public void cargarDatos(Cliente cliente){
        tfClienteId.setText(Integer.toString(cliente.getClienteId()));
        tfNIT.setText(cliente.getNIT());
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
    }
    public void agregarCliente(){
      
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarClientes(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfNIT.getText());
            statement.setString(2,tfNombre.getText());
            statement.setString(3,tfApellido.getText());
            statement.setString(4,tfTelefono.getText());
            statement.setString(5,tfDireccion.getText());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
            System.out.println(e.getMessage());
            }
        }
    }
   
    public void editarCliente() throws SQLException{
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarCliente(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfClienteId.getText()));
            statement.setString(2,tfNIT.getText());
            statement.setString(3,tfNombre.getText());
            statement.setString(4,tfApellido.getText());
            statement.setString(5,tfTelefono.getText());
            statement.setString(6,tfDireccion.getText());
            statement.execute();
        }catch(SQLException e){
        System.out.println(e.getMessage());
        }finally{
            try{
                if(statement !=null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
           }catch(SQLException e){
               System.out.println(e.getMessage());
           }
        }
        
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
}
