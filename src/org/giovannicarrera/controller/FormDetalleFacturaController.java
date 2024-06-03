/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
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
import org.giovannicarrera.modelo.Compra;
import org.giovannicarrera.modelo.DetalleFactura;
import org.giovannicarrera.modelo.Factura;
import org.giovannicarrera.modelo.Productos;
import org.giovannicarrera.system.Main;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class FormDetalleFacturaController implements Initializable {

    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfCantidadProducto;
    @FXML
    ComboBox cmbFactura ,cmbProducto,cmbCompra;
    @FXML
    Button btnGuardar,btnCancelar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuFacturaView();
        }else if(event.getSource() == btnGuardar){
            agregarDetalleFactura();
            agregarDetalleCompra();
            stage.menuFacturaView();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cmbCompra.setItems(listarCompra());
       cmbFactura.setItems(listarFactura());
       cmbProducto.setItems(listarProducto());
    }    
    
    public ObservableList<Compra> listarCompra(){
        ArrayList<Compra> compra = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");
                Date fechaCompra = resultSet.getDate("fechaCompra");
                compra.add(new Compra(compraId, fechaCompra));
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
        return FXCollections.observableArrayList(compra);
    }
    
    public ObservableList<Factura> listarFactura(){
        ArrayList<Factura> facturas = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarFacturaComple()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
                
                facturas.add(new Factura(facturaId,fecha, hora, cliente,empleado,total));
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
        return FXCollections.observableList(facturas);
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
    
    public void agregarDetalleFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call SP_agregarDetFacturas(?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
            statement.setInt(2, ((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void agregarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDetalleCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfCantidadProducto.getText());
            statement.setInt(2, ((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
            statement.setInt(3, ((Compra)cmbCompra.getSelectionModel().getSelectedItem()).getCompraId());
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
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
