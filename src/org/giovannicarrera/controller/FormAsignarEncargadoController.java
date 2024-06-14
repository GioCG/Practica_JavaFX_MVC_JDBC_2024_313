
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
    private static ResultSet resultSet = null;
    
    @FXML
    Button btnGuardar,btnCancelar;
    @FXML
    ComboBox cmbEmpleadoId,cmbEncargadoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnGuardar){
            asignarEncargado();
            stage.menuEmpleadosView();
            }  
                
        }
            
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEmpleadoId.setItems(listarEmpleado());
        cmbEncargadoId.setItems(listarEmpleado());
    }    
    
    public void asignarEncargado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AsignarEncargado(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Empleado)cmbEmpleadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.setInt(2, ((Empleado)cmbEncargadoId.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
        e.printStackTrace();
        }finally{
            try{
                if(statement !=null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
           }catch(SQLException e){
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
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
