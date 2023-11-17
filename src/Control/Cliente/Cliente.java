
package Control.Cliente;



import Control.DAO.ClienteDAO;
import Modelo.Usuario;
import Vista.Cliente.VistaCliente;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Cliente {
    
    private String servidorChat;//direccion IP
    private Socket cliente;//creamos la variable del socket del cliente
    private ClienteDAO clienteDAO;//llamamos a nuestra clase de la logica SQL
    private Usuario usuario;//Creamos el usuario del modelo 
    private VistaCliente vista;//llamamos a la vista del cliente
    
    public Cliente(String host) {//constructor del cliente
        servidorChat = host; 
        clienteDAO = new ClienteDAO();
        vista = new VistaCliente();
        
    }
    
    public void iniciarCliente() {//metodo para iniciar el cliente con los metodos que tiene en el try
        try {
            conectarAServidor();//metodo para conectarse al server
        }
        catch (EOFException excepcionEOF) {//control de errores de la conexion
            vista.error1();
        } 
        catch (IOException excepcionES) {
            excepcionES.printStackTrace();
        }

    }
    public void login(){//metodo para validar los datos del ususario
        String nombre = vista.nombre();//llamamos a los valores ingresados en los JOPtionPane
        String pasw = vista.pasw();
        usuario = new Usuario(nombre, pasw);//instanciamos el usuario con esos datos
        boolean login = false;
        login = clienteDAO.Login(usuario);//llamamos a la consulta SQL con el usuario que se quiere conectar
        if(login == true){//usamos el booleano para saber si son validos o no los datos
            vista.msg1();
            iniciarCliente();//si son validos los datos se inicia la conexion del cliente al servidor
        }else{
            vista.msg2();
            System.exit(0);//si son invalidos los datos se cierra el programa
        }
        
        
    }
    private void conectarAServidor() throws IOException {//metodo para que el cliente se conecte al servidor
        cliente = new Socket(InetAddress.getByName(servidorChat), 12345);//le pasamos al socket la IP y el puerto por el que debe conectarse
        HiloCliente hiloCliente = new HiloCliente(cliente);//creamos el hilo del cliente para poder enviar varias peticiones
        new Thread(hiloCliente).start();//iniciamos el hilo del cliente
    }
}
    
