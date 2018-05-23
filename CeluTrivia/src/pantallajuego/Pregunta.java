/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallajuego;

import celutrivia.Const;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Pregunta extends Pane {
    
    ArrayList<Respuesta> respuestas;
    private StackPane[] opciones;
    
    public Pregunta(String pregunta, Respuesta... respuestas) {
        this.respuestas = new ArrayList<>(Arrays.asList(respuestas));
        HBox b = new HBox();
        Rectangle r = new Rectangle(Const.MAX_WIDTH/1.24, Const.MAX_HEIGHT/5.4);
        StackPane question = new StackPane();
        Text p = new Text(pregunta);
        p.setTextAlignment(TextAlignment.CENTER);
        p.setTextOrigin(VPos.TOP);
        p.setWrappingWidth(r.getWidth()); 
        p.setLineSpacing(3); 
        p.setFont(Font.font(18));  
        p.setStyle(Const.FONT_BOLD); 
        p.setFill(Color.WHITE); 
        question.getChildren().addAll(r, p);
        r.setFill(Color.TRANSPARENT); 
        b.setAlignment(Pos.CENTER);
        b.getChildren().add(question);
        b.setMinWidth(Const.MAX_WIDTH);
        super.getChildren().add(b);
        crearRespuestas();
    }
    
    private void crearRespuestas() {
        Collections.shuffle(respuestas);
        opciones = new StackPane[4];
        HBox h = new HBox();
        h.setMinWidth(Const.MAX_WIDTH);
        h.setAlignment(Pos.CENTER); 
        GridPane grid = new GridPane();
        grid.setHgap(Const.MAX_WIDTH/15);
        grid.setVgap(Const.MAX_HEIGHT/24); 
        h.setTranslateY(Const.MAX_HEIGHT/5.4 + Const.MAX_HEIGHT/17);
        Text[] literal = {
            new Text("A) "),
            new Text("B) "),
            new Text("C) "),
            new Text("D) ")
        };
        
        for(Text t: literal) {
            t.setFill(Color.GOLD);
            t.setFont(Font.font(14));
            t.setStyle(Const.FONT_BOLD);
            t.setTextAlignment(TextAlignment.LEFT);
        }
        int ii = 0;
        for(int i=0; i<2; i++) {
            for(int j=0; j<2; j++){
                StackPane sp = new StackPane();
                Rectangle a = new Rectangle(Const.MAX_WIDTH/2.67, Const.MAX_HEIGHT/12);
                a.setFill(Color.TRANSPARENT);
                a.setOpacity(0.5); 
                Text t = new Text(respuestas.get(ii).getRespuesta()); 
                t.setFill(Color.WHITE);
                t.setFont(Font.font(14));
                t.setStyle(Const.FONT_BOLD);
                t.setTextAlignment(TextAlignment.LEFT); 
                t.setWrappingWidth(a.getWidth()); 
                HBox b = new HBox();
                b.getChildren().addAll(literal[ii], t);
                b.setAlignment(Pos.CENTER_LEFT);
                b.setMinWidth(a.getWidth());  
                sp.getChildren().addAll(a, b);
                grid.add(sp, j, i);
                opciones[ii] = sp;
                ii++;
                addEvent(sp);
            }
        }
        h.getChildren().add(grid); 
        super.getChildren().add(h);
    }
    
    private void addEvent(StackPane sp) {
        sp.setOnMouseEntered(e-> {
            sp.setCursor(Cursor.HAND);
            sp.setBackground(new Background(new BackgroundFill(Color.DARKGREY, new CornerRadii(10), Insets.EMPTY)));
                });
        
        sp.setOnMouseExited(e->{
            sp.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
        });
    }
    
    public StackPane[] getOpciones() {
        return opciones;
    }
    
    public Respuesta getRespuestaCorrecta() {
        for(Respuesta r: respuestas){ 
            if(r.isCorrecta())
                return r;
        }
        return null;
    }
}
