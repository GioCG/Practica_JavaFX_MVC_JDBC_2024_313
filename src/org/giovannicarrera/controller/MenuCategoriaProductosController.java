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
import org.giovannicarrera.modelo.CategoriaProductos;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuCategoriaProductosController implements Initializable {
    
    private int op;
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfCategoriaProductosId,tfNombreCategoria,tfCategoriaBuscar;
    @FXML
    TextArea taDescripcionCategoria;
    @FXML
    TableView tblCategoriaProductos;
    @FXML
    TableColumn colCategoriaProductosId,colNombreCategoria,colDescripcionCategoria;
    @FXML
    Button btnGuardar,btnEliminar,btnRegresar,btnBuscar;
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfCategoriaProductosId.getText().equals("")){
                agregarCategoria();
                cargarDatos();
                vaciarForm();
            }else{
                editarCategoria();
                cargarDatos();
                vaciarForm();
            }
        }else if(event.getSource() == btnEliminar){
            if(tfCategoriaProductosId.getText().equals("")){
            }else{
                if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarCategoria(((CategoriaProductos) tblCategoriaProductos.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
                cargarDatos();
                }
            }
        }else if(event.getSource()== btnBuscar){
            if(tfCategoriaBuscar.getText().equals("")){
               cargarDatos();
            }else{
                op=3;
                tblCategoriaProductos.getItems().clear();
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
            tblCategoriaProductos.getItems().add(buscarCategoria()); 
            op = 0;
        }else{
        tblCategoriaProductos.setItems(listarCategoriaProductos());
        colCategoriaProductosId.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, Integer>("categoriaProductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProductos, String>("descripcionCategoria"));
    
        }
    }
    
    @FXML
    public void cargarForm(){
        CategoriaProductos ts = (CategoriaProductos)tblCategoriaProductos.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfCategoriaProductosId.setText(Integer.toString(ts.getCategoriaProductosId()));
            tfNombreCategoria.setText(ts.getNombreCategoria());
            taDescripcionCategoria.setText(ts.getDescripcionCategoria());
        }
    }
    
    public void vaciarForm(){
        tfCategoriaProductosId.clear();
        tfNombreCategoria.clear();
        taDescripcionCategoria.clear();
    }
    
    public ObservableList<CategoriaProductos> listarCategoriaProductos(){
        ArrayList<CategoriaProductos> cargos = new ArrayList<>();
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_listarCategoriaProductos";
           statement = conexion.prepareStatement(sql);
           resultSet = statement.executeQuery();
           
           while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                cargos.add(new CategoriaProductos(categoriaProductosId,nombreCategoria,descripcionCategoria));
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
                e.printStackTrace();
            }
       }     
       return FXCollections.observableList(cargos);
    }
    
    public void eliminarCategoria(int catProId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,catProId);
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void agregarCategoria(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreCategoria.getText());
            statement.setString(2, taDescripcionCategoria.getText());
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
    
    public void editarCategoria(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaProductosId.getText()));
            statement.setString(2, tfNombreCategoria.getText());
            statement.setString(3, taDescripcionCategoria.getText());
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
    
    public CategoriaProductos buscarCategoria(){
        CategoriaProductos categoriaProductos = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProductos(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfCategoriaBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                categoriaProductos = new CategoriaProductos(categoriaProductosId, nombreCategoria,descripcionCategoria);
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
        return categoriaProductos;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
