
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.ClienteDTO;
import org.giovannicarrera.modelo.Cliente;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.SuperKinalAlert;

public class MenuClienteController implements Initializable {
    private int op;
    private Main stage;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    TableView tblClientes;
    @FXML
    Button btnMenuPrincipal,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    @FXML
    TextField tfClienteId;
    @FXML    
    TableColumn colClienteId,colNIT,colNombre,colApellido,colTelefono,colDireccion;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource()== btnMenuPrincipal){
            stage.menuPrincipalView();
        }else if(event.getSource()== btnAgregar){
            stage.formularioClientesView(1);
        }else if(event.getSource()== btnEditar){
            ClienteDTO.getClienteDTO().setCliente((Cliente)tblClientes.getSelectionModel().getSelectedItem());
            stage.formularioClientesView(2);
        }else if(event.getSource()== btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertConf(770).get() == ButtonType.OK){
                eliminarCliente(((Cliente) tblClientes.getSelectionModel().getSelectedItem()).getClienteId());
                cargarLista();
            }
        }else if(event.getSource()== btnBuscar){
            if(tfClienteId.getText().equals("")){
               cargarLista();
            }else{
                op=3;
                tblClientes.getItems().clear();
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
            tblClientes.getItems().add(buscarCliente()); 
            op = 0;
        }else{
            tblClientes.setItems(listarClientes()); 
            colClienteId.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
            colNIT.setCellValueFactory(new PropertyValueFactory<Cliente, String>("NIT"));
            colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
            colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
            colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
            colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));  
        }
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
        return FXCollections.observableArrayList(clientes);
    }
    
    public void eliminarCliente(int cliId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,cliId);
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public Cliente buscarCliente(){
        Cliente cliente = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,Integer.parseInt(tfClienteId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String NIT = resultSet.getString("NIT");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                cliente = new Cliente(clienteId,NIT, nombre, apellido,telefono,direccion);
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
        return cliente;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
}
