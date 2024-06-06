/*
======================================================================================
=======================Terminado-mismo erro que ticketSoporte=========================
======================================================================================
*/
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Productos;
import org.giovannicarrera.modelo.Promociones;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 *
 * @author Dell G7
 */
public class MenuPromocionesController implements Initializable{
    
    private int op;
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfPromocionId,tfPromocion,tfInicio,tfFinal,tfPromocionIdBuscar;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbProducto;
    @FXML
    TableView tblPromocion;
    @FXML
    TableColumn colPromocionId,colPromocion,colDescripcion, colInicio, colFinal, colProducto;
    @FXML
    Button btnGuardar,btnVaciarForm,btnRegresar,btnEliminar,btnBuscar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfPromocionId.getText().equals("")){
                agregarPromo();
                cargarDatos();
            }else{
                editarPromo();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciarForm){
            vaciarForm();
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarPromocion(((Promociones) tblPromocion.getSelectionModel().getSelectedItem()).getPromocionId());
                cargarDatos();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfPromocionIdBuscar.getText().equals("")){
               cargarDatos();
            }else{
                op=3;
                tblPromocion.getItems().clear();
                cargarDatos(); 
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
       cargarDatos();
       cmbProducto.setItems(listarProducto());
    } 
    
    public void cargarDatos(){
        if(op==3){
            tblPromocion.getItems().add(buscarPromocion()); 
            op = 0;
        }else{
        tblPromocion.setItems(listarPromo());
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promociones, Integer>("promocionId"));
        colPromocion.setCellValueFactory(new PropertyValueFactory<Promociones, Double>("precioPromocio"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promociones, String>("descripcionPromocion"));
        colInicio.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaInicio"));
        colFinal.setCellValueFactory(new PropertyValueFactory<Promociones, String>("fechaFinal"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promociones, String>("productos"));
    
        }
    }
    
    public void vaciarForm(){
        tfPromocionId.clear();
        tfPromocion.clear();
        tfInicio.clear();
        tfFinal.clear();
        taDescripcion.clear();
        cmbProducto.getSelectionModel().clearSelection();
    }
    
    public int obtenerIndexProducto(){
        int index = 0;
        String ProductoTbl = ((Promociones)tblPromocion.getSelectionModel().getSelectedItem()).getProductos(); 
        for(int i = 0 ; i <= cmbProducto.getItems().size() ; i++){
            String ProductoCmb = cmbProducto.getItems().get(i).toString();
            
            if(ProductoTbl.equals(ProductoCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }

    @FXML
    public void cargarForm(){
        Promociones ts = (Promociones)tblPromocion.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfPromocionId.setText(Integer.toString(ts.getPromocionId()));
            tfPromocion.setText(Double.toString(ts.getPrecioPromocio()));
            taDescripcion.setText(ts.getDescripcionPromocion());
            tfInicio.setText(ts.getFechaInicio().toString());
            tfFinal.setText(ts.getFechaFinal().toString());
            cmbProducto.getSelectionModel().select(obtenerIndexProducto());
        }
    }
    public ObservableList<Promociones> listarPromo(){
        ArrayList<Promociones> promo = new ArrayList<>();
     
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_ListarPromocionComple";
           statement = conexion.prepareStatement(sql);
           resultSet = statement.executeQuery();
           
           while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioPromocio = resultSet.getDouble("precioPromocio");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinal = resultSet.getDate("fechaFinal");
                String productos = resultSet.getString("productos");
                
                promo.add(new Promociones(promocionId, precioPromocio, descripcionPromocion, fechaInicio, fechaFinal, productos));
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
       return FXCollections.observableList(promo);
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
    
    public void agregarPromo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarPromocion(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPromocion.getText());
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, tfInicio.getText());
            statement.setString(4, tfFinal.getText());
            statement.setInt(5, ((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarPromo(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarPromocion(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfPromocionId.getText());
            statement.setString(2, tfPromocion.getText());
            statement.setString(3, taDescripcion.getText());
            statement.setString(4, tfInicio.getText());
            statement.setString(5, tfFinal.getText());
            statement.setInt(6, ((Productos)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void eliminarPromocion(int promId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, promId);
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
    
    public Promociones buscarPromocion(){
        Promociones promociones = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarPromocion(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfPromocionIdBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioPromocio = resultSet.getDouble("precioPromocio");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinal = resultSet.getDate("fechaFinal");
                String productos = resultSet.getString("productos");
                promociones = new Promociones(promocionId, precioPromocio, descripcionPromocion, fechaInicio, fechaFinal, productos);
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
        return promociones;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
}
