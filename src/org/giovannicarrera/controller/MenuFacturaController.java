/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;

import java.time.LocalDate;
import java.net.URL;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Cliente;
import org.giovannicarrera.modelo.Empleado;
import org.giovannicarrera.modelo.Factura;

import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class MenuFacturaController implements Initializable {
    
    private int op;
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfFacturaId, tfFecha, tfHora,tfFacturaBuscar;
    @FXML
    ComboBox cmbCliente, cmbEmpleado;
    @FXML
    TableView tblFactura;
    @FXML
    TableColumn colFacturaId, colFecha, colHora, colCliente, colEmpleado,colTotal;
    @FXML
    Button btnGuardar,btnVaciarForm,btnDetFactura,btnRegresar,btnEliminar,btnBuscar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnDetFactura){
            stage.formDetalleFacturaView();
        }else if(event.getSource() == btnGuardar){
            if(tfFacturaId.getText().equals("")){
                agregarFactura();
                agregarCompra();
                cargarDatos();
            }else{
                editarFactura();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciarForm){
            vaciarForm();
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarFactura(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFacturaId());
                cargarDatos();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfFacturaBuscar.getText().equals("")){
               cargarDatos();
            }else{
                op=3;
                tblFactura.getItems().clear();
                cargarDatos(); 
            }
            
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cmbCliente.setItems(listarClientes());
       cmbEmpleado.setItems(listarEmpleado());
    }    
    
    public void cargarDatos(){
        if(op==3){
            tblFactura.getItems().add(buscarFactura()); 
            op = 0;
        }else{
        tblFactura.setItems(listarFactura());
        colFacturaId.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("facturaId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura, Date>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Factura, Time>("hora"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Factura, String>("cliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura, String>("empleado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura, Double>("total"));
        }
    }
    
    public void vaciarForm(){
        tfFacturaId.clear();
        tfFecha.clear();
        tfHora.clear();
        tfFacturaBuscar.clear();
        cmbCliente.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        
            Factura fa = (Factura)tblFactura.getSelectionModel().getSelectedItem();
            if(fa != null){
                    tfFacturaId.setText(Integer.toString(fa.getFacturaId()));
                    tfFecha.setText(fa.getFecha().toString());
                    tfHora.setText(fa.getHora().toString());
                    cmbCliente.getSelectionModel().select(obtenerIndexCliente());
                    cmbEmpleado.getSelectionModel().select(obtenerIndexEmpleado());
            }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        String clienteTbl = ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getCliente();
        for(int i = 0 ; i <= cmbCliente.getItems().size() ; i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            
            if(clienteTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }

    public int obtenerIndexEmpleado() {
        int index = 0;
        String empleadoTbl = ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getEmpleado(); 
        for (int i = 0; i < cmbEmpleado.getItems().size(); i++) {
            String empleadoCmb = cmbEmpleado.getItems().get(i).toString();
            
            if (empleadoTbl.equals(empleadoCmb)) {
                index = i;
                return i;
            }
        }
        return index; 
    }
    
    public ObservableList<Empleado> listarEmpleado(){
        ArrayList<Empleado> empleado = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarEmpleadoComp()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId");
                String nombreEmpleado = resultSet.getString("nombreEmpleado");
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                Double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("cargo");
                int encargadoId = resultSet.getInt("encargadoId");
                empleado.add(new Empleado (empleadoId,nombreEmpleado, apellidoEmpleado, sueldo,horaEntrada,horaSalida,cargo,encargadoId));
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
    
    public ObservableList<Cliente> listarClientes(){
        
        ArrayList<Cliente> clientes = new ArrayList<>();

        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String NIT = resultSet.getString("NIT");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");

                clientes.add(new Cliente(clienteId,NIT, nombre, apellido,telefono,direccion));
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
        return FXCollections.observableList(clientes);
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
    public void agregarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarFactura(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfFecha.getText());
            statement.setString(2, tfHora.getText());
            statement.setInt(3, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(4, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
    
    public void agregarCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfFecha.getText());
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
    public void editarFactura(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarFactura(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setString(2, tfFecha.getText());
            statement.setString(3, tfHora.getText());
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
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
    
    public void eliminarFactura(int facId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, facId);
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
    
    public Factura buscarFactura(){
        Factura factura = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarFactura(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfFacturaBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("cliente");
                String empleado = resultSet.getString("empleado");
                Double total = resultSet.getDouble("total");
                factura = new Factura(facturaId, fecha,hora,cliente,empleado,total);
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
        return factura;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}   
    

