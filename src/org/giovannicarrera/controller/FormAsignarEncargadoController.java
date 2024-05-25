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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.system.Main;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class FormAsignarEncargadoController implements Initializable {
    private Main stage;

    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    Button btnGuardar,btnCancelar;
    @FXML
    ComboBox cmbClienteId,cmbEncargadoId;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void asignarEncargado() throws SQLException{
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AsignarEncargado(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt((String) cmbClienteId.getValue()));
            statement.setInt(2, Integer.parseInt((String) cmbEncargadoId.getValue()));
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
}
