/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package celutrivia;

import java.time.LocalTime;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;


public class Const { 
    
    private Const (){
        //Constructor privado
    }
    public static final Rectangle2D VISUAL_BOUNDS = Screen.getPrimary().getVisualBounds();
    public static final double MAX_WIDTH = VISUAL_BOUNDS.getWidth();
    public static final double MAX_HEIGHT = VISUAL_BOUNDS.getHeight();
    public static final double MAX_X = VISUAL_BOUNDS.getMaxX();
    public static final double MAX_Y = VISUAL_BOUNDS.getMaxY();
    public static final double MIN_X = VISUAL_BOUNDS.getMinX();
    public static final double MIN_Y = VISUAL_BOUNDS.getMinY();
    public static final String FILL_RED = "-fx-text-fill:Red";
    public static final String FONT_BOLD = "-fx-font-weight:bold";
    public static final String TITULO = "GHOSTBUSTER";
    public static final String TIMES_NEW_ROMAN = "Times New Roman";
    public static final int DESPLAZAMIENTO_X = 5;
    public static final int DESPLAZAMIENTO_Y = 5;
    public static final long MIL_MILLISECONDS = 1000L;
    public static final long DOSMIL_MILLISECONDS = 2000L;
    public static final long TRESMIL_MILLISECONDS = 3000L;
    public static final int UNO = 1;
    public static final int DOS = 2;
    public static final int TRES = 3;
    public static final double VELOCIDAD_NORMAL = 0.20;
    public static final double VELOCIDAD_DOBLE = VELOCIDAD_NORMAL * 2;
    public static final int PUNTOS_GHOST_FUERTE = 5;
    public static final int PUNTOS_GHOST_DEBIL = 1;
    public static final LocalTime MIN_HOUR_DAY = LocalTime.of(6, 0);
    public static final LocalTime MAX_HOUR_DAY = LocalTime.of(17, 59);
    public static final LocalTime MIN_HOUR_NIGHT = LocalTime.of(18, 0);
    public static final LocalTime NEUTRAL_HOUR_PM = LocalTime.of(23, 59);
    public static final LocalTime NEUTRAL_HOUR_AM = LocalTime.of(0, 0);
    public static final LocalTime MAX_HOUR_NIGHT = LocalTime.of(5, 59);
    public static final int DS = 25;
    public static final String PAUSA = "PAUSA";
    public static final String FIN_JUEGO = "FIN DEL JUEGO";
}
