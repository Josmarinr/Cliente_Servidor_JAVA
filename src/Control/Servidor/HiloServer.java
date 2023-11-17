
package Control.Servidor;

import Vista.Servidor.*;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.*;
import com.sun.speech.freetts.VoiceManager;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.speech.*;



public class HiloServer extends Thread {//clase que extiende de thread 
    
    private ObjectOutputStream salida;//creamos los objetos que saldran del servidor
    private ObjectInputStream entrada;//creamos los objetos que entraran al servidor
    private Socket conexion;//recibimos el socket para poder crear el hilo
    private int contador = 1;//contador para saber cuantos clientes se conectan
    private String mensaje;//string para el textfield donde se escribe la palabra a leer

    VoiceManager voice;//variable para recibir las voces de la libreria importada
    Voice voz;//variable para que el bot hable
    
    private VistaServer vista;//llamado a la vista para los sout

    
    public HiloServer(Socket conexion) {//constructor del hilo del server
        vista = new VistaServer();
        this.conexion = conexion;
        mensaje = "HELLO";
        
    }
    
     
    public void run(){//metodo propio de los thread
        ejecutarServidor();//metodo que creamos para iniciar el servidor 
    }
     
    public void ejecutarServidor() {
        // configurar servidor para que reciba conexiones; procesar las conexiones
        try {

            while (true) {
                try {
                    obtenerFlujos();//método para crear los input y outputstreams     
                    procesarConexion();//metodo para dejar al servidor en espera del mensaje a leer por el bot
                } 
                catch (EOFException excepcionEOF) {
                    vista.error4();
                } finally {
                    cerrarConexion();  
                    ++contador;
                }

            } 

        } 
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
     }
    
    public void obtenerFlujos() throws IOException {//metodo que crea los input y output streams
         salida = new ObjectOutputStream(conexion.getOutputStream());
         salida.flush();//vacia el canal de comunicación
         entrada = new ObjectInputStream(conexion.getInputStream());
     }
    
    public void procesarConexion() throws IOException{
        salida.writeObject(mensaje);//escribe el mensaje que llega al servidor
        salida.flush();
        do {
            try {
                mensaje = (String) entrada.readObject();//lee el mensaje que llega al servidor
                System.out.println(mensaje);//imprime el mensaje
                hablar(mensaje);//metodo para que el bot lea el mensaje
            }catch (ClassNotFoundException excepcionClaseNoEncontrada) {//control de errores
                vista.error5();
            }
        }while (!mensaje.equals("BYE"));//requerimiento para mantener el programa mientras el mensaje no sea BYE
    }
    
    private void cerrarConexion() {
        vista.msg7();
        try {
            
            salida.close();//cerramos los canales de salida
            entrada.close();//cerramos los canales de entrada
            conexion.close();//cerramos todas las conexiones
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();//impresión de error respecto al mensaje de conexión
        }
    }
    
    public void hablar(String mensaje){//metodo necesario para cloudGarden
        System.setProperty("mbrola.base", "\\Librerias\\mbrola");
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance();//instanciar la voz del programa
        voz = voice.getVoice("kevin16");//llamar a la voz del bot kevin16
        voz.allocate();//le asigna el uso de la voz al programa
        voz.speak(mensaje);//el bot dice el mensaje
        voz.deallocate();//cierra la voz del programa
    }
   

    }
    
