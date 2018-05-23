/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package celutrivia;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;


public class CuadroDialogo extends Dialog<ButtonType> {
    
    private DialogPane pane;
    private TextField texto;
    
    public CuadroDialogo() {
        super.initStyle(StageStyle.UNDECORATED);
        pane = super.getDialogPane();
        pane.getStylesheets().add(CeluTrivia.class.getResource("/celutrivia/Estilos.css").toExternalForm());
        titulo();
        contenido();
    }
    
    private void titulo() {
        HBox hb = new HBox();
        ImageView imagen = new ImageView(
                new Image(getClass().getResourceAsStream("logo.png"), 500, 100, true, true));
        hb.getChildren().add(imagen);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(15, 10, 10, 10));
        pane.setHeader(hb); 
    }
    
    private  void contenido() {
        HBox hb = new HBox();
        Text name = new Text("Participante:");
        texto = new TextField();
        texto.setMaxWidth(300); 
        hb.setSpacing(5);
        name.setStyle(Const.FONT_BOLD);
        name.setFill(Color.BLACK);
        hb.setPadding(new Insets(5, 20, 5, 20));
        hb.getChildren().addAll(name, texto);
        pane.setContent(hb); 
        pane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    }
    
    public String getText() {
        return texto.getText();
    }
}
