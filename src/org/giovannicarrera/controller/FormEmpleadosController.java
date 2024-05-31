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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.EmpleadosDTO;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * @author Dell G7
 */
public class FormEmpleadosController implements Initializable {

    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;

    @FXML
    Button btnGuardar,btnCancelar;
    @FXML    
    TextField tfEmpleadoId,tfNombreEmpleado,tfApellidoEmpleado,tfSueldo,tfHoraEntrada,tfHoraSalida,tfCargoId;

    
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
        tfCargoId.setText(Integer.toString(empleado.getCargoId()));
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
            statement.setString(6,tfCargoId.getText());
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
            statement.setString(7,tfCargoId.getText());
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
