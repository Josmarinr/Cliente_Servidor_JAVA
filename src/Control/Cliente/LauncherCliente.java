/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Cliente;


public class LauncherCliente {
    public static void main(String[] args) {
        String puerto = "12345";
        Cliente cliente = new Cliente("127.0.0.1");
        cliente.login();
    }
}
