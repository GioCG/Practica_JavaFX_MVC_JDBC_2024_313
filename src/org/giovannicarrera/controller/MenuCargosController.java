/*
====================================================
=====================Terminado======================
====================================================
*/
package org.giovannicarrera.controller;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Cargos;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class MenuCargosController implements Initializable {
    
    private int op;
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfCargoId,tfNombreCargo,tfCargoBuscar;
    @FXML
    TextArea taDescripcion;
    @FXML
    TableView tblCargos;
    @FXML
    TableColumn colCargoId,colNombreCargo,colDescripcionCargo;
    @FXML
    Button btnGuardar,btnEliminar,btnRegresar,btnBuscar;
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfCargoId.getText().equals("")){
                agregarCargos();
                cargarDatos();
                vaciarForm();
            }else{
                editarCargos();
                cargarDatos();
                vaciarForm();
            }
            
        }else if(event.getSource() == btnEliminar){
            if(tfCargoId.getText().equals("")){
            }else{
                if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarCargos(((Cargos) tblCargos.getSelectionModel().getSelectedItem()).getCargoId());
                cargarDatos();
                }
            }
        }else if(event.getSource()== btnBuscar){
            if(tfCargoBuscar.getText().equals("")){
               cargarDatos();
            }else{
                op=3;
                tblCargos.getItems().clear();
                cargarDatos(); 
            }
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       
    }    
    
    public void cargarDatos(){
        if(op==3){
          tblCargos.getItems().add(buscarCargos()); 
          op = 0;
        }else{
        tblCargos.setItems(listarCargos());
        colCargoId.setCellValueFactory(new PropertyValueFactory<Cargos, Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargos, String>("descripcionCargo"));
        }
    }
    
    public void vaciarForm(){
        tfCargoId.clear();
        tfNombreCargo.clear();
        taDescripcion.clear();
    }
    
    @FXML
    public void cargarForm(){
        Cargos ts = (Cargos)tblCargos.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfCargoId.setText(Integer.toString(ts.getCargoId()));
            tfNombreCargo.setText(ts.getNombreCargo());
            taDescripcion.setText(ts.getDescripcionCargo());
        }
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
    
    public void eliminarCargos(int carId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,carId);
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void agregarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCargo(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCargo.getText());
            statement.setString(2, taDescripcion.getText());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
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
    }
    
    public void editarCargos(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCargo(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            statement.setString(2, tfNombreCargo.getText());
            statement.setString(3, taDescripcion.getText());
            statement.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            try{
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
    }
    
    public Cargos buscarCargos(){
        Cargos cargos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCargoBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                cargos = new Cargos(cargoId, nombreCargo,descripcionCargo);
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
        return cargos;
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
