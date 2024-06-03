/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.CategoriaProductos;
import org.giovannicarrera.modelo.Distribuidor;
import org.giovannicarrera.modelo.Productos;
import org.giovannicarrera.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuFormProductoController implements Initializable {
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    private List<File> files = null;
    
    private Main stage;
    
    @FXML
    Button btnGuardar, btnCancelar;
    @FXML
    TextField tfProductoId,tfNombreProducto, tfDescripcionProducto,tfCantidadStock,tfPrecioVentaUnitario,tfPrecioVentaMayor,tfPrecioCompra;
    @FXML
    ImageView imgCargar;
    @FXML
    ComboBox cmbDistribuidor, cmbCategoria;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cmbDistribuidor.setItems(listarDistribuidor());
       cmbCategoria.setItems(listarCategoriaProductos());
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
            if(event.getSource() == btnGuardar){
                agregarProducto();
                stage.menuProductosView();
            }else if(event.getSource() == btnCancelar){
                stage.menuProductosView();
                }
            }      
    
    @FXML
    public void handleOnDrag(DragEvent event){
        if(event.getDragboard().hasFiles()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    
    @FXML
    public void handleOnDrop(DragEvent event){
        try{
            files = event.getDragboard().getFiles();
            FileInputStream file = new FileInputStream(files.get(0));
            Image image = new Image(file);
            imgCargar.setImage(image);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public ObservableList<Distribuidor> listarDistribuidor(){
        ArrayList<Distribuidor> distribuidor = new ArrayList<>();
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDistribuidor();";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while(resultSet.next()){
                int distribuidorId = resultSet.getInt("distribuidorId");
                String nombreDistribuidor = resultSet.getString("nombreDistribuidor");
                String direccionDistribuidor = resultSet.getString("direccionDistribuidor");
                String nitDistribuidor = resultSet.getString("nitDistribuidor");
                String telefonoDistribuidor = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                distribuidor.add(new Distribuidor(distribuidorId, nombreDistribuidor, direccionDistribuidor, nitDistribuidor, telefonoDistribuidor, web));
            }
        }catch (SQLException e){
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
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return FXCollections.observableList(distribuidor);
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
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfNombreProducto.getText());
            statement.setString(2,tfDescripcionProducto.getText());
            statement.setString(3,tfCantidadStock.getText());
            statement.setString(4,tfPrecioVentaUnitario.getText());
            statement.setString(5,tfPrecioVentaMayor.getText());
            statement.setString(6,tfPrecioCompra.getText());
            if (files != null && !files.isEmpty() && files.get(0) != null) {
                InputStream img = new FileInputStream(files.get(0));
                statement.setBinaryStream(7, img);
            } else {
                statement.setBinaryStream(7, null); 
            }
            statement.setInt(8, ((Distribuidor)cmbDistribuidor.getSelectionModel().getSelectedItem()).getDistribuidorId());
            statement.setInt(9, ((CategoriaProductos)cmbCategoria.getSelectionModel().getSelectedItem()).getCategoriaProductosId());
            statement.execute();
        }catch(Exception e){
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
    
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
