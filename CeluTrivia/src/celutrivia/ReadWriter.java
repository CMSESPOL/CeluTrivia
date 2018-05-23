package celutrivia;

import com.sun.deploy.net.URLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import pantallajuego.Pregunta;
import pantallajuego.Respuesta;

//Declaracion de la clase
public class ReadWriter {
    
    //Declaracion de atributos
    
    /**
     * variable privada final: Almancena el archivo a usar 
     */
    private final File f = new File("");
    
    /**
     * variable privada final: Almancena la direccion absoluta del archivo
     */
    private final String dire = f.getAbsolutePath();
    
     /**
     * variable privada final: Almancena la posicion donde se encuentra
     * el nombre del prroyecto 
     */
    private final int indiceDeProyecto = dire.indexOf("CeluTrivia");
    
    //Declaracion de metodos 
    
     /**
     * Metodo leerArchivo: Encargado de leer el archivo que se desea haciendo uso
     * de la direccion, ademas se utliza el metodo try/catch para evitar algun
     * error debido al uso de archivos, en caso de que lo haya lo mostrara en un 
     * mensaje con el tipo de error
     * @param nombre almacena el nombre del usuario
     * @return lista de lineas del archivo leido
     */
    public List<ArrayList<String>> leerArchivo(String nombre){
        List<ArrayList<String>> listLineas = new ArrayList<>();

        String direccion = dire.substring(0,indiceDeProyecto+10)+"\\doc\\"+nombre;
        
        try (BufferedReader br = new BufferedReader(new FileReader(new File(direccion)));){
            String linea;            
            // Leemos linea a linea el fichero
            while ((linea = br.readLine()) != null) {       //linea leida
                ArrayList<String> listPalabras = new ArrayList<>();
                
                //linea separada
                String[] separa = linea.split("[\\,]");
                listPalabras.addAll(Arrays.asList(separa));
                listLineas.add(listPalabras);             //guardamos la linea
            }
        } catch (Exception ex) {
//            ex.getStackTrace();
//            String t = "El archivo de historial ha sido eliminado o cambiado de su ubicación"
//                    + ", se creará un nuevo documento pero no constará con los datos anteriores."
//                    + "\nPor favor reinicie el juego.";
//            new MensajesAlerta().mensajeDeError(ex, t);
//            crearArchivo(nombre);
        }
        return listLineas;
    }
    
    /**
     * Metodo crearArchivo: Encargado de crear el archivo solo con los titulos, además
     * se utliza el metodo try/catch para evitar algún error debido al uso de 
     * archivos, en caso de que lo haya lo mostrara en un mensaje con el tipo de error
     * @param nombre almacena el nombre del usuario
     */
    public void crearArchivo(String nombre){ //pedir el (Stage primaryStage)
	String direccion = dire.substring(0,indiceDeProyecto+10)+"\\doc\\"+nombre;
        
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(direccion,true))){
            // Escribimos linea a linea en el fichero
            String oracion="Nombre,Tiempo de juego,Monedas,Fecha,Nivel";
                bw.write(oracion);
                bw.newLine(); 
        } catch (IOException ex) {
            ex.getStackTrace();
//            new MensajesAlerta().mensajeDeError(ex, "");
        }
    }
    
    /**
     * Metodo AgregarAlArchivo: Encargado de agregar el archivo que se desea haciendo uso
     * de la direccion, ademas se utliza el metodo try/catch para evitar algun
     * error debido al uso de archivos, en caso de que lo haya lo mostrara en un 
     * mensaje con el tipo de error
     * @param nombre almacena el nombre del usuario
     * @param linea almancena las lineas del archivo leido
     */
    public void agregarAlArchivo(List<String> linea, String nombre){
        String direccion = dire.substring(0,indiceDeProyecto+10)+"\\doc\\"+nombre;
        
       
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(direccion,true))){
            int a = linea.size();
            int n=0;
            
            StringBuilder bld = new StringBuilder();
            for (String palabras : linea) {
                n++;
                if(n<a){
                    bld.append(palabras);
                    bld.append(",");
                }else{
                    bld.append(palabras);
                }
            }
            bw.write(bld.toString());
            bw.newLine();
        } catch (IOException ex) {
            ex.getStackTrace();
//            new MensajesAlerta().mensajeDeError(ex, "");
        }
    }
    
    /**
     * Metodo leerArchivo: Encargado de sobreescribir el archivo que se desea haciendo uso
     * de la direccion, ademas se utliza el metodo try/catch para evitar algun
     * error debido al uso de archivos, en caso de que lo haya lo mostrara en un 
     * mensaje con el tipo de error
     * @param nombre almacena el nombre del usuario
     * @param lineas almancena las lineas del archivo leido
     */
    public void sobreEscribirArchivo(List<ArrayList<String>> lineas, String nombre){
        String direccion = dire.substring(0,indiceDeProyecto+10)+"\\doc\\"+nombre;
        
	try(BufferedWriter bw = new BufferedWriter(new FileWriter(direccion))){
            
            // Escribimos linea a linea en el fichero
            for (ArrayList<String> linea : lineas) {
                StringBuilder bld = new StringBuilder();
                int a = linea.size();
                int n=0;
                for (String palabra : linea) {
                    n++;
                    if(n<a){
                        bld.append(palabra);
                        bld.append(",");
                    }else{
                        bld.append(palabra);
                    }
                }
                bw.write(bld.toString());
                bw.newLine();
            }
        } catch (IOException ex) {
            ex.getStackTrace();
//            new MensajesAlerta().mensajeDeError(ex, "");
        }
    }
    
    public List<Pregunta> leerArchivoPreguntas(String nombre) {
        String direccion = dire.substring(0,indiceDeProyecto+10)+"\\doc\\"+nombre;
        List<Pregunta> preguntas = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(direccion), "UTF8"))){
            String linea;
            while((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                Respuesta[] respuestas = new Respuesta[4];
                for(int i=1; i<datos.length; i++) {
                    boolean correcto;
                    if(datos[i].contains("*")){
                        correcto = true;
                        datos[i] = datos[i].substring(0, datos[i].length()-1);
                    }else{
                        correcto = false;
                    }
                    respuestas[i-1] = new Respuesta(datos[i], correcto);
                }
                Pregunta pregunta = new Pregunta(datos[0], respuestas);
                preguntas.add(pregunta);
            }
        }catch(IOException ex){
          ex.getStackTrace();  
        }
        return preguntas;
    }
}