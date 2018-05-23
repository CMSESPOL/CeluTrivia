/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pantallajuego;

import celutrivia.CeluTrivia;
import celutrivia.Const;
import celutrivia.ReadWriter;
import celutrivia.Tiempo;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Juego {
    private final Pane pane;
    private List<Pregunta> varios;
    private List<Pregunta> espol;
    private List<Pregunta> microsoft;
    private  Tiempo tiempo;
    private Pregunta pregunta;
    private final Text puntaje;
    private int intpuntaje;
    private HBox vidas;
    private int intentos = 3;
    private int level;
    private Control control;
    public static boolean val  = true;
    int nivel = 0;
    int ronda = 3;
    Stage stage;
    private final String nombre;
    AudioClip audio3 = new AudioClip(CeluTrivia.class.getResource("/sonidos/click.wav").toString());
    AudioClip audio2 = new AudioClip(Juego.class.getResource("/sonidos/Die.mid").toString());
    AudioClip audio = new AudioClip(Juego.class.getResource("/sonidos/Bonus.mid").toString());
    
    
    public Juego(Stage stage,String nombre) {
        this.stage = stage;
        this.nombre = nombre;
        
        crearQuestionarios();
        pane = new Pane();
        fondo();
        vidas();
        addTiempo();
        puntaje = new Text("0");
        intpuntaje = 0;
        puntaje.setFont(Font.loadFont(getClass().getResourceAsStream("DS-DIGIB.ttf"), 60));
        puntaje.setTranslateX(670);
        puntaje.setTranslateY(80);
        pane.getChildren().add(puntaje);
        jugar();
    }
    
    //Control del tiempo
    public void iniciarControl() {
        control = new Control();
        Thread t = new Thread(control);
        t.start();
    }
    
    private void jugar() {
        List<String> lista = new ArrayList<>();
        iniciarControl();
        switch(nivel) {
            case 0:
                level = 50;
                mostrarPregunta(varios);
                break;
            case 1:
                level = 100;
                mostrarPregunta(espol);
                break;
            case 2:
                level = 200;
                mostrarPregunta(microsoft);
                break;
            case 3:
                lista.add(nombre);
                lista.add(getFecha());
                lista.add(puntaje.getText());
                control.detener();
                new ReadWriter().agregarAlArchivo(lista, "historial.txt");
                gano();
                break;    
            default:
                System.out.println(nivel);
                val = false;
                lista.add(nombre);
                lista.add(getFecha());
                lista.add(puntaje.getText());
                control.detener();
                new ReadWriter().agregarAlArchivo(lista, "historial.txt");
                gameOver();
            break;
        }
    }
    
    private void mostrarPregunta(List<Pregunta> preguntas) {

        SecureRandom rand = new SecureRandom();

        pregunta = preguntas.get(rand.nextInt(preguntas.size()));
        preguntas.remove(pregunta);
        pane.getChildren().add(pregunta);
        pregunta.setTranslateY(Const.MAX_Y/2.15);

        for(StackPane sp: pregunta.getOpciones()) {
            sp.setOnMouseClicked(e->{
                Text t = (Text)((HBox)sp.getChildren().get(1)).getChildren().get(1);
                if(t.getText().equals(pregunta.getRespuestaCorrecta().getRespuesta())){
                    System.out.println("Correcto"); 
                    puntuar();
                    audio.play(0.1);
                    pane.getChildren().remove(tiempo.getReloj());
                    pane.getChildren().remove(pregunta);
                    addTiempo();
                    siguiente();    
                }else {
                    intentos--;
                    pane.getChildren().remove(tiempo.getReloj());
                    pane.getChildren().remove(pregunta);
                    audio2.play(0.1);
                    vidas.getChildren().remove(0);
                    addTiempo();
                    siguiente();
                    System.out.println("Incorrecto");
                }
            });
         }
    }

    private void siguiente() {
        if(intentos == 0) {
            ronda = 0;
            nivel = 4;
        }
        control.detener();
        ronda--;
        if(ronda != 0) {    
            jugar();
        }else {
            nivel++;
            ronda = 3;
            jugar();
        }
    }
    
    private void puntuar() {
        tiempo.detener();
        switch(nivel) {
            case 0:
                intpuntaje += (level + tiempo.getTiempoRestante());
                break;
            case 1:
                intpuntaje += (level + tiempo.getTiempoRestante());
                break;
            case 2:
                intpuntaje += (level + tiempo.getTiempoRestante());
                break;
        }
        puntaje.setText(String.valueOf(intpuntaje));
    }
    
    private void crearQuestionarios() {
        varios = new ArrayList<>();
        espol = new ArrayList<>();
        microsoft = new ArrayList<>();
        ReadWriter rw = new ReadWriter();
        varios = rw.leerArchivoPreguntas("varios.txt");
        espol = rw.leerArchivoPreguntas("espol.txt");
        microsoft = rw.leerArchivoPreguntas("microsoft.txt");
    }
    
    private void addTiempo() {
        //Tiempo en pantalla
        tiempo = new Tiempo(25);
        Thread thread = new Thread(tiempo);
        thread.start();
        
        tiempo.getReloj().setTranslateX(Const.MAX_X - 200);
        tiempo.getReloj().setTranslateY(50);
        pane.getChildren().add(tiempo.getReloj());
    }
    
    private void vidas(){
        vidas = new HBox(1);
        for (int i = 0; i < 3; i++) {
            vidas.getChildren().add(new ImageView(new Image(getClass().getResourceAsStream("corazon.png"), 50, 50, true, false)));
        }
        vidas.setTranslateX(50);
        vidas.setTranslateY(50);
        pane.getChildren().add(vidas);
    }
    
    private void fondo() {
        BackgroundImage bi = new BackgroundImage(new Image(getClass().getResourceAsStream("fondo.png"), 
                Const.MAX_WIDTH, Const.MAX_HEIGHT, false, false), 
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
        pane.setBackground(new Background(bi));
    }
    
    public Pane getRoot() {
        return pane;
    }
    
    public void gameOver() {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Game Over!");
        mensaje.setHeaderText(null);
        mensaje.setGraphic(null);
        mensaje.initStyle(StageStyle.UNDECORATED);
        ImageView imFel = new ImageView(new Image(getClass().getResourceAsStream("GameOver.png")));
        mensaje.getDialogPane().setContent(imFel);

        DialogPane panel = mensaje.getDialogPane();
        panel.getStylesheets().add(Juego.class.getResource("/celutrivia/Estilos.css").toExternalForm());

        Optional<ButtonType> result = mensaje.showAndWait();
        if (result.isPresent()){
            audio3.play(0.1);
            //guardar los datos en el archivo
            val = false;
            new CeluTrivia().start(stage);
        } 
    }
    
    public void gano() {
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Victoria");
        mensaje.setHeaderText(null);
        mensaje.setGraphic(null);
        mensaje.initStyle(StageStyle.UNDECORATED);
        ImageView imFel = new ImageView(new Image(getClass().getResourceAsStream("win.png")));
        mensaje.getDialogPane().setContent(imFel);

        DialogPane panel = mensaje.getDialogPane();
        panel.getStylesheets().add(Juego.class.getResource("/celutrivia/Estilos.css").toExternalForm());

        Optional<ButtonType> result = mensaje.showAndWait();
        if (result.isPresent()){
            audio3.play(0.1);
            //guardar los datos en el archivo
            val = false;
            new CeluTrivia().start(stage);
        } 
    }
    
    private class Control implements Runnable{
        
        boolean correr = true;

        @Override
        public void run() {
            while(correr) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(tiempo.getTiempoRestante() <= 0){
                    Platform.runLater(()->{
                        tiempo.detener();
                        intentos--;
                        pane.getChildren().remove(tiempo.getReloj());
                        pane.getChildren().remove(pregunta);
                        vidas.getChildren().remove(0);
                        addTiempo();
                        siguiente();
                    });     
                }
            }
            
        }
        
        public void detener() {
            correr = false;
        }
    }
    
    public String getFecha() {
        //Instanciamos el objeto Calendar
        //en fecha obtenemos la fecha y hora del sistema
        Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día, 
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        String anio = String.valueOf(fecha.get(Calendar.YEAR));
        int mesPrueba = fecha.get(Calendar.MONTH)+1;
        String mesPruebaString = String.valueOf(mesPrueba);
        String mes = null;
        if (mesPrueba > 0 && mesPrueba < 10) {
            mes = "0" + mesPruebaString;
        } else {
            mes = mesPruebaString;
        }
        String dia = String.valueOf(fecha.get(Calendar.DAY_OF_MONTH));

        return dia + "/" + mes + "/" + anio;
    }
}
