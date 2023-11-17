
package Modelo;

public class Cliente {
  
    private String mensaje;
    private String nombre;
    private String contraseña;
    private String host;
    
    public Cliente(String host, String nombre, String contraseña){
        this.host = host;
        mensaje = "";
        this.nombre = nombre;
        this.contraseña = contraseña;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
}
