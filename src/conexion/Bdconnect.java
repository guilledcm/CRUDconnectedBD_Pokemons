/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Bdconnect {
    String bdUrl = "jdbc:sqlite:H://DAMGS2GuillermoDeCarlosMunoz//AccesoaDatos//CRUDconBD//BaseDeDatos//pokemonsDB";
    Connection con;
    
    public Bdconnect() {
        
    }

    public boolean conectar() {
    try {
        con = DriverManager.getConnection(bdUrl);
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos");
            return true;
        }
    } catch (SQLException ex) {
        System.out.println("Error al conectar a la base de datos: ");
        ex.printStackTrace(); 
        JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos, se creará una base nueva");
        crearBaseDeDatos();
    }
    return false;
}
    
   private void crearBaseDeDatos() {
    try {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Base de Datos");

        int select = fileChooser.showSaveDialog(null);

        if (select == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            con = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());

            Statement instruccion = con.createStatement();
            String crearTabla = "CREATE TABLE pokemon (" +
                    "numPokedex INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombrePokemon TEXT," +
                    "level INTEGER," +
                    "tipo TEXT)";
            instruccion.executeUpdate(crearTabla);

            System.out.println("Tabla 'pokemon' creada con éxito");

            instruccion.close();
            
        } else {
            System.out.println("Cancelado por el usuario");
        }
    } catch (SQLException ex) {
        System.err.println("Error al crear la base de datos: " + ex.getMessage());
        ex.printStackTrace();
    }
}

    public Connection getConexion() {
        return con;
    }

    public void desconectar() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Desconectado");
            }
        } catch (SQLException ex) {
            System.err.println("Error al desconectar: " + ex.getMessage());
        }
    }
}

