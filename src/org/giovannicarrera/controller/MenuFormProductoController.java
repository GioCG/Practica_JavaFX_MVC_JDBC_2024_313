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
import java.util.List;
import java.util.ResourceBundle;
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
    ComboBox cmbDistribuidor, cmbCategoriaProd;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
    
    public void agregarProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarProducto(?,?,?,?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreProducto.getText());
            InputStream img = new FileInputStream(files.get(0));
            statement.setBinaryStream(2, img);
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
 
    public void cargarCmbCategoriarod(){
        cmbCategoriaProd.getItems().add("1");
    }
    
    public void cargarCmbCategoriaProd(){
        cmbCategoriaProd.getItems().add("1");
    }
    
    public Productos buscarProducto(){
        Productos producto = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombre = resultSet.getString("nombre");
                Blob imagen = resultSet.getBlob("imagen");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return producto;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
