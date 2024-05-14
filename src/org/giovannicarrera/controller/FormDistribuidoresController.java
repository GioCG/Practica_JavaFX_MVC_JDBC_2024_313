
package org.giovannicarrera.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.dto.DistribuidorDTO;
import org.giovannicarrera.modelo.Distribuidor;
import org.giovannicarrera.system.Main;

/**
 * FXML Controller class
 *
 * @author Dell G7
 */
public class FormDistribuidoresController implements Initializable {

    private Main stage;
    private int op;   
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    
    @FXML
    TextField tfDistribuidorId, tfNombreDistribuidor, tfDireccionDistribuidor, tfNitDistribuidor, tfTelefonoDistribuidor, tfWeb;
    @FXML
    Button btnGuardarDistri, btnCancelarDistri;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }    
        @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelarDistri){
            DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
            stage.menuDistribuidorView();
        }else if(event.getSource() == btnGuardarDistri){
            if(op == 1){
            agregarDistribuidores(); 
            stage.menuDistribuidorView();
            }else if(op == 2){
                editarDistribuidores();
                DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                stage.menuDistribuidorView();
            }          
        }
    }
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombreDistribuidor.setText(distribuidor.getNombreDistribuidor());
        tfDireccionDistribuidor.setText(distribuidor.getDireccionDistribuidor());  
        tfNitDistribuidor.setText(distribuidor.getNitDistribuidor());        
        tfTelefonoDistribuidor.setText(distribuidor.getTelefonoDistribuidor());      
        tfWeb.setText(distribuidor.getWeb());
    }

    public void agregarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarDistribuidor(?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, tfNombreDistribuidor.getText());
            statement.setString(2, tfDireccionDistribuidor.getText());
            statement.setString(3, tfNitDistribuidor.getText());
            statement.setString(4, tfTelefonoDistribuidor.getText());
            statement.setString(5, tfWeb.getText());
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
    
    public void editarDistribuidores(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarDistribuidores(?, ?, ?, ?, ?, ?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfDistribuidorId.getText()));
            statement.setString(2, tfNombreDistribuidor.getText());
            statement.setString(3, tfDireccionDistribuidor.getText());
            statement.setString(4, tfNitDistribuidor.getText());
            statement.setString(5, tfTelefonoDistribuidor.getText());
            statement.setString(6, tfWeb.getText());      
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
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
