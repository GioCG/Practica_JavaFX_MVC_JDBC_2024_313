/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.giovannicarrera.controller;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.giovannicarrera.dao.Conexion;
import org.giovannicarrera.modelo.Usuario;
import org.giovannicarrera.system.Main;
import org.giovannicarrera.utils.ContraUtils;
import org.giovannicarrera.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 */

public class FormLoginController implements Initializable {
    
    private int op = 0;
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    @FXML
    Button btnLogin, btnRegistrar; 
    @FXML
    TextField tfUsuar;
    @FXML
    PasswordField tfContraseña;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnLogin){
            if(op == 0){
                Usuario usuario = buscarUsuario();    
                if(usuario != null){
                    if(ContraUtils.getInstance().checkPassword(tfContraseña.getText(), usuario.getPassword())){
                        SuperKinalAlert.getInstance().alertBienvenida(usuario.getUsuario());
                        if(usuario.getNivelesAccesoId() == 1){
                           btnRegistrar.setDisable(false);
                           btnLogin.setText("Ir al Menú");
                           op = 1;
                        }else if(usuario.getNivelesAccesoId() == 2){
                            stage.menuPrincipalView();
                        }
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertInfo(800);
                    }
                }else{
                  SuperKinalAlert.getInstance().mostrarAlertInfo(900);
                }
            }else{
                stage.menuPrincipalView();
            }
        }else if(event.getSource() == btnRegistrar){
            stage.formRegistrarView();
        }
    }  
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public Usuario buscarUsuario(){
        Usuario usuario = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarUsuar(?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,tfUsuar.getText());
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                int usuarId = resultSet.getInt("usuarId");
                String usuar = resultSet.getString("usuar");
                String contra = resultSet.getString("contra");
                int lvlAccesId = resultSet.getInt("lvlAccesId");
                int empleadoId = resultSet.getInt("empleadoId");
                usuario = new Usuario(usuarId, usuar, contra, lvlAccesId, empleadoId);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
                 try{
                    if(conexion != null){
                    conexion.close();
                    }
                    if(statement != null){
                    statement.close();
                    }
                    if(resultSet != null){
                    resultSet.close();
                    }
                 }catch(Exception e){{
                    e.printStackTrace();
                }
            }
        }
        return usuario;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    
}
