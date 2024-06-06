/*
===========================================================================
=====================error no guarda ni sale del form======================
===========================================================================
*/
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import org.giovannicarrera.dto.EmpleadosDTO;
import org.giovannicarrera.modelo.Cargos;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.system.Main;
import java.sql.ResultSet;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * @author Dell G7
 */
public class FormEmpleadosController implements Initializable {

    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;

    @FXML
    Button btnGuardar,btnCancelar;
    @FXML    
    TextField tfEmpleadoId,tfNombreEmpleado,tfApellidoEmpleado,tfSueldo,tfHoraEntrada,tfHoraSalida;
    @FXML
    ComboBox cmbCargoId;

    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnGuardar){
            if(op == 1){
            agregarEmpleado(); 
            stage.menuEmpleadosView();
            }else if(op == 2){
                editarEmpleado();
                EmpleadosDTO.getEmpleadosDTO().setEmpleado(null);
                stage.menuEmpleadosView();
            }  
                
        }
            
    }
    
        
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        cmbCargoId.setItems(listarCargos());
        if(EmpleadosDTO.getEmpleadosDTO().getEmpleado()!= null){
            cargarDatos(EmpleadosDTO.getEmpleadosDTO().getEmpleado());
        }
        
    }    
    
    
    
    public void cargarDatos(Empleado empleado){
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        tfEmpleadoId.setText(Integer.toString(empleado.getEmpleadoId()));
        tfNombreEmpleado.setText(empleado.getNombreEmpleado());
        tfApellidoEmpleado.setText(empleado.getApellidoEmpleado());
        tfSueldo.setText(Double.toString(empleado.getSueldo()));
        tfHoraEntrada.setText(formatoHora.format(empleado.getHoraEntrada()));
        tfHoraSalida.setText(formatoHora.format(empleado.getHoraSalida()));
    }
    

    public ObservableList<Cargos> listarCargos(){
        ArrayList<Cargos> cargos = new ArrayList<>();
     
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_listarCargo";
           statement = conexion.prepareStatement(sql);
           resultSet = statement.executeQuery();
           
           while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargos(cargoId,nombreCargo,descripcionCargo));
            }
       }catch(SQLException e){
            e.printStackTrace();
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
                e.printStackTrace();
            }
       }     
       return FXCollections.observableList(cargos);
    }
    
    public void agregarEmpleado(){
      
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarEmpleado(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfNombreEmpleado.getText());
            statement.setString(2,tfApellidoEmpleado.getText());
            statement.setString(3,tfSueldo.getText());
            statement.setString(4,tfHoraEntrada.getText());
            statement.setString(5,tfHoraSalida.getText());
            statement.setInt(6,((Cargos)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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
   
    public void editarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarEmpleado(?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfEmpleadoId.getText()));
            statement.setString(2,tfNombreEmpleado.getText());
            statement.setString(3,tfApellidoEmpleado.getText());
            statement.setString(4,tfSueldo.getText());
            statement.setString(5,tfHoraEntrada.getText());
            statement.setString(6,tfHoraSalida.getText());
            statement.setInt(7,((Cargos)cmbCargoId.getSelectionModel().getSelectedItem()).getCargoId());
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
