
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
import org.giovannicarrera.controller.FormClienteController;
import org.giovannicarrera.controller.MenuClienteController;
import org.giovannicarrera.controller.MenuPrincipalController;



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
    public static void main(String[] args) {
        launch(args);
    }

    
}
