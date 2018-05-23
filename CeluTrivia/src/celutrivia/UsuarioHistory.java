package celutrivia;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

//Declaracion de la clase
public class UsuarioHistory {
    
    //Declaracion de variables
    
    /**
     * variable private: Almancena el usuario 
     */ 
    private final SimpleStringProperty nombre;
    
     /**
     * variable private: Almancena la fecha en que se juega la partida   
     */ 
    private final SimpleStringProperty fecha;
    
     /**
     * variable private: Almancena el nivel del juego 
     */ 
    private final SimpleIntegerProperty nivel;
    
    //Declaracion de constructor
    
    /**
     * Constructor Usuario
     * @param nombre String
     * @param fecha String
     * @param nivel String
    */
    public UsuarioHistory(String nombre, String fecha, int nivel) {
        this.nombre = new SimpleStringProperty(nombre);
        this.fecha = new SimpleStringProperty(fecha);
        this.nivel = new SimpleIntegerProperty(nivel);
    }
    
     /**
     * Metodo getNombre: Permite obtener el nombre del usuario 
     * @return nombre
     */
    public String getNombre() {
        return nombre.get();
    }
    /**
     * Metodo getFecha: Permite obtener la fecha con formato 
     * @return fecha
     */
    public String getFecha() {
        return fecha.get();
    }

    /**
     * Metodo getNivel: Permite obtener el nivel en el que se encuentra el jugador 
     * @return nivel
     */
    public Integer getNivel() {
        return nivel.get();
    }

    /**
     * Metodo toString: Metodo sobreescrito que permite mostrar el historial del usuario
     * en el siguiente formato 
     * @return String 
     */
    @Override
    public String toString() {
        return getNombre()+","+getFecha()+","+getNivel();
    }    
}
