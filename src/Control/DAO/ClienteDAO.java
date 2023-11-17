
package Control.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Control.Conexion.*;

import Modelo.Usuario;

public class ClienteDAO {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public ClienteDAO() {
        con = null;
        st = null;
        rs = null;
    }
    
    //gestión de toda la consulta SQL de los datos almacenados y conexión del programa a la base de datos
    public boolean Login(Usuario usuario){
        
        String sql = "SELECT * FROM Clientes WHERE Nombre = '"+usuario.getNombre()+"' AND Contraseña = '"
                +usuario.getContraseña()+"'";
        
        try {
            
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                return true;
            }
            st.close();
            Conexion.desconectar();           
        } catch (SQLException ex) {
            
        }
        return false;
    }
    
    
    public boolean user(String name){
         String sql = "SELECT * FROM Clientes WHERE Nombre = '"+name+"'";
         try {
            
            con = Conexion.getConexion();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if(rs.next()){
                return true;
            }
            st.close();
            Conexion.desconectar();           
        } catch (SQLException ex) {
            
        }
        return false;
    }
    
}
