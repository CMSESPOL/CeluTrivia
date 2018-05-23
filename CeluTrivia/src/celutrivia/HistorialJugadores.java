package celutrivia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//Declaracion de la clase

public class HistorialJugadores{
    
    
    //Declaracion de atributos
    private final Pane root;
    private final TableView<UsuarioHistory> table = new TableView();
    AudioClip audio2 = new AudioClip(HistorialJugadores.class.getResource("/sonidos/click.wav").toString());
    
    ObservableList<UsuarioHistory> hist;
    Set<UsuarioHistory> treeset = new HashSet<>();
    List<ArrayList<String>> datos ;
    ArrayList<ArrayList<String>> update = new ArrayList<>() ;
    ReadWriter arc = new ReadWriter();
    String nombreArchivo = "historial.txt";

     //Declaracion de contructores
    
    /**
     * Constructor HistorialJugadores
     * @param stage Almacena la ventana principal 
     */
    public HistorialJugadores(Stage stage){            
        root = new Pane();
        //css
        root.getStylesheets().add(HistorialJugadores.class.getResource("/celutrivia/Estilos.css").toExternalForm());
        
        BackgroundImage bi = new BackgroundImage(new Image(getClass().getResourceAsStream("back.jpg"), 
                Const.MAX_WIDTH, Const.MAX_HEIGHT, false, false), 
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);
        root.setBackground(new Background(bi));
        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("logo.png"))); 
//        root.getStylesheets().add(ProyectodePoo2Par.class.getResource(Constantes.CARPETA.toString()+"Estilos.css").toExternalForm());
        
        Button btn = new Button("Volver");
        btn.setId("botonHistorial");
        btn.setTranslateX(20);
        btn.setTranslateY(610);
        btn.setPrefSize(150, 20);
        
//        Button btn2 = new Button("Borrar");
//        btn2.setTranslateX(600);
//        btn2.setTranslateY(510);
//        btn2.setPrefSize(150, 20);
        
        Label titulo = new Label("Historial de");
        Label titulo2 = new Label("Jugadores");
        titulo.setId("tabla");
        titulo2.setId("tabla");
        titulo.setTranslateX(20);
        titulo.setTranslateY(400);
        titulo2.setTranslateX(20);
        titulo2.setTranslateY(462);
        
        getresultados();
        table.setTranslateX(850);
        table.setTranslateY(100);
//        root.getChildren().addAll(btn, btn2, titulo, titulo2, table);
        root.getChildren().addAll(logo, btn, titulo, titulo2, table);
        
        btn.setOnMouseClicked(e ->{
            audio2.play(0.1);
            guardar();
            new CeluTrivia().start(stage);
        });
//        btn2.setOnAction(e -> {
//            if(table.getSelectionModel().isEmpty()){
//                new MensajesAlerta().seleccionHistorial();
//            }else{
//                if(new MensajesAlerta().opcionEliminar())hist.remove(table.getSelectionModel().getSelectedItem());
//            }
//        });
    }
    
    //Declaracion de metodos 
    
     /**
     * Metodo getRoot: Permite obtener el root en donde se agregara el historial
     * @return root
     */
    public Pane getRoot(){
        return root;
    }
    
    /**
     * Metodo getresultados: Permite obtener los resultados de la tabla de datos 
     */
    private void getresultados(){
        datos = arc.leerArchivo(nombreArchivo);
 
        table.setEditable(true);
        table.setMaxSize(710, 800);
        
        TableColumn nomCol = new TableColumn(datos.get(0).get(0));
        nomCol.setMinWidth(140);
        TableColumn fechaCol = new TableColumn(datos.get(0).get(1));
        fechaCol.setMinWidth(140);
        TableColumn nivelCol = new TableColumn(datos.get(0).get(2));
        nivelCol.setMinWidth(140);
        table.getColumns().addAll(nomCol, fechaCol, nivelCol);
        
        
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        nivelCol.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        
        int n = 0;
        for(ArrayList<String> i: datos){
            if(n!=0){
                treeset.add(new UsuarioHistory(i.get(0), i.get(1), Integer.valueOf(i.get(2))));
            }            
            n+=1;
        }
        
        hist = FXCollections.observableArrayList(treeset);
        table.setItems(hist);

    }
    
    public void guardar(){
        update.add(datos.get(0));
        for(UsuarioHistory i: hist){
            ArrayList<String> listPalabras = new ArrayList<>();
            
            String linea = i.toString();
            //linea separada
            String[] separa = linea.split("[\\,]");
            listPalabras.addAll(Arrays.asList(separa));
            update.add(listPalabras);
        }
        
        arc.sobreEscribirArchivo(update, nombreArchivo);
    }
}
