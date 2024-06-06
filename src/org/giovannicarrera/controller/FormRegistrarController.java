/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.modelo.LvlAcces;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.ContraUtils;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FormRegistrarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static Connection conexion;
    private static PreparedStatement statement;

    private static ResultSet resultSet;
    private Main stage;

    @FXML
    TextField tfUsuar, tfContraseña;
    @FXML
    ComboBox cmbEmpleado, cmbLvlAcces;
    @FXML
    Button btnRegistrar, btnCancelar, btnAgregarEmpledo,btnAgregarCargo;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegistrar){
            crearUsuario();
            stage.formLoginView();
        }else if(event.getSource() == btnCancelar){
            stage.formLoginView();
        }else if(event.getSource() == btnAgregarEmpledo){
            stage.formEmpleadosView(1);
        }else if(event.getSource() == btnAgregarCargo){
            stage.menuCargosView();
        }
    }
    
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        cmbEmpleado.setItems(listarEmpleado());
        cmbLvlAcces.setItems(listarLvl());
    }  
    
    public void crearUsuario(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarUsuar(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfUsuar.getText());
            statement.setString(2, ContraUtils.getInstance().encryptedPassword(tfContraseña.getText()));
            statement.setInt(3, ((LvlAcces)cmbLvlAcces.getSelectionModel().getSelectedItem()).getLvlAccesoId());
            statement.setInt(4, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
                if(conexion != null){
                    conexion.close();
                }
                if(statement != null){
                    statement.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public ObservableList<Empleado> listarEmpleado(){
        ArrayList<Empleado> empleado = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleadoComp()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("cargo");
                int encargadoId = resultSet.getInt("encargadoId");
                empleado.add(new Empleado (empleadoId,nombreEmpleado, apellidoEmpleado, sueldo,horaEntrada,horaSalida,cargo,encargadoId));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
               if(resultSet != null){
                   resultSet.close();
               }
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
        return FXCollections.observableArrayList(empleado);
    }
    
    public ObservableList <LvlAcces> listarLvl(){
        ArrayList <LvlAcces> lvlAcces = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarLvlAcces()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int lvlAccesId = resultSet.getInt("lvlAccesId");
                String LvlAcces = resultSet.getString("LvlAcces");
                lvlAcces.add(new LvlAcces(lvlAccesId, LvlAcces));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return FXCollections.observableList(lvlAcces);
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

}
