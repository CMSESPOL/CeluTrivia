/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package celutrivia;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import static pantallajuego.Juego.val;


public class Tiempo implements Runnable {
    private Text tiempo;
    private StackPane reloj;
    private int segundos;
    private Timeline timeline;
     private boolean detenido;
    
    public Tiempo(int segundos) {
        this.segundos = segundos;
        tiempo = new Text(String.valueOf(segundos));
        tiempo.setFont(Font.loadFont(getClass().getResourceAsStream("DS-DIGIB.ttf"), 60));
        reloj = new StackPane();
        reloj.getChildren().add(tiempo);
        timeline = new Timeline();
        KeyValue kv = new KeyValue(tiempo.fillProperty(), Color.RED);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        timeline.getKeyFrames().add(kf);
        detenido = false;
    }
    
    @Override
    public void run() {
        while(segundos != 0 && val && !detenido) {
            try {
                Thread.sleep(1000);
                segundos--;
                tiempo.setText(String.valueOf(segundos));
                if(segundos <= 10) {
                    timeline.play();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public StackPane getReloj() {
        return reloj;
    }
    
    public void detener() {
        timeline.pause();
        detenido = true;
    }
    
    public int getTiempoRestante() {
        return segundos;
    }
}
