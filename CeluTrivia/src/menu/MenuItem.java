/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menu;

import celutrivia.Const;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class MenuItem extends StackPane {
    
    private Rectangle rectangle;
    private LinearGradient gradiente;
    private Text text;
    AudioClip audio = new AudioClip(MenuItem.class.getResource("/sonidos/select.wav").toString());
    AudioClip audio2 = new AudioClip(MenuItem.class.getResource("/sonidos/click.wav").toString());
    
    /**
     * Constructor de la clase
     * @param name - Nombre del menu
     */
    public MenuItem(String name){
        gradiente = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, 
                new Stop[] { 
                            new Stop(0, Color.DARKBLUE),
                            new Stop(0.1, Color.BLACK),
                            new Stop(0.9, Color.BLACK),
                            new Stop(1, Color.DARKBLUE)

                            });
        
        rectangle = new Rectangle(200,30);
        rectangle.setOpacity(0.4);

        text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(Font.font(Const.TIMES_NEW_ROMAN, FontWeight.SEMI_BOLD, 30));
        setAlignment(Pos.CENTER);
        mouseEvt();
        super.getChildren().addAll(rectangle, text);
        
    }		
    
    private void mouseEvt(){
        setOnMouseEntered(evt -> {
                rectangle.setFill(gradiente);
                text.setFill(Color.WHITE);
                text.setFont(Font.font(Const.TIMES_NEW_ROMAN, FontWeight.SEMI_BOLD, 32));
                audio.play(0.1);
        });
			
        setOnMouseExited(evt -> {
                rectangle.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
                text.setFont(Font.font(Const.TIMES_NEW_ROMAN, FontWeight.SEMI_BOLD, 30));
        });
        
        setOnMousePressed(evt -> {
            rectangle.setFill(Color.DARKVIOLET);
            audio2.play(0.1);
        });

        setOnMouseReleased(evt -> rectangle.setFill(gradiente));
    }
}
