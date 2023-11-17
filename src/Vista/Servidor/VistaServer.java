
package Vista.Servidor;


public class VistaServer {
    
    public VistaServer(){//metodos para controlar los errores del  programa
        
    }
    
    public void error1(int puerto){
        System.out.println("No fue posible abrir el puerto: " + puerto + "En el servidor...");
    }
    public void error2(){
        System.out.println("No fue posible establecer la conexion con el cliente...");
    }
    public void error3(){
        System.out.println("Error al cerrar puertos en el servidor...");
    }
    
    
    public void error4(){
        System.err.println("El servidor terminó la conexión");
    }
    public void error5(){
        System.out.println("Se recibió un tipo de objeto desconocido");
    }
            
    
    public void msg1(int puerto){
        System.out.println("Servidor activo y escuchando en el puerto: " + puerto);
    }
    public void msg2(){
        System.out.println("Esperando por conexion del cliente...");
    }
    public void msg3(String text){
        System.out.println("Cliente conectado: " + text);
    }
    public void msg4(){
        System.out.println("Iniciando un nuevo hilo para procesar la solicitud");
    }
     public void msg5(int num){
         System.out.println("Hilo de procesamiento para el cliente: " + num + " ha sido iniciado");
     }
     public void msg6(){
         System.out.println("La conexion ha sido cerrada...");
     }
     
     public void msg7(){
         System.out.println("Finalizando la conexión");
     }
}
