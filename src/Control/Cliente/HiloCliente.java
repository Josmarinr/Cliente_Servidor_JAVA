
package Control.Cliente;


import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Modelo.Usuario;
import Vista.Cliente.VistaCliente;


public class HiloCliente extends Thread implements ActionListener {

    private ObjectOutputStream salida;//creamos el canal de salida de datos del cliente
    private ObjectInputStream entrada;//creamos el canal de entrada de datos del cliente
    private Socket conexion;
    private String mensaje;//mensaje que se envia al servidor para que lo lea
    
    private Usuario cliente;//creamos el usuario a conectarse
    private VistaCliente ventana;//llamamos a la vista del cliente
    
    public HiloCliente(Socket conexion) {//constructor de los hilos del cliente
        
        ventana = new VistaCliente();
        mensaje = "HELLO";
        this.conexion = conexion;
        
        this.ventana.btnLeer.addActionListener(this);//añadimos las escuchas de los botones
        this.ventana.btnLimpiar.addActionListener(this);
        this.ventana.btnSalir.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent evento) {//manejamos los actionperformed de los botones
         if (evento.getSource() == this.ventana.btnLeer) {//si oprime el boton leer sucede:
             if(this.ventana.txtTexto.getText().length()==0){//revisa que el espacio del mensaje no este vacio
                 ventana.msg3();
             }else{
                 enviarDatos(this.ventana.txtTexto.getText());//envia el mensaje al servidor
             }
             
         }
         if (evento.getSource() == this.ventana.btnLimpiar) {//limpia el txt del mensaje
             this.ventana.txtTexto.setText("");
         }
         if (evento.getSource() == this.ventana.btnSalir) {//cierra el programa
             cerrarConexion();
             ventana.setVisible(false);
             System.exit(0);
         }
    }
    
    
    public void run(){//metodo de los thread
        ejecutarCliente();
    }
    
    public void ejecutarCliente() {
// configurar el cliente para que intente la conexion
        try {
            
            obtenerFlujos();  //crear los input y output streams
            ventana.setVisible(true);//muestra la ventana del cliente
            procesarConexion();//metodo del proceso de conexion
            
        } 
        catch (EOFException excepcionEOF) {
            ventana.error2();
        }
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        } finally {
            cerrarConexion(); 
        }
    }
    

    private void obtenerFlujos() throws IOException {
        salida = new ObjectOutputStream(conexion.getOutputStream());//creamos los datos de salida del cliente
        salida.flush();
        entrada = new ObjectInputStream(conexion.getInputStream());//creamos los datos de entrada del cliente
        
        
    }
    
    private void procesarConexion() throws IOException {

        do {

            try {
                mensaje = (String) entrada.readObject();//lee si hay alguna respuesta del servidor
            }
            catch (ClassNotFoundException excepcionClaseNoEncontrada) {
            }
        } while (!mensaje.equals("BYE"));//hace la exepcion de la palabra de salida del programa

    }
    
    private void cerrarConexion() {//cierra todas las conexiones de entrada y salida de datos

        try {
            salida.close();
            entrada.close();
        } catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }
    }
    
    private void enviarDatos(String mensaje) {//envia el mensaje escrito en el txt al servidor
        try {
            salida.writeObject(mensaje);
            salida.flush();
        }
        catch (IOException excepcionES) {

        }
    }
    

} 
