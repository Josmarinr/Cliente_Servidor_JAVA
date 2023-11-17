
package Control.Servidor;

import Vista.Servidor.VistaServer;
import java.io.IOException;
import java.net.*;

public class Server {
    //creamos los sockets del servidor
    private ServerSocket sckEntradaServidor = null;
    private Socket sckSalidaServidor = null;
    //creamos las variables por donde se conectará el cliente al servidor
    private int puerto, contador;//creamos un contador para dar numero a cada cliente conectado
    
    private VistaServer vista;//llamamos a la vista para poder mostrar mensajes y errores
    
    Server(int puerto) { //contructores del servidor
        this.puerto = puerto;
        this.contador = 0;
        vista = new VistaServer();
    }
    
    public boolean iniciarServidor() {//booleano que nos ayuda a la creación del servidor y sus sockets
        if (!escucharPuerto()) {
            return false;
        }
        if (!procesarSolicitud()) {
            return false;
        }
        detenerServidor();
        return true;
    }

    private boolean escucharPuerto() { //método para crear el serversocket
        try {
            sckEntradaServidor = new ServerSocket(puerto);//al server socket se le pasa el puerto e conexión
        } catch (IOException exc) {
            vista.error1(puerto);
            return false;
        }
        vista.msg1(puerto);
        return true;
    }

    private boolean procesarSolicitud() { //booleano para retornar si el cliente se conectó o no. Además creamos los hilosd del server
        vista.msg2();
        try {
            while (true) {
                
                sckSalidaServidor = sckEntradaServidor.accept();
                vista.msg3(sckSalidaServidor.getInetAddress().getHostAddress());//obtenemmos la IP y el nombre del cliente que se conecta
                vista.msg4();
                HiloServer servidorHilo = new HiloServer(sckSalidaServidor);//le asignamos la creación de un hilo para cada cliente
                new Thread(servidorHilo).start();//iniciamos el hilo
                contador++;
                vista.msg5(contador);

            }
        } catch (IOException exc) {//obtención de errores
            vista.error2();
            detenerServidor();//si la conexión no puede realizarse se detiene el servidor
            return false;
        }
    }

    private boolean detenerServidor() { //booleano que nos sirve para saber cuando cerrar el servidor
        try {
            if (!sckSalidaServidor.isClosed()) {
                sckSalidaServidor.close();//si los puertos del servidor se cierran se acaba el servidor
            }
            if (!sckEntradaServidor.isClosed()) {
                sckEntradaServidor.close();
            }

        } catch (IOException exc) {
            vista.error3();
            return false;
        }
        vista.msg6();
        return true;
    }
}
