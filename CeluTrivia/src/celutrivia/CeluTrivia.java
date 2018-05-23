/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package celutrivia;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.MenuBox;
import menu.MenuItem;
import pantallajuego.Juego;


public class CeluTrivia extends Application {
    CuadroDialogo dialogo;
    Stage stage;
    AudioClip audio2 = new AudioClip(CeluTrivia.class.getResource("/sonidos/click.wav").toString());
    
    @Override
    public void start(Stage stage) {
       Pane pane = new Pane();
       //css
       pane.getStylesheets().add(CeluTrivia.class.getResource("/celutrivia/Estilos.css").toExternalForm());
       this.stage = stage;
       stage.setScene(new Scene(pane, 1355, 693)); 
       stage.show();
       BackgroundImage bi = new BackgroundImage(new Image(getClass().getResourceAsStream("back.jpg"), 
                Const.MAX_WIDTH, Const.MAX_HEIGHT, false, false), 
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
       pane.setBackground(new Background(bi));
       
       ImageView titulo = new ImageView(new Image(getClass().getResourceAsStream("logo.png"))); 
       dialogo = new CuadroDialogo();
       MenuBox menuBox = new MenuBox(
                new MenuItem("Nuevo juego"),
                new MenuItem("Historial"),
                new MenuItem("Salir"));
        
        menuBox.setTranslateX(40);
        menuBox.setTranslateY(480);
        
        pane.getChildren().addAll(titulo, menuBox);
        
        menuBox.getMenu()[0].setOnMouseClicked(e -> nuevoJuego());
        menuBox.getMenu()[1].setOnMouseClicked(e -> stage.setScene(new Scene(new HistorialJugadores(stage).getRoot(), 1355, 693)));
        menuBox.getMenu()[2].setOnMouseClicked(e -> botonSalida());
    }
    
    private void nuevoJuego() {
        Optional<ButtonType> result = dialogo.showAndWait();
        if(result.isPresent()) {
            click(result);
        }    
    }
    
    private void click(Optional<ButtonType> result) {
        audio2.play(0.1);
        if(result.get() == ButtonType.OK) {
            String nombre = dialogo.getText();
            if(nombre.isEmpty()) {
                result = dialogo.showAndWait();
                click(result);
            }else {
                //iniciar juego
                stage.getScene().setRoot(new Juego(stage, nombre).getRoot());
            }
        }else {
            dialogo.close();
        }
    }
    
    public void botonSalida() {
        TextInputDialog mensaje = new TextInputDialog();
        mensaje.setTitle("CeluTrivia!");
        mensaje.setHeaderText(null);
        mensaje.initStyle(StageStyle.UNDECORATED);
        mensaje.getDialogPane().setContent(new Label("¿Realmente desea salir del juego?"));

        DialogPane panel = mensaje.getDialogPane();
        panel.getStylesheets().add(CeluTrivia.class.getResource("/celutrivia/Estilos.css").toExternalForm());

        Optional<String> result = mensaje.showAndWait();
        result.ifPresent(e -> {
            audio2.play(0.1);
            Platform.exit();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
