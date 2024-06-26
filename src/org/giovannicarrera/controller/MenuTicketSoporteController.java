/*
======================================================================================
=================Terminado-1error en llenar form cliente y facturas===================
======================================================================================
*/
package org.giovannicarrera.controller;


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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Cliente;
import org.giovannicarrera.modelo.Factura;
import org.giovannicarrera.modelo.TicketSoporte;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;


public class MenuTicketSoporteController implements Initializable {
    private int op;
    Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    @FXML
    TextField tfTicketId,tfTicketBuscar;
    @FXML
    TextArea taDescripcion;
    @FXML
    ComboBox cmbEstatus, cmbCliente, cmbFactura;
    @FXML
    TableView tblTickets;
    @FXML
    TableColumn colTicketId, colDescripcion, colEstatus, colCliente, colFactura;
    @FXML
    Button btnGuardar,btnVaciarForm,btnRegresar,btnEliminar,btnBuscar;
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            if(tfTicketId.getText().equals("")){
                agregarTicket();
                cargarDatos();
            }else{
                editarTicket();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciarForm){
            vaciarForm();
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarTicketSoporte(((TicketSoporte) tblTickets.getSelectionModel().getSelectedItem()).getTicketSoporteId());
                cargarDatos();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfTicketBuscar.getText().equals("")){
               cargarDatos();
            }else{
                op=3;
                tblTickets.getItems().clear();
                cargarDatos(); 
            }
            
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       cargarDatos();
       cargarCmbEstatus();
       cmbCliente.setItems(listarClientes());
       cmbFactura.setItems(listarFactura());
    }    
    
    public void cargarDatos(){
        if(op==3){
            tblTickets.getItems().add(buscarTicketSoporte()); 
            op = 0;
        }else{
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcion"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente"));
        colFactura.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("factura"));
        }
    }
    
    public void cargarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso");
        cmbEstatus.getItems().add("Finalizado");
    }
    
    
    public void vaciarForm(){
        tfTicketId.clear();
        taDescripcion.clear();
        tfTicketBuscar.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbFactura.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
            TicketSoporte ts = (TicketSoporte)tblTickets.getSelectionModel().getSelectedItem();
            if(ts != null){
                tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
                taDescripcion.setText(ts.getDescripcion());
                cmbEstatus.getSelectionModel().select(0);
                cmbCliente.getSelectionModel().select(obtenerIndexCliente());
                cmbFactura.getSelectionModel().select(obtenerIndexFactura());
            }
    }
    
    public int obtenerIndexCliente(){
        int index = 0;
        String clienteTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente();
        for(int i = 0 ; i <= cmbCliente.getItems().size() ; i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            
            if(clienteTbl.equals(clienteCmb)){
                index = i;
                break;
            }
        }
        
        return index;
    }

    public int obtenerIndexFactura() {
        int index = 0;
        String facturaTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getFactura(); 
        for (int i = 0; i < cmbFactura.getItems().size(); i++) {
            String facturaCmb = cmbFactura.getItems().get(i).toString();
            
            if (facturaTbl.equals(facturaCmb)) {
                index = i;
                return i;
            }
        }
        return index; 
    }
    
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
     
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_listarTicketSoporteComplet";
           statement = conexion.prepareStatement(sql);
           resultSet = statement.executeQuery();
           
           while(resultSet.next()){
                int ticketId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcion");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                String factura = resultSet.getString("factura");
                
                tickets.add(new TicketSoporte(ticketId, descripcion, estatus, cliente, factura));
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
       return FXCollections.observableList(tickets);
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
    public void agregarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoporte(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
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
    
    public void editarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3, (cmbEstatus.getSelectionModel().getSelectedItem().toString()));
            statement.setInt(4, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Factura)cmbFactura.getSelectionModel().getSelectedItem()).getFacturaId());
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
    
    public void eliminarTicketSoporte(int ticSopId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarTicketSoporte(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ticSopId);
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
    
    public TicketSoporte buscarTicketSoporte(){
        TicketSoporte ticketSoporte = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarTicketSoporte(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcion");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                String factura = resultSet.getString("factura");
                
                ticketSoporte = new TicketSoporte(ticketSoporteId, descripcion,estatus,cliente,factura);
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
        return ticketSoporte;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
    
