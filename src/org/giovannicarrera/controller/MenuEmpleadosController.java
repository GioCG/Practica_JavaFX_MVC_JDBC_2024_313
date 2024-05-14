
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.EmpleadosDTO;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class MenuEmpleadosController implements Initializable {

    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblEmpleados;
    @FXML
    Button btnMenuPrincipal,btnAgregar,btnAsignarEncargado,btnEditar,btnEliminar,btnBuscar;
    @FXML
    TextField tfEmpleadoId;
    @FXML    
    TableColumn colEmpleadoId,colNombreEmpleado,colApellidoEmpleado,colSueldo,colHoraEntrada,colhoraSalida,colCargoId,colEncargadoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuPrincipal){
            stage.menuPrincipalView();
        }else if(event.getSource()== btnAgregar){
            stage.formEmpleadosView(1);
        }else if(event.getSource()== btnEditar){
            EmpleadosDTO.getEmpleadosDTO().setEmpleado((Empleado)tblEmpleados.getSelectionModel().getSelectedItem());
            stage.formEmpleadosView(2);
        }else if(event.getSource()== btnAsignarEncargado){
            stage.formAsignarEncargadoView();
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarCliente(((Empleado) tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId());
                cargarLista();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfEmpleadoId.getText().equals("")){
               cargarLista();
            }else{
                op=3;
                tblEmpleados.getItems().clear();
                cargarLista(); 
            }
            
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        cargarLista();
    }    
    
    public void cargarLista(){
        if(op==3){
            tblEmpleados.getItems().add(buscarCliente()); 
            op = 0;
        }else{
            tblEmpleados.setItems(listarEmpleado()); 
            colEmpleadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("empleadoId"));
            colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
            colApellidoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
            colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
            colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaEntrada"));
            colhoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado, Time>("horaSalida"));
            colCargoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("cargoId"));
            colEncargadoId.setCellValueFactory(new PropertyValueFactory<Empleado, Integer>("encargadoId"));  
        }
    }
    
    public ObservableList<Empleado> listarEmpleado(){
        ArrayList<Empleado> empleado = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleado()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                int cargoId = resultSet.getInt("cargoId");
                int encargadoId = resultSet.getInt("encargadoId");
                empleado.add(new Empleado (empleadoId,nombreEmpleado, apellidoEmpleado, sueldo,horaEntrada,horaSalida,cargoId,encargadoId));
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
    
    public void eliminarCliente(int cliId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,cliId);
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public Empleado buscarCliente(){
        Empleado empleado = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfEmpleadoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                int cargoId = resultSet.getInt("cargoId");
                int encargadoId = resultSet.getInt("encargadoId");
                empleado = new Empleado(empleadoId,nombreEmpleado, apellidoEmpleado, sueldo,horaEntrada,horaSalida,cargoId,encargadoId);
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
        return empleado;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
