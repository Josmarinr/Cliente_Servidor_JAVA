
package Control.Servidor;

public class LauncherServidor {
    
     public static void main(String[] args) {
        String puerto = "12345";//le pasamos el puerto por el cual se va a conectar el cliente
        Server servidor = new Server(Integer.parseInt(puerto));
        servidor.iniciarServidor();//llamamos el metodo de iniciar servidor
    }
}
