
package org.giovannicarrera.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.giovannicarrera.controller.FormAsignarEncargadoController;
import org.giovannicarrera.controller.FormClienteController;
import org.giovannicarrera.controller.FormDetalleFacturaController;
import org.giovannicarrera.controller.FormDistribuidoresController;
import org.giovannicarrera.controller.FormEmpleadosController;
import org.giovannicarrera.controller.MenuCargosController;
import org.giovannicarrera.controller.MenuCategoriaProductosController;
import org.giovannicarrera.controller.MenuClienteController;
import org.giovannicarrera.controller.MenuDistribuidorController;
import org.giovannicarrera.controller.MenuEmpleadosController;
import org.giovannicarrera.controller.MenuFacturaController;
import org.giovannicarrera.controller.MenuFormProductoController;
import org.giovannicarrera.controller.MenuPrincipalController;
import org.giovannicarrera.controller.MenuProductosController;
import org.giovannicarrera.controller.MenuTicketSoporteController;
import org.giovannicarrera.controller.MenuPromocionesController;



public class Main extends Application {
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/giovannicarrera/view/";
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setTitle("Super Kinal APP");
        Image image = new Image("org/giovannicarrera/image/Logo.png");
        stage.getIcons().add(image);
        menuPrincipalView();
        stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW+ fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(URLVIEW+fxmlName));
        
        scene = new Scene((AnchorPane)loader.load(file),width,height);
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
    }
    
    public void menuPrincipalView(){
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml",950,675);
            menuPrincipalView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    public void menuClientesView(){
        try{
            MenuClienteController menuClientesView = (MenuClienteController)switchScene("MenuClienteView.fxml",1200,750);
            menuClientesView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    public void formularioClientesView(int op){
        try{
            FormClienteController formClientesView = (FormClienteController)switchScene("FormClienteView.fxml",500,750);
            formClientesView.setOp(op);
            formClientesView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuTicketSoporteView() {
        try{
            MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml",1200,750);
            menuTicketSoporteView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuProductosView() {
        try{
            MenuProductosController menuProductosView = (MenuProductosController)switchScene("MenuProductosView.fxml",1200,750);
            menuProductosView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuFormProductoView(int op) {
        try{
            MenuFormProductoController menuFormProductoView = (MenuFormProductoController)switchScene("MenuFormProductoView.fxml",1200,750);
            menuFormProductoView.setOp(op);
            menuFormProductoView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuCargosView() {
        try{
            MenuCargosController menuCargosView = (MenuCargosController)switchScene("MenuCargosView.fxml",600,900);
            menuCargosView.setStage(this);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    public void menuCategoriaProductos() {
        try{
            MenuCategoriaProductosController menuCategoriaProductosView = (MenuCategoriaProductosController)switchScene("MenuCategoriaProductosView.fxml",600,900);
            menuCategoriaProductosView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void menuEmpleadosView() {
        try{
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml",1200,750);
            menuEmpleadosView.setStage(this);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    public void formEmpleadosView(int op) {
        try{
            FormEmpleadosController formEmpleadosView = (FormEmpleadosController)switchScene("FormEmpleadosView.fxml",500,750);
            formEmpleadosView.setOp(op);
            formEmpleadosView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void formAsignarEncargadoView() {
        try{
            FormAsignarEncargadoController formAsignarEncargadoView = (FormAsignarEncargadoController)switchScene("FormAsignarEncargadoView.fxml",500,750);
            formAsignarEncargadoView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    public void menuDistribuidorView() {
        try{
            MenuDistribuidorController menuDistribuidorView = (MenuDistribuidorController)switchScene("MenuDistribuidorView.fxml",1044,653);
            menuDistribuidorView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    
    public void formDistribuidoresView(int op) {
        try{
            FormDistribuidoresController formDistribuidoresView = (FormDistribuidoresController)switchScene("FormDistribuidoresView.fxml",500,750);
            formDistribuidoresView.setOp(op);
            formDistribuidoresView.setStage(this);
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
    }
    public void menuPromocionesView() {
        try{
            MenuPromocionesController menuPromocionesView = (MenuPromocionesController)switchScene("MenuPromocionesView.fxml",1200,750);
            menuPromocionesView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void menuFacturaView() {
        try{
            MenuFacturaController menuFacturaView = (MenuFacturaController)switchScene("MenuFacturaView.fxml",1200,750);
            menuFacturaView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void formDetalleFacturaView() {
        try{
            FormDetalleFacturaController formDetalleFacturaView = (FormDetalleFacturaController)switchScene("FormDetalleFacturaView.fxml",500,750);
            formDetalleFacturaView.setStage(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    public static void main(String[] args) {
        launch(args);
    } 
    
}
