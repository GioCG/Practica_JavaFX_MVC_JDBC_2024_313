/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;


import java.net.URL;
import java.sql.Blob;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.ProductoDTO;
import org.giovannicarrera.modelo.Productos;
import org.giovannicarrera.report.GenerarReporte;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

public class MenuProductosController implements Initializable {
    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TableView tblProductos;
    @FXML
    Button btnMenuPrincipal,btnAgregar,btnEditar,btnBuscar,btnEliminar,btnReporte;
    @FXML
    TextField tfProductoId;
    @FXML
    ImageView imgMostrar;
    @FXML    
    TableColumn colProductoId,colNombre,colDescripcion,colStock,colPresioUnitario,colPresioMayor,colCompra,colImagen,colDistribuidor,colCategoria;
    @FXML
    ComboBox cmbDistribuidor,cmbCategoria;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuPrincipal){
            stage.menuPrincipalView();
        }else if(event.getSource()== btnAgregar){
            stage.menuFormProductoView(1);
        }else if(event.getSource()== btnEditar){
            ProductoDTO.getProductoDTO().setProducto((Productos)tblProductos.getSelectionModel().getSelectedItem());
            stage.menuFormProductoView(2);
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getProductoId());
                cargarLista();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfProductoId.getText().equals("")){
               cargarLista();
            }else{
                op=3;
                tblProductos.getItems().clear();
                cargarLista(); 
            } 
        }else if(event.getSource()==btnReporte){
            GenerarReporte.getInstance().generarProductos();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        cargarLista();
    }    
    
    public void cargarLista(){
        if(op==3){
            tblProductos.getItems().add(buscarProducto()); 
            op = 0;
        }else{
            tblProductos.setItems(listarProducto()); 
            colProductoId.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("productoId"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Productos, String>("nombreProducto"));
            colDescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
            colStock.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("cantidadStock"));
            colPresioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaUnitario"));
            colPresioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioVentaMayor"));
            colCompra.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioCompra"));  
            colImagen.setCellValueFactory(new PropertyValueFactory<Productos, Blob>("imagen"));
            colDistribuidor.setCellValueFactory(new PropertyValueFactory<Productos, String>("distribuidor"));
            colCategoria.setCellValueFactory(new PropertyValueFactory<Productos, String>("categoria"));
        }
    }
    
    public int obtenerIndexDistribuidor(){
        int index = 0;
        String distribuidorTbl = ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDistribuidor(); 
        for(int i = 0 ; i <= cmbDistribuidor.getItems().size() ; i++){
           String clienteCmb = cmbDistribuidor.getItems().get(i).toString();
            
            if(distribuidorTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }

    public int obtenerIndexCategoria(){
        int index = 0;
        String categoriaTbl = ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getCategoria(); 
        for(int i = 0 ; i <= cmbCategoria.getItems().size() ; i++){
            String categoriaCmb = cmbCategoria.getItems().get(i).toString();
            
            if(categoriaTbl.equals(categoriaCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    public ObservableList<Productos> listarProducto(){
        ArrayList<Productos> producto = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProductoComple()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                Double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                Double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagen");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
                producto.add(new Productos(productoId, nombreProducto, descripcionProducto, cantidadStock,precioVentaUnitario, precioVentaMayor, precioCompra,imagen, distribuidor, categoria));
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
        return FXCollections.observableArrayList(producto);
    }
    
    
    public void eliminarProducto(int prodId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,prodId);
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public Productos buscarProducto(){
        Productos producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfProductoId.getText()));
            resultSet = statement.executeQuery();
            if(resultSet.next()){
               int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                Double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                Double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                Double precioCompra = resultSet.getDouble("precioCompra");
                Blob imagen = resultSet.getBlob("imagen");
                String distribuidor = resultSet.getString("distribuidor");
                String categoria = resultSet.getString("categoria");
               producto = new Productos(productoId, nombreProducto, descripcionProducto, cantidadStock,precioVentaUnitario, precioVentaMayor, precioCompra,imagen, distribuidor, categoria);
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
        return producto;
    } 
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
